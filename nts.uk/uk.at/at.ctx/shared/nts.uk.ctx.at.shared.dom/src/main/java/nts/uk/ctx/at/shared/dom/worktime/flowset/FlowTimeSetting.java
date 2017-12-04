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
public class FlowTimeSetting extends DomainObject {

	/** The rouding. */
	// 丸め
	private TimeRoundingSetting rouding;

	/** The passage time. */
	// 経過時間
	private AttendanceTime passageTime;

	/**
	 * Instantiates a new flow time setting.
	 *
	 * @param memento the memento
	 */
	public FlowTimeSetting(FlowTimeSettingGetMemento memento) {
		this.rouding = memento.getRouding();
		this.passageTime = memento.getPassageTime();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlowTimeSettingSetMemento memento) {
		memento.setRouding(this.rouding);
		memento.setPassageTime(this.passageTime);
	}
}
