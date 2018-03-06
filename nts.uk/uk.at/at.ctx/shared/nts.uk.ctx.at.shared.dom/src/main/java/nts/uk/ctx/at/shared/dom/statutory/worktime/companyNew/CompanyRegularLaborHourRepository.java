/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import java.util.Optional;

/**
 * The Interface CompanyRegularLaborHourRepository.
 */
public interface CompanyRegularLaborHourRepository {

	/**
	 * Find by company id.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<CompanyRegularLaborHour> findByCompanyId(String companyId);

	/**
	 * Adds the.
	 *
	 * @param companyRegularLaborHour the company regular labor hour
	 */
	void add(CompanyRegularLaborHour companyRegularLaborHour);

	/**
	 * Update.
	 *
	 * @param companyRegularLaborHour the company regular labor hour
	 */
	void update(CompanyRegularLaborHour companyRegularLaborHour);

}
