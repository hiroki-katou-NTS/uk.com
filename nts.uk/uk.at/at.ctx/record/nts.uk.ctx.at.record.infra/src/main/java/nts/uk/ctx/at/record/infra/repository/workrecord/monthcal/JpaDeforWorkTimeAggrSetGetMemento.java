/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal;

import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.DeforLaborCalSetting;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforLaborSettlementPeriod;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSetGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.KrcstDeforMCalSet;
import nts.uk.ctx.at.shared.dom.common.Month;

/**
 * The Class JpaDeforWorkTimeAggrSetGetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaDeforWorkTimeAggrSetGetMemento<T extends KrcstDeforMCalSet>
		implements DeforWorkTimeAggrSetGetMemento {

	/** The type value. */
	private T typeValue;

	/**
	 * Instantiates a new jpa defor work time aggr set get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaDeforWorkTimeAggrSetGetMemento(T typeValue) {
		super();
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
	 * DeforWorkTimeAggrSetGetMemento#getAggregateTimeSet()
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
	 * DeforWorkTimeAggrSetGetMemento#getExcessOutsideTimeSet()
	 */
	@Override
	public ExcessOutsideTimeSetReg getExcessOutsideTimeSet() {
		return new ExcessOutsideTimeSetReg(
				BooleanGetAtr.getAtrByInteger(this.typeValue.getIncludeLegalOt()),
				BooleanGetAtr.getAtrByInteger(this.typeValue.getIncludeHolidayOt()),
				BooleanGetAtr.getAtrByInteger(this.typeValue.getIncludeExtraOt()),
				BooleanGetAtr.getAtrByInteger(this.typeValue.getExceptLegalHdwkOt()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
	 * DeforWorkTimeAggrSetGetMemento#getDeforLaborCalSetting()
	 */
	@Override
	public DeforLaborCalSetting getDeforLaborCalSetting() {
		return new DeforLaborCalSetting(BooleanGetAtr.getAtrByInteger(this.typeValue.getIsOtIrg()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
	 * DeforWorkTimeAggrSetGetMemento#getSettlementPeriod()
	 */
	@Override
	public DeforLaborSettlementPeriod getSettlementPeriod() {
		return new DeforLaborSettlementPeriod(new Month(this.typeValue.getStrMonth()),
				new Month(this.typeValue.getPeriod()),
				BooleanGetAtr.getAtrByInteger(this.typeValue.getRepeatAtr()));
	}

}
