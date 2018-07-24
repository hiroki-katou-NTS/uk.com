package nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent;

import java.util.List;
import java.util.Optional;

public interface SpecialHolidayEventRepository {

	/**
	 * findBy CompanyId And No
	 * 
	 * @param companyId
	 * @param sHsNo
	 * @return
	 */
	List<SpecialHolidayEvent> findByCompanyIdAndNoLst(String companyId, List<Integer> sHsNo);

	/**
	 * findBy key
	 * 
	 * @param companyId
	 * @param key
	 * @return
	 */
	Optional<SpecialHolidayEvent> findByKey(String companyId, int eventNo);

	void insert(SpecialHolidayEvent domain);

	void update(SpecialHolidayEvent domain);

	void remove(String companyId, int specialHolidayEventNo);

}
