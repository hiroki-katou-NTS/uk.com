/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class CalculationResultRange.
 */
// 計算結果の範囲
// 責務 : 計算結果の有効範囲をきめる。
// Responsibility: determine the effective range of the calculation result.
@Getter
public class CalcResultRange extends DomainObject {

	/** The upper limit. */
	// 上限値チェック
	private CalcRangeCheck upperLimit;

	/** The lower limit. */
	// 下限値チェック
	private CalcRangeCheck lowerLimit;

	// ===================== Optional ======================= //
	/** The number range. */
	// 回数範囲
	private NumberRange numberRange;

	/** The time range. */
	// 時間範囲
	private TimeRange timeRange;

	/** The amount range. */
	// 金額範囲
	private AmountRange amountRange;

	/**
	 * Instantiates a new calculation result range.
	 *
	 * @param memento the memento
	 */
	public CalcResultRange(CalcResultRangeGetMemento memento) {
		this.upperLimit = memento.getUpperLimit();
		this.lowerLimit = memento.getLowerLimit();
		this.numberRange = memento.getNumberRange();
		this.timeRange = memento.getTimeRange();
		this.amountRange = memento.getAmountRange();

		// validate
		if (this.lowerLimit == CalcRangeCheck.SET) {
			this.numberRange.validateRange();
			this.timeRange.validateRange();
			this.amountRange.validateRange();
		}
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(CalcResultRangeSetMemento memento) {
		memento.setUpperLimit(this.upperLimit);
		memento.setLowerLimit(this.lowerLimit);
		memento.setNumberRange(this.numberRange);
		memento.setTimeRange(this.timeRange);
		memento.setAmountRange(this.amountRange);
	}
}
