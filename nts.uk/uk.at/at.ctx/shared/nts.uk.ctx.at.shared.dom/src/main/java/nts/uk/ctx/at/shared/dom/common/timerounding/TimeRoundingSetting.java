/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.timerounding;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class TimeRoundingSetting.
 */
// 時間丸め設定
@Getter
public class TimeRoundingSetting extends DomainObject{
	
	/** The unit. */
	// 単位
	private Unit roundingTime;
	
	/** The rounding. */
	// 端数処理
	private Rounding rounding;

	/**
	 * Instantiates a new time rounding setting.
	 *
	 * @param roundingTime the rounding time
	 * @param rounding the rounding
	 */
	public TimeRoundingSetting(Unit roundingTime, Rounding rounding) {
		this.roundingTime = roundingTime;
		this.rounding = rounding;
	}
	
	/**
	 * Instantiates a new time rounding setting.
	 *
	 * @param roundingTime the rounding time
	 * @param rounding the rounding
	 */
	public TimeRoundingSetting(int roundingTime, int rounding) {
		this.roundingTime = Unit.valueOf(roundingTime);
		this.rounding = Rounding.valueOf(rounding);
	}
}
