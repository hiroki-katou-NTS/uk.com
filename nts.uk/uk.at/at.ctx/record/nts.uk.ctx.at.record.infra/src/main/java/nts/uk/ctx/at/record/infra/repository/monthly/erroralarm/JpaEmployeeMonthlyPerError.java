package nts.uk.ctx.at.record.infra.repository.monthly.erroralarm;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.erroralarm.EmployeeMonthlyPerError;
import nts.uk.ctx.at.record.dom.monthly.erroralarm.EmployeeMonthlyPerErrorRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.erroralarm.KrcdtEmployeeMonthlyPerError;
import nts.uk.ctx.at.record.infra.entity.monthly.erroralarm.KrcdtEmployeeMonthlyPerErrorPK;

@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless
public class JpaEmployeeMonthlyPerError extends JpaRepository implements EmployeeMonthlyPerErrorRepository {

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public void insertAll(EmployeeMonthlyPerError domain) {
	
		// キー
		val key = new KrcdtEmployeeMonthlyPerErrorPK(
				domain.getNo(),
				domain.getErrorType().value,
				domain.getYearMonth().v(),
				domain.getEmployeeID(),
				domain.getClosureId().value,
				domain.getClosureDate().getClosureDay().v(),
				domain.getClosureDate().getLastDayOfMonth() ? 1 : 0);
		
		// 登録・更新
		KrcdtEmployeeMonthlyPerError entity = this.getEntityManager().find(KrcdtEmployeeMonthlyPerError.class, key);
		if (entity == null){
			KrcdtEmployeeMonthlyPerError entity2 = new KrcdtEmployeeMonthlyPerError();
			this.getEntityManager().persist(entity2.convertToEntity(domain, false));
			this.getEntityManager().flush();
		}
		else {
			this.commandProxy().update(entity.convertToEntity(domain, true));
		}
	}
	
}
