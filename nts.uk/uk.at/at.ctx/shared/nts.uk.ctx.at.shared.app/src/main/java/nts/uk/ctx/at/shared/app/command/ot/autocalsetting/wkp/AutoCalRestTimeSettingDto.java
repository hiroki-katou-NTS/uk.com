/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.autocalsetting.wkp;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSettingGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.TimeLimitUpperLimitSetting;

/**
 * The Class AutoCalRestTimeSettingDto.
 */
@Getter
@Setter
public class AutoCalRestTimeSettingDto {

	/** The rest time. */
	private AutoCalSettingDto restTime;

	/** The late night time. */
	private AutoCalSettingDto lateNightTime;

	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @return the auto cal rest time setting
	 */
	public AutoCalRestTimeSetting toDomain() {
		return new AutoCalRestTimeSetting(new DtoGetMemento(this));
	}

	/**
	 * The Class DtoGetMemento.
	 */
	private class DtoGetMemento implements AutoCalRestTimeSettingGetMemento {

		/** The command. */
		private AutoCalRestTimeSettingDto command;

		/**
		 * Instantiates a new dto get memento.
		 *
		 * @param companyId
		 *            the company id
		 * @param command
		 *            the command
		 */
		public DtoGetMemento(AutoCalRestTimeSettingDto command) {
			this.command = command;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
		 * AutoCalRestTimeSettingGetMemento#getRestTime()
		 */
		@Override
		public AutoCalSetting getRestTime() {
			return new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(command.getRestTime().getUpLimitOtSet()),
					AutoCalAtrOvertime.valueOf(command.getRestTime().getCalAtr()));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
		 * AutoCalRestTimeSettingGetMemento#getLateNightTime()
		 */
		@Override
		public AutoCalSetting getLateNightTime() {
			return new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(command.getLateNightTime().getUpLimitOtSet()),
					AutoCalAtrOvertime.valueOf(command.getLateNightTime().getCalAtr()));
		}
	}

}
