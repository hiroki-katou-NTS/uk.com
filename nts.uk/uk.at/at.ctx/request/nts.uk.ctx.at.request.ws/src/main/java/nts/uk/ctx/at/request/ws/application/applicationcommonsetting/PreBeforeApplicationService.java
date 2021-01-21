package nts.uk.ctx.at.request.ws.application.applicationcommonsetting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.common.AddAppEmploymentSetCommandHandler;
import nts.uk.ctx.at.request.app.command.application.common.AppEmploymentSetCommand;
import nts.uk.ctx.at.request.app.command.application.common.CopyAppEmploymentSetCommand;
import nts.uk.ctx.at.request.app.command.application.common.CopyAppEmploymentSetCommandHandler;
import nts.uk.ctx.at.request.app.command.application.common.DeleteAppEmploymentSetCommandHandler;
import nts.uk.ctx.at.request.app.command.application.common.UpdateAppEmploymentSetCommandHandler;
import nts.uk.ctx.at.request.app.find.application.common.AppEmploymentSetFinder;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppEmploymentSettingDto;
import nts.uk.ctx.at.request.app.find.setting.employment.appemploymentsetting.AppEmploymentSetDto;

@Path("at/request/application/common/setting")
@Produces("application/json")
public class PreBeforeApplicationService extends WebService{
	@Inject 
	private AppEmploymentSetFinder appEmploymentSetFinder;

	@Inject
	private AddAppEmploymentSetCommandHandler addHandler;

	@Inject
	private UpdateAppEmploymentSetCommandHandler updateHandler;

	@Inject
	private DeleteAppEmploymentSetCommandHandler deleteHandler;

	@Inject
	private CopyAppEmploymentSetCommandHandler copyHandler;

	@POST
	@Path("findEmploymentSetByCompanyId")
	public List<AppEmploymentSetDto> findEmploymentSetByCompanyId(){
		return appEmploymentSetFinder.findAllEmploymentSetting();
	}
	
	@POST
	@Path("findEmploymentSetByCode")
	public AppEmploymentSetDto findEmploymentSetByCode(String employmentCd){
		return appEmploymentSetFinder.findByEmployment(employmentCd);
	}
	
	@POST
	@Path("addEmploymentSetting")
	public void addEmploymentSetting(AppEmploymentSetCommand command){
		addHandler.handle(command);
	}
	@POST
	@Path("updateEmploymentSetting")
	public void updateEmploymentSetting(AppEmploymentSetCommand command){
		updateHandler.handle(command);
	}
	
	@POST
	@Path("deleteEmploymentSetting")
	public void deleteEmploymentSetting(AppEmploymentSetCommand command){
		deleteHandler.handle(command);
	}

	@POST
	@Path("copyEmploymentSetting")
	public void copyEmploymentSetting(CopyAppEmploymentSetCommand command){
		copyHandler.handle(command);
	}
}