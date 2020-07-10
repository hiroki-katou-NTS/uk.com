/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.predset.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class BreakDownTimeDayDto.
 */
@Getter
@Setter
@NoArgsConstructor
public class BreakDownTimeDayDto {
	/** The one day. */
	public Integer oneDay;

	/** The morning. */
	public Integer morning;

	/** The afternoon. */
	public Integer afternoon;

	/**
	 * Instantiates a new break down time day dto.
	 *
	 * @param oneDay the one day
	 * @param morning the morning
	 * @param afternoon the afternoon
	 */
	public BreakDownTimeDayDto(Integer oneDay, Integer morning, Integer afternoon) {
		super();
		this.oneDay = oneDay;
		this.morning = morning;
		this.afternoon = afternoon;
	}
	
	
}
