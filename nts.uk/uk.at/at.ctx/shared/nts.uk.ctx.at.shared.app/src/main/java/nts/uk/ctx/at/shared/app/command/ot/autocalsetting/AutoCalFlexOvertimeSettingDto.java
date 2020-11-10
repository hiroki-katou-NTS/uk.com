/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.autocalsetting;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalFlexOvertimeSettingGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.TimeLimitUpperLimitSetting;

/**
 * The Class AutoCalFlexOvertimeSettingDto.
 */
@Getter
@Setter
public class AutoCalFlexOvertimeSettingDto {

	/** The flex ot time. */
	// フレックス超過時間
	private AutoCalSettingDto flexOtTime;

	/**
	 * To domain.
	 *
	 * @return the auto cal flex overtime setting
	 */
	public AutoCalFlexOvertimeSetting toDomain() {
		return new AutoCalFlexOvertimeSetting(new DtoGetMemento(this));
	}

	/**
	 * The Class DtoGetMemento.
	 */
	private class DtoGetMemento implements AutoCalFlexOvertimeSettingGetMemento {

		/** The command. */
		private AutoCalFlexOvertimeSettingDto command;

		/**
		 * Instantiates a new dto get memento.
		 *
		 * @param command the command
		 */
		public DtoGetMemento(AutoCalFlexOvertimeSettingDto command) {
			this.command = command;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalFlexOvertimeSettingGetMemento#getFlexOtTime()
		 */
		@Override
		public AutoCalSetting getFlexOtTime() {
			return new AutoCalSetting(
					TimeLimitUpperLimitSetting.valueOf(command.getFlexOtTime().getUpLimitOtSet()),
					AutoCalAtrOvertime.valueOf(command.getFlexOtTime().getCalAtr()));
		}
	}

}
