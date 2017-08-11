package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author TanLV
 *
 */

/**
 * The Interface YearHolidayGrantRepository.
 */
public interface YearHolidayRepository {
	/**
	 * Gets the year holiday grant.
	 *
	 * @param companyId the company id
	 * @return the year holiday grant
	 */
	List<YearHoliday> findAll(String companyId);

	/**
	 * Gets the year holiday grant by code.
	 *
	 * @param companyId the company id
	 * @param yearHolidayCode the year holiday code
	 * @return the year holiday grant by code
	 */
	Optional<YearHoliday> findByCode(String companyId, String yearHolidayCode);
	
	/**
	 * Adds the year holiday grant.
	 *
	 * @param yearHolidayGrant the year holiday grant
	 */
	void add(YearHoliday yearHolidayGrant);
	
	/**
	 * Update the year holiday grant.
	 *
	 * @param yearHolidayGrant the year holiday grant
	 */
	void update(YearHoliday yearHolidayGrant);
	
	/**
	 * Removes the year holiday grant by id.
	 *
	 * @param companyId the company id
	 * @param yearHolidayCode the year holiday code
	 */
	void remove(String companyId, String yearHolidayCode);
}
