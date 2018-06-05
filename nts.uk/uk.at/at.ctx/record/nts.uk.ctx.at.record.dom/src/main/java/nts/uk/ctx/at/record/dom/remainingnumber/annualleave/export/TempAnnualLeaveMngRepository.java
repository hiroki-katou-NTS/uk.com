package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
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
}
