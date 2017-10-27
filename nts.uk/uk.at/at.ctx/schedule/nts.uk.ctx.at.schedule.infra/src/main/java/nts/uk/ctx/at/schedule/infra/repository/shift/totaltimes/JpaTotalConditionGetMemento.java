/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.totaltimes;

import nts.uk.ctx.at.schedule.dom.shift.totaltimes.ConditionThresholdLimit;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalConditionGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.UseAtr;
import nts.uk.ctx.at.schedule.infra.entity.shift.totaltimes.KshstTotalCondition;

/**
 * The Class JpaTotalConditionGetMemento.
 */
public class JpaTotalConditionGetMemento implements TotalConditionGetMemento {

	/** The entity. */
	private KshstTotalCondition entity;

	/**
	 * Instantiates a new jpa total condition get memento.
	 *
	 * @param totalTimes
	 *            the total times
	 */
	public JpaTotalConditionGetMemento(KshstTotalCondition totalTimes) {
		this.entity = totalTimes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalConditionGetMemento#
	 * getUpperLimitSettingAtr()
	 */
	@Override
	public UseAtr getUpperLimitSettingAtr() {
		return UseAtr.valueOf(this.entity.getUpperLimitSetAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalConditionGetMemento#
	 * getLowerLimitSettingAtr()
	 */
	@Override
	public UseAtr getLowerLimitSettingAtr() {
		return UseAtr.valueOf(this.entity.getLowerLimitSetAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalConditionGetMemento#
	 * getThresoldUpperLimit()
	 */
	@Override
	public ConditionThresholdLimit getThresoldUpperLimit() {
		return new ConditionThresholdLimit((int)this.entity.getThresoldUpperLimit());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalConditionGetMemento#
	 * getThresoldLowerLimit()
	 */
	@Override
	public ConditionThresholdLimit getThresoldLowerLimit() {
		return new ConditionThresholdLimit((int)this.entity.getThresoldLowerLimit());
	}

}
