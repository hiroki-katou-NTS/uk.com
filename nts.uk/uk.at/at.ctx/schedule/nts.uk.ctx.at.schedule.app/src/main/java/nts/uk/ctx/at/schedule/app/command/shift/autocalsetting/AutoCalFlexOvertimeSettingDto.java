package nts.uk.ctx.at.schedule.app.command.shift.autocalsetting;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalFlexOvertimeSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSetting;

@Getter
@Setter
public class AutoCalFlexOvertimeSettingDto {

	/** The flex ot time. */
	// フレックス超過時間
	private AutoCalSettingDto flexOtTime;

	/** The flex ot night time. */
	// フレックス超過深夜時間
	private AutoCalSettingDto flexOtNightTime;
	
	/**
	 * To domain.
	 *
	 * @param companyId the company id
	 * @return the auto cal overtime setting
	 */
	public AutoCalFlexOvertimeSetting toDomain(String companyId) {
		return new AutoCalFlexOvertimeSetting(new DtoGetMemento(companyId, this));
	}

	/**
	 * The Class DtoGetMemento.
	 */
	private class DtoGetMemento implements AutoCalFlexOvertimeSettingGetMemento {

		/** The company id. */
		private String companyId;

		/** The command. */
		private AutoCalFlexOvertimeSettingDto command;

		/**
		 * Instantiates a new dto get memento.
		 *
		 * @param companyId the company id
		 * @param command the command
		 */
		public DtoGetMemento(String companyId, AutoCalFlexOvertimeSettingDto command) {
			this.companyId = companyId;
			this.command = command;
		}

		@Override
		public AutoCalSetting getFlexOtTime() {
			return this.command.getFlexOtTime().toDomain();
		}

		@Override
		public AutoCalSetting getFlexOtNightTime() {
			return this.command.getFlexOtNightTime().toDomain();
		}

	}
	
}
