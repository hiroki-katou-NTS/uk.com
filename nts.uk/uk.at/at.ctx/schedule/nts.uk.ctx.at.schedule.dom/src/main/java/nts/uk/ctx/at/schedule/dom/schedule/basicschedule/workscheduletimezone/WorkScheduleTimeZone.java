/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone;

import javax.inject.Inject;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

/**
 * The Class WorkScheduleTimeZone.
 */
@Getter
// 勤務予定時間帯
public class WorkScheduleTimeZone extends DomainObject {

	@Inject
	private I18NResourcesForUK internationalization;

	/** The schedule cnt. */
	// 予定勤務回数
	private int scheduleCnt;

	/** The schedule start clock. */
	// 予定開始時刻
	private TimeWithDayAttr scheduleStartClock;

	/** The schedule end clock. */
	// 予定終了時刻
	private TimeWithDayAttr scheduleEndClock;

	/** The bounce atr. */
	// 直行直帰区分
	private BounceAtr bounceAtr;

	@Override
	public void validate() {
		super.validate();
	}

	public void validateTime() {
		if (this.scheduleStartClock == null) {
			throw new BusinessException("Msg_438", "73"); //#KSU001_73
		}

		if (this.scheduleEndClock == null) {
			throw new BusinessException("Msg_438", "74"); //#KSU001_74
		}
	}

	/**
	 * Instantiates a new work schedule time zone.
	 *
	 * @param memento
	 *            the memento
	 */
	public WorkScheduleTimeZone(WorkScheduleTimeZoneGetMemento memento) {
		this.scheduleCnt = memento.getScheduleCnt();
		this.scheduleStartClock = memento.getScheduleStartClock();
		this.scheduleEndClock = memento.getScheduleEndClock();
		this.bounceAtr = memento.getBounceAtr();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WorkScheduleTimeZoneSetMemento memento) {
		memento.setScheduleCnt(this.scheduleCnt);
		memento.setScheduleStartClock(this.scheduleStartClock);
		memento.setScheduleEndClock(this.scheduleEndClock);
		memento.setBounceAtr(this.bounceAtr);
	}

	public WorkScheduleTimeZone(int scheduleCnt, TimeWithDayAttr scheduleStartClock, TimeWithDayAttr scheduleEndClock,
			BounceAtr bounceAtr) {
		super();
		this.scheduleCnt = scheduleCnt;
		this.scheduleStartClock = scheduleStartClock;
		this.scheduleEndClock = scheduleEndClock;
		this.bounceAtr = bounceAtr;
	}

	/**
	 * Update again start time and end time
	 * 
	 * @param scheduleStartClock
	 * @param scheduleEndClock
	 */
	public void updateTime(TimeWithDayAttr scheduleStartClock, TimeWithDayAttr scheduleEndClock) {
		this.scheduleStartClock = scheduleStartClock;
		this.scheduleEndClock = scheduleEndClock;
	}

	/**
	 * Update 直行直帰区分
	 * 
	 * @param bounceAtr2
	 */
	public void updateBounceAtr(BounceAtr bounceAtr) {
		this.bounceAtr = bounceAtr;
	}
}
