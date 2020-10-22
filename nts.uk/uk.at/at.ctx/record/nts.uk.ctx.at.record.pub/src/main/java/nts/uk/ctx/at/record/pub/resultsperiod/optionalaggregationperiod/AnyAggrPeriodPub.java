package nts.uk.ctx.at.record.pub.resultsperiod.optionalaggregationperiod;

import java.util.List;
import java.util.Optional;

/**
 * 任意集計期間
 * The Interface AnyAggrPeriodPub.
 */
public interface AnyAggrPeriodPub {

	/**
	 * Finds all.
	 *
	 * @param companyId the company id
	 * @return the <code>AnyAggrPeriodExport</code> list
	 */
	List<AnyAggrPeriodExport> findAll(String companyId);

	/**
	 * Finds by company id.
	 *
	 * @param companyId the company id
	 * @return the optional <code>AnyAggrPeriodExport</code>
	 */
	Optional<AnyAggrPeriodExport> findByCompanyId(String companyId);

	/**
	 * Finds one.
	 *
	 * @param companyId     the company id
	 * @param aggrFrameCode the aggr frame code
	 * @return the optional <code>AnyAggrPeriodExport</code>
	 */
	Optional<AnyAggrPeriodExport> findOne(String companyId, String aggrFrameCode);

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
	 * @param aggrPeriodExport the export 任意集計期間
	 */
	void addAnyAggrPeriod(AnyAggrPeriodExport aggrPeriodExport);

	/**
	 * Update any aggr period.
	 *
	 * @param aggrPeriodExport the export 任意集計期間
	 */
	void updateAnyAggrPeriod(AnyAggrPeriodExport aggrPeriodExport);

	/**
	 * Delete any aggr period.
	 *
	 * @param companyId     the company id
	 * @param aggrFrameCode the aggr frame code
	 */
	void deleteAnyAggrPeriod(String companyId, String aggrFrameCode);

}
