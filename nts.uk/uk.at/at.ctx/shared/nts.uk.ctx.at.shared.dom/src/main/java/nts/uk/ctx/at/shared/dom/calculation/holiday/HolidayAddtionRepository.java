/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.calculation.holiday;

import java.util.List;
import java.util.Optional;

/**
 * The Interface HolidayAddtionRepository.
 */
public interface HolidayAddtionRepository {

	/**
	 * Find by company id.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<HolidayAddtionSet> findByCompanyId(String companyId);
	
	/**
	 * Find by C id.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<HolidayAddtionSet >findByCId(String companyId);
	
	/**
	 * Adds the.
	 *
	 * @param holidayAddtime the holiday addtime
	 */
	void add(HolidayAddtionSet holidayAddtime);

	/**
	 * Update.
	 *
	 * @param holidayAddtime the holiday addtime
	 */
	void update(HolidayAddtionSet holidayAddtime);

}
