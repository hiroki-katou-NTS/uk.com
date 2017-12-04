/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class IntervalTimeDto.
 */
@Getter
@Setter
public class IntervalTimeDto {

	/** The interval time. */
	private Integer intervalTime;
	
	/** The rounding. */
	private TimeRoundingSettingDto rounding;

	/**
	 * Instantiates a new interval time dto.
	 *
	 * @param intervalTime the interval time
	 * @param rounding the rounding
	 */
	public IntervalTimeDto(Integer intervalTime, TimeRoundingSettingDto rounding) {
		super();
		this.intervalTime = intervalTime;
		this.rounding = rounding;
	}
	
	
}
