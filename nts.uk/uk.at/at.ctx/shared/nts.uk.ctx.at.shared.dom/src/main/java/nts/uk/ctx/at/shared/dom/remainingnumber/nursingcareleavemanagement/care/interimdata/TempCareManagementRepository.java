package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 *　リポジトリ：暫定介護管理データ
  * @author yuri_tamakoshi
 */
public interface TempCareManagementRepository {

	/**
	 * 検索
	 * @param employeeId 社員ID
	 * @param ymd 年月日
	 * @return 該当する暫定介護管理データ
	 */
	public List<TempCareManagement> find(String employeeId, GeneralDate ymd);

	/**
	 * 検索　（期間）
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @return 該当する暫定介護データ　（年月日順）
	 *  RequestList686
	 */
	List<TempCareManagement> findByPeriodOrderByYmd(String employeeId, DatePeriod period);

	/**
	 * 登録および更新
	 * @param tempCareManagement 暫定介護データ
	 */
	void persistAndUpdate(TempCareManagement tempCareManagement);

	/**
	 * 削除
	 * @param employeeId 社員ID
	 * @param ymd 年月日
	 * @param tempCareManagement 暫定介護データ
	 */
	void remove(TempCareManagement tempCareManagement);

	/**
	 * 削除
	 * @param sid
	 * @param ymd
	 */
	public void deleteBySidAndYmd(String sid, GeneralDate ymd);
	
	/**
	 * 削除　期間
	 * @param sid
	 * @param period
	 */
	public void deleteBySidDatePeriod(String sid, DatePeriod period);

}

