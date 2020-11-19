package nts.uk.ctx.at.shared.dom.adapter.holidaymanagement;

import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;

/**
 * The Interface CompanyAdapter.
 */
public interface CompanyAdapter {
	
	/**
	 * Gets the first month.
	 *
	 * @param companyId the company id
	 * @return the first month
	 */
	CompanyDto getFirstMonth(String companyId);
	CompanyDto getFirstMonthRequire(CacheCarrier cacheCarrier,String companyId); 
	
	/**
	 * 暦上の年月を渡して、年度に沿った年月を取得する
	 * @param companyId 会社ID
	 * @param yearMonth 年月
	 * @return 年月
	 */
	// RequestList557
	YearMonth getYearMonthFromCalenderYM(CacheCarrier cacheCarrier, String companyId, YearMonth yearMonth);
	
	
	Optional<CompanyImport622> getCompanyNotAbolitionByCid(String cid);
	
	// 年度の期間を取得
	YearMonthPeriod get(String cid, int year);
}
