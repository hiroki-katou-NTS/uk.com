/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.MonthlyPatternSettingBatchDto;

/**
 * The Class MonthlyPatternSettingBatchSaveCommand.
 */
@Getter
@Setter
public class MonthlyPatternSettingBatchSaveCommand {

	/** The settings. */
	private List<MonthlyPatternSettingBatchDto> settings;

	/** The overwrite. */
	private boolean overwrite;
	
	/** The start year month. */
	private int startYearMonth;
	
	/** The end year month. */
	private int endYearMonth;
}
