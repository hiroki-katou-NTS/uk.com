package nts.uk.ctx.at.shared.dom.calculation.holiday;

import java.util.List;
import java.util.Optional;
/**
 * 
 * @author phongtq
 *
 */
public interface HolidayAddtionRepository {

	/**
	 * Find Holiday Addtime by CID
	 * @param companyId
	 * @return
	 */
	List<HolidayAddtion> findByCompanyId(String companyId);
	
	/**
	 * Check by CID
	 * @param companyId
	 * @return
	 */
	Optional<HolidayAddtion >findByCId(String companyId);
	
	/**
	 * Add Holiday Addtime
	 * @param holidayAddtime
	 */
	void add(HolidayAddtion holidayAddtime);

	/**
	 * Update Holiday Addtime
	 * @param holidayAddtime
	 */
	void update(HolidayAddtion holidayAddtime);

}
