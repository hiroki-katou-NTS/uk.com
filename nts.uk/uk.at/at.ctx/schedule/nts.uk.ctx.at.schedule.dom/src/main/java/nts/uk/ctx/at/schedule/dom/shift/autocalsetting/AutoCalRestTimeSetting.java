/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.autocalsetting;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class AutoCalRestTimeSetting.
 */
// 休出時間の自動計算設定
@Getter
public class AutoCalRestTimeSetting extends DomainObject {

	/** The rest time. */
	// 休出時間
	private AutoCalSetting restTime;

	/** The late night time. */
	// 休出深夜時間
	private AutoCalSetting lateNightTime;

	/**
	 * Instantiates a new auto cal rest time setting.
	 *
	 * @param restTime
	 *            the rest time
	 * @param lateNightTime
	 *            the late night time
	 */
	public AutoCalRestTimeSetting(AutoCalSetting restTime, AutoCalSetting lateNightTime) {
		super();
		this.restTime = restTime;
		this.lateNightTime = lateNightTime;
	}
}
