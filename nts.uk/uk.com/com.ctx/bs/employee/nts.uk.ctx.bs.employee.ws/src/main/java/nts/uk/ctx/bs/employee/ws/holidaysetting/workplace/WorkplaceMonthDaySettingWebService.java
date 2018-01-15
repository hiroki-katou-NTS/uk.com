package nts.uk.ctx.bs.employee.ws.holidaysetting.workplace;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.command.holidaysetting.workplace.WorkplaceMonthDaySettingRemoveCommand;
import nts.uk.ctx.bs.employee.app.command.holidaysetting.workplace.WorkplaceMonthDaySettingRemoveCommandHandler;
import nts.uk.ctx.bs.employee.app.command.holidaysetting.workplace.WorkplaceMonthDaySettingSaveCommand;
import nts.uk.ctx.bs.employee.app.command.holidaysetting.workplace.WorkplaceMonthDaySettingSaveCommandHandler;
import nts.uk.ctx.bs.employee.app.find.holidaysetting.workplace.WorkplaceMonthDaySettingDto;
import nts.uk.ctx.bs.employee.app.find.holidaysetting.workplace.WorkplaceMonthDaySettingFinder;

/**
 * The Class WorkplaceMonthDaySettingWebService.
 */
@Path("bs/employee/holidaysetting/workplace")
@Produces(MediaType.APPLICATION_JSON)
public class WorkplaceMonthDaySettingWebService extends WebService {
	/** The finder. */
	@Inject
	private WorkplaceMonthDaySettingFinder finder;
	
	/** The save handler. */
	@Inject
	private WorkplaceMonthDaySettingSaveCommandHandler saveHandler;
	
	/** The remove handler. */
	@Inject
	private WorkplaceMonthDaySettingRemoveCommandHandler removeHandler;
	
	/**
	 * Find all.
	 *
	 * @param year the year
	 * @param workplaceId the workplace id
	 * @return the workplace month day setting dto
	 */
	@Path("findWorkplaceMonthDaySetting/{year}/{workplaceId}")
	@POST
	public WorkplaceMonthDaySettingDto findAll(@PathParam("year") int year,@PathParam("workplaceId") String workplaceId){
		return this.finder.getWorkplaceMonthDaySetting(workplaceId, year);
	}
	
	/**
	 * Save workplace month day setting.
	 *
	 * @param command the command
	 */
	@Path("save")
	@POST
	public void saveWorkplaceMonthDaySetting(WorkplaceMonthDaySettingSaveCommand command){
		this.saveHandler.handle(command);
	}
	
	/**
	 * Removes the workplace month day setting.
	 *
	 * @param command the command
	 */
	@Path("remove")
	@POST
	public void removeWorkplaceMonthDaySetting(WorkplaceMonthDaySettingRemoveCommand command){
		this.removeHandler.handle(command);
	}
}
