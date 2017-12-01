/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class DesignatedTimeDto.
 */
@Getter
@Setter
public class DesignatedTimeDto {

	/** The one day time. */
	private Integer oneDayTime;
	
	/** The half day time. */
	private Integer halfDayTime;

	/**
	 * Instantiates a new designated time dto.
	 *
	 * @param oneDayTime the one day time
	 * @param halfDayTime the half day time
	 */
	public DesignatedTimeDto(Integer oneDayTime, Integer halfDayTime) {
		super();
		this.oneDayTime = oneDayTime;
		this.halfDayTime = halfDayTime;
	}
	
	
}
