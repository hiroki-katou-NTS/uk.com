package nts.uk.ctx.at.aggregation.ws.schedulecounter.employmentsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.aggregation.app.command.schedulecounter.employmentsetting.CriterionAmountUsageSettingCommand;
import nts.uk.ctx.at.aggregation.app.command.schedulecounter.employmentsetting.CriterionAmountUsageSettingCommandHandler;
import nts.uk.ctx.at.aggregation.app.find.schedulecounter.employmentsetting.CriterionAmountUsageSettingDto;
/**
 * KML002 L
 * @author hoangnd
 *
 */
import nts.uk.ctx.at.aggregation.app.find.schedulecounter.employmentsetting.EmploymentSettingsFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("ctx/at/schedule/budget/employmentsetting")
@Produces("application/json")
public class EmploymentSettingWebService extends WebService {
	
	@Inject
	private EmploymentSettingsFinder employmentSettingFinder;
	
	@Inject
	private CriterionAmountUsageSettingCommandHandler criterionAmountUsageSettingCommandHandler;
	
	@Path("init")
	@POST
	public CriterionAmountUsageSettingDto init() {
		
		return employmentSettingFinder.getSetting(AppContexts.user().companyId());
	}
	
	@Path("register")
	@POST
	public void register(CriterionAmountUsageSettingCommand command) {
		
		criterionAmountUsageSettingCommandHandler.handle(command);
	}
}
