package nts.uk.ctx.at.schedule.ws.task.taskschedule;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.task.taskpalette.TaskPaletteCommand;
import nts.uk.ctx.at.schedule.app.command.task.taskschedule.TaskScheduleAllDaySaveCommandHandler;
import nts.uk.ctx.at.schedule.app.command.task.taskschedule.TaskScheduleCommand;
import nts.uk.ctx.at.schedule.app.command.task.taskschedule.TaskScheduleTimeZoneSaveCommandHandler;

/**
 * 
 * @author quytb
 *
 */

@Path("at/schedule/task/taskschedule")
@Produces(MediaType.APPLICATION_JSON)
public class TaskScheduleWebService extends WebService{
	
	@Inject
	private TaskScheduleAllDaySaveCommandHandler taskScheduleAllDaySaveCommandHandler;
	
	@Inject
	private TaskScheduleTimeZoneSaveCommandHandler taskScheduleTimeZoneSaveCommandHandler;
	
	@POST
	@Path("register")
	public void register(TaskScheduleCommand command) {
		if(command.getMode() == 0) {
			 this.taskScheduleAllDaySaveCommandHandler.handle(command);
		} else if(command.getMode() == 1) {
			this.taskScheduleTimeZoneSaveCommandHandler.handle(command);
		}		
	}
	
}
