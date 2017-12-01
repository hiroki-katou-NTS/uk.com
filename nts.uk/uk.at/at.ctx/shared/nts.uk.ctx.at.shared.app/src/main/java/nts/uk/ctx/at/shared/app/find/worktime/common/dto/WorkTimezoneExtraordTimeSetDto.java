/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorkTimezoneExtraordTimeSetDto.
 */
@Getter
@Setter
public class WorkTimezoneExtraordTimeSetDto {
	
	/** The holiday frame set. */
	private HolidayFramsetDto holidayFrameSet;

	/** The time rounding set. */
	private TimeRoundingSettingDto timeRoundingSet;

	/** The OT frame set. */
	private ExtraordWorkOTFrameSetDto OTFrameSet;

	/** The calculate method. */
	private Integer calculateMethod;

}
