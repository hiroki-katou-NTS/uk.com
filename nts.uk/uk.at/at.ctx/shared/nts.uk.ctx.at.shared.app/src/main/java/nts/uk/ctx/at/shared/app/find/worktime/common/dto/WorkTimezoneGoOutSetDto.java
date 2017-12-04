/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorkTimezoneGoOutSetDto.
 */
@Getter
@Setter
public class WorkTimezoneGoOutSetDto {
	
	/** The total rounding set. */
	private TotalRoundingSetDto totalRoundingSet;
	
	/** The diff timezone setting. */
	private GoOutTimezoneRoundingSetDto diffTimezoneSetting;

	/**
	 * Instantiates a new work timezone go out set dto.
	 *
	 * @param totalRoundingSet the total rounding set
	 * @param diffTimezoneSetting the diff timezone setting
	 */
	public WorkTimezoneGoOutSetDto(TotalRoundingSetDto totalRoundingSet,
			GoOutTimezoneRoundingSetDto diffTimezoneSetting) {
		super();
		this.totalRoundingSet = totalRoundingSet;
		this.diffTimezoneSetting = diffTimezoneSetting;
	}
	
	

}
