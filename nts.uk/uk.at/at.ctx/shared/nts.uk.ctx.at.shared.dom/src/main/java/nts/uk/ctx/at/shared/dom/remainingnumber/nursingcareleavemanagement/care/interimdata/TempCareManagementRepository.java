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
	 */
	void remove(String employeeId,  GeneralDate ymd, TempCareManagement tempCareManagement);

	/**
	 * ドメインモデル「暫定介護管理データ」を取得する
	 * @param employeeId 社員ID
	 * @param period 指定期間の開始日<=対象日<=INPUT．指定期間の終了日
	 * @return 暫定介護管理データ
	 */
	 // RequestList686
	public List<TempCareManagement> findBySidPeriod(String employeeId, DatePeriod period);

}

