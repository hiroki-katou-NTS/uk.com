package nts.uk.ctx.at.schedule.app.command.shift.autocalsettingjob;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalFlexOvertimeSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.TimeLimitUpperLimitSetting;

@Getter
@Setter
public class AutoCalFlexOvertimeSettingDto {

	/** The flex ot time. */
	// フレックス超過時間
	private AutoCalSettingDto flexOtTime;


	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
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
		 * @param companyId
		 *            the company id
		 * @param command
		 *            the command
		 */
		public DtoGetMemento(String companyId, AutoCalFlexOvertimeSettingDto command) {
			this.companyId = companyId;
			this.command = command;
		}

		@Override
		public AutoCalSetting getFlexOtTime() {
			return new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(command.getFlexOtTime().getUpLimitOtSet()),
					AutoCalAtrOvertime.valueOf(command.getFlexOtTime().getCalAtr()));
		}


	}

}
