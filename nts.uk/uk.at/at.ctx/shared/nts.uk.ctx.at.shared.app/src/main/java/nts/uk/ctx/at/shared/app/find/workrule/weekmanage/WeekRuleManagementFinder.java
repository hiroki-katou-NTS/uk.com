package nts.uk.ctx.at.shared.app.find.workrule.weekmanage;

import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagementRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class WeekRuleManagementFinder {
    @Inject
    private WeekRuleManagementRepo repo;

    public WeekRuleManagementDto find() {
        String companyId = AppContexts.user().companyId();
        return repo.find(companyId)
                .map(i -> new WeekRuleManagementDto(i.getDayOfWeek().value))
                .orElse(null);
    }
}
