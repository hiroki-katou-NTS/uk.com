/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class IntervalTime.
 */
//インターバル時間
@Getter
public class IntervalTime extends WorkTimeDomainObject {
	
	/** The interval time. */
	//インターバル時間
	private AttendanceTime intervalTime;
	
	/** The rounding. */
	//丸め
	private TimeRoundingSetting rounding;
	
	/**
	 * Instantiates a new interval time.
	 *
	 * @param memento the memento
	 */
	public IntervalTime(IntervalTimeGetMemento memento) {
		this.intervalTime = memento.getIntervalTime();
		this.rounding = memento.getRounding();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(IntervalTimeSetMemento memento){
		memento.setIntervalTime(this.intervalTime);
		memento.setRounding(this.rounding);
	}
}
