/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.totaltimes;

import nts.uk.ctx.at.schedule.dom.shift.totaltimes.ConditionThresholdLimit;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalConditionSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.UseAtr;
import nts.uk.ctx.at.schedule.infra.entity.shift.totaltimes.KshstTotalCondition;
import nts.uk.ctx.at.schedule.infra.entity.shift.totaltimes.KshstTotalConditionPK;

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
	public JpaTotalConditionSetMemento(String cid, Integer totalTimesNo,
			KshstTotalCondition entity) {
		if (entity.getKshstTotalConditionPK() == null) {
			KshstTotalConditionPK pk = new KshstTotalConditionPK();
			pk.setCid(cid);
			pk.setTotalTimesNo(totalTimesNo);
			entity.setKshstTotalConditionPK(pk);
		}
		this.entity = entity;
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
		this.entity.setUpperLimitSetAtr(setUpperLimitSettingAtr.value);
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
		this.entity.setLowerLimitSetAtr(setLowerLimitSettingAtr.value);
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
		this.entity.setThresoldUpperLimit(setThresoldUpperLimit.v());
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
		this.entity.setThresoldLowerLimit(setThresoldLowerLimit.v());
	}
}
