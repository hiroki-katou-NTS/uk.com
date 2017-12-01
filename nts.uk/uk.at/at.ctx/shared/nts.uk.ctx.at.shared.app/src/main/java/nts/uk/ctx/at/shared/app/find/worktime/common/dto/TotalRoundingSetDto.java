/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class TotalRoundingSetDto.
 */
@Getter
@Setter
public class TotalRoundingSetDto {
	
	/** The set same frame rounding. */
	private Integer setSameFrameRounding;
	
	/** The frame stradd rounding set. */
	private Integer frameStraddRoundingSet;

	/**
	 * Instantiates a new total rounding set dto.
	 *
	 * @param setSameFrameRounding the set same frame rounding
	 * @param frameStraddRoundingSet the frame stradd rounding set
	 */
	public TotalRoundingSetDto(Integer setSameFrameRounding, Integer frameStraddRoundingSet) {
		super();
		this.setSameFrameRounding = setSameFrameRounding;
		this.frameStraddRoundingSet = frameStraddRoundingSet;
	}
	
	

}
