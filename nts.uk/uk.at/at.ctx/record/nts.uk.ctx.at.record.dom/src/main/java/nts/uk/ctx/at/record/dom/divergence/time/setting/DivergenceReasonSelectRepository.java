package nts.uk.ctx.at.record.dom.divergence.time.setting;

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
	void delete(DivergenceReasonSelect divergenceReasonSelect);

	/**
	 * Adds the.
	 *
	 * @param divergenceReasonSelect the divergence reason select
	 */
	void add(DivergenceReasonSelect divergenceReasonSelect);

	/**
	 * Update.
	 *
	 * @param divergenceReasonSelect the divergence reason select
	 */
	void update(DivergenceReasonSelect divergenceReasonSelect);
}
