package nts.uk.ctx.bs.employee.ws.holidaysetting.employment;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.command.holidaysetting.employment.EmploymentMonthDaySettingRemoveCommand;
import nts.uk.ctx.bs.employee.app.command.holidaysetting.employment.EmploymentMonthDaySettingRemoveCommandHandler;
import nts.uk.ctx.bs.employee.app.command.holidaysetting.employment.EmploymentMonthDaySettingSaveCommand;
import nts.uk.ctx.bs.employee.app.command.holidaysetting.employment.EmploymentMonthDaySettingSaveCommandHandler;
import nts.uk.ctx.bs.employee.app.find.holidaysetting.employment.EmploymentMonthDaySettingDto;
import nts.uk.ctx.bs.employee.app.find.holidaysetting.employment.EmploymentMonthDaySettingFinder;

/**
 * The Class EmploymentMonthDaySettingWebService.
 */
@Path("bs/employee/holidaysetting/employment")
@Produces(MediaType.APPLICATION_JSON)
public class EmploymentMonthDaySettingWebService extends WebService {
	/** The finder. */
	@Inject
	private EmploymentMonthDaySettingFinder finder;
	
	/** The save handler. */
	@Inject
	private EmploymentMonthDaySettingSaveCommandHandler saveHandler;
	
	/** The remove handler. */
	@Inject
	private EmploymentMonthDaySettingRemoveCommandHandler removeHandler;
	
	/**
	 * Find all.
	 *
	 * @param year the year
	 * @param empCd the emp cd
	 * @return the employment month day setting dto
	 */
	@Path("findEmploymentMonthDaySetting/{year}/{empCd}")
	@POST
	public EmploymentMonthDaySettingDto findAll(@PathParam("year") int year,@PathParam("empCd") String empCd){
		return this.finder.getEmploymentMonthDaySetting(empCd, year);
	}
	
	/**
	 * Save employment month day setting.
	 *
	 * @param command the command
	 */
	@Path("save")
	@POST
	public void saveEmploymentMonthDaySetting(EmploymentMonthDaySettingSaveCommand command){
		this.saveHandler.handle(command);
	}
	
	/**
	 * Removes the employment month day setting.
	 *
	 * @param command the command
	 */
	@Path("remove")
	@POST
	public void removeEmploymentMonthDaySetting(EmploymentMonthDaySettingRemoveCommand command){
		this.removeHandler.handle(command);
	}
}
