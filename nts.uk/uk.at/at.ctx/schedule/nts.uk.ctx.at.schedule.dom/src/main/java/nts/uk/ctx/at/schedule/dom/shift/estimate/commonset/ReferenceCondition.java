/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.commonset;

import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;

/**
 * The Class ReferenceCondition.
 */
@Getter
// 目安条件
public class ReferenceCondition {

	/** The yearly display condition. */
	// 年間表示条件
	private EstimatedCondition yearlyDisplayCondition;

	/** The monthly display condition. */
	// 月間表示条件
	private EstimatedCondition monthlyDisplayCondition;

	/** The alarm check condition. */
	// アラームチェック条件
	private EstimatedCondition alarmCheckCondition;

	/**
	 * Instantiates a new reference condition.
	 *
	 * @param yearlyDisplayCondition
	 *            the yearly display condition
	 * @param monthlyDisplayCondition
	 *            the monthly display condition
	 * @param alarmCheckCondition
	 *            the alarm check condition
	 */
	public ReferenceCondition(EstimatedCondition yearlyDisplayCondition,
			EstimatedCondition monthlyDisplayCondition, EstimatedCondition alarmCheckCondition) {
		super();
		this.yearlyDisplayCondition = yearlyDisplayCondition;
		this.monthlyDisplayCondition = monthlyDisplayCondition;
		this.alarmCheckCondition = alarmCheckCondition;
	}

}
