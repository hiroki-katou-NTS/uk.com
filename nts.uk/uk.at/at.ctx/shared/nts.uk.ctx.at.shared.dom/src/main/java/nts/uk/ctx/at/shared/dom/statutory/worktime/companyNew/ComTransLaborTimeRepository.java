/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import java.util.Optional;

/**
 * The Interface CompanyTransLaborHourRepository.
 */
public interface ComTransLaborTimeRepository {

	/**
	 * Gets the company trans labor hour by cid.
	 *
	 * @param companyId the company id
	 * @return the company trans labor hour by cid
	 */
	Optional<ComTransLaborTime> getCompanyTransLaborHourByCid(String companyId);

	/**
	 * Adds the.
	 *
	 * @param companyTransLaborHour the company trans labor hour
	 */
	void add(ComTransLaborTime companyTransLaborHour);

	/**
	 * Update.
	 *
	 * @param companyTransLaborHour the company trans labor hour
	 */
	void update(ComTransLaborTime companyTransLaborHour);
}
