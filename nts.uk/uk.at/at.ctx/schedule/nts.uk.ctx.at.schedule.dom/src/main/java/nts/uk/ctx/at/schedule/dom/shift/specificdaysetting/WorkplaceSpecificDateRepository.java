package nts.uk.ctx.at.schedule.dom.shift.specificdaysetting;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface WorkplaceSpecificDateRepository {
	
	/**
	 * insert
	 * @param companyId 会社ID
	 * @param domain 職場特定日設定
	 */
	void insert ( String companyId, WorkplaceSpecificDateItem domain );
	
	/**
	 * update
	 * @param companyId 会社ID
	 * @param domain 職場特定日設定
	 */
	void update ( String companyId, WorkplaceSpecificDateItem domain );
	
	/**
	 * delete
	 * @param workplaceId 職場ID
	 * @param ymd 年月日
	 */
	void delete( String workplaceId, GeneralDate ymd );
	
	/**
	 * delete
	 * @param workplaceId 職場ID
	 * @param period 期間
	 */
	void delete ( String workplaceId, DatePeriod period );
	
	/**
	 * get
	 * @param workplaceId 職場ID
	 * @param ymd 年月日
	 * @return
	 */
	Optional<WorkplaceSpecificDateItem> get( String workplaceId, GeneralDate ymd );
	
	/**
	 * *get
	 * @param workplaceId 職場ID
	 * @param period 期間
	 * @return
	 */
	List<WorkplaceSpecificDateItem> getList( String workplaceId, DatePeriod period );

}
