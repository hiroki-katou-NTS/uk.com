/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.company;

import java.util.Optional;

/**
 * The Interface CompanyLaborRegSetMonthlyActualRepository.
 */
public interface ComLaborRegSetMonthlyActualRepository {

	/**
	 * Gets the company labor reg set monthly actual by cid.
	 *
	 * @param companyId the company id
	 * @return the company labor reg set monthly actual by cid
	 */
	Optional<ComLaborRegSetMonthlyActual> getCompanyLaborRegSetMonthlyActualByCid(String companyId);

	/**
	 * Adds the.
	 *
	 * @param companyLaborRegSetMonthlyActual the company labor reg set monthly actual
	 */
	void add(ComLaborRegSetMonthlyActual companyLaborRegSetMonthlyActual);

	/**
	 * Update.
	 *
	 * @param companyLaborRegSetMonthlyActual the company labor reg set monthly actual
	 */
	void update(ComLaborRegSetMonthlyActual companyLaborRegSetMonthlyActual);
}
