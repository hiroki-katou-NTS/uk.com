/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.command.dto;

import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.MaxDaysRetention;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearsAmount;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSettingGetMemento;

@Setter
public class UpperLimitSettingDto {

	/** The retention years amount. */
	private Integer retentionYearsAmount;

	/** The max days cumulation. */
	private Integer maxDaysCumulation;

	/**
	 * To domain.
	 *
	 * @return the upper limit setting
	 */
	public UpperLimitSetting toDomain() {
		return new UpperLimitSetting(new GetMementoImpl(this));
	}

	/**
	 * The Class UpperLimitSettingGetMementoImpl.
	 */
	private class GetMementoImpl implements UpperLimitSettingGetMemento {

		/** The dto. */
		private UpperLimitSettingDto dto;

		public GetMementoImpl(UpperLimitSettingDto dto) {
			this.dto = dto;
		}

		@Override
		public RetentionYearsAmount getRetentionYearsAmount() {
			return new RetentionYearsAmount(dto.retentionYearsAmount);
		}

		@Override
		public MaxDaysRetention getMaxDaysCumulation() {
			return new MaxDaysRetention(dto.maxDaysCumulation);
		}

	}
}
