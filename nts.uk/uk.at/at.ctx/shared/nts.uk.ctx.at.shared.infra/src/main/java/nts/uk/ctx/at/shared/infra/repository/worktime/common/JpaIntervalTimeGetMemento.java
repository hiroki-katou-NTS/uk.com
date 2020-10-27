/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;

/**
 * The Class JpaIntervalTimeGetMemento.
 */
public class JpaIntervalTimeGetMemento implements IntervalTimeGetMemento {

	/** The entity. */
	private KshmtWtCom entity;

	/**
	 * Instantiates a new jpa interval time get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaIntervalTimeGetMemento(KshmtWtCom entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeGetMemento#
	 * getIntervalTime()
	 */
	// 就業時間帯の共通設定.インターバル時間設定.インターバル時間
	@Override
	public AttendanceTime getIntervalTime() {
		return new AttendanceTime(this.entity.getIntervalTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeGetMemento#
	 * getRounding()
	 */
	@Override
	public TimeRoundingSetting getRounding() {
		return new TimeRoundingSetting(this.entity.getIntervalExempUnit(), this.entity.getIntervalExempRounding());
	}

}
