/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import java.util.List;

/**
 * The Interface ContactPersonalSettingRepository.
 */
public interface ContactPersonalSettingRepository {

	/**
	 * Find all.
	 *
	 * @param ccd the ccd
	 * @param processingYm the processing ym
	 * @param processingNo the processing no
	 * @return the list
	 */
	List<ContactPersonalSetting> findAll(String ccd, int processingYm, int processingNo);

	/**
	 * Find all.
	 *
	 * @param ccd the ccd
	 * @return the list
	 */
	List<ContactPersonalSetting> findAll(String ccd);

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
