package nts.uk.ctx.at.request.ws.application.common;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import lombok.Value;
import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationCommonCmd;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationApproveHandler;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationCancelHandler;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationDelete;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationDenyHandler;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationReleaseHandler;
import nts.uk.ctx.at.request.app.find.application.common.AppDataDateFinder;
import nts.uk.ctx.at.request.app.find.application.common.AppDateDataDto;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationFinder;
import nts.uk.ctx.at.request.app.find.application.common.ApprovalRootOfSubjectRequestDto;
import nts.uk.ctx.at.request.app.find.application.common.GetDataApprovalRootOfSubjectRequest;
import nts.uk.ctx.at.request.app.find.application.common.GetDataCheckDetail;
import nts.uk.ctx.at.request.app.find.application.common.CheckDisplayMessage;
import nts.uk.ctx.at.request.app.find.application.common.GetAllDataAppPhaseFrame;
import nts.uk.ctx.at.request.app.find.application.common.GetAllNameByAppID;
import nts.uk.ctx.at.request.app.find.application.common.ObjApprovalRootInput;
import nts.uk.ctx.at.request.app.find.application.common.OutputDetailCheckDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationMetaDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationPeriodDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.InputCommonData;
import nts.uk.ctx.at.request.app.find.application.requestofearch.GetDataAppCfDetailFinder;
import nts.uk.ctx.at.request.app.find.application.requestofearch.GetMessageReasonForRemand;
import nts.uk.ctx.at.request.app.find.application.requestofearch.OutputMessageDeadline;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InputGetDetailCheck;

@Path("at/request/application")
@Produces("application/json")
public class ApplicationWebservice extends WebService {
	@Inject 
	private ApplicationFinder finderApp;
	
	@Inject 
	private CheckDisplayMessage checkDisplayMessage; 
	
	@Inject 
	private GetDataApprovalRootOfSubjectRequest getDataApprovalRoot;
	
	@Inject 
	private GetDataAppCfDetailFinder getDataAppCfDetailFinder;
	
	@Inject
	private GetMessageReasonForRemand getMessageReasonForRemand;
	
	@Inject
	private GetAllDataAppPhaseFrame getAllDataAppPhaseFrame;
	
	@Inject
	private GetDataCheckDetail getDataCheckDetail; 
	
	@Inject
	private GetAllNameByAppID getAllNameByAppID;
	
	@Inject
	private UpdateApplicationApproveHandler approveApp;
	
	@Inject
	private UpdateApplicationDenyHandler denyApp;
	
	@Inject
	private UpdateApplicationReleaseHandler releaseApp;
	
	@Inject
	private UpdateApplicationCancelHandler cancelApp;
	

	@Inject
	private UpdateApplicationDelete deleteApp;
	
	@Inject
	private AppDataDateFinder appDataDateFinder;
	
	
	
	/**
	 * approve application
	 * @return
	 */
	@POST
	@Path("approveapp")
	public JavaTypeResult<String> approveApp(InputCommonData command){
		 return new JavaTypeResult<String>(this.approveApp.handle(command));
	}
	
	/**
	 * deny application
	 * @return
	 */
	@POST
	@Path("denyapp")
	public JavaTypeResult<String> denyApp(InputCommonData command){
		return new JavaTypeResult<String>(this.denyApp.handle(command));
	}
	
	/**
	 * release application
	 * @return
	 */
	@POST
	@Path("releaseapp")
	public void releaseApp(InputCommonData command){
		 this.releaseApp.handle(command);
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
	 * delete application
	 * @return
	 */
	@POST
	@Path("deleteapp")
	public JavaTypeResult<String> deleteApp(UpdateApplicationCommonCmd command){
		 return new JavaTypeResult<String>(this.deleteApp.handle(command));
	}
	
	/**
	 * get All application
	 * @return
	 */
	@POST
	@Path("getall")
	public List<ApplicationDto> getAllApplication(){
		return this.finderApp.getAllApplication();
	}
	/**
	 * get All application by date
	 * @return
	 */
	@POST
	@Path("getallbydate/{applicationDate}")
	public List<ApplicationDto> getAllAppByDate(@PathParam("applicationDate") String applicationDate){
		 GeneralDate generalDate = GeneralDate.fromString(applicationDate, "YYYY/MM/DD");
		return this.finderApp.getAllAppByDate(generalDate);
	}
	
	/**
	 * get All application by type
	 * @return
	 */
	@POST
	@Path("getallbyapptype/{applicationType}")
	public List<ApplicationDto> getAllAppByAppType(@PathParam("applicationType") int applicationType){
		return this.finderApp.getAllAppByAppType(applicationType);
	}
	
	/**
	 * get application by code
	 * @return
	 */
	@POST
	@Path("getappbyid/{applicationID}")
	public ApplicationDto getAppById(@PathParam("applicationID") String applicationID){
		return this.finderApp.getAppById(applicationID);
	}
	
	
	/**
	 * check display reason
	 * @return
	 */
	@POST
	@Path("checkdisplayreason")
	public boolean checkDisplayReason( Application application,GeneralDate datebase) {
		return this.checkDisplayMessage.checkDisplayReasonApp(application, datebase);
	}
	
	/**
	 * check display reason
	 * @return
	 */
	@POST
	@Path("checkdisplayauthorizationcomment")
	public boolean checkAuthorizationComment( Application application,GeneralDate datebase) {
		return this.checkDisplayMessage.checkDisplayAuthorizationComment(application, datebase);
	}
	
	
	/**
	 * lấy message và deadline trên màn hình
	 * get message and deadline (getDataConfigDetail)
	 * @return
	 */
	@POST
	@Path("getmessagedeadline")
	public OutputMessageDeadline getDataConfigDetail(ApplicationMetaDto application) {
		return this.getDataAppCfDetailFinder.getDataConfigDetail(application);
	}
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
	 * get reason for remand
	 * @return
	 */
	@POST
	@Path("getreasonforremand/{applicationID}")
	public List<String> getReasonForRemand(@PathParam("applicationID") String applicationID) {
		return this.getMessageReasonForRemand.getMessageReasonForRemand(applicationID);
	}
	

	/**
	 * get add data by appID (info,list<phase>,fram
	 * @return
	 */
	@POST
	@Path("getalldatabyappid/{applicationID}")
	public ApplicationDto getAllDataAppPhaseFrame(@PathParam("applicationID") String applicationID){
		return this.getAllDataAppPhaseFrame.getAllDataAppPhaseFrame(applicationID);
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
	
	/**
	 * get getDetailedScreenPreBootMode (check)
	 * @return
	 */
	
	@POST
	@Path("getallnamebyappid")
	public List<String> getAllNameByAppID(){
		
		return this.getAllNameByAppID.getAllNameByAppID("000");
	}
	@POST
	@Path("getApplicationInfo")
	public List<ApplicationMetaDto> getAppInfo(ApplicationPeriodDto periodDate){
		return this.finderApp.getAppbyDate(periodDate);
	}
	
	@POST
	@Path("getAppDataByDate")
	public AppDateDataDto getAppDataByDate(AppDateParam param){
		return appDataDateFinder.getAppDataByDate(param.getAppTypeValue(), param.getAppDate(), param.getIsStartup(), param.getAppID());
	}
	
}

@Value
class AppDateParam {
	private Integer appTypeValue; 
	private String appDate;
	private Boolean isStartup;
	private String appID;
}
