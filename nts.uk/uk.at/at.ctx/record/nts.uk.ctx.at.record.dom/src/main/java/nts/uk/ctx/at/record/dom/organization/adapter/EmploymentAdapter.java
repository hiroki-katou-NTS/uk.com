/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.organization.adapter;

import java.util.List;

import nts.uk.ctx.at.record.dom.organization.EmploymentImported;

/**
 * The Interface EmploymentAdapter.
 */
public interface EmploymentAdapter {

	/**
	 * Gets the all employment.
	 *
	 * @param comId the com id
	 * @return the all employment
	 */
	List<EmploymentImported> getAllEmployment(String comId);
}
