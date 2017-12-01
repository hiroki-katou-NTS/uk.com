/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class TimeDiffDayOffWorkTimezone.
 */
//時差勤務の休日出勤用勤務時間帯
@Getter
public class DiffTimeDayOffWorkTimezone extends DomainObject {

	/** The rest timezone. */
	// 休憩時間帯
	private DiffTimeRestTimezone restTimezone;

	/** The work timezone. */
	// 勤務時間帯
	private List<DayOffTimezoneSetting> workTimezone;

	/**
	 * Instantiates a new diff time day off work timezone.
	 *
	 * @param memento
	 *            the memento
	 */
	public DiffTimeDayOffWorkTimezone(DiffTimeDayOffWorkTimezoneGetMemento memento) {
		this.restTimezone = memento.getRestTimezone();
		this.workTimezone = memento.getWorkTimezone();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(DiffTimeDayOffWorkTimezoneSetMemento memento) {
		memento.setRestTimezone(this.restTimezone);
		memento.setWorkTimezone(this.workTimezone);
	}
}
