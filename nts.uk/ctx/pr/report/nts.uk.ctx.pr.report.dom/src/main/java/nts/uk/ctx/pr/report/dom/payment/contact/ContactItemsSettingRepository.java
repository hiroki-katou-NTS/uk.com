/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import java.util.Optional;

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
	Optional<ContactItemsSetting> findByCode(ContactItemsCode code);
}
