/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import lombok.Getter;

//就業時間帯勤務区分
/**
 * The Class WorkTimeDivision.
 */
@Getter
public class WorkTimeDivision {

	/** The work time daily atr. */
	// 勤務形態区分
	private WorkTimeDailyAtr workTimeDailyAtr;

	/** The work time method set. */
	// 就業時間帯の設定方法
	private WorkTimeMethodSet workTimeMethodSet;

	/**
	 * Instantiates a new work time division.
	 *
	 * @param memento the memento
	 */
	public WorkTimeDivision(WorkTimeDivivsionGetMemento memento) {
		this.workTimeDailyAtr = memento.getWorkTimeDailyAtr();
		this.workTimeMethodSet = memento.getWorkTimeMethodSet();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkTimeDivivsionSetMemento memento) {
		memento.setWorkTimeDailyAtr(this.workTimeDailyAtr);
		memento.setWorkTimeMethodSet(this.workTimeMethodSet);
	}
}
