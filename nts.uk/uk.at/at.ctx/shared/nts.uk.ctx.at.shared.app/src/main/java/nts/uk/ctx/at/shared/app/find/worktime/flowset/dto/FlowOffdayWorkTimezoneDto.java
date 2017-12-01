/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flowset.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FlowWorkRestTimezoneDto;

/**
 * The Class FlowOffdayWorkTimezoneDto.
 */
@Getter
@Setter
public class FlowOffdayWorkTimezoneDto {

	/** The rest time zone. */
	private FlowWorkRestTimezoneDto restTimeZone;

	/** The lst work timezone. */
	private List<FlowWorkHolidayTimeZoneDto> lstWorkTimezone;
}
