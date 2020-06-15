/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSetSetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.KrcstRegMCalSet;

/**
 * The Class JpaRegularWorkTimeAggrSetSetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaRegularWorkTimeAggrSetSetMemento<T extends KrcstRegMCalSet>
		implements RegularWorkTimeAggrSetSetMemento {

	/** The type value. */
	private T typeValue;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
	 * RegularWorkTimeAggrSetSetMemento#setAggregateTimeSet(nts.uk.ctx.at.record
	 * .dom.workrecord.monthcal.ExcessOutsideTimeSetReg)
	 */
	@Override
	public void setAggregateTimeSet(ExcessOutsideTimeSetReg aggregateTimeSet) {
		this.typeValue.setIncludeLegalAggr(
				BooleanGetAtr.getAtrByBoolean(aggregateTimeSet.getLegalOverTimeWork()));
		this.typeValue.setIncludeHolidayAggr(
				BooleanGetAtr.getAtrByBoolean(aggregateTimeSet.getLegalHoliday()));
		this.typeValue.setIncludeExtraAggr(
				BooleanGetAtr.getAtrByBoolean(aggregateTimeSet.getSurchargeWeekMonth()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
	 * RegularWorkTimeAggrSetSetMemento#setExcessOutsideTimeSet(nts.uk.ctx.at.
	 * record.dom.workrecord.monthcal.ExcessOutsideTimeSetReg)
	 */
	@Override
	public void setExcessOutsideTimeSet(ExcessOutsideTimeSetReg excessOutsideTimeSet) {
		this.typeValue.setIncludeLegalOt(
				BooleanGetAtr.getAtrByBoolean(excessOutsideTimeSet.getLegalOverTimeWork()));
		this.typeValue.setIncludeHolidayOt(
				BooleanGetAtr.getAtrByBoolean(excessOutsideTimeSet.getLegalHoliday()));
		this.typeValue.setIncludeExtraOt(
				BooleanGetAtr.getAtrByBoolean(excessOutsideTimeSet.getSurchargeWeekMonth()));
		this.typeValue.setExceptLegalHdwkOt(
				BooleanGetAtr.getAtrByBoolean(excessOutsideTimeSet.getExceptLegalHdwk()));
	}

}
