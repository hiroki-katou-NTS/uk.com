/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWorktimeCommonSet;

/**
 * The Class JpaIntervalTimeGetMemento.
 */
public class JpaIntervalTimeGetMemento implements IntervalTimeGetMemento {

	/** The entity. */
	private KshmtWorktimeCommonSet entity;

	/**
	 * Instantiates a new jpa interval time get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaIntervalTimeGetMemento(KshmtWorktimeCommonSet entity) {
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
	// TODO: DienTX pls check - liệu có bị nhầm lẫn với rounding tổng ở ngoài 1 cấp!?
	@Override
	public TimeRoundingSetting getRounding() {
		return null;
	}

}
