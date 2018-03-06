/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import java.util.Optional;

/**
 * The Interface CompanyTransLaborHourRepository.
 */
public interface CompanyTransLaborHourRepository {

	/**
	 * Gets the company trans labor hour by cid.
	 *
	 * @param companyId the company id
	 * @return the company trans labor hour by cid
	 */
	Optional<CompanyTransLaborHour> getCompanyTransLaborHourByCid(String companyId);

	/**
	 * Adds the.
	 *
	 * @param companyTransLaborHour the company trans labor hour
	 */
	void add(CompanyTransLaborHour companyTransLaborHour);

	/**
	 * Update.
	 *
	 * @param companyTransLaborHour the company trans labor hour
	 */
	void update(CompanyTransLaborHour companyTransLaborHour);
}
