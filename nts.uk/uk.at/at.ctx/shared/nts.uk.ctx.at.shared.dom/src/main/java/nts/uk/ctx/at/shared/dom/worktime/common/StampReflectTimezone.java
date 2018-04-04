/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Builder;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class StampReflectTimezone.
 */
//打刻反映時間帯
@Getter
@Builder
public class StampReflectTimezone extends WorkTimeDomainObject {

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
	 * @param workNo the work no
	 * @param classification the classification
	 * @param endTime the end time
	 * @param startTime the start time
	 */
	public StampReflectTimezone(WorkNo workNo, GoLeavingWorkAtr classification, TimeWithDayAttr endTime,
			TimeWithDayAttr startTime) {
		super();
		this.workNo = workNo;
		this.classification = classification;
		this.endTime = endTime;
		this.startTime = startTime;
	}

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
	
	/**
	 * Update start time.
	 *
	 * @param startTime the start time
	 */
	public void updateStartTime(TimeWithDayAttr startTime) {
		this.startTime = startTime;
	}

	/**
	 * Update end time.
	 *
	 * @param endTime the end time
	 */
	public void updateEndTime(TimeWithDayAttr endTime) {
		this.endTime = endTime;
	}

}
