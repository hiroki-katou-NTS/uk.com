/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class FlowWorkRestTimezoneDto.
 */
@Getter
@Setter
public class FlowWorkRestTimezoneDto {
	/** The fix rest time. */
	private boolean fixRestTime;
	
	/** The timezone. */
	private List<DeductionTimeDto> timezone;
	
	/** The flow rest timezone. */
	private FlowRestTimezoneDto flowRestTimezone;
	
}
