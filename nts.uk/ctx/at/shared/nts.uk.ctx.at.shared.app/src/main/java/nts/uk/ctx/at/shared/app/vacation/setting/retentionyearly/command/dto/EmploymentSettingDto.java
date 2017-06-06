/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.ManagementCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;

/**
 * Gets the management category.
 *
 * @return the management category
 */
@Getter

/**
 * Sets the management category.
 *
 * @param managementCategory the new management category
 */
@Setter

/**
 * Instantiates a new employment setting dto.
 */
@NoArgsConstructor

/**
 * Instantiates a new employment setting dto.
 *
 * @param employmentCode the employment code
 * @param upperLimitSetting the upper limit setting
 * @param managementCategory the management category
 */
@AllArgsConstructor
public class EmploymentSettingDto{
	
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
	public EmploymentSetting toDomain(String companyId) {
		return new EmploymentSetting(new GetMementoImpl(companyId, this));
	}
	
	/**
	 * The Class GetMementoImpl.
	 */
	private class GetMementoImpl implements EmploymentSettingGetMemento{
		
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
		 * EmploymentSettingGetMemento#getCompanyId()
		 */
		@Override
		public String getCompanyId() {
			return this.companyId;
		}
		
		/*
		 * (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
		 * EmploymentSettingGetMemento#getEmploymentCode()
		 */
		@Override
		public String getEmploymentCode() {
			return dto.employmentCode;
		}

		/*
		 * (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
		 * EmploymentSettingGetMemento#getUpperLimitSetting()
		 */
		@Override
		public UpperLimitSetting getUpperLimitSetting() {
			return dto.upperLimitSetting.toDomain();
		}
		
		/*
		 * (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
		 * EmploymentSettingGetMemento#getManagementCategory()
		 */
		@Override
		public ManagementCategory getManagementCategory() {
			return ManagementCategory.valueOf(dto.managementCategory);
		}
	}
	
}
