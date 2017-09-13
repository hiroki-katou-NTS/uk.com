/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optionalitem.calculationformula;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRounding;
import nts.uk.ctx.at.shared.dom.common.numberrounding.NumberRounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Class DailyRounding.
 */
// 日別端数処理設定
@Getter
public class DailyRounding extends DomainObject {

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