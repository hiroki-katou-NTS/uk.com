/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import java.util.List;
import java.util.Optional;

/**
 * The Interface ContactPersonalSettingRepository.
 */
public interface ContactPersonalSettingRepository {

	/**
	 * Find all.
	 *
	 * @param ccd the ccd
	 * @return the list
	 */
	List<ContactPersonalSetting> findAll(String ccd);

	/**
	 * Find.
	 *
	 * @return the optional
	 */
	Optional<ContactPersonalSetting> find();

	/**
	 * Creates the.
	 *
	 * @param setting the setting
	 */
	void create(ContactPersonalSetting setting);

	/**
	 * Removes the.
	 *
	 * @param setting the setting
	 */
	void remove(ContactPersonalSetting setting);
}
