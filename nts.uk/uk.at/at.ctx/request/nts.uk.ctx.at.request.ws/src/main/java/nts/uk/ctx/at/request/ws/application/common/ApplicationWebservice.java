package nts.uk.ctx.at.request.ws.application.common;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationApproveHandler;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationCancelHandler;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationCommand;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationDelete;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationDenyHandler;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationReleaseHandler;
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
import nts.uk.ctx.at.request.app.find.application.common.OutputGetAllDataApp;
import nts.uk.ctx.at.request.app.find.application.requestofearch.GetDataAppCfDetailFinder;
import nts.uk.ctx.at.request.app.find.application.requestofearch.GetMessageReasonForRemand;
import nts.uk.ctx.at.request.app.find.application.requestofearch.InputMessageDeadline;
import nts.uk.ctx.at.request.app.find.application.requestofearch.OutputMessageDeadline;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InputGetDetailCheck;

@Path("at/request/application")
@Produces("application/json")
public class ApplicationWebservice extends WebService {
	@Inject 
	private ApplicationFinder finderApp;
	
	@Inject 
	private CheckDisplayMessage getDataBeforePreBootMode; 
	
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
	
	
	
	/**
	 * approve application
	 * @return
	 */
	@POST
	@Path("approveapp/{applicationID}")
	public void approveApp(@PathParam("applicationID") String applicationID){
		 this.approveApp.approveApp(applicationID);
	}
	
	/**
	 * deny application
	 * @return
	 */
	@POST
	@Path("denyapp/{applicationID}")
	public void denyApp(@PathParam("applicationID") String applicationID){
		 this.denyApp.denyApp(applicationID);
	}
	
	/**
	 * release application
	 * @return
	 */
	@POST
	@Path("releaseapp/{applicationID}")
	public void releaseApp(@PathParam("applicationID") String applicationID){
		 this.releaseApp.releaseApp(applicationID);
	}
	
	/**
	 * cancel application
	 * @return
	 */
	@POST
	@Path("cancelapp/{applicationID}")
	public void cancelApp(@PathParam("applicationID") String applicationID){
		 this.cancelApp.cancelApp(applicationID);
	}
	
	/**
	 * cancel application
	 * @return
	 */
	@POST
	@Path("deleteapp/{applicationID}")
	public void deleteApp(@PathParam("applicationID") String applicationID){
		 this.deleteApp.deleteApp(applicationID);
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
	public Optional<ApplicationDto> getAppById(@PathParam("applicationID") String applicationID){
		return this.finderApp.getAppById(applicationID);
	}
	
	
	/**
	 * check display reason
	 * @return
	 */
	@POST
	@Path("checkdisplayreason")
	public boolean checkDisplayReason( Application application,GeneralDate datebase) {
		return this.getDataBeforePreBootMode.checkDisplayReasonApp(application, datebase);
	}
	
	/**
	 * check display reason
	 * @return
	 */
	@POST
	@Path("checkdisplayauthorizationcomment")
	public boolean checkAuthorizationComment( Application application,GeneralDate datebase) {
		return this.getDataBeforePreBootMode.checkDisplayAuthorizationComment(application, datebase);
	}
	
	
	/**
	 * lấy message và deadline trên màn hình
	 * get message and deadline (getDataConfigDetail)
	 * @return
	 */
	@POST
	@Path("getmessagedeadline")
	public OutputMessageDeadline getDataConfigDetail(InputMessageDeadline inputMessageDeadline) {
		return this.getDataAppCfDetailFinder.getDataConfigDetail(inputMessageDeadline);
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
	public OutputGetAllDataApp getAllDataAppPhaseFrame(@PathParam("applicationID") String applicationID){
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
}
