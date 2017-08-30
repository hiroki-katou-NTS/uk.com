package nts.uk.ctx.at.shared.dom.specialholiday.yearservicecom.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.specialholiday.yearservicecom.YearServiceCom;

public interface YearServiceComRepository {
	/**
	 * get all data
	 * @param companyId
	 * @return
	 */
	List<YearServiceCom> findAll(String companyId);
	/**
	 * update length Service Year Atr
	 * @param yearServiceCom
	 * author: Hoang Yen
	 */
	void update(YearServiceCom yearServiceCom);
	/**
	 * insert length Service Year Atr
	 * @param yearServiceCom
	 * author: Hoang Yen
	 */
	void insert (YearServiceCom yearServiceCom);
	/**
	 * find a YearServiceCom by specialHolidayCode
	 * @param companyId
	 * @param specialHolidayCode
	 * @return
	 * author: Hoang Yen
	 */
	Optional<YearServiceCom> find(String companyId, int specialHolidayCode);
}
