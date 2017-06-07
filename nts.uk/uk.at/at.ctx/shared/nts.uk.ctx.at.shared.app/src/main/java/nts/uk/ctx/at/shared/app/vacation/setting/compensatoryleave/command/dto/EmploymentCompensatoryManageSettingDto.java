/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.command.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCompensatoryManageSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentManageGetMemento;

/**
 * The Class EmploymentCompensatoryManageSettingDto.
 */
@Getter
@Setter
public class EmploymentCompensatoryManageSettingDto {
	
	// 管理区分
	/** The is managed. */
	private Integer isManaged;

	// 使用期限
	/** The expiration time. */
	private Integer expirationTime;

	// 先取り許可
	/** The preemption permit. */
	private Integer preemptionPermit;
	
	/**
	 * To domain.
	 *
	 * @return the employment compensatory manage setting
	 */
	public EmploymentCompensatoryManageSetting toDomain(){
		return new EmploymentCompensatoryManageSetting(new EmploymentManageGetMementoImpl(this));
	}
	
	public class EmploymentManageGetMementoImpl implements EmploymentManageGetMemento{
		
		private EmploymentCompensatoryManageSettingDto employmentCompensatoryManageSettingDto;
		
		public EmploymentManageGetMementoImpl(EmploymentCompensatoryManageSettingDto dto){
			this.employmentCompensatoryManageSettingDto = dto;
		}
		
		@Override
		public ManageDistinct getIsManaged() {
			return ManageDistinct.valueOf(this.employmentCompensatoryManageSettingDto.isManaged);
		}

		@Override
		public ExpirationTime getExpirationTime() {
			return ExpirationTime.valueOf(this.employmentCompensatoryManageSettingDto.expirationTime);
		}

		@Override
		public ApplyPermission getPreemptionPermit() {
			return ApplyPermission.valueOf(this.employmentCompensatoryManageSettingDto.preemptionPermit);
		}
		
	}
}
