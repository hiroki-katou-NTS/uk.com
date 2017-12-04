/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Class WorkTimezoneMedicalSet.
 */
// 就業時間帯の医療設定
@Getter
public class WorkTimezoneMedicalSet extends DomainObject {

	/** The rounding set. */
	// 丸め設定
	private TimeRoundingSetting roundingSet;

	/** The work system atr. */
	// 勤務体系区分
	private WorkSystemAtr workSystemAtr;

	/** The application time. */
	// 申送時間
	private OneDayTime applicationTime;

	/**
	 * Instantiates a new work timezone medical set.
	 *
	 * @param memento the memento
	 */
	public WorkTimezoneMedicalSet(WorkTimezoneMedicalSetGetMemento memento) {
		this.roundingSet = memento.getRoundingSet();
		this.workSystemAtr = memento.getWorkSystemAtr();
		this.applicationTime = memento.getApplicationTime();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkTimezoneMedicalSetSetMemento memento) {
		memento.setRoundingSet(this.roundingSet);
		memento.setWorkSystemAtr(this.workSystemAtr);
		memento.setApplicationTime(this.applicationTime);
	}

}
