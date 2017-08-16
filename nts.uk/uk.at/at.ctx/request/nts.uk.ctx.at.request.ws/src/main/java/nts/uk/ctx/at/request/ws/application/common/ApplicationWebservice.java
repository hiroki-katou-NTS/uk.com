package nts.uk.ctx.at.request.ws.application.common;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.common.application.CreateApplicationCommand;
import nts.uk.ctx.at.request.app.command.common.application.CreateApplicationCommandHandler;
import nts.uk.ctx.at.request.app.command.common.application.DeleteApplicationCommand;
import nts.uk.ctx.at.request.app.command.common.application.DeleteApplicationCommandHandler;
import nts.uk.ctx.at.request.app.command.common.application.UpdateApplicationCommand;
import nts.uk.ctx.at.request.app.command.common.application.UpdateApplicationCommandHandler;
import nts.uk.ctx.at.request.app.find.common.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.common.application.ApplicationFinder;

@Path("request/application/common")
@Produces("application/json")
public class ApplicationWebservice extends WebService {
	
	@Inject
	private CreateApplicationCommandHandler createApp;
	
	@Inject
	private UpdateApplicationCommandHandler updateApp;
	
	@Inject
	private DeleteApplicationCommandHandler deleteApp;
	
	@Inject 
	private ApplicationFinder finderApp;
	
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
	public List<ApplicationDto> getAllAppByDate(@PathParam("applicationDate") BigDecimal applicationDate){
		return this.finderApp.getAllAppByDate(applicationDate);
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
	@Path("update")
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
}
