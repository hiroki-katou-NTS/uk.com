package nts.uk.screen.at.ws.kdw.kdw003.g;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.command.kdw.kdw003.g.CopyHistCommandHandler;
import nts.uk.screen.at.app.command.kdw.kdw003.g.DeleteHistCommandHandler;
import nts.uk.screen.at.app.command.kdw.kdw003.g.HistCommand;
import nts.uk.screen.at.app.command.kdw.kdw003.g.HistCommandCopy;
import nts.uk.screen.at.app.command.kdw.kdw003.g.HistCommandUpdate;
import nts.uk.screen.at.app.command.kdw.kdw003.g.RegisterHistCommandHandler;
import nts.uk.screen.at.app.command.kdw.kdw003.g.UpdateHistCommandHandler;
import nts.uk.screen.at.app.query.kdw.kdw003.g.EmployeeInfoDto;
import nts.uk.screen.at.app.query.kdw.kdw003.g.GetTargetEmployeeInfoSreenQuery;
import nts.uk.screen.at.app.query.kdw.kdw003.g.GetTaskInitialSelSettingScreenQuery;
import nts.uk.screen.at.app.query.kdw.kdw003.g.GetTaskItemInfoScreenQuery;
import nts.uk.screen.at.app.query.kdw.kdw003.g.TaskInitialSelSettingDto;
import nts.uk.screen.at.app.query.kdw.kdw003.g.TaskItemDto;

/**
 * 
 * @author quytb
 *
 */

@Path("screen/at/task")
@Produces("application/json")
public class Kdw003gWebService extends WebService {
	@Inject
	GetTaskItemInfoScreenQuery getTaskItemInfoScreenQuery;
	
	@Inject
	GetTargetEmployeeInfoSreenQuery employeeInfoSreenQuery;
	
	@Inject
	GetTaskInitialSelSettingScreenQuery initialSelSettingScreenQuery;
	
	@Inject
	RegisterHistCommandHandler registerHistCommandHandler;
	
	@Inject
	UpdateHistCommandHandler updateHistCommandHandler;
	
	@Inject
	DeleteHistCommandHandler deleteHistCommandHandler;
	
	@Inject
	CopyHistCommandHandler copyHistCommandHandler;	 
	 
	@POST
	@Path("/getTaskItemInfo")
	public List<TaskItemDto> getTaskItem() {		
		return this.getTaskItemInfoScreenQuery.GetTaskItemInfo();
	}
	
	@POST
	@Path("/getTargetEmployeeInfo")
	public List<EmployeeInfoDto> getWorkPlaceId(Kdw003gRequest request) {
		return this.employeeInfoSreenQuery.get(request.getEmployeeIds(), request.toDate());
	}
	
	@POST
	@Path("/getTaskInitialSettingHist/{employeeId}")
	public TaskInitialSelSettingDto getTaskInitialHist(@PathParam("employeeId") String employeeId) {
		return this.initialSelSettingScreenQuery.getTaskInintialSelSetting(employeeId);
	}
	
	@POST
	@Path("/register")
	public void registerHist(HistCommand command) {
		this.registerHistCommandHandler.handle(command);
	}
	
	@POST
	@Path("/update")
	public void updateHist(HistCommandUpdate command) {
		this.updateHistCommandHandler.handle(command);
	}
	
	@POST
	@Path("/remove")
	public void removeHist(HistCommand command) {
		this.deleteHistCommandHandler.handle(command);
	}
	
	@POST
	@Path("/copy")
	public void removeHist(HistCommandCopy command) {
		this.copyHistCommandHandler.handle(command);
	}
}
