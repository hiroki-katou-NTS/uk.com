package nts.uk.ctx.at.record.dom.remainingnumber.dayoff.temp;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 暫定代休・休出管理データ
 * @author shuichu_ishida
 */
public interface InterimBreakDayoffService {

	/**
	 * 作成
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param workInfoOfDailyList 日別実績の勤務情報リスト
	 * @param attendanceTimeOfDailyList 日別実績の勤怠時間リスト
	 */
	void create(String companyId, String employeeId, DatePeriod period,
			Optional<List<WorkInfoOfDailyPerformance>> workInfoOfDailyList,
			Optional<List<AttendanceTimeOfDailyPerformance>> attendanceTimeOfDailyList);
	
	/**
	 * 削除
	 * @param employeeId 社員ID
	 * @param period 期間
	 */
	void remove(String employeeId, DatePeriod period);
}
