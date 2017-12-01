/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class InstantRoundingDto.
 */
@Getter
@Setter
public class InstantRoundingDto {
	
	/** The Font rear section. */
	private Integer FontRearSection;

	/** The rounding time unit. */
	private Integer roundingTimeUnit;

	/**
	 * Instantiates a new instant rounding dto.
	 *
	 * @param fontRearSection the font rear section
	 * @param roundingTimeUnit the rounding time unit
	 */
	public InstantRoundingDto(Integer fontRearSection, Integer roundingTimeUnit) {
		super();
		FontRearSection = fontRearSection;
		this.roundingTimeUnit = roundingTimeUnit;
	}
	
	

}
