/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class DeductionTime.
 */
@Getter
// 控除時間帯(丸め付き)
public class DeductionTime {

	/** The start. */
	// 開始
	private TimeWithDayAttr start;

	/** The end. */
	// 終了
	private TimeWithDayAttr end;

	@Override
	public String toString() {
		return start + "," + end;
	}
	
	/**
	 * Instantiates a new deduction time.
	 *
	 * @param memento the memento
	 */
	public DeductionTime(DeductionTimeGetMemento memento){
		this.start = memento.getStart();
		this.end = memento.getEnd();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(DeductionTimeSetMemento memento){
		memento.setStart(this.start);
		memento.setEnd(this.end);
	}
}
