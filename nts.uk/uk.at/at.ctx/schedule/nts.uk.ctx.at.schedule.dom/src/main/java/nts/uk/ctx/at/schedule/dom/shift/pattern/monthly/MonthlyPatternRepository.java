/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.monthly;

import java.util.List;
import java.util.Optional;

/**
 * The Interface MonthlyPatternRepository.
 */
public interface MonthlyPatternRepository {
	
	/**
	 * Adds the.
	 *
	 * @param monthlyPattern the monthly pattern
	 */
	public void add(MonthlyPattern monthlyPattern);
	
	
	/**
	 * Update.
	 *
	 * @param monthlyPattern the monthly pattern
	 */
	public void update(MonthlyPattern monthlyPattern);
	
	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param monthlyPatternCode the monthly pattern code
	 */
	public void remove(String companyId, String monthlyPatternCode);
	
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	//	会社の月間パターンを取得する
	public List<MonthlyPattern> findAll(String companyId);
	
	
	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param monthlyPatternCode the monthly pattern code
	 * @return the optional
	 */
	public Optional<MonthlyPattern> findById(String companyId, String monthlyPatternCode);

	/**
	 * Check exists.
	 * exists(会社ID, 月間パターンコード)
	 *
	 * @param companyId the company id
	 * @param monthlyPatternCode the monthly pattern code
	 */
	public boolean exists(String companyId, String monthlyPatternCode);

}
