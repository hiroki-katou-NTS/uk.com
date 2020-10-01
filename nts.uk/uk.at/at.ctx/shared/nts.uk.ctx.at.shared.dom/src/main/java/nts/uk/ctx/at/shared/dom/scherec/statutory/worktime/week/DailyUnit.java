/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;

/**
 * The Class DailyTimeNew.
 */
@Getter
// 日の時間.
public class DailyUnit extends DomainObject {

	/** The Constant DEFAULT_VALUE. */
	public static final int DEFAULT_VALUE = 0;

	// 1日の時間
	/** The daily time. */
	private TimeOfDay dailyTime;

	/**
	 * Instantiates a new daily time.
	 *
	 * @param dailyTime
	 *            the daily time
	 */
	public DailyUnit(TimeOfDay dailyTime) {
		super();
		this.dailyTime = dailyTime;
	}

	public static DailyUnit zero() {
		return new DailyUnit(new TimeOfDay(0));
	}
	
}
