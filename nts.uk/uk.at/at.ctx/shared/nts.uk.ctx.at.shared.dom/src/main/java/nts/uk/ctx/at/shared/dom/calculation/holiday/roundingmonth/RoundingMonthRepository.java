package nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author phongtq
 *
 */
public interface RoundingMonthRepository {

	/**
	 * Find by CID
	 * 
	 * @param companyId
	 * @param itemTimeId
	 * @return
	 */
	List<RoundingMonth> findByCompanyId(String companyId, String itemTimeId);

	/**
	 * Add Rounding Month
	 * 
	 * @param month
	 */
	void add(RoundingMonth month);

	/**
	 * Update Rounding Month
	 * 
	 * @param month
	 */
	void update(RoundingMonth month);

	/**
	 * Check by CID
	 * 
	 * @param companyId
	 * @param timeItemId
	 * @return
	 */
	Optional<RoundingMonth> findByCId(String companyId, String timeItemId);

}
