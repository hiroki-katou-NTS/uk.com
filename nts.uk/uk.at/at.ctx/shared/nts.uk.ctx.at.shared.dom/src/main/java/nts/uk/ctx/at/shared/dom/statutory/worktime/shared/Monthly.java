/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.shared;

import java.time.Month;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.MonthlyTime;

/**
 * The Class Monthly.
 */
@Getter
public class Monthly extends DomainObject {

	/** The time. */
	private MonthlyTime time;

	/** The month. */
	private Month month;

	/**
	 * Instantiates a new monthly.
	 *
	 * @param time the time
	 * @param month the month
	 */
	public Monthly(MonthlyTime time, Month month) {
		this.time = time;
		this.month = month;
	}
}