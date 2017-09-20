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
import nts.uk.ctx.at.request.app.command.application.common.CreateApplicationCommand;
import nts.uk.ctx.at.request.app.command.application.common.CreateApplicationCommandHandler;
import nts.uk.ctx.at.request.app.command.application.common.DeleteApplicationCommand;
import nts.uk.ctx.at.request.app.command.application.common.DeleteApplicationCommandHandler;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationCommand;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationApproveHandler;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationFinder;
import nts.uk.ctx.at.request.app.find.application.common.ApprovalRootOfSubjectRequestDto;
import nts.uk.ctx.at.request.app.find.application.common.GetDataApprovalRootOfSubjectRequest;
import nts.uk.ctx.at.request.app.find.application.common.CheckDisplayMessage;
import nts.uk.ctx.at.request.app.find.application.common.GetAllDataAppPhaseFrame;
import nts.uk.ctx.at.request.app.find.application.common.ObjApprovalRootInput;
import nts.uk.ctx.at.request.app.find.application.common.OutputGetAllDataApp;
import nts.uk.ctx.at.request.app.find.application.requestofearch.GetDataAppCfDetailFinder;
import nts.uk.ctx.at.request.app.find.application.requestofearch.GetMessageReasonForRemand;
import nts.uk.ctx.at.request.app.find.application.requestofearch.InputMessageDeadline;
import nts.uk.ctx.at.request.app.find.application.requestofearch.OutputMessageDeadline;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InputGetDetailCheck;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;

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
	private BeforePreBootMode beforePreBootMode; 
	
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
	public DetailedScreenPreBootModeOutput getDetailCheck(InputGetDetailCheck inputGetDetailCheck){ 
		
		return this.beforePreBootMode.getDetailedScreenPreBootMode(inputGetDetailCheck.getApplication(),inputGetDetailCheck.getBaseDate());
	}
}
