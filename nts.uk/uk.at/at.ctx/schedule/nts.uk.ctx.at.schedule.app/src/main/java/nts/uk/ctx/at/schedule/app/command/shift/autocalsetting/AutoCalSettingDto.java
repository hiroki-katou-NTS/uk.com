/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.autocalsetting;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.TimeLimitUpperLimitSetting;

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
@Setter
public class AutoCalSettingDto {

	/** The up limit ot set. */
	// 上限残業時間の設定
	private Integer upLimitOtSet;

	/** The cal atr. */
	/// 計算区分
	private Integer calAtr;
	
	/**
	 * To domain.
	 *
	 * @return the auto cal setting
	 */
	public AutoCalSetting toDomain() {
		return new AutoCalSetting(new DtoGetMemento(this));
	}

	/**
	 * The Class DtoGetMemento.
	 */
	private class DtoGetMemento implements AutoCalSettingGetMemento {

		/** The command. */
		private AutoCalSettingDto command;

		/**
		 * Instantiates a new dto get memento.
		 *
		 * @param command the command
		 */
		public DtoGetMemento(AutoCalSettingDto command) {
			this.command = command;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSettingGetMemento#getUpLimitOtSet()
		 */
		@Override
		public TimeLimitUpperLimitSetting getUpLimitOtSet() {
			return TimeLimitUpperLimitSetting.valueOf(this.command.getUpLimitOtSet());
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSettingGetMemento#getcalAtr()
		 */
		@Override
		public AutoCalAtrOvertime getcalAtr() {
			return AutoCalAtrOvertime.valueOf(this.command.getCalAtr());
		}
	}
	
}
