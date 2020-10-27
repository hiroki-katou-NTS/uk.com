/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;

/**
 * The Class JpaIntervalTimeSetMemento.
 */
public class JpaIntervalTimeSetMemento implements IntervalTimeSetMemento {

	/** The entity. */
	private KshmtWtCom entity;

	/**
	 * Instantiates a new jpa interval time set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaIntervalTimeSetMemento(KshmtWtCom entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSetMemento#
	 * setIntervalTime(nts.uk.ctx.at.shared.dom.common.time.AttendanceTime)
	 */
	@Override
	public void setIntervalTime(AttendanceTime intervalTime) {
		this.entity.setIntervalTime(intervalTime.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSetMemento#
	 * setRounding(nts.uk.ctx.at.shared.dom.common.timerounding.
	 * TimeRoundingSetting)
	 */
	@Override
	public void setRounding(TimeRoundingSetting rounding) {
		this.entity.setIntervalExempUnit(rounding.getRoundingTime().value);
		this.entity.setIntervalExempRounding(rounding.getRounding().value);
	}

}
