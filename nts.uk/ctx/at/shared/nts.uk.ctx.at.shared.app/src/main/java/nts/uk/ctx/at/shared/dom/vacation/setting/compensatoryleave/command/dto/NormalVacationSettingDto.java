/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.command.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.NormalVacationGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.NormalVacationSetting;

/**
 * The Class NormalVacationSettingDto.
 */
@Getter
@Setter
public class NormalVacationSettingDto {

	/** The expiration time. */
	private ExpirationTime expirationTime;

	/** The preemption permit. */
	private ApplyPermission preemptionPermit;

	/** The is manage by time. */
	private ManageDistinct isManageByTime;

	/** The digestive unit. */
	private TimeVacationDigestiveUnit digestiveUnit;

	public NormalVacationSetting toDomain() {
		return new NormalVacationSetting(new NormalVacationSettingGetMementoImpl(this));
	}

	public class NormalVacationSettingGetMementoImpl implements NormalVacationGetMemento {

		/** The normal vacation setting. */
		private NormalVacationSettingDto normalVacationSettingDto;

		/**
		 * @param companyId
		 * @param isManaged
		 * @param normalVacationSetting
		 * @param occurrenceVacationSetting
		 */
		public NormalVacationSettingGetMementoImpl(NormalVacationSettingDto normalVacationSettingDto) {
			this.normalVacationSettingDto = normalVacationSettingDto;
		}

		@Override
		public ExpirationTime getExpirationTime() {
			return ExpirationTime.valueOf(this.normalVacationSettingDto.expirationTime.value);
		}

		@Override
		public ApplyPermission getPreemptionPermit() {
			return ApplyPermission.valueOf(this.normalVacationSettingDto.preemptionPermit.value);
		}

		@Override
		public ManageDistinct getIsManageByTime() {
			return ManageDistinct.valueOf(this.normalVacationSettingDto.isManageByTime.value);
		}

		@Override
		public TimeVacationDigestiveUnit getdigestiveUnit() {
			return TimeVacationDigestiveUnit.valueOf(this.normalVacationSettingDto.digestiveUnit.value);
		}

	}

}
