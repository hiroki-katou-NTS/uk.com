/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.command.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCompensatoryTimeManageSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentTimeManageGetMemento;

/**
 * The Class EmploymentCompensatoryTimeManageSettingDto.
 */
@Getter
@Setter
public class EmploymentCompensatoryTimeManageSettingDto {

	// 管理区分
	/** The is managed. */
	private Integer isManaged;

	// 消化単位
	/** The digestive unit. */
	private Integer digestiveUnit;

	/**
	 * To domain.
	 *
	 * @return the employment compensatory time manage setting
	 */
	public EmploymentCompensatoryTimeManageSetting toDomain() {
		return new EmploymentCompensatoryTimeManageSetting(new EmploymentTimeManageGetMementoImpl(this));
	}

	public class EmploymentTimeManageGetMementoImpl implements EmploymentTimeManageGetMemento {

		private EmploymentCompensatoryTimeManageSettingDto employmentCompensatoryTimeManageSettingDto;

		public EmploymentTimeManageGetMementoImpl(
				EmploymentCompensatoryTimeManageSettingDto employmentCompensatoryTimeManageSettingDto) {
			this.employmentCompensatoryTimeManageSettingDto = employmentCompensatoryTimeManageSettingDto;
		}

		@Override
		public ManageDistinct getIsManaged() {
			return ManageDistinct.valueOf(this.employmentCompensatoryTimeManageSettingDto.isManaged);
		}

		@Override
		public TimeVacationDigestiveUnit getDigestiveUnit() {
			return TimeVacationDigestiveUnit.valueOf(this.employmentCompensatoryTimeManageSettingDto.digestiveUnit);
		}

	}
}
