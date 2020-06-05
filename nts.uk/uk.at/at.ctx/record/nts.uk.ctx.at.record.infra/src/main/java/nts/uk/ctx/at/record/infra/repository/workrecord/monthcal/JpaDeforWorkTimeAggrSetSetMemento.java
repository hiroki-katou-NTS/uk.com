/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal;

import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.DeforLaborCalSetting;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforLaborSettlementPeriod;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSetSetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.KrcstDeforMCalSet;

/**
 * The Class JpaDeforWorkTimeAggrSetSetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaDeforWorkTimeAggrSetSetMemento<T extends KrcstDeforMCalSet>
		implements DeforWorkTimeAggrSetSetMemento {

	/** The type value. */
	private T typeValue;

	/**
	 * Instantiates a new jpa defor work time aggr set set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaDeforWorkTimeAggrSetSetMemento(T typeValue) {
		super();
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
	 * DeforWorkTimeAggrSetSetMemento#setAggregateTimeSet(nts.uk.ctx.at.record.
	 * dom.workrecord.monthcal.ExcessOutsideTimeSetReg)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
	 * DeforWorkTimeAggrSetSetMemento#setDeforLaborCalSetting(nts.uk.ctx.at.
	 * record.dom.monthlyaggrmethod.regularandirregular.DeforLaborCalSetting)
	 */
	@Override
	public void setDeforLaborCalSetting(DeforLaborCalSetting deforLaborCalSetting) {
		this.typeValue.setIsOtIrg(
				BooleanGetAtr.getAtrByBoolean(deforLaborCalSetting.isOtTransCriteria()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
	 * DeforWorkTimeAggrSetSetMemento#setSettlementPeriod(nts.uk.ctx.at.record.
	 * dom.workrecord.monthcal.DeforLaborSettlementPeriod)
	 */
	@Override
	public void setSettlementPeriod(DeforLaborSettlementPeriod settlementPeriod) {
		this.typeValue.setStrMonth(settlementPeriod.getStartMonth().v());
		this.typeValue.setPeriod(settlementPeriod.getPeriod().v());
		this.typeValue.setRepeatAtr(BooleanGetAtr.getAtrByBoolean(settlementPeriod.getRepeatAtr()));
	}

}
