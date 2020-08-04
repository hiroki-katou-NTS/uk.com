/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;

/**
 * The Class MonthlyTimeNew.
 */
@Getter
// 月単位
public class MonthlyUnit extends ValueObject {

	/** The monthly. */
	// 月度
	private Month month;

	/** The monthly time. */
	// 月間時間
	private MonthlyEstimateTime monthlyTime;

	/**
	 * Instantiates a new monthly unit.
	 *
	 * @param month
	 *            the month
	 * @param monthlyTime
	 *            the monthly time
	 */
	public MonthlyUnit(Month month, MonthlyEstimateTime monthlyTime) {
		super();
		this.month = month;
		this.monthlyTime = monthlyTime;
	}

}
