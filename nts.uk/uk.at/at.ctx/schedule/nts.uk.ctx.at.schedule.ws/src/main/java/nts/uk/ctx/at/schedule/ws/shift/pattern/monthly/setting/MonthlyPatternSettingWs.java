/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.pattern.monthly.setting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly.setting.MonthlyPatternSettingDeleteCommand;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly.setting.MonthlyPatternSettingDeleteCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly.setting.MonthlyPatternSettingSaveCommand;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly.setting.MonthlyPatternSettingSaveCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.MonthlyPatternSettingFinder;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.MonthlyPatternSettingDto;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.MonthlyPatternSettingFindAllDto;

/**
 * The Class MonthlyPatternSettingWs.
 */
@Path("ctx/at/schedule/pattern/monthly/setting")
@Produces(MediaType.APPLICATION_JSON)
public class MonthlyPatternSettingWs {
	
	/** The finder. */
	@Inject
	private MonthlyPatternSettingFinder finder;
	
	/** The add. */
	@Inject
	private  MonthlyPatternSettingSaveCommandHandler save;
	
	/** The delete. */
	@Inject
	private  MonthlyPatternSettingDeleteCommandHandler delete;
	
	/**
	 * Find by id.
	 *
	 * @param employeeId the employee id
	 * @return the monthly pattern setting dto
	 */
	@POST
	@Path("findAll")
	public List<String> findAll(MonthlyPatternSettingFindAllDto input){
		return this.finder.findAllByEmployeeId(input.getEmployeeIds());
	}
	/**
	 * Find by id.
	 *
	 * @param employeeId the employee id
	 * @return the monthly pattern setting dto
	 */
	@POST
	@Path("findById/{employeeId}")
	public MonthlyPatternSettingDto findById(@PathParam("employeeId") String employeeId){
		return this.finder.findById(employeeId);
	}
	/**
	 * Adds the.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save( MonthlyPatternSettingSaveCommand command){
		this.save.handle(command);
	}
	
	/**
	 * Delete.
	 *
	 * @param command the command
	 */
	@POST
	@Path("delete")
	public void delete(MonthlyPatternSettingDeleteCommand command) {
		this.delete.handle(command);
	}

}
