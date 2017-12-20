package nts.uk.ctx.at.request.ws.application.stamp;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.stamp.RegisterAppStampCommandHandler;
import nts.uk.ctx.at.request.app.command.application.stamp.UpdateAppStampCommandHandler;
import nts.uk.ctx.at.request.app.command.application.stamp.command.AppStampCmd;
import nts.uk.ctx.at.request.app.find.application.stamp.AppStampFinder;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampNewPreDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.StampCombinationDto;
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
	@Path("findByID")
	public AppStampDto findByID(String appID){
		return this.appStampFinder.getAppStampByID(appID);
	}
	
	@POST
	@Path("newAppStampInitiative")
	public AppStampNewPreDto newAppStampInitiative(){
		return this.appStampFinder.newAppStampPreProcess();
	}
	
	@POST
	@Path("insert")
	public JavaTypeResult<String> insert(AppStampCmd command){
		return new JavaTypeResult<String>(this.registerApplicationStampCommandHandler.handle(command));
	}
	
	@POST
	@Path("update")
	public void update(AppStampCmd command){
		this.updateApplicationStampCommandHandler.handle(command);
	}
	
	@POST
	@Path("enum/stampCombination")
	public List<StampCombinationDto> getStampCombinationAtr(){
		return this.appStampFinder.getStampCombinationAtr();
	}
	
}
