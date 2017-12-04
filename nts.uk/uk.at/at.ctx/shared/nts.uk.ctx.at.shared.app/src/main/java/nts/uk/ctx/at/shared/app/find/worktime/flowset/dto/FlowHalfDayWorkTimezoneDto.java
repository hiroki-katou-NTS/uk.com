/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flowset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FlowWorkRestTimezoneDto;

/**
 * The Class FlowHalfDayWorkTimezoneDto.
 */
@Getter
@Setter
public class FlowHalfDayWorkTimezoneDto {

	/** The rest timezone. */
	private FlowWorkRestTimezoneDto restTimezone;

	/** The work time zone. */
	private FlowWorkTimezoneSettingDto workTimeZone;
}
