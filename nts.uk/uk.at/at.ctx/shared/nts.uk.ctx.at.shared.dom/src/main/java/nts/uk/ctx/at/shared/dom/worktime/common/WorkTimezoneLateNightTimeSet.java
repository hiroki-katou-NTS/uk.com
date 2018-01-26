/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class WorkTimezoneLateNightTimeSet.
 */
// 就業時間帯の深夜時間設定
@Getter
public class WorkTimezoneLateNightTimeSet extends WorkTimeDomainObject {

	/** The rounding setting. */
	// 丸め設定
	private TimeRoundingSetting roundingSetting;

	/**
	 * Instantiates a new work timezone late night time set.
	 *
	 * @param roundingSetting the rounding setting
	 */
	public WorkTimezoneLateNightTimeSet(TimeRoundingSetting roundingSetting) {
		super();
		this.roundingSetting = roundingSetting;
	}
	
	/**
	 * Instantiates a new work timezone late night time set.
	 *
	 * @param memento the memento
	 */
	public WorkTimezoneLateNightTimeSet(WorkTimezoneLateNightTimeSetGetMemento memento) {
		this.roundingSetting = memento.getRoundingSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkTimezoneLateNightTimeSetSetMemento memento) {
		memento.setRoundingSetting(this.roundingSetting);
	}

	
}
