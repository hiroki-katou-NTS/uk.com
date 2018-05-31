package nts.uk.ctx.at.record.infra.repository.monthlyclosureupdatelog;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdatePersonLog;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdatePersonLogRepository;
import nts.uk.ctx.at.record.infra.entity.monthlyclosureupdatelog.KrcdtMcloseUpdPerLog;
import nts.uk.ctx.at.record.infra.entity.monthlyclosureupdatelog.KrcdtMcloseUpdPerLogPk;

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
		String sql = "SELECT c FROM KrcdtMcloseUpdPerLog c WHERE c.pk.monthlyClosureUpdateLogId = :logId";
		return this.queryProxy().query(sql, KrcdtMcloseUpdPerLog.class).setParameter("logId", monthlyClosureUpdateLogId)
				.getList(c -> c.toDomain());
	}

	@Override
	public void add(MonthlyClosureUpdatePersonLog domain) {
		this.commandProxy().insert(KrcdtMcloseUpdPerLog.fromDomain(domain));
	}

	@Override
	public void delete(String monthlyLogId, String empId) {
		Optional<KrcdtMcloseUpdPerLog> optEntity = this.queryProxy()
				.find(new KrcdtMcloseUpdPerLogPk(empId, monthlyLogId), KrcdtMcloseUpdPerLog.class);
		if (optEntity.isPresent())
			this.commandProxy().remove(optEntity.get());
	}

	@Override
	public Optional<MonthlyClosureUpdatePersonLog> getById(String monthlyClosureUpdateLogId, String employeeId) {
		Optional<KrcdtMcloseUpdPerLog> optEntity = this.queryProxy()
				.find(new KrcdtMcloseUpdPerLogPk(employeeId, monthlyClosureUpdateLogId), KrcdtMcloseUpdPerLog.class);
		if (optEntity.isPresent())
			return Optional.of(optEntity.get().toDomain());
		else
			return Optional.empty();
	}

}
