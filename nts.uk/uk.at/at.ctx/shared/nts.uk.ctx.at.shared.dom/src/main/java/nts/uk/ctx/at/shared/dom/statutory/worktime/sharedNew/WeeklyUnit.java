/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;

/**
 * The Class Weekly.
 */
// 週単位
@Getter
public class WeeklyUnit extends ValueObject {

	/** The Constant DEFAULT_VALUE. */
	public static final int DEFAULT_VALUE = 0;

	/** The time. */
	// 週間時間
	private WeeklyTime time;

	/** The start. */
	// 週開始
	private WeekStart start;

	/**
	 * Instantiates a new weekly.
	 *
	 * @param time
	 *            the time
	 * @param start
	 *            the start
	 */
	public WeeklyUnit(WeeklyTime time, WeekStart start) {
		super();
		this.time = time;
		this.start = start;
	}

}
