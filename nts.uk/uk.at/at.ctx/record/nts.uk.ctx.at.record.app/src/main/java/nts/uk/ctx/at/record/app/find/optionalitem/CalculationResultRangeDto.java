/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optionalitem;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.optionalitem.AmountRange;
import nts.uk.ctx.at.record.dom.optionalitem.CalculationRangeCheck;
import nts.uk.ctx.at.record.dom.optionalitem.CalculationResultRangeGetMemento;
import nts.uk.ctx.at.record.dom.optionalitem.CalculationResultRangeSetMemento;
import nts.uk.ctx.at.record.dom.optionalitem.NumberRange;
import nts.uk.ctx.at.record.dom.optionalitem.TimeRange;

/**
 * The Class CalculationResultRangeDto.
 */
@Getter
@Setter
public class CalculationResultRangeDto implements CalculationResultRangeSetMemento, CalculationResultRangeGetMemento {

	/** The upper check. */
	// 上限値チェック
	private boolean upperCheck;

	/** The lower check. */
	// 下限値チェック
	private boolean lowerCheck;

	// ===================== Optional ======================= //
	/** The number upper. */
	// 回数範囲
	private int numberUpper;

	/** The number lower. */
	private int numberLower;

	/** The time upper. */
	// 時間範囲
	private int timeUpper;

	/** The time lower. */
	private int timeLower;

	/** The amount upper. */
	// 金額範囲
	private int amountUpper;

	/** The amount lower. */
	private int amountLower;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrecord.CalculationResultRangeSetMemento#
	 * setUpperLimit(nts.uk.ctx.at.shared.dom.workrecord.CalculationRangeCheck)
	 */
	@Override
	public void setUpperLimit(CalculationRangeCheck upper) {
		this.upperCheck = upper == CalculationRangeCheck.SET ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrecord.CalculationResultRangeSetMemento#
	 * setLowerLimit(nts.uk.ctx.at.shared.dom.workrecord.CalculationRangeCheck)
	 */
	@Override
	public void setLowerLimit(CalculationRangeCheck lower) {
		this.lowerCheck = lower == CalculationRangeCheck.SET ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrecord.CalculationResultRangeSetMemento#
	 * setNumberRange(nts.uk.ctx.at.shared.dom.workrecord.NumberRange)
	 */
	@Override
	public void setNumberRange(NumberRange range) {
		this.numberLower = range.getLowerLimit().v().intValue();
		this.numberUpper = range.getUpperLimit().v().intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrecord.CalculationResultRangeSetMemento#
	 * setTimeRange(nts.uk.ctx.at.shared.dom.workrecord.TimeRange)
	 */
	@Override
	public void setTimeRange(TimeRange range) {
		this.timeLower = range.getLowerLimit().v().intValue();
		this.timeUpper = range.getUpperLimit().v().intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrecord.CalculationResultRangeSetMemento#
	 * setAmountRange(nts.uk.ctx.at.shared.dom.workrecord.AmountRange)
	 */
	@Override
	public void setAmountRange(AmountRange range) {
		this.amountLower = range.getLowerLimit().v().intValue();
		this.amountUpper = range.getUpperLimit().v().intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrecord.CalculationResultRangeGetMemento#
	 * getUpperLimit()
	 */
	@Override
	public CalculationRangeCheck getUpperLimit() {
		return this.upperCheck ? CalculationRangeCheck.SET : CalculationRangeCheck.NOT_SET;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrecord.CalculationResultRangeGetMemento#
	 * getLowerLimit()
	 */
	@Override
	public CalculationRangeCheck getLowerLimit() {
		return this.upperCheck ? CalculationRangeCheck.SET : CalculationRangeCheck.NOT_SET;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrecord.CalculationResultRangeGetMemento#
	 * getNumberRange()
	 */
	@Override
	public NumberRange getNumberRange() {
		// TODO...
		return new NumberRange();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrecord.CalculationResultRangeGetMemento#
	 * getTimeRange()
	 */
	@Override
	public TimeRange getTimeRange() {
		// TODO...
		return new TimeRange();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrecord.CalculationResultRangeGetMemento#
	 * getAmountRange()
	 */
	@Override
	public AmountRange getAmountRange() {
		// TODO...
		return new AmountRange();
	}

}
