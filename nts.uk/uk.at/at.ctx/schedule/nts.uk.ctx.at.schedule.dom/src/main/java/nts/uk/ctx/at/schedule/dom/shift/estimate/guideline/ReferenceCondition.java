/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.guideline;

import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTermOfUse;

/**
 * The Class EstimateCondition.
 */
// 目安条件
@Getter
public class ReferenceCondition {

	/** The yearly display condition. */
	// 年間表示条件
	private EstimateTermOfUse yearlyDisplayCondition;

	/** The monthly display condition. */
	// 月間表示条件
	private EstimateTermOfUse monthlyDisplayCondition;

	/** The alarm check condition. */
	// アラームチェック条件
	private EstimateTermOfUse alarmCheckCondition;
}
