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

import nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly.setting.MonthlyPatternSettingBatchSaveCommand;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly.setting.MonthlyPatternSettingBatchSaveCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.WorkMonthlySettingFinder;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.WorkMonthlySettingDto;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.WorkMonthlySettingFindDto;

/**
 * The Class WorkMonthlySettingWs.
 */
@Path("ctx/at/schedule/pattern/work/monthy/setting")
@Produces(MediaType.APPLICATION_JSON)
public class WorkMonthlySettingWs {
	
	/** The finder. */
	@Inject
	private WorkMonthlySettingFinder finder;
	
	/** The batch. */
	@Inject
	private MonthlyPatternSettingBatchSaveCommandHandler batch;

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
	 * Sets the ting batch.
	 *
	 * @param command the new ting batch
	 */
	@POST
	@Path("batch")
	public void settingBatch(MonthlyPatternSettingBatchSaveCommand command){
		this.batch.handle(command);
	}
	
}
