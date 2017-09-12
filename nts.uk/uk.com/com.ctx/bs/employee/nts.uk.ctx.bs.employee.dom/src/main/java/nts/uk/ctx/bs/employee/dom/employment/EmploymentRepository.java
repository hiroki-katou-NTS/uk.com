/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.employment;

import java.util.List;
import java.util.Optional;

/**
 * The Interface EmploymentRepository.
 */
public interface EmploymentRepository {

	/**
	 * Find all.
	 *
	 * @param CompanyId the company id
	 * @return the list
	 */
	List<Employment> findAll(String companyId);

	/**
	 * Find by emp codes.
	 *
	 * @param empCodes the emp codes
	 * @return the list
	 */
	List<Employment> findByEmpCodes(List<String> empCodes);

	/**
	 * Find employment.
	 *
	 * @param companyCode the company code
	 * @param employmentCode the employment code
	 * @return the optional
	 */
	Optional<Employment> findEmployment(String companyCode, String employmentCode);

}
