/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Class FlowTimeSetting.
 */
//流動時間設定
@Getter
public class FlTimeSetting extends DomainObject {

	/** The rounding. */
	// 丸め
	private TimeRoundingSetting rounding;

	/** The passage time. */
	// 経過時間
	private AttendanceTime passageTime;

	/**
	 * Instantiates a new flow time setting.
	 *
	 * @param memento the memento
	 */
	public FlTimeSetting(FlTimeGetMemento memento) {
		this.rounding = memento.getRouding();
		this.passageTime = memento.getPassageTime();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlTimeSetMemento memento) {
		memento.setRouding(this.rounding);
		memento.setPassageTime(this.passageTime);
	}
}
