/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;

/**
 * The Class DiffTimeOTTimezoneSet.
 */
@Getter
public class DiffTimeOTTimezoneSet extends OverTimeOfTimeZoneSet {

	/** The is update start time. */
	// 開始時刻に合わせて時刻を変動させる
	private boolean isUpdateStartTime;

	/**
	 * Instantiates a new diff time OT timezone set.
	 *
	 * @param memento the memento
	 */
	public DiffTimeOTTimezoneSet(DiffTimeOTTimezoneGetMemento memento) {
		super(memento);
		this.isUpdateStartTime = memento.isIsUpdateStartTime();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(DiffTimeOTTimezoneSetMemento memento) {
		memento.setIsUpdateStartTime(this.isUpdateStartTime);
		super.saveToMemento(memento);
	}

	public void restoreData(DiffTimeOTTimezoneSet other) {
		this.isUpdateStartTime = other.isUpdateStartTime;
		super.restoreData(other);
	}
}
