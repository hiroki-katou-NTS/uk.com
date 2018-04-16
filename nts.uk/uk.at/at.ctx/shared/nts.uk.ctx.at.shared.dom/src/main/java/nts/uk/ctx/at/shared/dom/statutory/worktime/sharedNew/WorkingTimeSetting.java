/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class WorkingTimeSetting.
 */
@Getter
// 労働時間設定
public class WorkingTimeSetting extends DomainObject {

	/** The weekly time. */
	// 週単位
	private WeeklyUnit weeklyTime;

	/** The daily time. */
	// 日単位
	private DailyUnit dailyTime;

	/**
	 * Instantiates a new working time setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public WorkingTimeSetting(WorkingTimeSettingGetMemento memento) {
		this.dailyTime = memento.getDailyTime();
		this.weeklyTime = memento.getWeeklyTime();
	}

	public WorkingTimeSetting(WeeklyUnit weeklyTime, DailyUnit dailyTime) {
		this.weeklyTime = weeklyTime;
		this.dailyTime = dailyTime;
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WorkingTimeSettingSetMemento memento) {
		memento.setDailyTime(this.dailyTime);
		memento.setWeeklyTime(this.weeklyTime);
	}
}
