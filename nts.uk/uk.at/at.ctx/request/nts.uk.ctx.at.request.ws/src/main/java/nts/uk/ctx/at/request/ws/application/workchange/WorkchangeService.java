package nts.uk.ctx.at.request.ws.application.workchange;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/*import nts.arc.layer.app.command.JavaTypeResult;*/
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommand_Old;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommand;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommandCheck;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommandHandler;
import nts.uk.ctx.at.request.app.command.application.workchange.UpdateAppWorkChangeCommandHandler;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeCommonSetDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeCommonSetFinder;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeFinder;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeOutputDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeParam;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeRecordWorkInfoFinder;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeSetDto_Old;
import nts.uk.ctx.at.request.app.find.application.workchange.RecordWorkInfoDto;
import nts.uk.ctx.at.request.app.find.application.workchange.WorkChangeDetailFinder;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.AppWorkChangeDetailDto;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.AppWorkChangeDispInfoDto;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.WorkChangeCheckRegisterDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeService;

@Path("at/request/application/workchange")
@Produces("application/json")
public class WorkchangeService extends WebService {

	@Inject
	AppWorkChangeCommonSetFinder commonFinder;

	@Inject
	private AddAppWorkChangeCommandHandler addHandler;

	@Inject
	private UpdateAppWorkChangeCommandHandler updateHandler;

	@Inject
	WorkChangeDetailFinder detailFinder;

	@Inject
	AppWorkChangeRecordWorkInfoFinder workInfoFinder;

	@Inject
	private AppWorkChangeFinder appWorkFinder;

	/**
	 * 起動する アルゴリズム「勤務変更申請画面初期（新規）」を実行する
	 * 
	 * @return
	 */
	@POST
	@Path("getWorkChangeCommonSetting")
	public AppWorkChangeCommonSetDto getWorkChangeCommonSetting(WorkchangeSetParam workchangeSetParam) {
		return commonFinder.getWorkChangeCommonSetting(workchangeSetParam.getSIDs(), workchangeSetParam.getAppDate());
	}

	/**
	 * アルゴリズム「勤務変更申請登録」を実行する
	 */
	@POST
	@Path("addworkchange")
	public ProcessResult addWorkChange(AddAppWorkChangeCommand_Old command) {
//		return addHandler.handle(command);
		return null;
	}

	/**
	 * 
	 * @param appId
	 * @return
	 */
	@POST
	@Path("getWorkchangeByAppID/{appId}")
	public AppWorkChangeDetailDto getWorkchangeByAppID(@PathParam("appId") String appId) {
		return appWorkFinder.startDetailScreen(appId);
	}

	/**
	 * アルゴリズム「勤務変更申請登録（更新）」を実行する
	 */
	@POST
	@Path("updateworkchange")
	public ProcessResult updateWorkChange(AddAppWorkChangeCommand_Old command) {
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
	public AppWorkChangeDispInfoDto getStartNew(AppWorkChangeParam param) {
		return appWorkFinder.getStartNew(param);
	}
	
	@POST
	@Path("changeAppDate")
	public AppWorkChangeDispInfoDto changeAppDate(AppWorkChangeParam param) {
		return appWorkFinder.changeAppDate(param);
	}
	
	@POST
	@Path("changeWorkSelection")
	public AppWorkChangeDispInfoDto changeWorkSelection(AppWorkChangeParam param) {
		return appWorkFinder.changeWorkSelection(param);
	}
	
	@POST
	@Path("checkBeforeRegister")
	public WorkChangeCheckRegisterDto checkBeforeRegister(AddAppWorkChangeCommand_Old command) {
//		return appWorkFinder.checkBeforeRegister(command);
		return null;
	}
	
	@POST
	@Path("checkBeforeUpdate")
	public WorkChangeCheckRegisterDto checkBeforeUpdate(AddAppWorkChangeCommand_Old command) {
//		appWorkFinder.checkBeforeUpdate(command);
//		return new WorkChangeCheckRegisterDto(Collections.emptyList(), Collections.emptyList());
		return null;
	}
	
	@POST
	@Path("startMobile")
	public AppWorkChangeOutputDto startMobile() {
		List<GeneralDate> dates = new ArrayList<GeneralDate>();
		GeneralDate today = GeneralDate.today();
		dates.add(today);
		return appWorkFinder.getStartKAFS07(true, "000000000000-0117", "292ae91c-508c-4c6e-8fe8-3e72277dec16", dates);
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
	

}
