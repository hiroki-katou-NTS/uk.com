/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule;

import java.util.List;

/**
 * The Interface VaAcRuleGetMemento.
 */
public interface AcquisitionRuleGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();
	
	/**
	 * Gets the settingclassification.
	 *
	 * @return the settingclassification
	 */
	Category getSettingclassification();
	
	/**
	 * Gets the acquisition order.
	 *
	 * @return the acquisition order
	 */
	List<AcquisitionOrder> getAcquisitionOrder();
}
