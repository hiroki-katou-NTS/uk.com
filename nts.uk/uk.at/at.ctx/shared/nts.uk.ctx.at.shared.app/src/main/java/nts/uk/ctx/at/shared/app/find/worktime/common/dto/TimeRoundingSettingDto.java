/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class TimeRoundingSettingDto.
 */
@Getter
@Setter
public class TimeRoundingSettingDto{

	/** The unit. */
	private Integer roundingTime;
	
	/** The rounding. */
	private Integer rounding;

	/**
	 * Instantiates a new time rounding setting dto.
	 */
	public TimeRoundingSettingDto() {
		super();
	}
	
	/**
	 * Instantiates a new time rounding setting dto.
	 *
	 * @param roundingTime the rounding time
	 * @param rounding the rounding
	 */
	public TimeRoundingSettingDto(Integer roundingTime, Integer rounding) {
		super();
		this.roundingTime = roundingTime;
		this.rounding = rounding;
	}
	
}
