/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class Timezone.
 */
//時間帯(使用区分付き)
@Getter
public class TimezoneUse extends TimeZone {

	/** The use atr. */
	//使用区分
	private UseSetting useAtr;
	
	/** The work no. */
	//勤務NO
	private int workNo;

	public void updateStartTime(TimeWithDayAttr start) {
		this.start = start;
	}
	
	/**
	 * Update end time.
	 *
	 * @param end the end
	 */
	public void updateEndTime(TimeWithDayAttr end) {
		this.end = end;
	}
	
	/**
	 * Instantiates a new timezone.
	 *
	 * @param memento the memento
	 */
	public TimezoneUse(TimezoneGetMemento memento) {
		this.useAtr = memento.getUseAtr();
		this.workNo = memento.getWorkNo();
		this.start = memento.getStart();
		this.end = memento.getEnd();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(TimezoneSetMemento memento){
		memento.setUseAtr(this.useAtr);
		memento.setWorkNo(this.workNo);
		memento.setStart(this.start);
		memento.setEnd(this.end);
	}

	/**
	 * Checks if is used.
	 *
	 * @return true, if is used
	 */
	public boolean isUsed() {
		return this.useAtr == UseSetting.USE ? true : false;
	}
}
