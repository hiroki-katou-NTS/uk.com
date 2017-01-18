/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social;

import java.util.List;

/**
 * The Interface SocialInsuranceOfficeRepository.
 */
public interface SocialInsuranceOfficeRepository {

	/**
	 * Adds the.
	 *
	 * @param office the office
	 */
    void add(SocialInsuranceOffice office);

	/**
	 * Update.
	 *
	 * @param office the office
	 */
    void update(SocialInsuranceOffice office);

	/**
	 * Removes the.
	 *
	 * @param id the id
	 * @param version the version
	 */
    void remove(String id, Long version);

	/**
	 * Find all.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	List<SocialInsuranceOffice> findAll(int companyCode);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the social insurance office
	 */
	SocialInsuranceOffice findById(String id);
}
