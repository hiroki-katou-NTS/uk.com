package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.breaktime;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

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
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 */
	public void aggregate(AttendanceTimeOfDailyPerformance attendanceTimeOfDaily){

		if (attendanceTimeOfDaily == null) return;
		
		val totalWorkingTime = attendanceTimeOfDaily.getActualWorkingTimeOfDaily().getTotalWorkingTime();
		val breakTimeOfDaily = totalWorkingTime.getBreakTimeOfDaily();
		
		this.breakTime = this.breakTime.addMinutes(
				breakTimeOfDaily.getToRecordTotalTime().getTotalTime().getTime().v());
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(BreakTimeOfMonthly target){
		
		this.breakTime = this.breakTime.addMinutes(target.breakTime.v());
	}
}
