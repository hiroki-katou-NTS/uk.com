package nts.uk.ctx.at.schedule.ws.schedule.setting.functioncontrol;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.schedule.setting.functioncontrol.ScheFuncControlCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.setting.functioncontrol.ScheFuncControlCommandHandler;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.functioncontrol.ScheFuncControlDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.functioncontrol.ScheFuncControlFinder;

/**
 * 
 * @author tanlv
 *
 */

@Path("ctx/at/schedule/setting/functioncontrol")
@Produces("application/json")
public class ScheFuncControlWebSevice extends WebService {
	@Inject
	private ScheFuncControlFinder scheFuncControlFinder;
	
	@Inject
	private ScheFuncControlCommandHandler scheFuncControlCommandHandler;

	/**
	 * Find Schedule Function Control
	 * 
	 * @return
	 */
	@Path("findScheFuncControl")
	@POST
	public ScheFuncControlDto findScheFuncControl() {
		return scheFuncControlFinder.getScheFuncControl();
	}
	
	/**
	 * Save Schedule Function Control
	 * 
	 * @param command
	 */
	@Path("saveScheFuncControl")
	@POST
	public void saveScheFuncControl(ScheFuncControlCommand command) { 
		this.scheFuncControlCommandHandler.handle(command);
	}
}
