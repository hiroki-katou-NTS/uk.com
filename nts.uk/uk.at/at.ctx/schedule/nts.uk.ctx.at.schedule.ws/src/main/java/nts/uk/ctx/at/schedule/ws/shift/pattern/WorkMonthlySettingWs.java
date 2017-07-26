/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.pattern;


import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.schedule.app.command.shift.pattern.MonthlyPatternSettingBatchSaveCommand;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.MonthlyPatternSettingBatchSaveCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.WorkMonthlySettingFinder;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.WorkMonthlySettingDto;

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
	 * Find by id.
	 *
	 * @param monthlyPatternCode the monthly pattern code
	 * @return the list
	 */
	@POST
	@Path("findById/{monthlyPatternCode}")
	public List<WorkMonthlySettingDto> findById(
			@PathParam("monthlyPatternCode") String monthlyPatternCode) {
		return this.finder.findById(monthlyPatternCode);
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
