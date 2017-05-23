package nts.uk.ctx.pr.core.ws.vacation.setting.retentionyearly;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.vacation.setting.retentionyearly.command.RetentionYearlySaveCommand;
import nts.uk.ctx.pr.core.app.vacation.setting.retentionyearly.command.RetentionYearlySaveCommandHandler;
import nts.uk.ctx.pr.core.app.vacation.setting.retentionyearly.find.RetentionYearlyFinder;
import nts.uk.ctx.pr.core.app.vacation.setting.retentionyearly.find.dto.RetentionYearlyFindDto;
import nts.uk.shr.com.context.AppContexts;


@Path("ctx/pr/core/vacation/setting/retentionyearly")
@Produces("application/json")
public class RetentionYearlyWebService extends WebService {

	@Inject
	private RetentionYearlyFinder finder;
	
	@Inject
	private RetentionYearlySaveCommandHandler save;
	
	@POST
	@Path("find")
	public RetentionYearlyFindDto findById() {
		String companyId = AppContexts.user().companyCode();
		return this.finder.findById(companyId);
	}
	
	@POST
	@Path("save")
	public void save(RetentionYearlySaveCommand command) {
		this.save.handle(command);
	}
}
