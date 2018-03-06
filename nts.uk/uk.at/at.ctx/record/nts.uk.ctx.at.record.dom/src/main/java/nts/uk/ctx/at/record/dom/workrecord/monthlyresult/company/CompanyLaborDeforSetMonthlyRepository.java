/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company;

import java.util.Optional;

/**
 * The Interface CompanyLaborDeforSetMonthlyRepository.
 */
public interface CompanyLaborDeforSetMonthlyRepository {

	/**
	 * Gets the company labor defor set monthly by cid.
	 *
	 * @param companyId the company id
	 * @return the company labor defor set monthly by cid
	 */
	Optional<CompanyLaborDeforSetMonthly> getCompanyLaborDeforSetMonthlyByCid(String companyId);

	/**
	 * Adds the.
	 *
	 * @param companyLaborDeforSetMonthly the company labor defor set monthly
	 */
	void add(CompanyLaborDeforSetMonthly companyLaborDeforSetMonthly);

	/**
	 * Update.
	 *
	 * @param companyLaborDeforSetMonthly the company labor defor set monthly
	 */
	void update(CompanyLaborDeforSetMonthly companyLaborDeforSetMonthly);
}
