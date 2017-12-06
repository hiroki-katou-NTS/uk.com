package nts.uk.ctx.at.schedule.ws.plannedyearholiday.frame;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.plannedyearholiday.frame.PlanYearHdFrameSaveCommand;
import nts.uk.ctx.at.schedule.app.command.plannedyearholiday.frame.PlanYearHdFrameSaveCommandHandler;
import nts.uk.ctx.at.schedule.app.find.plannedyearholiday.frame.PlanYearHdFrameFindDto;
import nts.uk.ctx.at.schedule.app.find.plannedyearholiday.frame.PlanYearHdFrameFinder;

/**
 * The Class ManagementCategoryWs.
 */
@Path("at/schedule/planyearhdframe")
@Produces(MediaType.APPLICATION_JSON)
public class PlanYearHdFrameWs extends WebService {
	
	@Inject
	private PlanYearHdFrameFinder finder;
	
	@Inject
	private PlanYearHdFrameSaveCommandHandler saveHandler;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Path("findAll")
	@POST
	public List<PlanYearHdFrameFindDto> findAll() {
		return this.finder.findAll();
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@Path("save")
	@POST
	public void save(PlanYearHdFrameSaveCommand command) {
		this.saveHandler.handle(command);
	}
}
