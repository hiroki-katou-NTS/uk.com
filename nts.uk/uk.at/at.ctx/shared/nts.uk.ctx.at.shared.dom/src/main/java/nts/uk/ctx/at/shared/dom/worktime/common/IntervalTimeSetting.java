/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class IntervalTimeSetting.
 */
//インターバル時間設定
@Getter
public class IntervalTimeSetting extends WorkTimeDomainObject {

	/** The use interval exemption time. */
	// インターバル免除時間を使用する
	private boolean useIntervalExemptionTime;

	/** The interval exemption time round. */
	// インターバル免除時間丸め
	private TimeRoundingSetting intervalExemptionTimeRound;

	/** The interval time. */
	// インターバル時間
	private IntervalTime intervalTime;

	/** The use interval time. */
	// インターバル時間を使用する
	private boolean useIntervalTime;
	
	
	/**
	 * Instantiates a new interval time setting.
	 *
	 * @param memento the memento
	 */
	public IntervalTimeSetting(IntervalTimeSettingGetMemento memento) {
		this.useIntervalExemptionTime = memento.getuseIntervalExemptionTime();
		this.intervalExemptionTimeRound = memento.getIntervalExemptionTimeRound();
		this.intervalTime = memento.getIntervalTime();
		this.useIntervalTime = memento.getuseIntervalTime();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(IntervalTimeSettingSetMemento memento){
		memento.setUseIntervalExemptionTime(this.useIntervalExemptionTime);
		memento.setIntervalExemptionTimeRound(this.intervalExemptionTimeRound);
		memento.setIntervalTime(this.intervalTime);
		memento.setUseIntervalTime(this.useIntervalTime);
	}
}
