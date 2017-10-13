/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.employment;

import java.util.List;

import nts.uk.ctx.bs.employee.app.find.employment.dto.EmploymentDto;

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
