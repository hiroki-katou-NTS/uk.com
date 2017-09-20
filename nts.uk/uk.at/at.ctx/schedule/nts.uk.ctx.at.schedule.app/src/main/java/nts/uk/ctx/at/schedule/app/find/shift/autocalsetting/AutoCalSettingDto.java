/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.autocalsetting;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.TimeLimitUpperLimitSetting;

/**
 * The Class AutoCalSettingDto.
 */

/**
 * Gets the cal atr.
 *
 * @return the cal atr
 */
@Getter

/**
 * Sets the cal atr.
 *
 * @param calAtr the new cal atr
 */

/**
 * Sets the cal atr.
 *
 * @param calAtr the new cal atr
 */
@Setter
public class AutoCalSettingDto implements AutoCalSettingSetMemento{

	/** The up limit ot set. */
	// 上限残業時間の設定
	private Integer upLimitOtSet;

	/** The cal atr. */
	/// 計算区分
	private Integer calAtr;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSettingSetMemento#setTimeLimitUpperLimitSetting(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.TimeLimitUpperLimitSetting)
	 */
	@Override
	public void setTimeLimitUpperLimitSetting(TimeLimitUpperLimitSetting timeLimitUpperLimitSetting) {
		this.upLimitOtSet = timeLimitUpperLimitSetting.value;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSettingSetMemento#setAutoCalAtrOvertime(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalAtrOvertime)
	 */
	@Override
	public void setAutoCalAtrOvertime(AutoCalAtrOvertime autoCalAtrOvertime) {
		this.calAtr = autoCalAtrOvertime.value;
	}
}
