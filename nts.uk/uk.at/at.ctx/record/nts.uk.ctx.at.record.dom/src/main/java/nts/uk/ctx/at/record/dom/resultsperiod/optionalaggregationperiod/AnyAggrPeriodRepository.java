package nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod;

import java.util.List;
import java.util.Optional;

/**
 * The interface Any aggr period repository.<br>
 * Repository 任意集計期間
 */
public interface AnyAggrPeriodRepository {

	/**
	 * Finds all.
	 *
	 * @param companyId the company id
	 * @return the <code>AnyAggrPeriod</code> list
	 */
	List<AnyAggrPeriod> findAll(String companyId);

	/**
	 * Finds by company id.
	 *
	 * @param companyId the company id
	 * @return the optional <code>AnyAggrPeriod</code>
	 */
	Optional<AnyAggrPeriod> findByCompanyId(String companyId);

	/**
	 * Finds one.
	 *
	 * @param companyId     the company id
	 * @param aggrFrameCode the aggr frame code
	 * @return the optional <code>AnyAggrPeriod</code>
	 */
	Optional<AnyAggrPeriod> findOne(String companyId, String aggrFrameCode);

	/**
	 * Check existed.
	 *
	 * @param companyId     the company id
	 * @param aggrFrameCode the aggr frame code
	 * @return {@code true} if existed, otherwise {@code false}
	 */
	boolean checkExisted(String companyId, String aggrFrameCode);

	/**
	 * Add any aggr period.
	 *
	 * @param domain the domain 任意集計期間
	 */
	void addAnyAggrPeriod(AnyAggrPeriod domain);

	/**
	 * Update any aggr period.
	 *
	 * @param domain the domain 任意集計期間
	 */
	void updateAnyAggrPeriod(AnyAggrPeriod domain);

	/**
	 * Delete any aggr period.
	 *
	 * @param companyId     the company id
	 * @param aggrFrameCode the aggr frame code
	 */
	void deleteAnyAggrPeriod(String companyId, String aggrFrameCode);

}
