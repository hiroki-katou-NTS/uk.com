package nts.uk.ctx.at.record.dom.divergence.time.history;

import java.util.List;

/**
 * The Interface CompanyDivergenceReferenceTimeHistoryRepository.
 */
public interface CompanyDivergenceReferenceTimeHistoryRepository {
	
	/**
	 * Find by key.
	 *
	 * @param companyId the company id
	 * @param histId the hist id
	 * @return the company divergence reference time history
	 */
	CompanyDivergenceReferenceTimeHistory findByKey(String companyId, String histId);
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<CompanyDivergenceReferenceTimeHistory> findAll(String companyId);
	
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
