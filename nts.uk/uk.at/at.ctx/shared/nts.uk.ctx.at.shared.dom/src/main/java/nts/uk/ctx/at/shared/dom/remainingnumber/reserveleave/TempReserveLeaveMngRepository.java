package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * リポジトリ�暫定積立年休管球�ータ
 * @author shuichu_ishida
 */
public interface TempReserveLeaveMngRepository {

	/**
	 * 検索
	 * @param employeeId 社員ID
	 * @param ymd 年月日
	 * @return 該当する暫定積立年休管球�ータ
	 */
	Optional<TempReserveLeaveManagement> find(String employeeId, GeneralDate ymd);

	/**
	 * 検索　�期間�
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @return 該当する暫定積立年休管球�ータ　�年月日頼
	 */
	List<TempReserveLeaveManagement> findByPeriodOrderByYmd(String employeeId, DatePeriod period);

	/**
	 * 登録および更新
	 * @param tempReserveLeaveManagement 暫定積立年休管球�ータ
	 */
	void persistAndUpdate(TempReserveLeaveManagement tempReserveLeaveManagement);
	
	/**
	 * 削除
	 * @param employeeId 社員ID
	 * @param ymd 年月日
	 */
	void remove(String employeeId, GeneralDate ymd);
	
	/**
	 * 削除　�基準日以前�
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 */
	void removePastYmd(String employeeId, GeneralDate criteriaDate);
	
	/**
	 * 
	 * @param employeeId
	 * @param period
	 */
	void removeBetweenPeriod(String employeeId, DatePeriod period);
	
	/**
	 * @param employeeId
	 * @return List<TempReserveLeaveManagement>
	 */
	List<TempReserveLeaveManagement> findByEmployeeId(String employeeId);
}
