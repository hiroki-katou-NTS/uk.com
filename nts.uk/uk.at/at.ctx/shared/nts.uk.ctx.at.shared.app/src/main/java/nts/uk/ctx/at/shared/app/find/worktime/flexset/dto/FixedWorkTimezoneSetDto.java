/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flexset.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.EmTimeZoneSetDto;

/**
 * The Class FixedWorkTimezoneSetDto.
 */
@Getter
@Setter
public class FixedWorkTimezoneSetDto {
	
	/** The working timezone. */
	private List<EmTimeZoneSetDto> workingTimezone;

	/** The OT timezone. */
	private List<OverTimeOfTimeZoneSetDto> OTTimezone;

	/**
	 * Instantiates a new fixed work timezone set dto.
	 *
	 * @param workingTimezone the working timezone
	 * @param oTTimezone the o T timezone
	 */
	public FixedWorkTimezoneSetDto(List<EmTimeZoneSetDto> workingTimezone, List<OverTimeOfTimeZoneSetDto> oTTimezone) {
		super();
		this.workingTimezone = workingTimezone;
		OTTimezone = oTTimezone;
	}
	
	

}
