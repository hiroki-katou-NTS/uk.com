/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.ws.monthlyworkschedule;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.monthlyworkschedule.OutputItemMonthlyWorkScheduleCommand;
import nts.uk.ctx.at.function.app.command.monthlyworkschedule.OutputItemMonthlyWorkScheduleCopyCommand;
import nts.uk.ctx.at.function.app.command.monthlyworkschedule.OutputItemMonthlyWorkScheduleDeleteCommand;
import nts.uk.ctx.at.function.app.command.monthlyworkschedule.OutputItemMonthlyWorkScheduleDeleteHandler;
import nts.uk.ctx.at.function.app.command.monthlyworkschedule.OutputItemMonthlyWorkScheduleSaveHandler;
import nts.uk.ctx.at.function.app.find.annualworkschedule.PeriodDto;
import nts.uk.ctx.at.function.app.find.annualworkschedule.RoleWhetherLoginDto;
import nts.uk.ctx.at.function.app.find.annualworkschedule.RoleWhetherLoginFinder;
import nts.uk.ctx.at.function.app.find.monthlyworkschedule.MonthlyPerformanceDataReturnDto;
import nts.uk.ctx.at.function.app.find.monthlyworkschedule.MonthlyReturnItemDto;
import nts.uk.ctx.at.function.app.find.monthlyworkschedule.OutputItemMonthlyWorkScheduleDto;
import nts.uk.ctx.at.function.app.find.monthlyworkschedule.OutputItemMonthlyWorkScheduleFinder;

/**
 * The Class OutputItemMonthlyWorkScheduleWS.
 */
@Path("at/function/monthlyworkschedule")
@Produces(MediaType.APPLICATION_JSON)
public class OutputItemMonthlyWorkScheduleWS extends WebService {

	/** The output item monthly work schedule finder. */
	@Inject
	private OutputItemMonthlyWorkScheduleFinder outputItemMonthlyWorkScheduleFinder;
	
	/** The role whether login finder. */
	@Inject
	private RoleWhetherLoginFinder roleWhetherLoginFinder;

	/** The output item monthly work schedule save handler. */
	@Inject
	private OutputItemMonthlyWorkScheduleSaveHandler outputItemMonthlyWorkScheduleSaveHandler;

	/** The output item monthly work schedule delete handler. */
	@Inject
	private OutputItemMonthlyWorkScheduleDeleteHandler outputItemMonthlyWorkScheduleDeleteHandler;
	
	/**
	 * Gets the current loginer role.
	 *
	 * @return the current loginer role
	 */
	@Path("getCurrentLoginerRole")
	@POST
	public RoleWhetherLoginDto getCurrentLoginerRole() {
		return this.roleWhetherLoginFinder.getCurrentLoginerRole();
	}

	/**
	 * Find.
	 *
	 * @return the map
	 */
	@Path("find/{itemType}")
	@POST
	public Map<String, Object> find(@PathParam("itemType") int itemType) {
		return this.outputItemMonthlyWorkScheduleFinder.findBySelectionAndCidAndSid(itemType);
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Path("findall/{itemType}")
	@POST
	public List<OutputItemMonthlyWorkScheduleDto> findAll(@PathParam("itemType") int itemType) {
		return this.outputItemMonthlyWorkScheduleFinder.findAll(itemType);
	}

	/**
	 * Save.
	 *
	 * @param command
	 *            the command
	 */
	@Path("save")
	@POST
	public void save(OutputItemMonthlyWorkScheduleCommand command) {
		this.outputItemMonthlyWorkScheduleSaveHandler.handle(command);
	}

	/**
	 * Delete.
	 *
	 * @param command
	 *            the command
	 */
	@Path("delete")
	@POST
	public void delete(OutputItemMonthlyWorkScheduleDeleteCommand command) {
		this.outputItemMonthlyWorkScheduleDeleteHandler.handle(command);
	}

	/**
	 * Find copy.
	 *
	 * @return the list
	 */
	@Path("findCopy")
	@POST
	public MonthlyPerformanceDataReturnDto findCopy() {
		return this.outputItemMonthlyWorkScheduleFinder.getFormatMonthlyPerformance();
	}


	/**
	 * @param command
	 *            the command
	 * @return
	 */
	@Path("executeCopy")
	@POST
	public MonthlyReturnItemDto executeCopy(OutputItemMonthlyWorkScheduleCopyCommand copy) {
		return this.outputItemMonthlyWorkScheduleFinder.executeCopy(copy);
	}

	@Path("get/monthlyPeriod")
	@POST
	public PeriodDto getList() {
		return this.outputItemMonthlyWorkScheduleFinder.getPeriod();
	}

	@Path("get/freeSettingAuthority")
	@POST
	public FreeSettingAuthorityDto getFreeSettingAuthority() {
		return new FreeSettingAuthorityDto(this.outputItemMonthlyWorkScheduleFinder.checkAuthority());
		
	}
}
