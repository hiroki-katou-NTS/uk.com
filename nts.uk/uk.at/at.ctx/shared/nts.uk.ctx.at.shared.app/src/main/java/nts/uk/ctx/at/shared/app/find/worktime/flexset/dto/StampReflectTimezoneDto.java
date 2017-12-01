/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flexset.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class StampReflectTimezoneDto.
 */
@Getter
@Setter
public class StampReflectTimezoneDto {
	
	/** The work no. */
	private Integer workNo;

	/** The classification. */
	 private Integer classification;

	/** The end time. */
	private Integer endTime;

	/** The start time. */
	private Integer startTime;

}
