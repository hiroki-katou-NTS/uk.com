/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.holidaysetting.employee;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.holidaysetting.employee.EmployeeMonthDaySettingRemoveCommand;
import nts.uk.ctx.at.shared.app.command.holidaysetting.employee.EmployeeMonthDaySettingRemoveCommandHandler;
import nts.uk.ctx.at.shared.app.command.holidaysetting.employee.EmployeeMonthDaySettingSaveCommand;
import nts.uk.ctx.at.shared.app.command.holidaysetting.employee.EmployeeMonthDaySettingSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.holidaysetting.employee.EmployeeMonthDaySettingDto;
import nts.uk.ctx.at.shared.app.find.holidaysetting.employee.EmployeeMonthDaySettingFinder;

/**
 * The Class EmployeeMonthDaySettingWebService.
 */
@Path("at/shared/holidaysetting/employee")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeMonthDaySettingWebService extends WebService {
	/** The finder. */
	@Inject
	private EmployeeMonthDaySettingFinder finder;
	
	/** The save handler. */
	@Inject
	private EmployeeMonthDaySettingSaveCommandHandler saveHandler;
	
	/** The remove handler. */
	@Inject
	private EmployeeMonthDaySettingRemoveCommandHandler removeHandler;
	
	/**
	 * Find all.
	 *
	 * @param year the year
	 * @param sId the s id
	 * @return the employee month day setting dto
	 */
	@Path("findEmployeeMonthDaySetting/{year}/{sId}")
	@POST
	public EmployeeMonthDaySettingDto findAll(@PathParam("year") int year,@PathParam("sId") String sId){
		return this.finder.getEmployeeMonthDaySetting(sId, year);
	}
	
	
	/**
	 * Find all employee register.
	 *
	 * @return the list
	 */
	@Path("findEmployeeMonthDaySetting/findAllEmployeeRegister/{year}")
	@POST
	public List<String> findAllEmployeeRegister(@PathParam("year") int year){
		return this.finder.findAllEmployeeRegister(year);
	}
	
	/**
	 * Save employee month day setting.
	 *
	 * @param command the command
	 */
	@Path("save")
	@POST
	public void saveEmployeeMonthDaySetting(EmployeeMonthDaySettingSaveCommand command){
		this.saveHandler.handle(command);
	}
	
	/**
	 * Removes the employee month day setting.
	 *
	 * @param command the command
	 */
	@Path("remove")
	@POST
	public void removeEmployeeMonthDaySetting(EmployeeMonthDaySettingRemoveCommand command){
		this.removeHandler.handle(command);
	}
}
