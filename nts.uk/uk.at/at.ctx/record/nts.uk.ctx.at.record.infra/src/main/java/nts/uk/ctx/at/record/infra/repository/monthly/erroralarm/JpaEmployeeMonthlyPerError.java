package nts.uk.ctx.at.record.infra.repository.monthly.erroralarm;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.erroralarm.EmployeeMonthlyPerError;
import nts.uk.ctx.at.record.dom.monthly.erroralarm.EmployeeMonthlyPerErrorRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.erroralarm.KrcdtEmployeeMonthlyPerError;
import nts.uk.ctx.at.record.infra.entity.monthly.erroralarm.KrcdtEmployeeMonthlyPerErrorPK;

@Stateless
public class JpaEmployeeMonthlyPerError extends JpaRepository implements EmployeeMonthlyPerErrorRepository {

	@Override
	public void insertAll(EmployeeMonthlyPerError domain) {
	
		// キー
		val key = new KrcdtEmployeeMonthlyPerErrorPK(
				domain.getErrorType().value,
				domain.getYearMonth().v(),
				domain.getEmployeeID(),
				domain.getClosureId().value,
				domain.getClosureDate().getClosureDay().v(),
				(domain.getClosureDate().getLastDayOfMonth() ? 1 : 0));
		
		// 登録・更新
		KrcdtEmployeeMonthlyPerError entity = this.getEntityManager().find(KrcdtEmployeeMonthlyPerError.class, key);
		if (entity == null){
			entity = new KrcdtEmployeeMonthlyPerError();
			entity.convertToEntity(domain, Optional.of(key));
			this.getEntityManager().persist(entity);
		}
		else {
			this.commandProxy().update(entity.convertToEntity(domain, Optional.of(key)));
		}
	}
	
}
