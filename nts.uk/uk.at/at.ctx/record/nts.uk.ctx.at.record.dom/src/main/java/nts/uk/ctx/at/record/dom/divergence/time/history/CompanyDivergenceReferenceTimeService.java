package nts.uk.ctx.at.record.dom.divergence.time.history;

import java.util.Date;

/**
 * The Interface CompanyDivergenceReferenceTimeService.
 */
public interface CompanyDivergenceReferenceTimeService {

	/**
	 * Check divergence time.
	 *
	 * @param userId the user id
	 * @param companyId the company id
	 * @param processDate the process date
	 * @param divergenceTimeNo the divergence time no
	 * @param DivergenceTimeOccurred the divergence time occurred
	 */
	public void CheckDivergenceTime(Integer userId, String companyId, Date processDate, Integer divergenceTimeNo, String DivergenceTimeOccurred );
}
