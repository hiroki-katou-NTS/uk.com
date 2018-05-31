/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class WorkTimezoneExtraordTimeSet.
 */
// 就業時間帯の臨時時間設定
@Getter
public class WorkTimezoneExtraordTimeSet extends WorkTimeDomainObject {

	/** The holiday frame set. */
	// 休出枠設定
	private HolidayFramset holidayFrameSet;

	/** The time rounding set. */
	// 時間丸め設定
	private TimeRoundingSetting timeRoundingSet;

	/** The o T frame set. */
	// 残業枠設定
	private ExtraordWorkOTFrameSet oTFrameSet;

	/** The calculate method. */
	// 計算方法
	private ExtraordTimeCalculateMethod calculateMethod;

	/**
	 * Instantiates a new work timezone extraord time set.
	 *
	 * @param memento
	 *            the memento
	 */
	public WorkTimezoneExtraordTimeSet(WorkTimezoneExtraordTimeSetGetMemento memento) {
		this.holidayFrameSet = memento.getHolidayFrameSet();
		this.timeRoundingSet = memento.getTimeRoundingSet();
		this.oTFrameSet = memento.getOTFrameSet();
		this.calculateMethod = memento.getCalculateMethod();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WorkTimezoneExtraordTimeSetSetMemento memento) {
		memento.setHolidayFrameSet(this.holidayFrameSet);
		memento.setTimeRoundingSet(this.timeRoundingSet);
		memento.setOTFrameSet(this.oTFrameSet);
		memento.setCalculateMethod(this.calculateMethod);
	}

	/**
	 * Correct data.
	 *
	 * @param screenMode
	 *            the screen mode
	 */
	public void correctData(ScreenMode screenMode) {
		if (ScreenMode.SIMPLE.equals(screenMode)) {
			this.timeRoundingSet.setDefaultDataRoundingDown();
		}
	}
}
