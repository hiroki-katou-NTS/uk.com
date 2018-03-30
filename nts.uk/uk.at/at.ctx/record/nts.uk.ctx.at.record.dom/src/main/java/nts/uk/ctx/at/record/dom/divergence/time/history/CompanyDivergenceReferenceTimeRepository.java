
package nts.uk.ctx.at.record.dom.divergence.time.history;

import java.util.List;
import java.util.Optional;

/**
 * The Interface CompanyDivergenceReferenceTimeRepository.
 */
public interface CompanyDivergenceReferenceTimeRepository {

	/**
	 * Find by key.
	 *
	 * @param histId
	 *            the hist id
	 * @param divergenceTimeNo
	 *            the divergence time no
	 * @return the optional of company divergence reference time
	 */
	Optional<CompanyDivergenceReferenceTime> findByKey(String histId, Integer divergenceTimeNo);

	/**
	 * Find all.
	 *
	 * @param histId
	 *            the hist id
	 * @return the list
	 */
	List<CompanyDivergenceReferenceTime> findAll(String histId);

	/**
	 * Update.
	 *
	 * @param listDomain
	 *            the list domain
	 */
	void update(List<CompanyDivergenceReferenceTime> listDomain);

	/**
	 * Delete.
	 *
	 * @param domain
	 *            the domain
	 */
	void delete(CompanyDivergenceReferenceTime domain);

	/**
	 * Adds the default data when create history.
	 *
	 * @param historyId
	 *            the history id
	 */
	void addDefaultDataWhenCreateHistory(String historyId);

	/**
	 * Copy data from latest history.
	 *
	 * @param targetHistId
	 *            the target hist id
	 * @param destHistId
	 *            the dest hist id
	 */
	void copyDataFromLatestHistory(String targetHistId, String destHistId);

	/**
	 * Find by history id and divergence time nos.
	 *
	 * @param historyId
	 *            the history id
	 * @param divTimeNos
	 *            the div time nos
	 * @return the company divergence reference time
	 */
	List<CompanyDivergenceReferenceTime> findByHistoryIdAndDivergenceTimeNos(String historyId,
			List<Integer> divTimeNos);
}
