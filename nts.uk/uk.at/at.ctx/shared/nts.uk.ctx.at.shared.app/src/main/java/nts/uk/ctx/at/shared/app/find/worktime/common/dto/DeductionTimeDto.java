/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeductionTimeDto {

	/** The start. */
	private Integer start;

	/** The end. */
	private Integer end;

	/**
	 * Instantiates a new deduction time dto.
	 *
	 * @param start the start
	 * @param end the end
	 */
	public DeductionTimeDto(Integer start, Integer end) {
		super();
		this.start = start;
		this.end = end;
	}
	
}
