/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;

/**
 * The Class YearlyEstimateNumberOfDay.
 */
// 年間目安日数設定
@Getter
public class YearlyEstimateNumberOfDay extends DomainObject{

	/** The days. */
	//日数
	private YearlyEstimateDays days;
	
	/** The estimated condition. */
	//目安利用条件
	private  EstimatedCondition estimatedCondition;

	/**
	 * Instantiates a new yearly estimate number of day.
	 *
	 * @param days the days
	 * @param estimatedCondition the estimated condition
	 */
	public YearlyEstimateNumberOfDay(YearlyEstimateDays days,
			EstimatedCondition estimatedCondition) {
		this.days = days;
		this.estimatedCondition = estimatedCondition;
	}
	
	
}
