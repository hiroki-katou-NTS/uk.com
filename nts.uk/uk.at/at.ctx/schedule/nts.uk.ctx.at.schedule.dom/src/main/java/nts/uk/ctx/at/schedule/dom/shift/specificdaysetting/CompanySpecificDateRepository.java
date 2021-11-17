package nts.uk.ctx.at.schedule.dom.shift.specificdaysetting;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface CompanySpecificDateRepository {
	/**
	 * insert
	 * @param domain 全社特定日設定
	 */
	void insert( CompanySpecificDateItem domain );
	
	/**
	 * update
	 * @param domain 全社特定日設定
	 */
	void update ( CompanySpecificDateItem domain );
	
	/**
	 * delete
	 * @param companyId 会社ID
	 * @param ymd 年月日
	 */
	void delete ( String companyId, GeneralDate ymd );
	
	/**
	 * delete
	 * @param companyId 会社ID
	 * @param period 期間
	 */
	void delete ( String companyId, DatePeriod period );
	
	/**
	 * get
	 * @param companyId 会社ID
	 * @param ymd 年月日
	 * @return
	 */
	Optional<CompanySpecificDateItem> get( String companyId, GeneralDate ymd );
	
	/**
	 * *get
	 * @param companyId 会社ID
	 * @param period 期間
	 * @return
	 */
	List<CompanySpecificDateItem> getList( String companyId, DatePeriod period );
	
}
