/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTime;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;

/**
 * The Class JpaIntervalTimeSettingSetMemento.
 */
public class JpaIntervalTimeSettingSetMemento implements IntervalTimeSettingSetMemento {

	/** The entity. */
	private KshmtWtCom entity;

	/**
	 * Instantiates a new jpa interval time setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaIntervalTimeSettingSetMemento(KshmtWtCom entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingSetMemento#
	 * setUseIntervalExemptionTime(boolean)
	 */
	@Override
	public void setUseIntervalExemptionTime(boolean useIntervalExemptionTime) {
		this.entity.setUseIntervalExempTime(BooleanGetAtr.getAtrByBoolean(useIntervalExemptionTime));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingSetMemento#
	 * setIntervalExemptionTimeRound(nts.uk.ctx.at.shared.dom.common.
	 * timerounding.TimeRoundingSetting)
	 */
	@Override
	public void setIntervalExemptionTimeRound(TimeRoundingSetting intervalExemptionTimeRound) {
		this.entity.setIntervalExempUnit(intervalExemptionTimeRound.getRoundingTime().value);
		this.entity.setIntervalExempRounding(intervalExemptionTimeRound.getRounding().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingSetMemento#
	 * setIntervalTime(nts.uk.ctx.at.shared.dom.worktime.common.IntervalTime)
	 */
	@Override
	public void setIntervalTime(IntervalTime intervalTime) {
		intervalTime.saveToMemento(new JpaIntervalTimeSetMemento(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingSetMemento#
	 * setUseIntervalTime(boolean)
	 */
	@Override
	public void setUseIntervalTime(boolean useIntervalTime) {
		this.entity.setUseIntervalTime(BooleanGetAtr.getAtrByBoolean(useIntervalTime));
	}

}
