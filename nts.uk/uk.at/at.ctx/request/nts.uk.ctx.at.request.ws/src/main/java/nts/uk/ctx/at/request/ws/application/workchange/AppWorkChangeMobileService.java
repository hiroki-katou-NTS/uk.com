package nts.uk.ctx.at.request.ws.application.workchange;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommand;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommandCheck;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommandHandler;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeDetailParam;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeFinder;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeOutputDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeParam;
import nts.uk.ctx.at.request.app.find.application.workchange.ChangeWkTypeTimeDto;
import nts.uk.ctx.at.request.app.find.application.workchange.CheckWorkTimeParam;
import nts.uk.ctx.at.request.app.find.application.workchange.UpdateWorkChangeParam;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.AppWorkChangeDispInfoDto;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.WorkChangeCheckRegisterDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

@Path("at/request/application/workchange/mobile")
@Produces("application/json")
public class AppWorkChangeMobileService extends WebService{
	@Inject
	private AddAppWorkChangeCommandHandler addHandler;

	@Inject
	private AppWorkChangeFinder appWorkFinder;
	
	@POST
	@Path("startMobile")
	public AppWorkChangeOutputDto startMobile(AppWorkChangeParam appWorkChangeParam) {
		return appWorkFinder.getStartKAFS07(appWorkChangeParam);
	}
	
	@POST
	@Path("changeDateKAFS07")
	public AppWorkChangeDispInfoDto changeDateKAFS07(UpdateWorkChangeParam updateWorkChangeParam) {
		return appWorkFinder.getUpdateKAFS07(updateWorkChangeParam);
	}

	@POST
	@Path("checkBeforeRegister_New")
	public WorkChangeCheckRegisterDto checkBeforeRegisterNew(AddAppWorkChangeCommandCheck command) {
		return appWorkFinder.checkBeforeRegisterNew(command);
	}

	@POST
	@Path("addWorkChange_New")
	public ProcessResult addWorkChange_New(AddAppWorkChangeCommand command) {
		return addHandler.handle(command);
	}
	
	@POST
	@Path("startDetailMobile")
	public AppWorkChangeOutputDto startDetail(AppWorkChangeDetailParam appWorkChangeDetailParam) {
		
		return appWorkFinder.getDetailKAFS07(appWorkChangeDetailParam);
	}
	@POST
	@Path("checkWorkTime")
	public ChangeWkTypeTimeDto checkWorkTime(CheckWorkTimeParam checkWorkTimeParam) {
		
		return appWorkFinder.checkWorkTime(checkWorkTimeParam);
	}
}
