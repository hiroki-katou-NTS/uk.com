/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.autocalsetting;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSetting;

/**
 * The Class AutoCalOvertimeSettingDto.
 */
@Setter

/**
 * Gets the legal mid ot time.
 *
 * @return the legal mid ot time
 */
@Getter
public class AutoCalOvertimeSettingDto  {

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

		@Override
		public AutoCalSetting getEarlyOtTime() {
			return this.command.getEarlyOtTime().toDomain();
		}

		@Override
		public AutoCalSetting getEarlyMidOtTime() {
			return this.command.getEarlyMidOtTime().toDomain();
		}

		@Override
		public AutoCalSetting getNormalOtTime() {
			return this.command.getNormalOtTime().toDomain();
		}

		@Override
		public AutoCalSetting getNormalMidOtTime() {
			return this.command.getNormalMidOtTime().toDomain();
		}

		@Override
		public AutoCalSetting getLegalOtTime() {
			return this.command.getLegalOtTime().toDomain();
		}

		@Override
		public AutoCalSetting getLegalMidOtTime() {
			return this.command.getLegalMidOtTime().toDomain();
		}
	}
}
