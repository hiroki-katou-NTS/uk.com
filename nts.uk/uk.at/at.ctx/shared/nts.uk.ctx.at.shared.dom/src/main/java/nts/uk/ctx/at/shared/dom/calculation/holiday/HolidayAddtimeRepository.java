package nts.uk.ctx.at.shared.dom.calculation.holiday;

import java.util.List;
import java.util.Optional;

public interface HolidayAddtimeRepository {

	/**
	 * Find Holiday Addtime by CID
	 * @param companyId
	 * @return
	 */
	List<HolidayAddtime> findByCompanyId(String companyId);
	
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	Optional<HolidayAddtime >findByCId(String companyId);
	/**
	 * Add Holiday Addtime
	 * @param holidayAddtime
	 */
	void add(HolidayAddtime holidayAddtime);

	/**
	 * Update Holiday Addtime
	 * @param holidayAddtime
	 */
	void update(HolidayAddtime holidayAddtime);

}
