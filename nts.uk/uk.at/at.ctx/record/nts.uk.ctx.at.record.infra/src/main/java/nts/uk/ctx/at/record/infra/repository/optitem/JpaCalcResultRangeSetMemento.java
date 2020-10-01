/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem;

import java.util.Optional;

import nts.uk.ctx.at.record.infra.entity.optitem.KrcstCalcResultRange;
import nts.uk.ctx.at.shared.dom.scherec.optitem.AmountRange;
import nts.uk.ctx.at.shared.dom.scherec.optitem.CalcRangeCheck;
import nts.uk.ctx.at.shared.dom.scherec.optitem.CalcResultRangeSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.NumberRange;
import nts.uk.ctx.at.shared.dom.scherec.optitem.TimeRange;

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
		this.typeValue.setLowerNumberRange(range.isPresent() && range.get().getLowerLimit().isPresent()
				? range.get().getLowerLimit().get().v() : null);
		this.typeValue.setUpperNumberRange(range.isPresent() && range.get().getUpperLimit().isPresent()
				? range.get().getUpperLimit().get().v() : null);
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
		this.typeValue.setLowerTimeRange(range.isPresent() && range.get().getLowerLimit().isPresent()
				? range.get().getLowerLimit().get().v() : null);
		this.typeValue.setUpperTimeRange(range.isPresent() && range.get().getUpperLimit().isPresent()
				? range.get().getUpperLimit().get().v() : null);
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
		this.typeValue.setLowerAmountRange(range.isPresent() && range.get().getLowerLimit().isPresent()
				? range.get().getLowerLimit().get().v() : null);
		this.typeValue.setUpperAmountRange(range.isPresent() && range.get().getUpperLimit().isPresent()
				? range.get().getUpperLimit().get().v() : null);
	}

}
