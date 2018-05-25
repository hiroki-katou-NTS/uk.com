/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule;

import java.util.Optional;

/**
 * The Interface FormatPerformanceAdapter.
 */
// @author HoangDD
public interface FormatPerformanceAdapter {
	
	/**
	 * Gets the format performance.
	 *
	 * @param companyId the company id
	 * @return the format performance
	 */
	Optional<FormatPerformanceImport> getFormatPerformance(String companyId);
}
