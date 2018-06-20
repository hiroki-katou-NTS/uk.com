/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule.scrA;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface SEmpHistExportAdapter.
 * @author HoangDD
 */
public interface SEmpHistExportAdapter {
	
	/**
	 * Gets the s emp hist export.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the s emp hist export
	 */
	Optional<SEmpHistExportImported> getSEmpHistExport(String companyId, String employeeId, GeneralDate baseDate);
}
