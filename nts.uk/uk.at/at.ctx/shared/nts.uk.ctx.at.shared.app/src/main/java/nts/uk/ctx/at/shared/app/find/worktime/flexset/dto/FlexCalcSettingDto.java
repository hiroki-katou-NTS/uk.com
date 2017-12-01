/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flexset.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class FlexCalcSettingDto.
 */
@Getter
@Setter
public class FlexCalcSettingDto {

	/** The remove from work time. */
	private Integer removeFromWorkTime;

	/** The calculate sharing. */
	private Integer calculateSharing;

	/**
	 * Instantiates a new flex calc setting dto.
	 *
	 * @param removeFromWorkTime the remove from work time
	 * @param calculateSharing the calculate sharing
	 */
	public FlexCalcSettingDto(Integer removeFromWorkTime, Integer calculateSharing) {
		super();
		this.removeFromWorkTime = removeFromWorkTime;
		this.calculateSharing = calculateSharing;
	}
	
	
}
