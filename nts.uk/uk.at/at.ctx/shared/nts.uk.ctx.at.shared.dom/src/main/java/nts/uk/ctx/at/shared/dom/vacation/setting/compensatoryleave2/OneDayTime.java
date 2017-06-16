/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2;

import nts.arc.primitive.TimeClockPrimitiveValue;

/**
 * The Class OneDayTime.
 */
public class OneDayTime extends TimeClockPrimitiveValue<OneDayTime>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;


	/**
	 * Instantiates a new one day time.
	 *
	 * @param rawValue the raw value
	 */
	public OneDayTime(Long rawValue) {
		super(rawValue);
	}
	
	/**
	 * Instantiates a new one day time.
	 *
	 * @param hour the hour
	 * @param minute the minute
	 * @param second the second
	 */
	public OneDayTime(int hour, int minute, int second) {
		super(hour, minute, second);
	}

}
