/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import lombok.Getter;
import nts.arc.error.BusinessException;
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

	/** The shift one. */
	public static Integer SHIFT_ONE = 1;
	
	/** The shift two. */
	public static Integer SHIFT_TWO = 2;

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

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.TimeZone#validate()
	 */
	@Override
	public void validate() {
		if (this.isUsed()) {
			if (this.workNo == SHIFT_TWO && (this.start == null || this.end == null)) {
				throw new BusinessException("Msg_516", "KMK003_216");
			}
			this.validateRange("KMK003_216");
		}
	}

	/**
	 * Restore default data.
	 */
	public void restoreDefaultData() {
		this.start = TimeWithDayAttr.THE_PRESENT_DAY_0000;
		this.end = TimeWithDayAttr.THE_PRESENT_DAY_0000;
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
		
		// If Work No is 1, useAtr allway is USE.
		if (this.workNo == SHIFT_ONE) {
			this.useAtr = UseSetting.USE;
		}
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
		return this.useAtr == UseSetting.USE;
	}
}
