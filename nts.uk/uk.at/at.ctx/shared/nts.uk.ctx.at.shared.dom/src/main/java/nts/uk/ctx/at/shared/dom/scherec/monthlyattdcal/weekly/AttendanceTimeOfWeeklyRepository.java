package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/*
 * リポジトリ：週別実績の勤怠時間
 * @author shuichi_ishida
 */
public interface AttendanceTimeOfWeeklyRepository {

	/**
	 * 検索
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param weekNo 週NO
	 * @return 該当する週別実績の勤怠時間
	 */
	Optional<AttendanceTimeOfWeekly> find(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, int weekNo);

	/**
	 * 検索　（締め）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @return 週別実績の勤怠時間　（開始日順）
	 */
	List<AttendanceTimeOfWeekly> findByClosure(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate);
	
	/**
	 * 検索　（年月）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @return 週別実績の勤怠時間　（開始日順）
	 */
	List<AttendanceTimeOfWeekly> findByYearMonth(String employeeId, YearMonth yearMonth);

	/**
	 * 検索　（社員IDリストと締め）
	 * @param employeeIds 社員IDリスト
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @return 週別実績の勤怠時間　（開始日順）
	 */
	List<AttendanceTimeOfWeekly> findBySids(List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate);

	/**
	 * 検索　（社員IDリストと年月リスト）
	 * @param employeeIds 社員IDリスト
	 * @param yearMonths 年月リスト
	 * @return 月別実績の勤怠時間　（開始日順）
	 */
	List<AttendanceTimeOfWeekly> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths);
	
	/**
	 * 検索　（社員IDリストと基準日）
	 * @param employeeIds 社員IDリスト
	 * @param datePeriod 基準日
	 * @return 月別実績の勤怠時間　（開始日順）
	 */
	List<AttendanceTimeOfWeekly> findBySidsAndDatePeriod(List<String> employeeIds, DatePeriod datePeriod);

	/**
	 * 検索　一日でも一致（社員IDと期間）
	 * @param employeeId
	 * @param datePeriod
	 * @return
	 */
	List<AttendanceTimeOfWeekly> findMatchAnyOneDay(String employeeId, DatePeriod datePeriod);

	/**
	 * 検索　（基準日）
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @return 週別実績の勤怠時間
	 */
	List<AttendanceTimeOfWeekly> findByDate(String employeeId, GeneralDate criteriaDate);
	
	/**
	 * 登録および更新
	 * @param attendanceTimeOfWeekly 週別実績の勤怠時間
	 */
	void persistAndUpdate(AttendanceTimeOfWeekly attendanceTimeOfWeekly);
	
	/**
	 * 登録（INSERTのみ）
	 * @param domain 週別実績の勤怠時間
	 */
	void persist(AttendanceTimeOfWeekly domain);
	
	/**
	 * 削除
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param weekNo 週NO
	 */
	void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate,
			int weekNo);

	/**
	 * 削除　（締め）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 */
	void removeByClosure(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);
	
	/**
	 * 削除　（年月）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 */
	void removeByYearMonth(String employeeId, YearMonth yearMonth);
}
