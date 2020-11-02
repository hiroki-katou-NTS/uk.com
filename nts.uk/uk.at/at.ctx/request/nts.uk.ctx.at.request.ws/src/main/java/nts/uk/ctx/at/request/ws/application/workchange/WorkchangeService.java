package nts.uk.ctx.at.request.ws.application.workchange;

/*import nts.arc.layer.app.command.JavaTypeResult;*/
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommand;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommandCheck;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommandHandler;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommandHandlerPC;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommandPC;
import nts.uk.ctx.at.request.app.command.application.workchange.UpdateAppWorkChangeCommandHandler;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeAppdateDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeCommonSetFinder;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeDetailParam;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeFinder;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeOutputDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeParamPC;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeRecordWorkInfoFinder;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeSetDto_Old;
import nts.uk.ctx.at.request.app.find.application.workchange.RecordWorkInfoDto;
import nts.uk.ctx.at.request.app.find.application.workchange.WorkChangeDetailFinder;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.AppWorkChangeDispInfoDto;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.AppWorkChangeDispInfoDto_Old;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.WorkChangeCheckRegisterDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

@Path("at/request/application/workchange")
@Produces("application/json")
public class WorkchangeService extends WebService {

	@Inject
	AppWorkChangeCommonSetFinder commonFinder;

	@Inject
	private AddAppWorkChangeCommandHandler addHandler;
	
	@Inject
    private AddAppWorkChangeCommandHandlerPC addHandlerPC;

	@Inject
	private UpdateAppWorkChangeCommandHandler updateHandler;

	@Inject
	WorkChangeDetailFinder detailFinder;

	@Inject
	AppWorkChangeRecordWorkInfoFinder workInfoFinder;

	@Inject
	private AppWorkChangeFinder appWorkFinder;

	/**
	 * アルゴリズム「勤務変更申請登録」を実行する
	 */
	@POST
	@Path("addworkchange_PC")
	public ProcessResult addWorkChange(AddAppWorkChangeCommand command) {
		return addHandler.handle(command);
	}
	
	/**
     * アルゴリズム「勤務変更申請登録」を実行する
     */
    @POST
    @Path("addworkchange")
    public ProcessResult addWorkChangePC(AddAppWorkChangeCommand command) {
        return addHandlerPC.handle(command);
    }

	/**
	 * 
	 * @param appId
	 * @return
	 */
	@POST
	@Path("getWorkchangeByAppID_PC")
	public AppWorkChangeOutputDto getWorkchangeByAppID(AppWorkChangeDetailParam appWorkChangeDetailParam) {
		return appWorkFinder.startDetailScreen(appWorkChangeDetailParam);
	}

	/**
	 * アルゴリズム「勤務変更申請登録（更新）」を実行する
	 */
	@POST
	@Path("updateworkchange")
	public ProcessResult updateWorkChange(AddAppWorkChangeCommandPC command) {
		return updateHandler.handle(command);
	}

	@POST
	@Path("getRecordWorkInfoByDate")
	public RecordWorkInfoDto getRecordWorkInfoByDate(WorkchangeEmpParam workchangeEmpParam) {
		return workInfoFinder.getRecordWorkInfor(workchangeEmpParam.getAppDate(), workchangeEmpParam.getEmployeeID());
	}

	@POST
	@Path("workChangeSet")
	public AppWorkChangeSetDto_Old getWorkChangeSet() {
		return appWorkFinder.findByCom();
	}

	@POST
	@Path("isTimeRequired")
	public boolean isTimeRequired(String workTypeCD) {
		return appWorkFinder.isTimeRequired(workTypeCD);
	}

	@POST
	@Path("startNew")
	public AppWorkChangeDispInfoDto getStartNew(AppWorkChangeParamPC param) {
		return appWorkFinder.getStartNew(param);
	}

	@POST
	@Path("changeAppDate")
	public AppWorkChangeDispInfoDto changeAppDate(AppWorkChangeAppdateDto param) {
		return appWorkFinder.changeAppDate(param);
	}

	@POST
	@Path("changeWorkSelection")
	public AppWorkChangeDispInfoDto_Old changeWorkSelection(AppWorkChangeParamPC param) {
		return appWorkFinder.changeWorkSelection(param);
	}

	@POST
	@Path("checkBeforeRegister")
	public WorkChangeCheckRegisterDto checkBeforeRegister(AddAppWorkChangeCommandCheck command) {
		 return appWorkFinder.checkBeforeRegisterNew(command);
	}
	
	@POST
    @Path("checkBeforeRegisterPC")
    public WorkChangeCheckRegisterDto checkBeforeRegisterPC(AddAppWorkChangeCommandCheck command) {
         return appWorkFinder.checkBeforeRegisterPC(command);
    }

	@POST
	@Path("checkBeforeUpdate")
	public WorkChangeCheckRegisterDto checkBeforeUpdate(AddAppWorkChangeCommandPC command) {
		// appWorkFinder.checkBeforeUpdate(command);
		// return new WorkChangeCheckRegisterDto(Collections.emptyList(),
		// Collections.emptyList());
		return null;
	}

	

}
