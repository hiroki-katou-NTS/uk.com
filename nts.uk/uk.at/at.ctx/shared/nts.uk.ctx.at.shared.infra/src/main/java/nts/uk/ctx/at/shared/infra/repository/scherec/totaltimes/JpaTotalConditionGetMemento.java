/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.scherec.totaltimes;

import nts.uk.ctx.at.shared.dom.scherec.totaltimes.ConditionThresholdLimit;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshstTotalCondition;

/**
 * The Class JpaTotalConditionGetMemento.
 */
public class JpaTotalConditionGetMemento implements TotalConditionGetMemento {

	/** The entity. */
	private KshstTotalCondition entity;

	/**
	 * Instantiates a new jpa total condition get memento.
	 *
	 * @param totalTimes the total times
	 */
	public JpaTotalConditionGetMemento(KshstTotalCondition totalTimes) {
		this.entity = totalTimes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionGetMemento#
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
	 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionGetMemento#
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
	 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionGetMemento#
	 * getThresoldUpperLimit()
	 */
	@Override
	public ConditionThresholdLimit getThresoldUpperLimit() {
		return new ConditionThresholdLimit(this.entity.getThresoldUpperLimit().intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionGetMemento#
	 * getThresoldLowerLimit()
	 */
	@Override
	public ConditionThresholdLimit getThresoldLowerLimit() {
		return new ConditionThresholdLimit(this.entity.getThresoldLowerLimit().intValue());
	}
	
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionGetMemento#getAttendanceItemId()
	 */
	@Override
	public Integer getAttendanceItemId() {
		return this.entity.getAttendanceItemId();
	}

}
