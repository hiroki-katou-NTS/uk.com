package nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyAttendanceTimeCaculationImport {
	//残業枠No,残業時間
	private Map<Integer,TimeWithCalculationImport> overTime;

	//休出枠,休出時間
	private Map<Integer,TimeWithCalculationImport> holidayWorkTime;

	//加給Ｎｏ,加給時間
	private Map<Integer,Integer> bonusPayTime;

	//特定日加給No,特定日加給時間
	private Map<Integer,Integer> specBonusPayTime;

	//フレックス時間
	private TimeWithCalculationImport flexTime;

	//所定外深夜時間
	private TimeWithCalculationImport midNightTime;

	//計算合計外深夜時間
	private AttendanceTime timeOutSideMidnight;

	//計算残業深夜時間
	private AttendanceTime calOvertimeMidnight;

	//計算休出深夜時間 (法定区分,休出深夜時間)
	private Map<Integer, Integer> calHolidayMidnight;

	//遅刻時間1
	private AttendanceTime lateTime1;

	//早退時間1
	private AttendanceTime earlyLeaveTime1;

	//遅刻時間2
	private AttendanceTime lateTime2;

	//早退時間2
	private AttendanceTime earlyLeaveTime2;

	//私用外出時間
	private AttendanceTime privateOutingTime;

	//組合外出時間
	private AttendanceTime unionOutingTime;
}
