/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem;

import java.util.Optional;

import nts.uk.ctx.at.record.infra.entity.optitem.KrcmtAnyfResultRange;
import nts.uk.ctx.at.shared.dom.scherec.optitem.AmountRange;
import nts.uk.ctx.at.shared.dom.scherec.optitem.CalcRangeCheck;
import nts.uk.ctx.at.shared.dom.scherec.optitem.CalcResultRangeGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.NumberRange;
import nts.uk.ctx.at.shared.dom.scherec.optitem.TimeRange;

/**
 * The Class JpaCalcResultRangeGetMemento.
 */
public class JpaCalcResultRangeGetMemento implements CalcResultRangeGetMemento {

	/** The type value. */
	private KrcmtAnyfResultRange typeValue;

	/**
	 * Instantiates a new jpa calc result range get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaCalcResultRangeGetMemento(KrcmtAnyfResultRange typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.CalcResultRangeGetMemento#getUpperLimit(
	 * )
	 */
	@Override
	public CalcRangeCheck getUpperLimit() {
		return CalcRangeCheck.valueOf(this.typeValue.getUpperLimitAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.CalcResultRangeGetMemento#getLowerLimit(
	 * )
	 */
	@Override
	public CalcRangeCheck getLowerLimit() {
		return CalcRangeCheck.valueOf(this.typeValue.getLowerLimitAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.CalcResultRangeGetMemento#getNumberRange
	 * ()
	 */
	@Override
	public Optional<NumberRange> getNumberRange() {
		return Optional.of(new NumberRange(this.typeValue.getUpperNumberRange(),
				this.typeValue.getLowerNumberRange()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.CalcResultRangeGetMemento#getTimeRange()
	 */
	@Override
	public Optional<TimeRange> getTimeRange() {
		return Optional.of(new TimeRange(this.typeValue.getUpperTimeRange(),
				this.typeValue.getLowerTimeRange()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.CalcResultRangeGetMemento#getAmountRange
	 * ()
	 */
	@Override
	public Optional<AmountRange> getAmountRange() {
		return Optional.of(new AmountRange(this.typeValue.getUpperAmountRange(),
				this.typeValue.getLowerAmountRange()));
	}

}
