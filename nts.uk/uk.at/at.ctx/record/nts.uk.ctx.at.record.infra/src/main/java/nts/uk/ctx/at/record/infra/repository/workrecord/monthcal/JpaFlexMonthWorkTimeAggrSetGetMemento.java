/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggregateSetting;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggregateTimeSetting;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.CarryforwardSetInShortageFlex;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.SettlePeriod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.SettlePeriodMonths;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.ShortageFlexSetting;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSetGetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.KrcstFlexMCalSet;
import nts.uk.ctx.at.shared.dom.common.Month;

/**
 * The Class JpaFlexMonthWorkTimeAggrSetGetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaFlexMonthWorkTimeAggrSetGetMemento<T extends KrcstFlexMCalSet>
		implements FlexMonthWorkTimeAggrSetGetMemento {

	/** The type value. */
	private T typeValue;

	/**
	 * Instantiates a new jpa flex month work time aggr set get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaFlexMonthWorkTimeAggrSetGetMemento(T typeValue) {
		super();
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
	 * FlexMonthWorkTimeAggrSetGetMemento#getAggrMethod()
	 */
	@Override
	public FlexAggregateMethod getAggrMethod() {
		return FlexAggregateMethod.valueOf(this.typeValue.getAggrMethod());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
	 * FlexMonthWorkTimeAggrSetGetMemento#getInsufficSet()
	 */
	@Override
	public ShortageFlexSetting getInsufficSet() {
		return ShortageFlexSetting.of(
				EnumAdaptor.valueOf(this.typeValue.getInsufficSet(), CarryforwardSetInShortageFlex.class),
				EnumAdaptor.valueOf(this.typeValue.getSettlePeriod(), SettlePeriod.class),
				new Month(this.typeValue.getStartMonth()),
				EnumAdaptor.valueOf(this.typeValue.getSettlePeriodMon(), SettlePeriodMonths.class));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
	 * FlexMonthWorkTimeAggrSetGetMemento#getLegalAggrSet()
	 */
	@Override
	public AggregateTimeSetting getLegalAggrSet() {
		return AggregateTimeSetting
				.of(EnumAdaptor.valueOf(this.typeValue.getLegalAggrSet(), AggregateSetting.class));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
	 * FlexMonthWorkTimeAggrSetGetMemento#getIncludeOverTime()
	 */
	@Override
	public Boolean getIncludeOverTime() {
		return (this.typeValue.getIncludeOt() != 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
	 * FlexMonthWorkTimeAggrSetGetMemento#getIncludeIllegalHdwk()
	 */
	@Override
	public Boolean getIncludeIllegalHdwk() {
		return (this.typeValue.getIncludeHdwk() != 0);
	}
}
