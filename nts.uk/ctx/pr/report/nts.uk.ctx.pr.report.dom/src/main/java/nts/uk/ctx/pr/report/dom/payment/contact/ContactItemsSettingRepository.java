/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import java.util.List;

/**
 * The Interface ContactItemsSettingRepository.
 */
public interface ContactItemsSettingRepository {

	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the optional
	 */
	ContactItemsSetting findByCode(ContactItemsCode code, List<String> empCds);

	/**
	 * Save.
	 *
	 * @param contactItemsSetting the contact items setting
	 */
	void save(ContactItemsSetting contactItemsSetting);
}
