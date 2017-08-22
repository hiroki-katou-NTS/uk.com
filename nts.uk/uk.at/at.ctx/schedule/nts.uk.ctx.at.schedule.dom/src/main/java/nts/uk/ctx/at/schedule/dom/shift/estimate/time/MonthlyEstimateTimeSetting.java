/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.time;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;

/**
 * The Class MonthlyEstimateTimeSetting.
 */
// 月間目安時間設定
@Getter
@Setter
public class MonthlyEstimateTimeSetting extends DomainObject{

	/** The time. */
	// 時間
	private MonthlyEstimateTime time;
	
	/** The estimated condition. */
	// 目安利用条件
	private EstimatedCondition estimatedCondition;
}
