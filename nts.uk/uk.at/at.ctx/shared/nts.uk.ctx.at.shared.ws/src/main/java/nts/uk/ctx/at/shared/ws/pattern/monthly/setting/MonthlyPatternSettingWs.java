/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.pattern.monthly.setting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.shared.app.command.pattern.monthly.setting.CopyMonthPatternSettingCommand;
import nts.uk.ctx.at.shared.app.command.pattern.monthly.setting.CopyMonthPatternSettingCommandHandler;
import nts.uk.ctx.at.shared.app.command.pattern.monthly.setting.MonthlyPatternSettingDeleteCommand;
import nts.uk.ctx.at.shared.app.command.pattern.monthly.setting.MonthlyPatternSettingDeleteCommandHandler;
import nts.uk.ctx.at.shared.app.command.pattern.monthly.setting.MonthlyPatternSettingSaveCommand;
import nts.uk.ctx.at.shared.app.command.pattern.monthly.setting.MonthlyPatternSettingSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.pattern.monthly.setting.HistoryDto;
import nts.uk.ctx.at.shared.app.find.pattern.monthly.setting.MonthlyPatternSettingDto;
import nts.uk.ctx.at.shared.app.find.pattern.monthly.setting.MonthlyPatternSettingFindAllDto;
import nts.uk.ctx.at.shared.app.find.pattern.monthly.setting.MonthlyPatternSettingFinder;

/**
 * The Class MonthlyPatternSettingWs.
 */
@Path("ctx/at/shared/pattern/monthly/setting")
@Produces(MediaType.APPLICATION_JSON)
public class MonthlyPatternSettingWs {
	
	/** The finder. */
	@Inject
	private MonthlyPatternSettingFinder finder;
	
	/** The add. */
	@Inject
	private MonthlyPatternSettingSaveCommandHandler save;
	
	@Inject
	private CopyMonthPatternSettingCommandHandler copy;
	
	/** The delete. */
	@Inject
	private  MonthlyPatternSettingDeleteCommandHandler delete;
	
	/**
	 * Find by id.
	 * @param input the input
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<MonthlyPatternSettingDto> findById(MonthlyPatternSettingFindAllDto input){
		return this.finder.findAllMonthlyPatternSettingBySid(input.getEmployeeIds(), input.getMonthlyPatternCodes());
	}
	
	/**
	 * Find by id.
	 *
	 * @param historyId the history id
	 * @return the monthly pattern setting dto
	 */
	@POST
	@Path("findById/{historyId}")
	public MonthlyPatternSettingDto findById(@PathParam("historyId") String historyId){
		return this.finder.findById(historyId);
	}
	
	/**
	 * Find by id.
	 *
	 * @param historyId the history id
	 * @return the monthly pattern setting dto
	 */
	@POST
	@Path("findBySId/{employeeId}")
	public MonthlyPatternSettingDto findBySId(@PathParam("employeeId") String employeeId){
		return this.finder.findBySId(employeeId);
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
	 * Copy the.
	 *
	 * @param command the command
	 */
	@POST
	@Path("copy")
	public void save(CopyMonthPatternSettingCommand command){
		this.copy.handle(command);
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
	
	/**
	 * Gets the list working condition.
	 *
	 * @param employeeId the employee id
	 * @return the list working condition
	 */
	@POST
	@Path("getListHistory/{employeeId}")
	public List<HistoryDto> getListWorkingCondition(@PathParam("employeeId") String employeeId){
		return this.finder.findListWorkingConditionBySID(employeeId);
	}
}
