/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class PrioritySetDto.
 */
@Getter
@Setter
public class PrioritySetDto {
	
	/** The priority atr. */
	private Integer priorityAtr;
	
	/** The stamp atr. */
	private Integer stampAtr;

	/**
	 * Instantiates a new priority set dto.
	 *
	 * @param priorityAtr the priority atr
	 * @param stampAtr the stamp atr
	 */
	public PrioritySetDto(Integer priorityAtr, Integer stampAtr) {
		super();
		this.priorityAtr = priorityAtr;
		this.stampAtr = stampAtr;
	}
	
	

}
