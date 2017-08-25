/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.employment;

import java.util.Optional;

/**
 * The Class CompanyEstablishmentRepository.
 */
public interface EmploymentEstablishmentRepository {

	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param targetYear the target year
	 * @param employmentCode the employment code
	 * @return the optional
	 */
	public Optional<EmploymentEstablishment> findById(String companyId,  String employmentCode,int targetYear);
	
	
	/**
	 * Save employment establishment.
	 *
	 * @param employmentEstablishment the employment establishment
	 */
	public void saveEmploymentEstablishment(EmploymentEstablishment employmentEstablishment);
}
