/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class GoOutTimezoneRoundingSetDto.
 */
@Getter
@Setter
public class GoOutTimezoneRoundingSetDto {
	
	/** The pub hol work timezone. */
	private GoOutTypeRoundingSetDto pubHolWorkTimezone;
	
	/** The work timezone. */
	private GoOutTypeRoundingSetDto workTimezone;
	
	/** The OT timezone. */
	private GoOutTypeRoundingSetDto OTTimezone;

	/**
	 * Instantiates a new go out timezone rounding set dto.
	 *
	 * @param pubHolWorkTimezone the pub hol work timezone
	 * @param workTimezone the work timezone
	 * @param oTTimezone the o T timezone
	 */
	public GoOutTimezoneRoundingSetDto(GoOutTypeRoundingSetDto pubHolWorkTimezone, GoOutTypeRoundingSetDto workTimezone,
			GoOutTypeRoundingSetDto oTTimezone) {
		super();
		this.pubHolWorkTimezone = pubHolWorkTimezone;
		this.workTimezone = workTimezone;
		OTTimezone = oTTimezone;
	}

	
}
