/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;

/**
 * The Class DailyTimeNew.
 */
@Getter
// 日の時間.
public class DailyTimeNew extends DomainObject {
	
	/** The Constant DEFAULT_VALUE. */
	public static final int DEFAULT_VALUE = 0;
	
	/** The daily time. */
	/** 1日の時間. */
	private TimeOfDay dailyTime;
	
	/**
	 * Instantiates a new daily time new.
	 *
	 * @param memento the memento
	 */
	public DailyTimeNew (DailyTimeNew memento) {
		this.dailyTime  = memento.getDailyTime();
		
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento (DailyTimeNewSetMemento memento) {
		memento.setTimeOfDay(this.dailyTime);
	}
}
