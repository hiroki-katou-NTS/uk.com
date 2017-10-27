/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;

/**
 * The Class MonthlyEstimateNumberOfDay.
 */
// 月間目安日数設定
@Getter
public class MonthlyEstimateNumberOfDay extends DomainObject{

	/** The days. */
	// 日数
	private MonthlyEstimateDays days;
	
	/** The estimated condition. */
	// 目安利用条件
	private EstimatedCondition estimatedCondition;

	/**
	 * Instantiates a new monthly estimate number of day.
	 *
	 * @param days the days
	 * @param estimatedCondition the estimated condition
	 */
	public MonthlyEstimateNumberOfDay(MonthlyEstimateDays days,
			EstimatedCondition estimatedCondition) {
		this.days = days;
		this.estimatedCondition = estimatedCondition;
	} 
	
	
	
}
