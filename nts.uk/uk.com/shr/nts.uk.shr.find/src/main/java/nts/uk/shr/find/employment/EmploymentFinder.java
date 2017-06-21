/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.shr.find.employment;

import java.util.List;

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
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	List<EmploymentDto> findAll();
	
}
