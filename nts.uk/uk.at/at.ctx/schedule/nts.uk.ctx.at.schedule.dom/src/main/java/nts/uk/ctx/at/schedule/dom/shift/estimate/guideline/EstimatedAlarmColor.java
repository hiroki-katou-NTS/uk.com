/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.guideline;

import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.shift.estimate.GuidelineCondition;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

/**
 * The Class EstimateAlarmColor.
 */
// 目安アラーム色
@Getter
public class EstimatedAlarmColor {

	/** The estimate term of use. */
	// 目安利用条件
	private GuidelineCondition guidelineCondition;

	/** The color. */
	// 色
	private ColorCode color;
}
