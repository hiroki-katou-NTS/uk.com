/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.personallaborcondition;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class BreakdownTimeDay.
 */
// １日の時間内訳
@Getter
public class BreakdownTimeDay extends DomainObject{

	/** The one day. */
	// 1日
	private AttendanceTime oneDay;
	
	/** The morning. */
	// 午前
	private AttendanceTime morning;
	
	/** The afternoon. */
	// 午後
	private AttendanceTime afternoon;

	/**
	 * Instantiates a new breakdown time day.
	 *
	 * @param oneDay the one day
	 * @param morning the morning
	 * @param afternoon the afternoon
	 */
	public BreakdownTimeDay(AttendanceTime oneDay, AttendanceTime morning, AttendanceTime afternoon) {
		this.oneDay = oneDay;
		this.morning = morning;
		this.afternoon = afternoon;
	}
	
}
