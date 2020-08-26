/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.pattern.work;


import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly.MonthlyPatternRegisterCommand;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly.MonthlyPatternRegisterCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly.setting.MonthlyPatternSettingBatchSaveCommand;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly.setting.MonthlyPatternSettingBatchSaveCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.work.WorkMonthlySettingBatchSaveCommand;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.work.WorkMonthlySettingBatchSaveCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.WorkMonthlySettingFinder;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.WorkMonthlySettingDto;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.WorkMonthlySettingFindDto;

/**
 * The Class WorkMonthlySettingWs.
 */
@Path("ctx/at/schedule/pattern/work/monthly/setting")
@Produces(MediaType.APPLICATION_JSON)
public class WorkMonthlySettingWs {
	
	/** The finder. */
	@Inject
	private WorkMonthlySettingFinder finder;
	
	/** The batch. */
	@Inject
	private MonthlyPatternSettingBatchSaveCommandHandler batch;
	
	/** The save month. */
	@Inject
	private WorkMonthlySettingBatchSaveCommandHandler saveMonth;

	@Inject
	private MonthlyPatternRegisterCommandHandler register;

	/**
	 * Find by month.
	 *
	 * @param input the input
	 * @return the list
	 */
	@POST
	@Path("findByMonth")
	public List<WorkMonthlySettingDto> findByMonth(WorkMonthlySettingFindDto input) {
		return this.finder.findByMonth(input.getMonthlyPatternCode(), input.getYearMonth());
	}
	
	
	/**
	 * Gets the item of month.
	 *
	 * @param input the input
	 * @return the item of month
	 */
	@POST
	@Path("getItemOfMonth")
	public List<WorkMonthlySettingDto> getItemOfMonth(WorkMonthlySettingFindDto input) {
		return this.finder.getDefaultItemOfMonth(input.getYearMonth());
	}
	
	/**
	 * Sets the ting batch.
	 *
	 * @param command the new ting batch
	 */
	@POST
	@Path("batch")
	public void settingBatch(MonthlyPatternSettingBatchSaveCommand command){
		this.batch.handle(command);
	}
	
	/**
	 * Save month.
	 *
	 * @param command the command
	 */
	@POST
	@Path("saveMonth")
	public void saveMonth(WorkMonthlySettingBatchSaveCommand command){
		this.saveMonth.handle(command);
	}

	/**
	 * register (add or update)
	 * @param command
	 */
	@POST
	@Path("register")
	public void register(MonthlyPatternRegisterCommand command){
		this.register.handle(command);
	}
	
}
