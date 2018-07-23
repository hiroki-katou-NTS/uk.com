package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * リポジトリ：暫定年休管理データ
 * @author shuichu_ishida
 */
public interface TempAnnualLeaveMngRepository {
	
	/**
	 * @param employeeID
	 * @return List<TempAnnualLeaveManagement>
	 */
	List<TempAnnualLeaveManagement> findByEmployeeID(String employeeID);
	/**
	 * 検索
	 * @param employeeId 社員ID
	 * @param ymd 年月日
	 * @return 該当する暫定年休管理データ
	 */
	Optional<TempAnnualLeaveManagement> find(String employeeId, GeneralDate ymd);

	/**
	 * 検索　（期間）
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @return 該当する暫定年休管理データ　（年月日順）
	 */
	List<TempAnnualLeaveManagement> findByPeriodOrderByYmd(String employeeId, DatePeriod period);

	/**
	 * 登録および更新
	 * @param tempAnnualLeaveManagement 暫定年休管理データ
	 */
	void persistAndUpdate(TempAnnualLeaveManagement tempAnnualLeaveManagement);
	
	/**
	 * 削除
	 * @param employeeId 社員ID
	 * @param ymd 年月日
	 */
	void remove(String employeeId, GeneralDate ymd);
	
	/**
	 * 削除　（期間）
	 * @param employeeId 社員ID
	 * @param period 期間
	 */
	void removeByPeriod(String employeeId, DatePeriod period);
	
	/**
	 * 削除　（基準日以前）
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 */
	void removePastYmd(String employeeId, GeneralDate criteriaDate);
	/**
	 * ドメインモデル「暫定年休管理データ」を取得する
	 * @param employeeId
	 * @param workTypeCode
	 * @param period 指定期間の開始日<=年月日<=INPUT．指定期間の終了日
	 * @return
	 */
	List<TempAnnualLeaveManagement> findBySidWorkTypePeriod(String employeeId, String workTypeCode, DatePeriod period);
}
