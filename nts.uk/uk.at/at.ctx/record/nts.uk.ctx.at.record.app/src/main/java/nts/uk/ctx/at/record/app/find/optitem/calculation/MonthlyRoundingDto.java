/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.calculation;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRounding;
import nts.uk.ctx.at.shared.dom.common.numberrounding.NumberRounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Class MonthlyRoundingDto.
 */
@Getter
@Setter
public class MonthlyRoundingDto {

	/** The number rounding. */
	// 数値丸め
	private NumberRounding numberRounding;

	/** The time rounding. */
	// 時間丸め
	private TimeRoundingSetting timeRounding;

	/** The amount rounding. */
	// 金額丸め
	private AmountRounding amountRounding;
}
