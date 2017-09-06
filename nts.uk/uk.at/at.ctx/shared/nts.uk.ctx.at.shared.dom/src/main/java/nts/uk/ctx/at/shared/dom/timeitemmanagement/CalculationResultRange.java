/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.timeitemmanagement;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class CalculationResultRange.
 */
// 計算結果の範囲
// 責務 : 計算結果の有効範囲をきめる。
// Responsibility: determine the effective range of the calculation result.
@Getter
public class CalculationResultRange extends DomainObject {

	/** The upper limit. */
	// 上限値チェック
	private CalculationRangeCheck upperLimit;

	/** The lower limit. */
	// 下限値チェック
	private CalculationRangeCheck lowerLimit;

	// ===================== Optional ======================= //
	/** The number range. */
	// 回数範囲
	private NumberRangeValue numberRange;

	/** The time range. */
	// 時間範囲
	private TimeRangeValue timeRange;

	/** The amount range. */
	// 金額範囲
	private AmountRange amountRange;
}
