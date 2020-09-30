/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.optitem.AmountRange;
import nts.uk.ctx.at.shared.dom.scherec.optitem.CalcRangeCheck;
import nts.uk.ctx.at.shared.dom.scherec.optitem.CalcResultRangeSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.NumberRange;
import nts.uk.ctx.at.shared.dom.scherec.optitem.TimeRange;

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
	private Double numberUpper;

	/** The number lower. */
	private Double numberLower;

	/** The time upper. */
	private Integer timeUpper;

	/** The time lower. */
	private Integer timeLower;

	/** The amount upper. */
	private Integer amountUpper;

	/** The amount lower. */
	private Integer amountLower;

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
	public void setNumberRange(Optional<NumberRange> range) {
		if (range.isPresent() && range.get().getLowerLimit().isPresent()) {
			this.numberLower = range.get().getLowerLimit().get().v();

		}
		if (range.isPresent() && range.get().getUpperLimit().isPresent()) {
			this.numberUpper = range.get().getUpperLimit().get().v();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrecord.CalculationResultRangeSetMemento#
	 * setTimeRange(nts.uk.ctx.at.shared.dom.workrecord.TimeRange)
	 */
	@Override
	public void setTimeRange(Optional<TimeRange> range) {
		if (range.isPresent() && range.get().getLowerLimit().isPresent()) {
			this.timeLower = range.get().getLowerLimit().get().v().intValue();

		}
		if (range.isPresent() && range.get().getUpperLimit().isPresent()) {
			this.timeUpper = range.get().getUpperLimit().get().v().intValue();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrecord.CalculationResultRangeSetMemento#
	 * setAmountRange(nts.uk.ctx.at.shared.dom.workrecord.AmountRange)
	 */
	@Override
	public void setAmountRange(Optional<AmountRange> range) {
		if (range.isPresent() && range.get().getLowerLimit().isPresent()) {
			this.amountLower = range.get().getLowerLimit().get().v().intValue();

		}
		if (range.isPresent() && range.get().getUpperLimit().isPresent()) {
			this.amountUpper = range.get().getUpperLimit().get().v().intValue();
		}
	}

}
