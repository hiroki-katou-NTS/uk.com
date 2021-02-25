package nts.uk.ctx.at.shared.infra.repository.workrule.week;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagement;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagementRepo;
import nts.uk.ctx.at.shared.infra.entity.workrule.week.KsrmtWeekRuleMng;

@Stateless
public class JpaWeekRuleManagementRepoImpl extends JpaRepository implements WeekRuleManagementRepo {

	@Override
	public Optional<WeekRuleManagement> find(String cid) {
		
		return this.queryProxy().find(cid, KsrmtWeekRuleMng.class)
				.map(w -> w.toDomain());
	}

	@Override
	public void add(WeekRuleManagement domain) {
		
		this.commandProxy().insert(KsrmtWeekRuleMng.map(domain));
	}

	@Override
	public void update(WeekRuleManagement domain) {
		
		this.queryProxy().find(domain.getCid(), KsrmtWeekRuleMng.class)
			.ifPresent(w -> {
				w.startOfWeek = domain.getWeekStart().value;
				
				this.commandProxy().update(w);
			});
	}

}
