package nts.uk.ctx.at.record.dom.monthly;

import java.util.List;
import java.util.Optional;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/*
 * 月別実績の勤怠時間
 * @author shuichi_ishida
 */
public interface AttendanceTimeOfMonthlyRepository {

	/**
	 * 検索　（期間）
	 * @param employeeID 社員ID
	 * @param datePeriod 期間　（検索する範囲）
	 * @return 期間内に一部でも含まれる月別実績の勤怠時間
	 */
	List<AttendanceTimeOfMonthly> findByPeriod(String employeeID, DatePeriod datePeriod);

	/**
	 * 検索　（キー一致）
	 * @param employeeID 社員ID
	 * @param datePeriod 期間
	 * @return 該当する月別実績の勤怠時間
	 */
	Optional<AttendanceTimeOfMonthly> findByPK(String employeeID, DatePeriod datePeriod);
	
	/**
	 * 追加
	 * @param attendanceTimeOfMonthly 月別実績の勤怠時間
	 */
	void insert(AttendanceTimeOfMonthly attendanceTimeOfMonthly);
	
	/**
	 * 更新
	 * @param attendanceTimeOfMonthly 月別実績の勤怠時間
	 */
	void update(AttendanceTimeOfMonthly attendanceTimeOfMonthly);
	
	/**
	 * 削除
	 * @param employeeID 社員ID
	 * @param datePeriod 期間　（期間内に一部でも含まれる月別実績の勤怠時間を削除）
	 */
	void removeByPeriod(String employeeID, DatePeriod datePeriod);
}
