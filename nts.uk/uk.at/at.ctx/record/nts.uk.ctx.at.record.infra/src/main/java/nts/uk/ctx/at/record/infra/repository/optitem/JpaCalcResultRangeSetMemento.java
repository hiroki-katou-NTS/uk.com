/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.optitem.AmountRange;
import nts.uk.ctx.at.record.dom.optitem.CalcRangeCheck;
import nts.uk.ctx.at.record.dom.optitem.CalcResultRangeSetMemento;
import nts.uk.ctx.at.record.dom.optitem.NumberRange;
import nts.uk.ctx.at.record.dom.optitem.TimeRange;
import nts.uk.ctx.at.record.infra.entity.optitem.KrcstCalcResultRange;

/**
 * The Class JpaCalcResultRangeSetMemento.
 */
public class JpaCalcResultRangeSetMemento implements CalcResultRangeSetMemento {

	/** The type value. */
	private KrcstCalcResultRange typeValue;

	/**
	 * Instantiates a new jpa calc result range set memento.
	 *
	 * @param typeValue the type value
	 */
	public JpaCalcResultRangeSetMemento(KrcstCalcResultRange typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.CalcResultRangeSetMemento#setUpperLimit(
	 * nts.uk.ctx.at.record.dom.optitem.CalcRangeCheck)
	 */
	@Override
	public void setUpperLimit(CalcRangeCheck upper) {
		this.typeValue.setUpperLimitAtr(upper.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.CalcResultRangeSetMemento#setLowerLimit(
	 * nts.uk.ctx.at.record.dom.optitem.CalcRangeCheck)
	 */
	@Override
	public void setLowerLimit(CalcRangeCheck lower) {
		this.typeValue.setLowerLimitAtr(lower.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.CalcResultRangeSetMemento#setNumberRange
	 * (nts.uk.ctx.at.record.dom.optitem.NumberRange)
	 */
	@Override
	public void setNumberRange(Optional<NumberRange> range) {
		if (range.isPresent() && range.get().getLowerLimit().isPresent()) {
			this.typeValue.setLowerNumberRange(range.get().getLowerLimit().get().v());
		}
		if (range.isPresent() && range.get().getUpperLimit().isPresent()) {
			this.typeValue.setUpperNumberRange(range.get().getUpperLimit().get().v());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.CalcResultRangeSetMemento#setTimeRange(
	 * nts.uk.ctx.at.record.dom.optitem.TimeRange)
	 */
	@Override
	public void setTimeRange(Optional<TimeRange> range) {
		if (range.isPresent() && range.get().getLowerLimit().isPresent()) {
			this.typeValue.setLowerTimeRange(range.get().getLowerLimit().get().v());
		}
		if (range.isPresent() && range.get().getUpperLimit().isPresent()) {
			this.typeValue.setUpperTimeRange(range.get().getUpperLimit().get().v());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.CalcResultRangeSetMemento#setAmountRange
	 * (nts.uk.ctx.at.record.dom.optitem.AmountRange)
	 */
	@Override
	public void setAmountRange(Optional<AmountRange> range) {
		if (range.isPresent() && range.get().getLowerLimit().isPresent()) {
			this.typeValue.setLowerAmountRange(range.get().getLowerLimit().get().v());
		}
		if (range.isPresent() && range.get().getUpperLimit().isPresent()) {
			this.typeValue.setUpperAmountRange(range.get().getUpperLimit().get().v());
		}
	}

}
