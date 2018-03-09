package nts.uk.ctx.at.record.dom.divergence.time.history;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * The Interface WorkTypeDivergenceReferenceTimeRepository.
 */
public interface WorkTypeDivergenceReferenceTimeRepository {
	
	/**
	 * Find by key.
	 *
	 * @param histId the hist id
	 * @param workTypeCode the work type code
	 * @param divergenceTimeNo the divergence time no
	 * @return the work type divergence reference time
	 */
	WorkTypeDivergenceReferenceTime findByKey(String histId, WorkTypeCode workTypeCode, DivergenceType divergenceTimeNo);
	
	/**
	 * Find all.
	 *
	 * @param histId the hist id
	 * @return the list
	 */
	List<WorkTypeDivergenceReferenceTime> findAll(String histId);
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(WorkTypeDivergenceReferenceTime domain);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(WorkTypeDivergenceReferenceTime domain);
	
	/**
	 * Delete.
	 *
	 * @param domain the domain
	 */
	void delete(WorkTypeDivergenceReferenceTime domain);
}
