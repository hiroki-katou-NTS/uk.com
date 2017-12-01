/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorkTimezoneStampSetDto.
 */
@Getter
@Setter
public class WorkTimezoneStampSetDto {
	
	/** The rounding set. */
	private List<RoundingSetDto> roundingSet;
	
	/** The priority set. */
	private List<PrioritySettingDto> prioritySet;

	/**
	 * Instantiates a new work timezone stamp set dto.
	 *
	 * @param roundingSet the rounding set
	 * @param prioritySet the priority set
	 */
	public WorkTimezoneStampSetDto(List<RoundingSetDto> roundingSet, List<PrioritySettingDto> prioritySet) {
		super();
		this.roundingSet = roundingSet;
		this.prioritySet = prioritySet;
	}
	
	

}
