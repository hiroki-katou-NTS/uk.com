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
import nts.uk.ctx.at.request.app.find.application.common.GetDataBeforePreBootMode;
import nts.uk.ctx.at.request.app.find.application.common.ObjApprovalRootInput;
import nts.uk.ctx.at.request.app.find.application.requestofearch.GetDataAppCfDetailFinder;
import nts.uk.ctx.at.request.app.find.application.requestofearch.InputMessageDeadline;
import nts.uk.ctx.at.request.app.find.application.requestofearch.OutputMessageDeadline;
import nts.uk.ctx.at.request.dom.application.common.Application;

@Path("at/request/application")
@Produces("application/json")
public class ApplicationWebservice extends WebService {
	
	@Inject
	private CreateApplicationCommandHandler createApp;
	
	@Inject
	private UpdateApplicationApproveHandler updateApp;
	
	@Inject
	private DeleteApplicationCommandHandler deleteApp;
	
	@Inject 
	private ApplicationFinder finderApp;
	
	@Inject 
	private GetDataBeforePreBootMode getDataBeforePreBootMode; 
	
	@Inject 
	private GetDataApprovalRootOfSubjectRequest getDataApprovalRoot;
	
	@Inject 
	private GetDataAppCfDetailFinder getDataAppCfDetailFinder;
	
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
	 * add new application
	 * @return
	 */
	@POST
	@Path("create")
	public void createApplication(CreateApplicationCommand command) {
		this.createApp.handle(command);
	}
	/**
	 * update  application
	 * @return
	 */
	@POST
	@Path("updatetoapprove")
	public void updateApplication(UpdateApplicationCommand command) {
		this.updateApp.handle(command);
	}
	
	/**
	 * delete  application
	 * @return
	 */
	@POST
	@Path("delete")
	public void deleteApplication( String applicationID) {
		DeleteApplicationCommand command = new DeleteApplicationCommand(applicationID);
		this.deleteApp.handle(command);
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
	
	/**
	 * get data  ApprovalRootOfSubjectRequest
	 * @return
	 */
	@POST
	@Path("getdataapprovalroot")
	public List<ApprovalRootOfSubjectRequestDto> getDataApprovalRoot(ObjApprovalRootInput objApprovalRootInput) {
		return this.getDataApprovalRoot.getApprovalRootOfSubjectRequest(objApprovalRootInput);
	}
	//List<ApprovalRootOutput>
}
