package nts.uk.ctx.at.record.infra.repository.monthlyclosureupdatelog;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLog;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLogRepository;
import nts.uk.ctx.at.record.infra.entity.monthlyclosureupdatelog.KrcdtMclosureUpdLog;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaMonthlyClosureUpdateLogRepository extends JpaRepository implements MonthlyClosureUpdateLogRepository {

	@Override
	public List<MonthlyClosureUpdateLog> getAll(String companyId, int closureId) {
		String sql = "SELECT c FROM KrcdtMclosureUpdLog c WHERE c.companyId = :companyId AND c.closureId = :closureId";
		return this.queryProxy().query(sql, KrcdtMclosureUpdLog.class).setParameter("companyId", companyId)
				.setParameter("closureId", closureId).getList(c -> c.toDomain());
	}

	@Override
	public void add(MonthlyClosureUpdateLog domain) {
		this.commandProxy().insert(KrcdtMclosureUpdLog.fromDomain(domain));
	}

	@Override
	public Optional<MonthlyClosureUpdateLog> getLogById(String id) {
		Optional<KrcdtMclosureUpdLog> opt = this.queryProxy().find(id, KrcdtMclosureUpdLog.class);
		if (opt.isPresent())
			return Optional.of(opt.get().toDomain());
		else
			return Optional.empty();
	}

}
