/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company;

import java.util.Optional;

/**
 * The Interface CompanyLaborDeforSetMonthlyRepository.
 */
public interface ComDeforLaborMonthActCalSetRepository {

	/**
	 * Gets the company labor defor set monthly by cid.
	 *
	 * @param companyId the company id
	 * @return the company labor defor set monthly by cid
	 */
	Optional<ComDeforLaborMonthActCalSet> getCompanyLaborDeforSetMonthlyByCid(String companyId);

	/**
	 * Adds the.
	 *
	 * @param companyLaborDeforSetMonthly the company labor defor set monthly
	 */
	void add(ComDeforLaborMonthActCalSet companyLaborDeforSetMonthly);

	/**
	 * Update.
	 *
	 * @param companyLaborDeforSetMonthly the company labor defor set monthly
	 */
	void update(ComDeforLaborMonthActCalSet companyLaborDeforSetMonthly);
}
