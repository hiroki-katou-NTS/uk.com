/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.optitem.AmountRange;
import nts.uk.ctx.at.record.dom.optitem.CalcRangeCheck;
import nts.uk.ctx.at.record.dom.optitem.CalcResultRangeSetMemento;
import nts.uk.ctx.at.record.dom.optitem.NumberRange;
import nts.uk.ctx.at.record.dom.optitem.TimeRange;

/**
 * The Class CalculationResultRangeDto.
 */
@Getter
@Setter
public class CalcResultRangeDto implements CalcResultRangeSetMemento {

	/** The upper check. */
	private boolean upperCheck;

	/** The lower check. */
	private boolean lowerCheck;

	// ===================== Optional ======================= //
	/** The number upper. */
	private int numberUpper;

	/** The number lower. */
	private int numberLower;

	/** The time upper. */
	private int timeUpper;

	/** The time lower. */
	private int timeLower;

	/** The amount upper. */
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
	public void setUpperLimit(CalcRangeCheck upper) {
		this.upperCheck = upper == CalcRangeCheck.SET ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrecord.CalculationResultRangeSetMemento#
	 * setLowerLimit(nts.uk.ctx.at.shared.dom.workrecord.CalculationRangeCheck)
	 */
	@Override
	public void setLowerLimit(CalcRangeCheck lower) {
		this.lowerCheck = lower == CalcRangeCheck.SET ? true : false;
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

}
