/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@Setter
/**
 * The Class PublicHolidaySetting.
 */
public class PublicHolidaySetting extends AggregateRoot{
	
	/** The company ID. */
	// 会社ID
	private String companyID;
	
	/** The is manage com public hd. */
	// 会社の公休管理をする
	private boolean isManageComPublicHd;
	
	/** The public hd management classification. */
	// 公休管理区分
	private PublicHolidayManagementClassification publicHdManagementClassification;
	
	/**
	 * Instantiates a new public holiday setting.
	 *
	 * @param memento the memento
	 */
	public PublicHolidaySetting(PublicHolidaySettingGetMemento memento) {
		this.companyID = memento.getCompanyID();
		this.isManageComPublicHd = memento.getIsManageComPublicHd();
		this.publicHdManagementClassification = memento.getPublicHdManagementClassification();
	}

	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(PublicHolidaySettingSetMemento memento) {
		memento.setCompanyID(this.companyID);
		memento.setIsManageComPublicHd(this.isManageComPublicHd);
		memento.setPublicHdManagementClassification(this.publicHdManagementClassification);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyID == null) ? 0 : companyID.hashCode());
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
		PublicHolidaySetting other = (PublicHolidaySetting) obj;
		if (companyID == null) {
			if (other.companyID != null)
				return false;
		} else if (!companyID.equals(other.companyID))
			return false;
		return true;
	}
}
