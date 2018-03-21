package nts.uk.ctx.at.record.dom.divergence.time;

import java.util.List;

public interface DivergenceReasonSelectRepository {

	/**
	 * Find all reason.
	 *
	 * @param divTimeNo the div time no
	 * @param companyId the company id
	 * @return the list
	 */
	List<DivergenceReasonSelect> findAllReason(int divTimeNo, String companyId);

	/**
	 * Find reason info.
	 *
	 * @param divTimeNo the div time no
	 * @param company the company
	 * @param reasonCode the reason code
	 * @return the divergence reason select
	 */
	DivergenceReasonSelect findReasonInfo(int divTimeNo,String company,String reasonCode);

	/**
	 * Delete.
	 *
	 * @param divergenceReasonSelect the divergence reason select
	 */
	void delete(Integer divTimeNo, DivergenceReasonSelect divergenceReasonSelect);

	/**
	 * Adds the.
	 *
	 * @param divergenceReasonSelect the divergence reason select
	 */
	void add(Integer divTimeNo, DivergenceReasonSelect divergenceReasonSelect);

	/**
	 * Update.
	 *
	 * @param divergenceReasonSelect the divergence reason select
	 */
	void update(int divTimeNo,DivergenceReasonSelect divergenceReasonSelect);
}
