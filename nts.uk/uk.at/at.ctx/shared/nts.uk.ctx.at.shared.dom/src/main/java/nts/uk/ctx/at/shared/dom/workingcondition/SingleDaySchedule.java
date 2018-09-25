/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * The Class SingleDaySchedule.
 */
// 単一日勤務予定
@Getter
public class SingleDaySchedule extends DomainObject {

	/** The work type code. */
	// 勤務種類コード
	private Optional<WorkTypeCode> workTypeCode;

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
		this.workTypeCode = memento.getWorkTypeCode();
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
		memento.setWorkTypeCode(this.workTypeCode);
		memento.setWorkTimeCode(this.workTimeCode);
		memento.setWorkingHours(this.workingHours);
	}

	public SingleDaySchedule(String workTypeCode, List<TimeZone> workingHours,
			Optional<String> workTimeCode) {
		super();
		this.workTypeCode = Optional.ofNullable(StringUtils.isEmpty(workTypeCode) ? null : new WorkTypeCode(workTypeCode));
		this.workingHours = workingHours;
		this.workTimeCode = Optional.empty();
		if (workTimeCode.isPresent()){
			this.workTimeCode = Optional.of(new WorkTimeCode(workTimeCode.get()));
		}
	}

}
