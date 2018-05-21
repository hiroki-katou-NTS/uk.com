/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.person;

import java.util.List;

/**
 * The Interface EmployeeInfoPublisher.
 */
public interface EmployeeInfoPublisher {
	
	/**
	 * Find.
	 *
	 * @param dto the dto
	 * @return the list
	 */
	List<EmployeeInfoExport> find(EmployeeInfoDto dto);
}
