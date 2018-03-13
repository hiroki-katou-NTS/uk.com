package nts.uk.ctx.at.record.dom.divergence.time.history;

import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * The Interface WorkTypeDivergenceReferenceTimeHistoryRepository.
 */
public interface WorkTypeDivergenceReferenceTimeHistoryRepository {
	
	/**
	 * Find by key.
	 *
	 * @param companyId the company id
	 * @param workTypeCode the work type code
	 * @param histId the hist id
	 * @return the work type divergence reference time history
	 */
	WorkTypeDivergenceReferenceTimeHistory findByKey(String histId);
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @param workTypeCode the work type code
	 * @return the work type divergence reference time history
	 */
	WorkTypeDivergenceReferenceTimeHistory findAll(String companyId, WorkTypeCode workTypeCode);
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(WorkTypeDivergenceReferenceTimeHistory domain);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(WorkTypeDivergenceReferenceTimeHistory domain);
	
	/**
	 * Delete.
	 *
	 * @param domain the domain
	 */
	void delete(WorkTypeDivergenceReferenceTimeHistory domain);
}
