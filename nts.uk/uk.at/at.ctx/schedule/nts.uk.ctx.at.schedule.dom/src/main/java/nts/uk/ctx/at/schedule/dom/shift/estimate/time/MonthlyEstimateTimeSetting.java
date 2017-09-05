/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.time;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;

/**
 * The Class MonthlyEstimateTimeSetting.
 */
// 月間目安時間設定
@Getter
public class MonthlyEstimateTimeSetting extends DomainObject{

	/** The estimated condition. */
	// 目安利用条件
	private EstimatedCondition estimatedCondition;

	/** The time. */
	// 時間
	private MonthlyEstimateTime time;
	

	/**
	 * Instantiates a new monthly estimate time setting.
	 *
	 * @param time the time
	 * @param estimatedCondition the estimated condition
	 */
	public MonthlyEstimateTimeSetting(MonthlyEstimateTime time,
			EstimatedCondition estimatedCondition) {
		this.time = time;
		this.estimatedCondition = estimatedCondition;
	}
	
	
}
