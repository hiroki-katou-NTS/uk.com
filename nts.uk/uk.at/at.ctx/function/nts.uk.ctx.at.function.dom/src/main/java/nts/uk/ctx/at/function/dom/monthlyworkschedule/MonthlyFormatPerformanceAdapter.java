package nts.uk.ctx.at.function.dom.monthlyworkschedule;

import java.util.Optional;

// TODO: Auto-generated Javadoc
/**
 * The Interface MonthlyFormatPerformanceAdapter.
 */
public interface MonthlyFormatPerformanceAdapter {

	/**
	 * Gets the format performance.
	 *
	 * @param companyId the company id
	 * @return the format performance
	 */
	Optional<MonthlyFormatPerformanceImport> getFormatPerformance(String companyId);
}
