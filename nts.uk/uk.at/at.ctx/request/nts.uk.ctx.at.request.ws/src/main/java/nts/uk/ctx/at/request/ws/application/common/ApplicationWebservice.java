package nts.uk.ctx.at.request.ws.application.common;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.common.AppDetailBehaviorCmd;
import nts.uk.ctx.at.request.app.command.application.common.ReflectAplicationCommmandHandler;
import nts.uk.ctx.at.request.app.command.application.common.RemandApplicationHandler;
import nts.uk.ctx.at.request.app.command.application.common.ApproveAppHandler;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationCancelHandler;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationCommonCmd;
import nts.uk.ctx.at.request.app.command.application.common.DeleteAppHandler;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationDenyHandler;
import nts.uk.ctx.at.request.app.command.application.common.ReleaseAppHandler;
import nts.uk.ctx.at.request.app.command.setting.request.ApplicationDeadlineCommand;
import nts.uk.ctx.at.request.app.command.setting.request.UpdateApplicationDeadlineCommandHandler;
import nts.uk.ctx.at.request.app.find.application.common.AppDataDateFinder;
import nts.uk.ctx.at.request.app.find.application.common.AppDateDataDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationFinder;
import nts.uk.ctx.at.request.app.find.application.common.ApprovalRootOfSubjectRequestDto;
import nts.uk.ctx.at.request.app.find.application.common.DetailMobDto;
import nts.uk.ctx.at.request.app.find.application.common.GetDataApprovalRootOfSubjectRequest;
import nts.uk.ctx.at.request.app.find.application.common.GetDataCheckDetail;
import nts.uk.ctx.at.request.app.find.application.common.ObjApprovalRootInput;
import nts.uk.ctx.at.request.app.find.application.common.OutputDetailCheckDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppDateParamCommon;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationMetaDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationPeriodDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationSendDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ClosureParam;
import nts.uk.ctx.at.request.app.find.application.common.dto.InputCommonData;
import nts.uk.ctx.at.request.app.find.application.requestofearch.GetDataAppCfDetailFinder;
import nts.uk.ctx.at.request.app.find.setting.request.application.ApplicationDeadlineDto;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApplicationForRemandOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InputGetDetailCheck;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.RemandCommand;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.MailSenderResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ApproveProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.ScreenIdentifier;
import nts.uk.shr.infra.web.util.StartPageLogService;

@Path("at/request/application")
@Produces("application/json")
public class ApplicationWebservice extends WebService {
	
	
	@Inject 
	private GetDataApprovalRootOfSubjectRequest getDataApprovalRoot;
	
	@Inject 
	private GetDataAppCfDetailFinder getDataAppCfDetailFinder;
	
	@Inject
	private GetDataCheckDetail getDataCheckDetail; 
	
	@Inject
	private UpdateApplicationDenyHandler denyApp;
	
	@Inject
	private RemandApplicationHandler remandApplicationHandler;
	
	@Inject
	private UpdateApplicationCancelHandler cancelApp;
	

	@Inject
	private AppDataDateFinder appDataDateFinder;
	@Inject
	private UpdateApplicationDeadlineCommandHandler update;

	@Inject
	private ReflectAplicationCommmandHandler relect;
	@Inject
	private StartPageLogService writeLogSv;
	
	@Inject
	private DetailBeforeUpdate detailBeforeUpdate;
	
	// refactor 4
	
	@Inject 
	private ApplicationFinder finderApp;
	
	@Inject
	private DeleteAppHandler deleteApp;
	
	@Inject
	private ApproveAppHandler approveApp;
	
	@Inject
	private ReleaseAppHandler releaseApp;
	
	/**
	 * deny application
	 * @return
	 */
	@POST
	@Path("denyapp")
	public ProcessResult denyApp(InputCommonData command){
		return this.denyApp.handle(command);
	}
	
	/**
	 * remand application
	 * @return
	 */
	@POST
	@Path("remandapp")
	public MailSenderResult remandApp(RemandCommand command){
		return remandApplicationHandler.handle(command);
	}
	
	/**
	 * cancel application
	 * @return
	 */
	@POST
	@Path("cancelapp")
	public void cancelApp(UpdateApplicationCommonCmd command){
		 this.cancelApp.handle(command);
	}
	
	/**
	 * lấy message và deadline trên màn hình
	 * get message and deadline (getDataConfigDetail)
	 * @return
	 */
	/*@POST
	@Path("getmessagedeadline")
	public OutputMessageDeadline getDataConfigDetail(ApplicationMetaDto application) {
		return this.getDataAppCfDetailFinder.getDataConfigDetail(application);
	}*/
	//new InputMessageDeadline("000000000000-0005",null,1,null)
	
