package nts.uk.ctx.at.record.dom.divergence.time.history;

/**
 * The Interface CompanyDivergenceReferenceTimeHistoryRepository.
 */
public interface CompanyDivergenceReferenceTimeHistoryRepository {
	
	
	/**
	 * Find by hist id.
	 *
	 * @param companyId the company id
	 * @param histId the hist id
	 * @return the company divergence reference time history
	 */
	CompanyDivergenceReferenceTimeHistory findByHistId(String companyId, String histId);
	
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
