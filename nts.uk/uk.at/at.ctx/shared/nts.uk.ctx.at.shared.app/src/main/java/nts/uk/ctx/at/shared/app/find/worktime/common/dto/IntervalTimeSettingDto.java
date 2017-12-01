/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class IntervalTimeSettingDto.
 */
@Getter
@Setter
public class IntervalTimeSettingDto {

	/** The use interval exemption time. */
	private boolean useIntervalExemptionTime;

	/** The interval exemption time round. */
	private TimeRoundingSettingDto intervalExemptionTimeRound;

	/** The interval time. */
	private IntervalTimeDto intervalTime;

	/** The use interval time. */
	private boolean useIntervalTime;
}
