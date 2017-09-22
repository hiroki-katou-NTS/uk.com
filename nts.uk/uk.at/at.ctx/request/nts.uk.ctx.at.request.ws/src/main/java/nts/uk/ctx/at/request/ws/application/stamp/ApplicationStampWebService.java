package nts.uk.ctx.at.request.ws.application.stamp;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.stamp.RegisterAppStampCommandHandler;
import nts.uk.ctx.at.request.app.command.application.stamp.UpdateAppStampCommandHandler;
import nts.uk.ctx.at.request.app.command.application.stamp.command.AppStampCmd;
import nts.uk.ctx.at.request.app.find.application.stamp.AppStampFinder;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Path("at/request/application/stamp")
@Produces("application/json")
public class ApplicationStampWebService extends WebService {
	
	@Inject
	private AppStampFinder appStampFinder;
	
	@Inject 
	private RegisterAppStampCommandHandler registerApplicationStampCommandHandler;
	
	@Inject
	private UpdateAppStampCommandHandler updateApplicationStampCommandHandler;
	
	@POST
	@Path("newAppStampInitiative")
	public void newAppStampInitiative(){
		this.appStampFinder.newAppStampPreProcess();
	}
	
	@POST
	@Path("insert")
	public void insert(AppStampCmd command){
		this.registerApplicationStampCommandHandler.handle(command);
	}
	
	@POST
	@Path("update")
	public void update(AppStampCmd command){
		this.updateApplicationStampCommandHandler.handle(command);
	}
	
}