	/**
	 * get data  ApprovalRootOfSubjectRequest
	 * @return
	 */
	@POST
	@Path("getdataapprovalroot")
	public List<ApprovalRootOfSubjectRequestDto> getDataApprovalRoot(ObjApprovalRootInput objApprovalRootInput) {
		return this.getDataApprovalRoot.getApprovalRootOfSubjectRequest(objApprovalRootInput);
	}
	
	/**
	 * get getDetailedScreenPreBootMode (check)
	 * @return
	 */
	@POST
	@Path("getdetailcheck")
	public OutputDetailCheckDto getDetailCheck(InputGetDetailCheck inputGetDetailCheck){
		
		return this.getDataCheckDetail.getDataCheckDetail(inputGetDetailCheck);
	}
	
	@POST
	@Path("getApplicationInfo")
	public List<ApplicationMetaDto> getAppInfo(ApplicationPeriodDto periodDate){
		return this.finderApp.getAppbyDate(periodDate);
	}
	
	@POST
	@Path("getAppInfoByAppID")
	public ApplicationMetaDto getAppInfo(String appID){
		return this.finderApp.getAppByID(appID);
	}
	
	@POST
	@Path("getAppInfoByListAppID")
	public List<ApplicationMetaDto> getListAppInfo(List<String> listAppID){
		return this.finderApp.getListAppInfo(listAppID);
	}
	
	
	@POST
	@Path("getAppInfoForRemandByAppId")
	public ApplicationForRemandOutput getAppInfoByAppIdForRemand(List<String> appID){
		return this.finderApp.getAppByIdForRemand(appID);
	}
	@POST
	@Path("getApplicationForSendByAppID")
	public ApplicationSendDto getApplicationForSendByAppID(String appID){
		return finderApp.getAppByIdForSend(appID);
	}
	
	@POST
	@Path("getAppDataByDate")
	public AppDateDataDto getAppDataByDate(AppDateParamCommon param){
		int overtimeAtr = 2;
		String overtimeAtrParam = param.getOvertimeAtrParam();
		if(overtimeAtrParam!=null){
			if(overtimeAtrParam.equals("0")){
				overtimeAtr = 0;
			}
			if(overtimeAtrParam.equals("1")){
				overtimeAtr = 1;
			}
		}
		return appDataDateFinder.getAppDataByDate(
				param.getAppTypeValue(), 
				param.getAppDate(), 
				param.getIsStartup(), 
				param.getAppID(),
				param.getEmployeeID(),
				overtimeAtr);
	}
	
	@POST
	@Path("getDetailRealData")
	public AchievementOutput getDetailRealData(String appID){
		return finderApp.getDetailRealData(appID);
	}
	
	/**
	 * @author yennth
	 * @param closureId
	 * @return
	 */
	@POST
    @Path("getalldatabyclosureId")
    public List<ApplicationDeadlineDto> getDeadlineByClosureId(ClosureParam closureId){
        return this.getDataAppCfDetailFinder.findByClosureId(closureId.getClosureId());
    }
	/**
	 * update application deadline
	 * @param command
	 * @author yennth
	 */
	@POST
	@Path("update")
	public void update(List<ApplicationDeadlineCommand> command){
		this.update.handle(command);
	}
	
	@POST
	@Path("reflect-app")
	public void reflectApp(List<String> command){
		relect.handle(command);
	}
	@POST
	@Path("write-log")
	public void writeLog(ParamWriteLog paramLog){
		writeLogSv.writeLog(new ScreenIdentifier(paramLog.getProgramId(), paramLog.getScreenId(), paramLog.getQueryString()));
	}
	
	@POST
	@Path("getDetailMob")
	public DetailMobDto getDetailMob(String appID) {
		return finderApp.getDetailMob(appID);
	}
	
	@POST
	@Path("checkVersion")
	public void checkVersion(VersionCheckParam param) {
		String companyID = AppContexts.user().companyId();
		detailBeforeUpdate.exclusiveCheck(companyID, param.appID, param.version);
	}
	
	// refactor 4
	
	@POST
	@Path("getDetailPC/{appID}")
	public AppDispInfoStartupDto getDetailPC(@PathParam("appID") String appID) {
		return finderApp.getDetailPC(appID);
	}
	
	@POST
	@Path("deleteapp")
	public ProcessResult deleteApp(AppDispInfoStartupDto command){
		return deleteApp.handle(command);
	}
	
	@POST
	@Path("approveapp")
	public ApproveProcessResult approveApp(AppDetailBehaviorCmd command){
		return approveApp.handle(command);
	}
	
	@POST
	@Path("releaseapp")
	public ProcessResult releaseApp(AppDispInfoStartupDto command){
		return releaseApp.handle(command);
	}
}

