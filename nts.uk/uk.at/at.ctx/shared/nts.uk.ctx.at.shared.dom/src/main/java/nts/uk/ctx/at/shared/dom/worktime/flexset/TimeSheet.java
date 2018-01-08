/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class TimeSheet.
 */
// 時間帯
@Getter
public class TimeSheet extends DomainObject {

	/** The start time. */
	// 開始時刻
	private TimeWithDayAttr startTime;

	/** The end time. */
	// 終了時刻
	private TimeWithDayAttr endTime;
	
	
	/**
	 * Instantiates a new time sheet.
	 *
	 * @param memento the memento
	 */
	public TimeSheet(TimeSheetGetMemento memento) {
		this.startTime = memento.getStartTime();
		this.endTime = memento.getEndTime();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(TimeSheetSetMemento memento){
		memento.setStartTime(this.startTime);
		memento.setEndTime(this.endTime);
	}
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate(){
		super.validate();
		// コアタイム時間帯.開始時刻 >= コアタイム時間帯.終了時刻 => Msg_770 
		if (this.startTime.greaterThanOrEqualTo(this.endTime)) {
			throw new BusinessException("Msg_770","KMK003_157");
		}
	}
}
