/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.access.jobtitle;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.access.jobtitle.dto.AcJobTitleDto;

/**
 * The Interface PersonAdapter.
 */
public interface JobTitleAdapter {

	/**
	 * Find by person ids.
	 *
	 * @param personIds the person ids
	 * @return the list
	 */
	List<AcJobTitleDto> findAll(String companyId, GeneralDate referenceDate);
}
