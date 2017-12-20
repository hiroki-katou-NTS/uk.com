/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;

/**
 * The Class DiffTimeDeductTimezone.
 */
// 時差勤務の控除時間帯
@Getter
public class DiffTimeDeductTimezone extends DeductionTime {

	/** The is update start time. */
	// 開始時刻に合わせて時刻を変動させる
	private boolean isUpdateStartTime;

	/**
	 * Instantiates a new diff time deduct timezone.
	 *
	 * @param memento
	 *            the memento
	 */
	public DiffTimeDeductTimezone(DiffTimeDeductTimezoneGetMemento memento) {
		super(memento);
		this.isUpdateStartTime = memento.isIsUpdateStartTime();
	}

	public void saveToMemento(DiffTimeDeductTimezoneSetMemento memento) {
		memento.setIsUpdateStartTime(this.isUpdateStartTime);
		super.saveToMemento(memento);
	}
}
