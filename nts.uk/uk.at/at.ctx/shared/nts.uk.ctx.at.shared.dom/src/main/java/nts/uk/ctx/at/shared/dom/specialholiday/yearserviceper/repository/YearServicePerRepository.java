package nts.uk.ctx.at.shared.dom.specialholiday.yearserviceper.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceper.YearServicePer;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceper.YearServicePerSet;

public interface YearServicePerRepository {
	/**
	 * get all service set
	 * @param companyId
	 * @return
	 * author: Hoang Yen
	 */
	List<YearServicePerSet> findAllPerSet (String companyId);
	/**
	 * update year month day
	 * @param yearServiceSet
	 * author: Hoang Yen
	 */
	void updatePerSet(List<YearServicePerSet> yearServicePerSetLs);
	/**
	 * insert year month day
	 * @param yearServiceSet
	 * author: Hoang Yen
	 */
	void insertPerSet(List<YearServicePerSet> yearServicePerSetLs);
	/**
	 * find by code
	 * @param companyId
	 * @param specialHolidayCode
	 * @param yearServiceType
	 * @return
	 * author: Hoang Yen
	 */
	Optional<YearServicePerSet> findPerSet(String companyId, String specialHolidayCode, String yearServiceCode, int yearServiceType);
	/**
	 * find by year
	 * @param companyId
	 * @param year
	 * @return
	 */
	List<YearServicePerSet> findYearPerSet (String companyId, String yearServiceCode, Integer year);
	/**
	 * get all service person
	 * @param companyId
	 * @return
	 * author: Hoang Yen
	 */
	List<YearServicePer> findAllPer (String companyId);
	/**
	 * update name
	 * @param yearServicePer
	 * author: Hoang Yen
	 */
	void updatePer(YearServicePer yearServicePer);
	/**
	 * insert item
	 * @param yearServicePer
	 * author: Hoang Yen
	 */
	void insertPer(YearServicePer yearServicePer);
	/**
	 * find by code
	 * @param companyId
	 * @param specialHolidayCode
	 * @param yearServiceCode
	 * @return
	 * author HoangYen
	 */
	Optional<YearServicePer> findPer(String companyId, String specialHolidayCode, String yearServiceCode);
}
