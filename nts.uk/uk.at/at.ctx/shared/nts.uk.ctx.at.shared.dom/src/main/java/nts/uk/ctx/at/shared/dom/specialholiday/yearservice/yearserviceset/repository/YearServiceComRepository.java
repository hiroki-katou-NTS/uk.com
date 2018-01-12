package nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceset.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearservicecom.YearServiceCom;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceset.YearServiceSet;

public interface YearServiceComRepository {
	/**
	 * get all data
	 * @param companyId
	 * @return
	 * author: Hoang Yen
	 */
	List<YearServiceSet> findAllSet (String companyId, String specialHolidayCode);
	/**
	 * update year month day
	 * @param yearServiceSet
	 * author: Hoang Yen
	 */
	void updateSet(List<YearServiceSet> yearServiceSetLs);
	/**
	 * insert year month day
	 * @param yearServiceSet
	 * author: Hoang Yen
	 */
	void insertSet(List<YearServiceSet> yearServiceSet);
	/**
	 * find by code
	 * @param companyId
	 * @param specialHolidayCode
	 * @param yearServiceType
	 * @return
	 * author: Hoang Yen
	 */
	Optional<YearServiceSet> findSet(String companyId, String specialHolidayCode, int yearServiceType);
	/**
	 * find by year
	 * @param companyId
	 * @param year
	 * @return
	 */
	List<YearServiceSet> findYearSet (String companyId, int year);
	/**
	 * get all data
	 * @param companyId
	 * @return
	 */
	Optional<YearServiceCom> findAllCom(String companyId, String specialHolidayCode);
	/**
	 * update length Service Year Atr
	 * @param yearServiceCom
	 * author: Hoang Yen
	 */
	void updateCom(YearServiceCom yearServiceCom);
	/**
	 * insert length Service Year Atr
	 * @param yearServiceCom
	 * author: Hoang Yen
	 */
	void insertCom (YearServiceCom yearServiceCom);
	/**
	 * find a YearServiceCom by specialHolidayCode
	 * @param companyId
	 * @param specialHolidayCode
	 * @return
	 * author: Hoang Yen
	 */
	Optional<YearServiceCom> findCom(String companyId, String specialHolidayCode);
}
