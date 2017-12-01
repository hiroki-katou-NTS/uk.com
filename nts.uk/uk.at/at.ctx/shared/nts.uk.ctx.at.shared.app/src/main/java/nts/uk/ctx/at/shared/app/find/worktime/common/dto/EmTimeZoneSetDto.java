/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.fixedset.dto.TimeZoneRoundingDto;

/**
 * The Class EmTimeZoneSetDto.
 */
@Getter
@Setter
public class EmTimeZoneSetDto {

	/** The Employment time frame no. */
	private Integer EmploymentTimeFrameNo;
	
	/** The timezone. */
	private TimeZoneRoundingDto timezone;
}
