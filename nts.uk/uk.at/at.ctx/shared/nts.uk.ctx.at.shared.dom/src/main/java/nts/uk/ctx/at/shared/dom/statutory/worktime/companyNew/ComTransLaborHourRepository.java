/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import java.util.Optional;

/**
 * The Interface CompanyTransLaborHourRepository.
 */
public interface ComTransLaborHourRepository {

	/**
	 * Gets the company trans labor hour by cid.
	 *
	 * @param companyId the company id
	 * @return the company trans labor hour by cid
	 */
	Optional<ComTransLaborHour> getCompanyTransLaborHourByCid(String companyId);

	/**
	 * Adds the.
	 *
	 * @param companyTransLaborHour the company trans labor hour
	 */
	void add(ComTransLaborHour companyTransLaborHour);

	/**
	 * Update.
	 *
	 * @param companyTransLaborHour the company trans labor hour
	 */
	void update(ComTransLaborHour companyTransLaborHour);
}
