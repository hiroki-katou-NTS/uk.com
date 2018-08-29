/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
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

	public Map<String, String> validateTime() {
		Map<String, String> msgErrMap = new HashMap<String, String>();
		if (this.scheduleStartClock == null) {
			msgErrMap.put("KSU001_73", "Msg_438");
		}
		if (this.scheduleEndClock == null) {
			msgErrMap.put("KSU001_74", "Msg_438");
		}
		return msgErrMap;
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
	
	public static WorkScheduleTimeZone createFromJavaType(int scheduleCnt, int scheduleStartClock, int scheduleEndClock,
			int bounceAtr) {
		return new WorkScheduleTimeZone(scheduleCnt, new TimeWithDayAttr(scheduleStartClock),
				new TimeWithDayAttr(scheduleEndClock), EnumAdaptor.valueOf(bounceAtr, BounceAtr.class));
	}

	/**
	 * Update 直行直帰区分
	 * 
	 * @param bounceAtr2
	 */
	public void updateBounceAtr(BounceAtr bounceAtr) {
		this.bounceAtr = bounceAtr;
	}
	
	public boolean diffScheduleStartClock(TimeWithDayAttr scheStartClock){
		return scheduleStartClock.v().intValue() != scheStartClock.v().intValue();
	}
	
	public boolean diffScheduleEndClock(TimeWithDayAttr scheEndClock){
		return scheduleEndClock.v().intValue() != scheEndClock.v().intValue();
	}
	
	public boolean diffBounceAtr(BounceAtr bAtr){
		return bounceAtr.value != bAtr.value;
	}
}
