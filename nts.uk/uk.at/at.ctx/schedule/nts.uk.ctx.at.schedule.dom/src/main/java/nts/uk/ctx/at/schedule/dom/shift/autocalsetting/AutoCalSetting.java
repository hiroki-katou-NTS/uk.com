/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.autocalsetting;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.Getter;

/**
 * The Class AutoCalSetting.
 */
// 自動計算設定

/**
 * Gets the cal atr.
 *
 * @return the cal atr
 */
@Getter
public class AutoCalSetting extends ValueObject {

	/** The up limit ot set. */
	// 上限残業時間の設定
	private TimeLimitUpperLimitSetting upLimitOtSet;

	/** The cal atr. */
	/// 計算区分
	private AutoCalAtrOvertime calAtr;

	/**
	 * Instantiates a new auto cal setting.
	 *
	 * @param upLimitOtSet the up limit ot set
	 * @param calAtr the cal atr
	 */
	
	public AutoCalSetting(AutoCalSettingGetMemento memento) {
		this.upLimitOtSet = memento.getUpLimitOtSet();
		this.calAtr = memento.getcalAtr();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(AutoCalSettingSetMemento memento) {
		memento.setAutoCalAtrOvertime(this.calAtr);
		memento.setTimeLimitUpperLimitSetting(this.upLimitOtSet);
	}

}
