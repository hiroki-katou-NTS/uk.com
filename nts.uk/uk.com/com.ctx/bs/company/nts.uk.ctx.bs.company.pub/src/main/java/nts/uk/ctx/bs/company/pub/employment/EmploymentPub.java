/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.company.pub.employment;

import java.util.List;

/**
 * The Interface WorkplacePub.
 */
public interface EmploymentPub {

	/**
	 * Find by emp codes.
	 *
	 * @param employmentCodes the employment codes
	 * @return the list
	 */
	List<PubEmploymentDto> findByEmpCodes(List<String> employmentCodes);

}
