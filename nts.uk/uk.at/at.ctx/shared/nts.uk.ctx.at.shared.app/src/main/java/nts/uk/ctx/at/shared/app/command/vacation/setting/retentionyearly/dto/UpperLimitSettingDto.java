/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.retentionyearly.dto;

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
	 * The Class GetMementoImpl.
	 */
	private class GetMementoImpl implements UpperLimitSettingGetMemento {

		/** The dto. */
		private UpperLimitSettingDto dto;

		/**
		 * Instantiates a new gets the memento impl.
		 *
		 * @param dto the dto
		 */
		public GetMementoImpl(UpperLimitSettingDto dto) {
			this.dto = dto;
		}

		/*
		 * (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
		 * UpperLimitSettingGetMemento#getRetentionYearsAmount()
		 */
		@Override
		public RetentionYearsAmount getRetentionYearsAmount() {
			return new RetentionYearsAmount(dto.retentionYearsAmount);
		}

		/*
		 * (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
		 * UpperLimitSettingGetMemento#getMaxDaysCumulation()
		 */
		@Override
		public MaxDaysRetention getMaxDaysCumulation() {
			return new MaxDaysRetention(dto.maxDaysCumulation);
		}

	}
}
