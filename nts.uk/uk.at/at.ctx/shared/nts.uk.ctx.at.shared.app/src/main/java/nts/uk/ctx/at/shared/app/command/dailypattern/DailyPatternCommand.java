/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.dailypattern;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.dailypattern.dto.DailyPatternDto;

/**
 * The Class PatternCalendarCommand.
 */

/**
 * Sets the pattern calendar dto.
 *
 * @param patternCalendarDto the new pattern calendar dto
 */
@Setter

/**
 * Gets the pattern calendar dto.
 *
 * @return the pattern calendar dto
 */
@Getter
public class DailyPatternCommand {
	   
   	/** The pattern calendar dto. */
    private DailyPatternDto patternCalendarDto;
}
