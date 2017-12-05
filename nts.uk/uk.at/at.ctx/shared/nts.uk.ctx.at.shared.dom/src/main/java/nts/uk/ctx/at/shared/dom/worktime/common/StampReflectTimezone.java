/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class StampReflectTimezone.
 */
//打刻反映時間帯
@Getter
public class StampReflectTimezone extends DomainObject {

	/** The work no. */
	// 勤務NO
	private WorkNo workNo;

	/** The classification. */
	// 出退勤区分
	private GoLeavingWorkAtr classification;

	/** The end time. */
	// 終了時刻
	private TimeWithDayAttr endTime;

	/** The start time. */
	// 開始時刻
	private TimeWithDayAttr startTime;

	/**
	 * Instantiates a new stamp reflect timezone.
	 *
	 * @param memento the memento
	 */
	public StampReflectTimezone(StampReflectTimezoneGetMemento memento) {
		this.workNo = memento.getWorkNo();
		this.classification = memento.getClassification();
		this.endTime = memento.getEndTime();
		this.startTime = memento.getStartTime();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(StampReflectTimezoneSetMemento memento) {
		memento.setWorkNo(this.workNo);
		memento.setClassification(this.classification);
		memento.setEndTime(this.endTime);
		memento.setStartTime(this.startTime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();

		// TODO: Validate message 516
	}
	
}
