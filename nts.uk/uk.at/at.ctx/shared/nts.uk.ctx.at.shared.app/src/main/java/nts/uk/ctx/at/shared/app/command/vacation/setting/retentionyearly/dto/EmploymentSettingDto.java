/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.retentionyearly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentSettingDto {
	
	/** The employment code. */
	private String employmentCode;
	
	/** The upper limit setting. */
	private UpperLimitSettingDto upperLimitSetting;
	
	/** The management category. */
	private Integer managementCategory;
	
	/**
	 * To domain.
	 *
	 * @param companyId the company id
	 * @return the employment setting
	 */
	public EmptYearlyRetentionSetting toDomain(String companyId) {
		return new EmptYearlyRetentionSetting(new GetMementoImpl(companyId, this));
	}
	
	/**
	 * The Class GetMementoImpl.
	 */
	private class GetMementoImpl implements EmptYearlyRetentionGetMemento {
		
		/** The dto. */
		private EmploymentSettingDto dto;
		
		/** The company id. */
		private String companyId;
		
		/**
		 * Instantiates a new gets the memento impl.
		 *
		 * @param companyId the company id
		 * @param dto the dto
		 */
		public GetMementoImpl(String companyId, EmploymentSettingDto dto) {
			super();
			this.companyId = companyId;
			this.dto = dto;
		}
		
		/*
		 * (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
		 * EmptYearlyRetentionGetMemento#getCompanyId()
		 */
		@Override
		public String getCompanyId() {
			return this.companyId;
		}
		
		/*
		 * (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
		 * EmptYearlyRetentionGetMemento#getEmploymentCode()
		 */
		@Override
		public String getEmploymentCode() {
			return dto.employmentCode;
		}

		
		/*
		 * (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
		 * EmptYearlyRetentionGetMemento#getManagementCategory()
		 */
		@Override
		public ManageDistinct getManagementCategory() {
			return ManageDistinct.valueOf(dto.managementCategory);
		}
	}
	
}
