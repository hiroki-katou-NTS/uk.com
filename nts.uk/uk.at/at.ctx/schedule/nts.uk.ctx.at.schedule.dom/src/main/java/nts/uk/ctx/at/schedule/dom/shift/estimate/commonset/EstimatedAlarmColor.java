/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.commonset;

import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

/**
 * The Class EstimateAlarmColor.
 */
// 目安アラーム色
@Getter
public class EstimatedAlarmColor {

	/** The estimate term of use. */
	// 目安利用条件
	private EstimatedCondition guidelineCondition;

	/** The color. */
	// 色
	private ColorCode color;

	/**
	 * Instantiates a new estimated alarm color.
	 *
	 * @param guidelineCondition
	 *            the guideline condition
	 * @param color
	 *            the color
	 */
	public EstimatedAlarmColor(EstimatedCondition guidelineCondition, ColorCode color) {
		super();
		this.guidelineCondition = guidelineCondition;
		this.color = color;
	}

}
