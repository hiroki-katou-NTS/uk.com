/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class RoundingSetDto.
 */
@Getter
@Setter
public class RoundingSetDto {
	

	/** The rounding set. */
	private InstantRoundingDto roundingSet;
	
	/** The section. */
	private Integer section;

	/**
	 * Instantiates a new rounding set dto.
	 *
	 * @param roundingSet the rounding set
	 * @param section the section
	 */
	public RoundingSetDto(InstantRoundingDto roundingSet, Integer section) {
		super();
		this.roundingSet = roundingSet;
		this.section = section;
	}

	
}
