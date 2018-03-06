/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class WorkingTimeSettingNew.
 */
@Getter
//* 労働時間設定.
public class WorkingTimeSettingNew extends DomainObject {
		
	/** The daily time. */
	/** 日単位. */
	private DailyTimeNew dailyTime;

	/** The weekly time. */
	/** 週間時間. */
	private WeeklyTimeNew weeklyTime;
	
	/**
	 * Instantiates a new working time setting new.
	 *
	 * @param memento the memento
	 */
	public WorkingTimeSettingNew (WorkingTimeSettingNew memento) {
		this.dailyTime  = memento.getDailyTime();
		this.weeklyTime = memento.getWeeklyTime();	
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento (WorkingTimeSettingNewSetMemento memento) {		
		memento.setDailyTime(this.dailyTime);
		memento.setWeeklyTime(this.weeklyTime);
	}
}
