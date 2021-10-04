/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem;

import java.util.Optional;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.infra.entity.optitem.KrcmtAnyfResultRange;
import nts.uk.ctx.at.shared.dom.scherec.optitem.*;

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
//		return Optional.of(new NumberRange(this.typeValue.getUpperNumberRange(),
//				this.typeValue.getLowerNumberRange()));
	    return Optional.of(new NumberRange(
	            Optional.of(new DailyTimesRange(this.typeValue.getUpperDayNumberRange(), this.typeValue.getLowerDayNumberRange())), 
	            Optional.of(new MonthlyTimesRange(
	                    this.typeValue.getUpperMonNumberRange() == null ? null : this.typeValue.getUpperMonNumberRange().doubleValue(), 
	                    this.typeValue.getLowerMonNumberRange() == null ? null : this.typeValue.getLowerMonNumberRange().doubleValue()))));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.CalcResultRangeGetMemento#getTimeRange()
	 */
	@Override
	public Optional<TimeRange> getTimeRange() {
//		return Optional.of(new TimeRange(this.typeValue.getUpperTimeRange(),
//				this.typeValue.getLowerTimeRange()));
	    return Optional.of(new TimeRange(
	            Optional.of(new DailyTimeRange(this.typeValue.getUpperDayTimeRange(), this.typeValue.getLowerDayTimeRange())),
	            Optional.of(new MonthlyTimeRange(this.typeValue.getUpperMonTimeRange(), this.typeValue.getLowerMonTimeRange()))));
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
//		return Optional.of(new AmountRange(this.typeValue.getUpperAmountRange(),
//				this.typeValue.getLowerAmountRange()));
	    return Optional.of(new AmountRange(
	            Optional.of(new DailyAmountRange(this.typeValue.getUpperdayAmountRange(), this.typeValue.getLowerDayAmountRange())),
	            Optional.of(new MonthlyAmountRange(this.typeValue.getUpperMonAmountRange(), this.typeValue.getLowerMonAmountRange()))));
	}

	@Override
	public Optional<DailyResultInputUnit> getInputUnit() {
		if (this.typeValue.getTimeItemInputUnit() == null && this.typeValue.getNumberItemInputUnit() == null && this.typeValue.getAmountItemInputUnit() == null)
			return Optional.empty();
		return Optional.of(new DailyResultInputUnit(
				Optional.ofNullable(this.typeValue.getTimeItemInputUnit() == null ? null : EnumAdaptor.valueOf(this.typeValue.getTimeItemInputUnit(), TimeItemInputUnit.class)),
				Optional.ofNullable(this.typeValue.getNumberItemInputUnit() == null ? null : EnumAdaptor.valueOf(this.typeValue.getNumberItemInputUnit(), NumberItemInputUnit.class)),
				Optional.ofNullable(this.typeValue.getAmountItemInputUnit() == null ? null : EnumAdaptor.valueOf(this.typeValue.getAmountItemInputUnit(), AmountItemInputUnit.class))
		));
	}

}
