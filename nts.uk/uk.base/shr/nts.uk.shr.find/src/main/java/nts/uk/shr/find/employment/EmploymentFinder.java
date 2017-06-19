/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.shr.find.employment;

/**
 * The Interface EmploymentFinder.
 */
public interface EmploymentFinder {
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the employment dto
	 */
	EmploymentDto findByCode(String code);
}
