/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flowset.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class FlowStampReflectTimezoneDto.
 */
@Getter
@Setter
public class FlowStampReflectTimezoneDto {

	/** The two times work reflect basic time. */
	private Integer twoTimesWorkReflectBasicTime;

	/** The stamp reflect timezone. */
	private List<StampReflectTimezoneDto> stampReflectTimezone;
}
