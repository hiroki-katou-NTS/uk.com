/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

/**
 * The Class SingleDaySchedule.
 * xóa workTypeCode đi
 */
// 単一日勤務予定
@Getter
public class SingleDaySchedule extends DomainObject {

	/** The working hours. */
	// 勤務時間帯
	private List<TimeZone> workingHours;

	/** The work time code. */
	// 就業時間帯コード
	private Optional<WorkTimeCode> workTimeCode;

	/**
	 * Instantiates a new single day schedule.
	 *
	 * @param memento
	 *            the memento
	 */
	public SingleDaySchedule(SingleDayScheduleGetMemento memento) {
		this.workingHours = memento.getWorkingHours();
		this.workTimeCode = memento.getWorkTimeCode();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(SingleDayScheduleSetMemento memento) {
		memento.setWorkTimeCode(this.workTimeCode);
		memento.setWorkingHours(this.workingHours);
	}

	public SingleDaySchedule( List<TimeZone> workingHours,
			Optional<String> workTimeCode) {
		super();
		this.workingHours = workingHours;
		this.workTimeCode = Optional.empty();
		if (workTimeCode.isPresent()){
			this.workTimeCode = Optional.of(new WorkTimeCode(workTimeCode.get()));
		}
	}

}
