/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class GoOutTimeRoundingSettingDto.
 */
@Getter
@Setter
public class GoOutTimeRoundingSettingDto {

	/** The rounding method. */
	private Integer roundingMethod;
	
	/** The rounding setting. */
	private TimeRoundingSettingDto roundingSetting;

	/**
	 * Instantiates a new go out time rounding setting dto.
	 *
	 * @param roundingMethod the rounding method
	 * @param roundingSetting the rounding setting
	 */
	public GoOutTimeRoundingSettingDto(Integer roundingMethod, TimeRoundingSettingDto roundingSetting) {
		super();
		this.roundingMethod = roundingMethod;
		this.roundingSetting = roundingSetting;
	}
	
	
}
