/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacationsetting.acquisitionrule;

import java.util.List;


/**
 * The Interface VaAcRuleSetMemento.
 */
public interface VaAcRuleSetMemento {
	
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(String companyId);
	
	
	
	/**
	 * Sets the settingclassification.
	 *
	 * @param settingclassification the new settingclassification
	 */
	void  setSettingclassification(Settingclassification settingclassification );
	
	
	
	/**
	 * Sets the acquisition order.
	 *
	 * @param listVacationAcquisitionOrder the new acquisition order
	 */
	void  setAcquisitionOrder(List<VacationAcquisitionOrder> listVacationAcquisitionOrder);
	
	
}
