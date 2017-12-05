/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;

/**
 * The Class TimeDiffHalfDayWorkTimezone.
 */
// 時差勤務の平日出勤用勤務時間帯
@Getter
public class DiffTimeHalfDayWorkTimezone extends DomainObject {

	/** The rest timezone. */
	// 休憩時間帯
	private DiffTimeRestTimezone restTimezone;

	/** The work timezone. */
	// 勤務時間帯
	private DiffTimezoneSetting workTimezone;

	/** The Am pm cls. */
	// 午前午後区分
	private AmPmAtr amPmAtr;

	/**
	 * Instantiates a new diff time half day work timezone.
	 *
	 * @param memento
	 *            the memento
	 */
	public DiffTimeHalfDayWorkTimezone(DiffTimeHalfDayGetMemento memento) {
		this.restTimezone = memento.getRestTimezone();
		this.workTimezone = memento.getWorkTimezone();
		this.amPmAtr = memento.getAmPmAtr();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(DiffTimeHalfDaySetMemento memento) {
		memento.setRestTimezone(this.restTimezone);
		memento.setWorkTimezone(this.workTimezone);
		memento.setAmPmAtr(this.amPmAtr);
	}
}
