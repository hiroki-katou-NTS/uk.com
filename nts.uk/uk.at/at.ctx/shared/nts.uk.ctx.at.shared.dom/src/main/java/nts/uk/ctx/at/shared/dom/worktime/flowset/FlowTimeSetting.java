/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class FlowTimeSetting.
 */
//流動時間設定
@Getter
@NoArgsConstructor
public class FlowTimeSetting extends WorkTimeDomainObject implements Cloneable{

	/** The rounding. */
	// 丸め
	private TimeRoundingSetting rounding;

	/** The elapsed time. */
	// 経過時間
	private AttendanceTime elapsedTime;

	/**
	 * Instantiates a new fl time setting.
	 *
	 * @param memento the memento
	 */
	public FlowTimeSetting(FlowTimeGetMemento memento) {
		this.rounding = memento.getRouding();
		this.elapsedTime = memento.getElapsedTime();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlowTimeSetMemento memento) {
		memento.setRouding(this.rounding);
		memento.setElapsedTime(this.elapsedTime);
	}
	
	@Override
	public FlowTimeSetting clone() {
		FlowTimeSetting cloned = new FlowTimeSetting();
		try {
			cloned.rounding = rounding.clone();
			cloned.elapsedTime = new AttendanceTime(this.elapsedTime.v());
		}
		catch (Exception e){
			throw new RuntimeException("FlowTimeSetting clone error.");
		}
		return cloned;
	}
	
	/**
	 * 経過時間を変動させる（マイナスの場合、0にする）
	 */
	public void fluctuationElapsedTimeNegativeToZero(AttendanceTimeOfExistMinus fluctuationTime) {
		this.elapsedTime = this.elapsedTime.addMinutes(fluctuationTime.valueAsMinutes());
		this.elapsedTime = this.elapsedTime.isNegative() ? AttendanceTime.ZERO : this.elapsedTime;
	}
}
