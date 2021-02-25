package nts.uk.ctx.at.schedule.ws.schedule.employeeinfo.sortsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.sortsetting.RegisterSortSettingCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.sortsetting.RegisterSortSettingCommandHandler;
import nts.uk.ctx.at.schedule.app.find.schedule.employeeinfo.sortsetting.GetSortSettingCompanyFinder;
import nts.uk.ctx.at.schedule.app.find.schedule.employeeinfo.sortsetting.SortSettingDto;

/**
 * @author hieult
 */
@Path("ctx/at/schedule/setting/employee/sortsetting")
@Produces(MediaType.APPLICATION_JSON)
public class SortSettingWs extends WebService {

	@Inject
	private GetSortSettingCompanyFinder finder;
	
	@Inject
	private RegisterSortSettingCommandHandler handler;
	
	@POST
	@Path("startPage")
	public SortSettingDto getData(){
		return finder.getSortSetting();
	}
	
	@POST
	@Path("save")
	public void save(RegisterSortSettingCommand command){
		this.handler.handle(command);
	}
	
	
}
