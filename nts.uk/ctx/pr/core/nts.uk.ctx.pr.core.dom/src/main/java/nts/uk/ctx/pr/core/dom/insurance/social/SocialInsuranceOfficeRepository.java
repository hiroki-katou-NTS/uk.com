/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;

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
	 * @param companyCode the company code
	 * @param officeCode the office code
	 */
    void remove(CompanyCode companyCode, OfficeCode officeCode);

	/**
	 * Find all.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	List<SocialInsuranceOffice> findAll(CompanyCode companyCode);

	/**
	 * Find by office code.
	 *
	 * @param officeCode the office code
	 * @return the social insurance office
	 */
	Optional<SocialInsuranceOffice> findByOfficeCode(CompanyCode companyCode, OfficeCode officeCode);

	/**
	 * Checks if is duplicate code.
	 *
	 * @param code the code
	 * @return true, if is duplicate code
	 */
	boolean isDuplicateCode(CompanyCode companyCode, OfficeCode officeCode);
}
