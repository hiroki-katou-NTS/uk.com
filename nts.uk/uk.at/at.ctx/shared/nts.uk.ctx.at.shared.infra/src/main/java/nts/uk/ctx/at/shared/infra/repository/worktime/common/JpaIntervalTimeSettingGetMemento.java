/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTime;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;

/**
 * The Class JpaIntervalTimeSettingGetMemento.
 */
public class JpaIntervalTimeSettingGetMemento implements IntervalTimeSettingGetMemento {

	/** The entity. */
	private KshmtWtCom entity;

	/**
	 * Instantiates a new jpa interval time setting get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaIntervalTimeSettingGetMemento(KshmtWtCom entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingGetMemento#
	 * getuseIntervalExemptionTime()
	 */
	@Override
	public boolean getuseIntervalExemptionTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUseIntervalExempTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingGetMemento#
	 * getIntervalExemptionTimeRound()
	 */
	@Override
	public TimeRoundingSetting getIntervalExemptionTimeRound() {
		return new TimeRoundingSetting(this.entity.getIntervalExempUnit(),
				this.entity.getIntervalExempRounding());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingGetMemento#
	 * getIntervalTime()
	 */
	@Override
	public IntervalTime getIntervalTime() {
		return new IntervalTime(new JpaIntervalTimeGetMemento(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingGetMemento#
	 * getuseIntervalTime()
	 */
	@Override
	public boolean getuseIntervalTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUseIntervalTime());
	}

}
