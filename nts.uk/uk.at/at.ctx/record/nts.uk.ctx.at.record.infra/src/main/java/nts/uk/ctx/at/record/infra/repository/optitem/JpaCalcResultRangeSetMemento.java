/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem;

import nts.uk.ctx.at.record.dom.optitem.AmountRange;
import nts.uk.ctx.at.record.dom.optitem.CalcRangeCheck;
import nts.uk.ctx.at.record.dom.optitem.CalcResultRangeSetMemento;
import nts.uk.ctx.at.record.dom.optitem.NumberRange;
import nts.uk.ctx.at.record.dom.optitem.TimeRange;
import nts.uk.ctx.at.record.infra.entity.optitem.KrcstCalcResultRange;
import nts.uk.ctx.at.record.infra.entity.optitem.KrcstCalcResultRangePK;

/**
 * The Class JpaCalcResultRangeSetMemento.
 */
public class JpaCalcResultRangeSetMemento implements CalcResultRangeSetMemento {

	/** The type value. */
	private KrcstCalcResultRange typeValue;

	/**
	 * Instantiates a new jpa calc result range set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaCalcResultRangeSetMemento(KrcstCalcResultRange typeValue) {
		KrcstCalcResultRangePK krcstCalcResultRangePK = typeValue.getKrcstCalcResultRangePK();
		// Check PK exist
		if (krcstCalcResultRangePK == null) {
			krcstCalcResultRangePK = new KrcstCalcResultRangePK();
		}
		typeValue.setKrcstCalcResultRangePK(krcstCalcResultRangePK);

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
	public void setNumberRange(NumberRange range) {
		this.typeValue.setUpperNumberRange(range.getUpperLimit().v().intValue());
		this.typeValue.setLowerNumberRange(range.getLowerLimit().v().intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.CalcResultRangeSetMemento#setTimeRange(
	 * nts.uk.ctx.at.record.dom.optitem.TimeRange)
	 */
	@Override
	public void setTimeRange(TimeRange range) {
		this.typeValue.setUpperTimeRange(range.getUpperLimit().v().intValue());
		this.typeValue.setLowerTimeRange(range.getLowerLimit().v().intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.CalcResultRangeSetMemento#setAmountRange
	 * (nts.uk.ctx.at.record.dom.optitem.AmountRange)
	 */
	@Override
	public void setAmountRange(AmountRange range) {
		this.typeValue.setUpperAmountRange(range.getUpperLimit().v().intValue());
		this.typeValue.setLowerAmountRange(range.getLowerLimit().v().intValue());
	}

}
