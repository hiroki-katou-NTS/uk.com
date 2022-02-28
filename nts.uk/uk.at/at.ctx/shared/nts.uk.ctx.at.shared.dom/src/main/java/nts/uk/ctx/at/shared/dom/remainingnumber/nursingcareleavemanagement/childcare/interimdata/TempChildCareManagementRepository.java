package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 *　リポジトリ：暫定子の看護管理データ
  * @author yuri_tamakoshi
 */

public interface TempChildCareManagementRepository{

	/**
	 * 検索
	 * @param employeeId 社員ID
	 * @param ymd 年月日
	 * @return 該当する暫定子の看護管理データ
	 */
	public List<TempChildCareManagement> find(String employeeId, GeneralDate ymd);

	/**
	 * 検索　（期間）
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @return 該当する暫定子の看護データ　（年月日順）
	 *  RequestList685
	 */
	List<TempChildCareManagement> findByPeriodOrderByYmd(String employeeId, DatePeriod period);

	/**
	 * 登録および更新
	 * @param tempChildCareManagement 暫定子の看護データ
	 */
	void persistAndUpdate(TempChildCareManagement tempChildCareManagement);

	/**
	 * 削除
	 * @param tempChildCareManagement 暫定子の看護管理データ
	 */
	void remove(TempChildCareManagement tempChildCareManagement);

	/**
	 * 削除　(年月日)
	 * @param sid
	 * @param ymd
	 */
	public void removeBySidAndYmd(String sid, GeneralDate ymd);
	
	/**
	 * 削除　(期間)
	 * @param sid
	 * @param period
	 */
	public void deleteByPeriod(String sid, DatePeriod period);
	
	
	/**
	 *  年月日より前全て削除
	 * @param sid
	 * @param ymd
	 */
	public void deleteBySidBeforeTheYmd(String sid, GeneralDate ymd);
}
