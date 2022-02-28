package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.interimdata;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface TempPublicHolidayManagementRepository {

	/**
	 * 検索 暫定公休管理データ
	 * @param sid 社員ID
	 * @param ymd　年月日
	 * @return
	 */
	List<TempPublicHolidayManagement> find(String sid, GeneralDate ymd);
	/**
	 * 検索 暫定公休管理データ（期間）
	 * @param sid 社員ID
	 * @param period　期間
	 * @return
	 */
	List<TempPublicHolidayManagement> findByPeriodOrderByYmd(String sid, DatePeriod period);
	
	
	/**
	 * 削除 暫定公休管理データ（期間）
	 * @param sid
	 * @param period
	 */
	void deleteByPeriod(String sid, DatePeriod period);
	
	/**
	 * 削除　（年月日）
	 * @param employeeId
	 * @param date
	 */
	public void deleteByDate(String employeeId, GeneralDate date);
	
	/**
	 * 登録もしくは更新
	 * @param domain
	 */
	public void persistAndUpdate(TempPublicHolidayManagement domain);
	
	/**
	 *  年月日より前全て削除
	 * @param sid
	 * @param ymd
	 */
	public void deleteBySidBeforeTheYmd(String sid, GeneralDate ymd);
}
