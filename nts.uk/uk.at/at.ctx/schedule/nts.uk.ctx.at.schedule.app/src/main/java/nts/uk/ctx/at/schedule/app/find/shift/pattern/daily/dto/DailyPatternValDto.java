/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.pattern.daily.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class DailyPatternValDto.
 */
@Getter
@Setter
public class DailyPatternValDto{

	/** The disp order. */
	private Integer dispOrder;

	/** The work type set cd. */
	private String workTypeSetCd;

	/** The working hours cd. */
	private String workingHoursCd;

	/** The days. */
	private Integer days;

	/**
	 * Instantiates a new daily pattern val dto.
	 */
	public DailyPatternValDto() {
		super();
	}

	/**
	 * Instantiates a new daily pattern val dto.
	 *
	 * @param dispOrder
	 *            the disp order
	 * @param workTypeSetCd
	 *            the work type set cd
	 * @param workingHoursCd
	 *            the working hours cd
	 * @param days
	 *            the days
	 */
	public DailyPatternValDto(Integer dispOrder, String workTypeSetCd, String workingHoursCd,
			Integer days) {
		this.dispOrder = dispOrder;
		this.workTypeSetCd = workTypeSetCd;
		this.workingHoursCd = workingHoursCd;
		this.days = days;
	}
}
