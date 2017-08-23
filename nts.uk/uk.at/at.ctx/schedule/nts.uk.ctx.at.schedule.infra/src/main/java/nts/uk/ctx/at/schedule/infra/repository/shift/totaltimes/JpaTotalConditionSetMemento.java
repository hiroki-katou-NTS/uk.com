/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.totaltimes;

import nts.uk.ctx.at.schedule.dom.shift.totaltimes.ConditionThresholdLimit;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalConditionSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.UseAtr;
import nts.uk.ctx.at.schedule.infra.entity.shift.totaltimes.KshstTotalCondition;

/**
 * The Class JpaTotalConditionSetMemento.
 */
public class JpaTotalConditionSetMemento implements TotalConditionSetMemento {

	/** The entity. */
	private KshstTotalCondition entity;

	/**
	 * Instantiates a new jpa total condition set memento.
	 *
	 * @param totalTimes
	 *            the total times
	 */
	public JpaTotalConditionSetMemento(KshstTotalCondition totalTimes) {
		this.entity = totalTimes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalConditionSetMemento#
	 * setUpperLimitSettingAtr(nts.uk.ctx.at.schedule.dom.shift.totaltimes.
	 * UseAtr)
	 */
	@Override
	public void setUpperLimitSettingAtr(UseAtr setUpperLimitSettingAtr) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalConditionSetMemento#
	 * setLowerLimitSettingAtr(nts.uk.ctx.at.schedule.dom.shift.totaltimes.
	 * UseAtr)
	 */
	@Override
	public void setLowerLimitSettingAtr(UseAtr setLowerLimitSettingAtr) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalConditionSetMemento#
	 * setThresoldUpperLimit(nts.uk.ctx.at.schedule.dom.shift.totaltimes.
	 * ConditionThresholdLimit)
	 */
	@Override
	public void setThresoldUpperLimit(ConditionThresholdLimit setThresoldUpperLimit) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalConditionSetMemento#
	 * setThresoldLowerLimit(nts.uk.ctx.at.schedule.dom.shift.totaltimes.
	 * ConditionThresholdLimit)
	 */
	@Override
	public void setThresoldLowerLimit(ConditionThresholdLimit setThresoldLowerLimit) {
		// TODO Auto-generated method stub

	}
}
