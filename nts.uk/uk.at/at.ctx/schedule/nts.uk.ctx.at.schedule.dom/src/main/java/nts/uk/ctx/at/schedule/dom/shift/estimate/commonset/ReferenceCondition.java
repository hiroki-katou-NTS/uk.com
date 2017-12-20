/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
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

//	/** The alarm check condition. */
//	// アラームチェック条件
//	private EstimatedCondition alarmCheckCondition;
//	
	/** The yearly alarm ck condition. */
// 年間アラームチェック条件
	private EstimatedCondition yearlyAlarmCkCondition;
	
	/** The monthly alarm ck condition. */
	// 月間アラームチェック条件
	private EstimatedCondition monthlyAlarmCkCondition;

	/**
	 * Instantiates a new reference condition.
	 *
	 * @param yearlyDisplayCondition the yearly display condition
	 * @param monthlyDisplayCondition the monthly display condition
	 * @param yearlyAlarmCkCondition the yearly alarm ck condition
	 * @param monthlyAlarmCkCondition the monthly alarm ck condition
	 */
	public ReferenceCondition(EstimatedCondition yearlyDisplayCondition, EstimatedCondition monthlyDisplayCondition,
			EstimatedCondition yearlyAlarmCkCondition, EstimatedCondition monthlyAlarmCkCondition) {
		super();
		this.yearlyDisplayCondition = yearlyDisplayCondition;
		this.monthlyDisplayCondition = monthlyDisplayCondition;
		this.yearlyAlarmCkCondition = yearlyAlarmCkCondition;
		this.monthlyAlarmCkCondition = monthlyAlarmCkCondition;
	}


}
