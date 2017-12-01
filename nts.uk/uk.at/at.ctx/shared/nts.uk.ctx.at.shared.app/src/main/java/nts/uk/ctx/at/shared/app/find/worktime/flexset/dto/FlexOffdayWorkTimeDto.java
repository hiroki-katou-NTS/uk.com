/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flexset.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FlowWorkRestTimezoneDto;

/**
 * The Class FlexOffdayWorkTimeDto.
 */
@Getter
@Setter
public class FlexOffdayWorkTimeDto {

	/** The work timezone. */
	private List<HolidayWorkTimeSheetSetDto> workTimezone;

	/** The rest timezone. */
	private FlowWorkRestTimezoneDto restTimezone;
}
