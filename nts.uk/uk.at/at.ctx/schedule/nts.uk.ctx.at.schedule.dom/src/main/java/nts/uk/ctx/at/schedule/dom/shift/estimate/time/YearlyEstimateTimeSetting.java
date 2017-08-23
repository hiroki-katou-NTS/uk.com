/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.time;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;

/**
 * The Class YearlyEstimateTimeSetting.
 */
// 年間目安時間設定
@Getter
public class YearlyEstimateTimeSetting extends DomainObject{
	
	/** The estimated condition. */
	//目安利用条件
	private EstimatedCondition estimatedCondition;
	
	/** The time. */
	// 時間
	private YearlyEstimateTime time;

	/**
	 * Instantiates a new yearly estimate time setting.
	 *
	 * @param estimatedCondition the estimated condition
	 * @param time the time
	 */
	public YearlyEstimateTimeSetting(EstimatedCondition estimatedCondition,
			YearlyEstimateTime time) {
		this.estimatedCondition = estimatedCondition;
		this.time = time;
	}

	
}
