package nts.uk.ctx.at.shared.ws.vacation.setting.retentionyearly;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.command.EmploymentSaveCommand;
import nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.command.EmploymentSaveCommandHandler;
import nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.find.EmploymentSettingFinder;
import nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.find.dto.EmploymentSettingFindDto;

@Path("ctx/at/shared/vacation/setting/employmentsetting/")
@Produces("application/json")
public class EmploymentSettingWebService extends WebService {
	
	@Inject
	private EmploymentSaveCommandHandler save;
	
	@Inject
	private EmploymentSettingFinder finder;
	
	@POST
	@Path("find/{empCode}")
	public EmploymentSettingFindDto find(@PathParam("empCode") String empCode) {
		return this.finder.find(empCode);
		
	}
	
	@POST
	@Path("save")
	public void save(EmploymentSaveCommand command) {
		this.save.handle(command);
	}

}
