/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.scherec.totaltimes;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.totaltimes.ConditionThresholdLimit;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.memento.TotalConditionSetMemento;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshmtTotalCondition;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshmtTotalConditionPK;

/**
 * The Class JpaTotalConditionSetMemento.
 */
public class JpaTotalConditionSetMemento implements TotalConditionSetMemento {

	/** The entity. */
	private KshmtTotalCondition entity;

	/**
	 * Instantiates a new jpa total condition set memento.
	 *
	 * @param cid the cid
	 * @param totalTimesNo the total times no
	 * @param entity the entity
	 */
	public JpaTotalConditionSetMemento(String cid, Integer totalTimesNo,
			KshmtTotalCondition entity) {
		if (entity.getKshmtTotalConditionPK() == null) {
			KshmtTotalConditionPK pk = new KshmtTotalConditionPK();
			pk.setCid(cid);
			pk.setTotalTimesNo(totalTimesNo);
			entity.setKshmtTotalConditionPK(pk);
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionSetMemento#
	 * setUpperLimitSettingAtr(nts.uk.ctx.at.shared.dom.scherec.totaltimes.
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
	 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionSetMemento#
	 * setLowerLimitSettingAtr(nts.uk.ctx.at.shared.dom.scherec.totaltimes.
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
	 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionSetMemento#
	 * setThresoldUpperLimit(nts.uk.ctx.at.shared.dom.scherec.totaltimes.
	 * ConditionThresholdLimit)
	 */
	@Override
	public void setThresoldUpperLimit(Optional<ConditionThresholdLimit> setThresoldUpperLimit) {
		this.entity.setThresoldUpperLimit(setThresoldUpperLimit.map(c -> c.v()).orElse(null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionSetMemento#
	 * setThresoldLowerLimit(nts.uk.ctx.at.shared.dom.scherec.totaltimes.
	 * ConditionThresholdLimit)
	 */
	@Override
	public void setThresoldLowerLimit(Optional<ConditionThresholdLimit> setThresoldLowerLimit) {
		this.entity.setThresoldLowerLimit(setThresoldLowerLimit.map(c -> c.v()).orElse(null));
	}

	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionSetMemento#setAttendanceItemId(java.lang.Integer)
	 */
	@Override
	public void setAttendanceItemId(Optional<Integer> attendanceItemId) {
		this.entity.setAttendanceItemId(attendanceItemId.orElse(null));
	}
	
}
