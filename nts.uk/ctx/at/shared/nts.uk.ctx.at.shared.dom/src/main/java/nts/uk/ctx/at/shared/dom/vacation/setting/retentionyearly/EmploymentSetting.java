/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class EmploymentSetting.
 */
@Getter
public class EmploymentSetting extends DomainObject{
	
	/** The company id. */
	private String companyId;
	
	/** The employment code. */
	private String employmentCode; 
	
	/** The upper limit setting. */
	private UpperLimitSetting upperLimitSetting;
	
	/** The management category. */
	private ManagementCategory managementCategory;
	
	/**
	 * Instantiates a new employment setting.
	 *
	 * @param memento the memento
	 */
	public EmploymentSetting(EmploymentSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.employmentCode = memento.getEmploymentCode();
		this.upperLimitSetting = memento.getUpperLimitSetting();
		this.managementCategory = memento.getManagementCategory();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(EmploymentSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmploymentCode(this.employmentCode);
		memento.setUpperLimitSetting(this.upperLimitSetting);
		memento.setManagementCategory(this.managementCategory);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((employmentCode == null) ? 0 : employmentCode.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmploymentSetting other = (EmploymentSetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (employmentCode == null) {
			if (other.employmentCode != null)
				return false;
		} else if (!employmentCode.equals(other.employmentCode))
			return false;
		return true;
	}
	
	
}
