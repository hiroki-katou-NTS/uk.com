package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.breaktime;

import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の休憩時間
 * @author shuichu_ishida
 */
@Getter
public class BreakTimeOfMonthly {

	/** 休憩時間 */
	private AttendanceTimeMonth breakTime;
	
	/**
	 * コンストラクタ
	 */
	public BreakTimeOfMonthly(){
		
		this.breakTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param breakTime 休憩時間
	 * @return 月別実績の休憩時間
	 */
	public static BreakTimeOfMonthly of(AttendanceTimeMonth breakTime){
		
		val domain = new BreakTimeOfMonthly();
		domain.breakTime = breakTime;
		return domain;
	}
	
	/**
	 * 集計
	 * @param datePeriod 期間
	 * @param attendanceTimeOfDailys 日別実績の勤怠時間リスト
	 */
	public void aggregate(
			DatePeriod datePeriod,
			List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys){
		
		this.breakTime = new AttendanceTimeMonth(0);
		
		// 日別実績の「休憩時間」を集計する
		for (val attendanceTimeOfDaily : attendanceTimeOfDailys){
			if (!datePeriod.contains(attendanceTimeOfDaily.getYmd())) continue;
			val totalWorkingTime = attendanceTimeOfDaily.getActualWorkingTimeOfDaily().getTotalWorkingTime();
			val breakTimeOfDaily = totalWorkingTime.getBreakTimeOfDaily();
			
			this.breakTime = this.breakTime.addMinutes(
					breakTimeOfDaily.getToRecordTotalTime().getTotalTime().getTime().v());
		}
	}
}
