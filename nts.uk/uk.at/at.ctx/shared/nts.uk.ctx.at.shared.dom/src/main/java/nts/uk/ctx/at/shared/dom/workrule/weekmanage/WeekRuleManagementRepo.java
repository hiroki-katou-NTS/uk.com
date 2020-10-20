package nts.uk.ctx.at.shared.dom.workrule.weekmanage;

import java.util.Optional;

public interface WeekRuleManagementRepo {

	public Optional<WeekRuleManagement> find(String cid);
	
	public void add(WeekRuleManagement domain);
	
	public void update(WeekRuleManagement domain);
}
