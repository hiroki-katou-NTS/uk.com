package nts.uk.ctx.at.record.dom.divergence.time.history;

import nts.arc.time.GeneralDate;

/**
 * The Interface CompanyDivergenceReferenceTimeHistoryRepository.
 */
public interface CompanyDivergenceReferenceTimeHistoryRepository {
	
	/**
	 * Findby period date.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the company divergence reference time history
	 */
	CompanyDivergenceReferenceTimeHistory findbyPeriodDate(GeneralDate startDate, GeneralDate endDate);
	
	/**
	 * Find by hist id.
	 *
	 * @param histId the hist id
	 * @return the company divergence reference time history
	 */
	CompanyDivergenceReferenceTimeHistory findByHistId(String histId);
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	CompanyDivergenceReferenceTimeHistory findAll(String companyId);
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(CompanyDivergenceReferenceTimeHistory domain);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(CompanyDivergenceReferenceTimeHistory domain);
	
	/**
	 * Delete.
	 *
	 * @param domain the domain
	 */
	void delete(CompanyDivergenceReferenceTimeHistory domain);
}
