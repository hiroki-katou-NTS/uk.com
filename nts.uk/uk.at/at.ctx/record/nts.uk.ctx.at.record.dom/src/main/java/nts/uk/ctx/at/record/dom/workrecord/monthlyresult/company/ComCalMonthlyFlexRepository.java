/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company;

import java.util.Optional;

/**
 * The Interface CompanyCalMonthlyFlexRepository.
 */
public interface ComCalMonthlyFlexRepository {

	/**
	 * Gets the company cal monthly flex by cid.
	 *
	 * @param companyId the company id
	 * @return the company cal monthly flex by cid
	 */
	Optional<ComCalMonthlyFlex> getCompanyCalMonthlyFlexByCid(String companyId);

	/**
	 * Adds the.
	 *
	 * @param companyCalMonthlyFlex the company cal monthly flex
	 */
	void add(ComCalMonthlyFlex companyCalMonthlyFlex);

	/**
	 * Update.
	 *
	 * @param companyCalMonthlyFlex the company cal monthly flex
	 */
	void update(ComCalMonthlyFlex companyCalMonthlyFlex);
}
