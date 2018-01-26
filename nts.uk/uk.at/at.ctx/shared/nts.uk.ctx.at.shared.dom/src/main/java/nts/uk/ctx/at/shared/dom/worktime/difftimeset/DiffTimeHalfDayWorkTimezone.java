/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class DiffTimeHalfDayWorkTimezone.
 */
// 時差勤務の平日出勤用勤務時間帯
@Getter
public class DiffTimeHalfDayWorkTimezone extends WorkTimeDomainObject {

	/** The rest timezone. */
	// 休憩時間帯
	private DiffTimeRestTimezone restTimezone;

	/** The work timezone. */
	// 勤務時間帯
	private DiffTimezoneSetting workTimezone;

	/** The am pm atr. */
	// 午前午後区分
	private AmPmAtr amPmAtr;

	/**
	 * Instantiates a new diff time half day work timezone.
	 *
	 * @param memento the memento
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
	
	/**
	 * Restore data.
	 *
	 * @param screenMode the screen mode
	 * @param diffTimeWorkSet the diff time work set
	 * @param other the other
	 */
	public void restoreData(ScreenMode screenMode, DiffTimeWorkSetting diffTimeWorkSet,
			DiffTimeHalfDayWorkTimezone other) {
		switch (screenMode) {
		case SIMPLE:
			this.restoreSimpleMode(other);
			break;
		case DETAIL:
			this.restoreDetailMode(diffTimeWorkSet, other);;
			break;
		default:
			throw new RuntimeException("ScreenMode not found.");
		}
	}
	
	/**
	 * Restore simple mode.
	 *
	 * @param other the other
	 */
	private void restoreSimpleMode(DiffTimeHalfDayWorkTimezone other) {
		if (other.getAmPmAtr() != AmPmAtr.ONE_DAY) {
			this.workTimezone.restoreData(other.getWorkTimezone());
			this.restTimezone.restoreData(other.getRestTimezone());
		}
	}
	
	/**
	 * Restore detail mode.
	 *
	 * @param diffTimeWorkSet the diff time work set
	 * @param other the other
	 */
	private void restoreDetailMode(DiffTimeWorkSetting diffTimeWorkSet, DiffTimeHalfDayWorkTimezone other) {
		// restore data of dayAtr = AM, PM
		if (!diffTimeWorkSet.isUseHalfDayShift() && other.getAmPmAtr() != AmPmAtr.ONE_DAY) {
			this.workTimezone.restoreData(other.getWorkTimezone());
			this.restTimezone.restoreData(other.getRestTimezone());
		}
	}
}
