/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.scherec.totaltimes;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.totaltimes.ConditionThresholdLimit;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.memento.TotalConditionGetMemento;
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
	public Optional<ConditionThresholdLimit> getThresoldUpperLimit() {
		if (this.entity.getThresoldUpperLimit() == null) {
			return Optional.empty();
		}
		
		return Optional.of(new ConditionThresholdLimit(this.entity.getThresoldUpperLimit()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionGetMemento#
	 * getThresoldLowerLimit()
	 */
	@Override
	public Optional<ConditionThresholdLimit> getThresoldLowerLimit() {
		if (this.entity.getThresoldLowerLimit() == null) {
			return Optional.empty();
		}
		
		return Optional.of(new ConditionThresholdLimit(this.entity.getThresoldLowerLimit()));
	}
	
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionGetMemento#getAttendanceItemId()
	 */
	@Override
	public Optional<Integer> getAttendanceItemId() {
		
		return Optional.ofNullable(this.entity.getAttendanceItemId());
	}

}
