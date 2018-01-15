package nts.uk.ctx.bs.employee.ws.holidaysetting.company;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.command.holidaysetting.company.CompanyMonthDaySettingRemoveCommand;
import nts.uk.ctx.bs.employee.app.command.holidaysetting.company.CompanyMonthDaySettingRemoveCommandHandler;
import nts.uk.ctx.bs.employee.app.command.holidaysetting.company.CompanyMonthDaySettingSaveCommand;
import nts.uk.ctx.bs.employee.app.command.holidaysetting.company.CompanyMonthDaySettingSaveCommandHandler;
import nts.uk.ctx.bs.employee.app.find.holidaysetting.company.CompanyMonthDaySettingDto;
import nts.uk.ctx.bs.employee.app.find.holidaysetting.company.CompanyMonthDaySettingFinder;

/**
 * The Class CompanyMonthDaySettingWebService.
 */
@Path("bs/employee/holidaysetting/company")
@Produces(MediaType.APPLICATION_JSON)
public class CompanyMonthDaySettingWebService extends WebService {
	/** The finder. */
	@Inject
	private CompanyMonthDaySettingFinder finder;
	
	/** The save handler. */
	@Inject
	private CompanyMonthDaySettingSaveCommandHandler saveHandler;
	
	/** The remove handler. */
	@Inject
	private CompanyMonthDaySettingRemoveCommandHandler removeHandler;
	
	/**
	 * Find all.
	 *
	 * @param year the year
	 * @return the list
	 */
	@Path("findCompanyMonthDaySetting/{year}")
	@POST
	public CompanyMonthDaySettingDto findAll(@PathParam("year") int year){
		return this.finder.getCompanyMonthDaySetting(year);
	}
	
	/**
	 * Save company month day setting.
	 *
	 * @param command the command
	 */
	@Path("save")
	@POST
	public void saveCompanyMonthDaySetting(CompanyMonthDaySettingSaveCommand command){
		this.saveHandler.handle(command);
	}
	
	
	/**
	 * Removes the company month day setting.
	 *
	 * @param command the command
	 */
	@Path("remove")
	@POST
	public void removeCompanyMonthDaySetting(CompanyMonthDaySettingRemoveCommand command){
		this.removeHandler.handle(command);
	}
}
