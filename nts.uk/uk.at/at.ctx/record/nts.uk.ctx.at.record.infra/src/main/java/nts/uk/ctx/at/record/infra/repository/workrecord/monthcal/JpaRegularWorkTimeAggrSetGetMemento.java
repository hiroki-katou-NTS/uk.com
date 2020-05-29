/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSetGetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.KrcstRegMCalSet;

/**
 * The Class JpaRegularWorkTimeAggrSetGetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaRegularWorkTimeAggrSetGetMemento<T extends KrcstRegMCalSet>
		implements RegularWorkTimeAggrSetGetMemento {

	/** The type value. */
	private T typeValue;

	/**
	 * Instantiates a new jpa regular work time aggr set get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaRegularWorkTimeAggrSetGetMemento(T typeValue) {
		super();
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
	 * RegularWorkTimeAggrSetGetMemento#getAggregateTimeSet()
	 */
	@Override
	public ExcessOutsideTimeSetReg getAggregateTimeSet() {
		return new ExcessOutsideTimeSetReg(
				BooleanGetAtr.getAtrByInteger(this.typeValue.getIncludeLegalAggr()),
				BooleanGetAtr.getAtrByInteger(this.typeValue.getIncludeHolidayAggr()),
				BooleanGetAtr.getAtrByInteger(this.typeValue.getIncludeExtraAggr()),
				false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
	 * RegularWorkTimeAggrSetGetMemento#getExcessOutsideTimeSet()
	 */
	@Override
	public ExcessOutsideTimeSetReg getExcessOutsideTimeSet() {
		return new ExcessOutsideTimeSetReg(
				BooleanGetAtr.getAtrByInteger(this.typeValue.getIncludeLegalOt()),
				BooleanGetAtr.getAtrByInteger(this.typeValue.getIncludeHolidayOt()),
				BooleanGetAtr.getAtrByInteger(this.typeValue.getIncludeExtraOt()),
				BooleanGetAtr.getAtrByInteger(this.typeValue.getExceptLegalHdwkOt()));
	}

}
