/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeductGoOutRoundingSetDto {

	/** The deduct time rounding setting. */
	private GoOutTimeRoundingSettingDto deductTimeRoundingSetting;
	
	/** The appro time rounding setting. */
	private GoOutTimeRoundingSettingDto approTimeRoundingSetting;

	/**
	 * Instantiates a new deduct go out rounding set dto.
	 *
	 * @param deductTimeRoundingSetting the deduct time rounding setting
	 * @param approTimeRoundingSetting the appro time rounding setting
	 */
	public DeductGoOutRoundingSetDto(GoOutTimeRoundingSettingDto deductTimeRoundingSetting,
			GoOutTimeRoundingSettingDto approTimeRoundingSetting) {
		super();
		this.deductTimeRoundingSetting = deductTimeRoundingSetting;
		this.approTimeRoundingSetting = approTimeRoundingSetting;
	}
	
	
}
