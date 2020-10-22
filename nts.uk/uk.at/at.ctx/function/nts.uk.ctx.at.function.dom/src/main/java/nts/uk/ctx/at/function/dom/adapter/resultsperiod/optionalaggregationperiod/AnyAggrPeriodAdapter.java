package nts.uk.ctx.at.function.dom.adapter.resultsperiod.optionalaggregationperiod;

import java.util.List;
import java.util.Optional;

/**
 * The interface Any aggr period adapter.
 */
public interface AnyAggrPeriodAdapter {

	/**
	 * Finds all.
	 *
	 * @param companyId the company id
	 * @return the <code>AnyAggrPeriodImport</code> list
	 */
	List<AnyAggrPeriodImport> findAll(String companyId);

	/**
	 * Finds by company id.
	 *
	 * @param companyId the company id
	 * @return the optional <code>AnyAggrPeriodImport</code>
	 */
	Optional<AnyAggrPeriodImport> findByCompanyId(String companyId);

	/**
	 * Finds one.
	 *
	 * @param companyId     the company id
	 * @param aggrFrameCode the aggr frame code
	 * @return the optional <code>AnyAggrPeriodImport</code>
	 */
	Optional<AnyAggrPeriodImport> findOne(String companyId, String aggrFrameCode);

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
	 * @param aggrPeriodImport the import 任意集計期間
	 */
	void addAnyAggrPeriod(AnyAggrPeriodImport aggrPeriodImport);

	/**
	 * Update any aggr period.
	 *
	 * @param aggrPeriodImport the import 任意集計期間
	 */
	void updateAnyAggrPeriod(AnyAggrPeriodImport aggrPeriodImport);

	/**
	 * Delete any aggr period.
	 *
	 * @param companyId     the company id
	 * @param aggrFrameCode the aggr frame code
	 */
	void deleteAnyAggrPeriod(String companyId, String aggrFrameCode);

}
