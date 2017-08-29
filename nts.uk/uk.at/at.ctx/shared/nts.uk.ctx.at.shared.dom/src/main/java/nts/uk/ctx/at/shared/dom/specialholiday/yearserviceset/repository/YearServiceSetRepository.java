package nts.uk.ctx.at.shared.dom.specialholiday.yearserviceset.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceset.YearServiceSet;

public interface YearServiceSetRepository {
	/**
	 * get all data
	 * @param companyId
	 * @return
	 * author: Hoang Yen
	 */
	List<YearServiceSet> findAll (String companyId);
	/**
	 * update year month day
	 * @param yearServiceSet
	 * author: Hoang Yen
	 */
	void update(YearServiceSet yearServiceSet);
	/**
	 * insert year month day
	 * @param yearServiceSet
	 * author: Hoang Yen
	 */
	void insert(YearServiceSet yearServiceSet);
	/**
	 * find by code
	 * @param companyId
	 * @param specialHolidayCode
	 * @param yearServiceType
	 * @return
	 * author: Hoang Yen
	 */
	Optional<YearServiceSet> find(String companyId, int specialHolidayCode, int yearServiceType);
}
