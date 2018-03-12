package nts.uk.ctx.at.schedule.ws.schedule.setting.displaycontrol;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.schedule.app.command.schedule.setting.displaycontrol.ScheDispControlCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.setting.displaycontrol.ScheDispControlCommandHandler;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.displaycontrol.ScheDispControlDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.displaycontrol.ScheDispControlFinder;

/**
 * 
 * @author tanlv
 *
 */

@Path("ctx/at/schedule/setting/displaycontrol")
@Produces("application/json")
public class ScheDisplayControlWebService {
	@Inject
	private ScheDispControlFinder scheDispControlFinder;
	
	@Inject
	private ScheDispControlCommandHandler scheDispControlCommandHandler;

	/**
	 * Find Schedule Display Control
	 * 
	 * @return
	 */
	@Path("getScheDispControl")
	@POST
	public ScheDispControlDto getScheDispControl() {
		return scheDispControlFinder.getScheDispControl();
	}
	
	/**
	 * Save Schedule Display Control
	 * 
	 * @param command
	 */
	@Path("saveScheDispControl")
	@POST
	public void saveScheDispControl(ScheDispControlCommand command) { 
		this.scheDispControlCommandHandler.handle(command);
	}
}
