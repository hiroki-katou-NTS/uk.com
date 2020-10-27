package nts.uk.ctx.at.record.infra.repository.monthlyclosureupdatelog;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdatePersonLog;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdatePersonLogRepository;
import nts.uk.ctx.at.record.infra.entity.monthlyclosureupdatelog.KrcdtMcloseTarget;
import nts.uk.ctx.at.record.infra.entity.monthlyclosureupdatelog.KrcdtMcloseTargetPk;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaMonthlyClosureUpdatePersonLogRepository extends JpaRepository
		implements MonthlyClosureUpdatePersonLogRepository {

	@Override
	public List<MonthlyClosureUpdatePersonLog> getAll(String monthlyClosureUpdateLogId) {
		String sql = "SELECT c FROM KrcdtMcloseTarget c WHERE c.pk.monthlyClosureUpdateLogId = :logId";
		return this.queryProxy().query(sql, KrcdtMcloseTarget.class).setParameter("logId", monthlyClosureUpdateLogId)
				.getList(c -> c.toDomain());
	}

	@Override
	public void add(MonthlyClosureUpdatePersonLog domain) {
		this.commandProxy().insert(KrcdtMcloseTarget.fromDomain(domain));
	}

	@Override
	public void delete(String monthlyLogId, String empId) {
		Optional<KrcdtMcloseTarget> optEntity = this.queryProxy()
				.find(new KrcdtMcloseTargetPk(empId, monthlyLogId), KrcdtMcloseTarget.class);
		if (optEntity.isPresent())
			this.commandProxy().remove(optEntity.get());
	}

	@Override
	public Optional<MonthlyClosureUpdatePersonLog> getById(String monthlyClosureUpdateLogId, String employeeId) {
		Optional<KrcdtMcloseTarget> optEntity = this.queryProxy()
				.find(new KrcdtMcloseTargetPk(employeeId, monthlyClosureUpdateLogId), KrcdtMcloseTarget.class);
		if (optEntity.isPresent())
			return Optional.of(optEntity.get().toDomain());
		else
			return Optional.empty();
	}

}
