/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.autocalsetting;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalOvertimeSettingGetMemento;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;

/**
 * The Class AutoCalOvertimeSettingDto.
 */

/**
 * Sets the legal mid ot time.
 *
 * @param legalMidOtTime the new legal mid ot time
 */
@Setter

/**
 * Gets the legal mid ot time.
 *
 * @return the legal mid ot time
 */

/**
 * Gets the legal mid ot time.
 *
 * @return the legal mid ot time
 */
@Getter
public class AutoCalOvertimeSettingDto {

	/** The early ot time. */
	// 早出残業時間
	private AutoCalSettingDto earlyOtTime;

	/** The early mid ot time. */
	// 早出深夜残業時間
	private AutoCalSettingDto earlyMidOtTime;

	/** The normal ot time. */
	// 普通残業時間
	private AutoCalSettingDto normalOtTime;

	/** The normal mid ot time. */
	// 普通深夜残業時間
	private AutoCalSettingDto normalMidOtTime;

	/** The legal ot time. */
	// 法定内残業時間
	private AutoCalSettingDto legalOtTime;

	/** The legal mid ot time. */
	// 法定内深夜残業時間
	private AutoCalSettingDto legalMidOtTime;

	/**
	 * To domain.
	 *
	 * @param companyId the company id
	 * @return the auto cal overtime setting
	 */
	public AutoCalOvertimeSetting toDomain(String companyId) {
		return new AutoCalOvertimeSetting(new DtoGetMemento(companyId, this));
	}

	/**
	 * The Class DtoGetMemento.
	 */
	private class DtoGetMemento implements AutoCalOvertimeSettingGetMemento {

		/** The company id. */
		private String companyId;

		/** The command. */
		private AutoCalOvertimeSettingDto command;

		/**
		 * Instantiates a new dto get memento.
		 *
		 * @param companyId the company id
		 * @param command the command
		 */
		public DtoGetMemento(String companyId, AutoCalOvertimeSettingDto command) {
			this.companyId = companyId;
			this.command = command;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSettingGetMemento#getEarlyOtTime()
		 */
		@Override
		public AutoCalSetting getEarlyOtTime() {
			return new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(command.getEarlyOtTime().getUpLimitOtSet()),
					AutoCalAtrOvertime.valueOf(command.getEarlyOtTime().getCalAtr()));
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSettingGetMemento#getEarlyMidOtTime()
		 */
		@Override
		public AutoCalSetting getEarlyMidOtTime() {
			return new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(command.getEarlyMidOtTime().getUpLimitOtSet()),
					AutoCalAtrOvertime.valueOf(command.getEarlyMidOtTime().getCalAtr()));
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSettingGetMemento#getNormalOtTime()
		 */
		@Override
		public AutoCalSetting getNormalOtTime() {
			return new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(command.getNormalOtTime().getUpLimitOtSet()),
					AutoCalAtrOvertime.valueOf(command.getNormalOtTime().getCalAtr()));
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSettingGetMemento#getNormalMidOtTime()
		 */
		@Override
		public AutoCalSetting getNormalMidOtTime() {
			return new AutoCalSetting(
					TimeLimitUpperLimitSetting.valueOf(command.getNormalMidOtTime().getUpLimitOtSet()),
					AutoCalAtrOvertime.valueOf(command.getNormalMidOtTime().getCalAtr()));
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSettingGetMemento#getLegalOtTime()
		 */
		@Override
		public AutoCalSetting getLegalOtTime() {
			return new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(command.getLegalOtTime().getUpLimitOtSet()),
					AutoCalAtrOvertime.valueOf(command.getLegalOtTime().getCalAtr()));
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSettingGetMemento#getLegalMidOtTime()
		 */
		@Override
		public AutoCalSetting getLegalMidOtTime() {
			return new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(command.getLegalMidOtTime().getUpLimitOtSet()),
					AutoCalAtrOvertime.valueOf(command.getLegalMidOtTime().getCalAtr()));
		}
	}
}
