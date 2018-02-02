package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.divergencetime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の乖離時間
 * @author shuichu_ishida
 */
@Getter
public class DivergenceTimeOfMonthly {

	/** 乖離時間 */
	private Map<Integer, AggregateDivergenceTime> divergenceTimeList;
	
	/**
	 * コンストラクタ
	 */
	public DivergenceTimeOfMonthly(){
		
		this.divergenceTimeList = new HashMap<>();
	}
	
	/**
	 * ファクトリー
	 * @param divergenceTimeList 乖離時間
	 * @return 月別実績の乖離時間
	 */
	public static DivergenceTimeOfMonthly of(List<AggregateDivergenceTime> divergenceTimeList){
		
		val domain = new DivergenceTimeOfMonthly();
		for (val divergenceTime : divergenceTimeList){
			val divergenceTimeNo = Integer.valueOf(divergenceTime.getDivergenceTimeNo());
			if (!domain.divergenceTimeList.containsKey(divergenceTimeNo)) {
				domain.divergenceTimeList.put(divergenceTimeNo, divergenceTime);
			}
		}
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
		
		this.divergenceTimeList = new HashMap<>();
		
		// 日別実績の「乖離時間」「控除時間」「控除後乖離時間」を集計
		for (val attendanceTimeOfDaily : attendanceTimeOfDailys){
			if (!datePeriod.contains(attendanceTimeOfDaily.getYmd())) continue;
			val actualWorkingTime = attendanceTimeOfDaily.getActualWorkingTimeOfDaily();
			val divergenceTimeOfDaily = actualWorkingTime.getDivTime();
			for (val divergenceTime : divergenceTimeOfDaily.getDivergenceTime()){
				val divTimeNo = Integer.valueOf(divergenceTime.getDivTimeId());
				if (!this.divergenceTimeList.containsKey(divTimeNo)) {
					this.divergenceTimeList.put(divTimeNo, new AggregateDivergenceTime(divTimeNo.intValue()));
				}
				val targetDivergenceTime = this.divergenceTimeList.get(divTimeNo);
				
				targetDivergenceTime.addMinutesToDivergenceTime(divergenceTime.getDivTime().v());
				targetDivergenceTime.addMinutesToDeductionTime(divergenceTime.getDeductionTime().v());
				targetDivergenceTime.addMinutesToDivergenceTimeAfterDeduction(
						divergenceTime.getDivTimeAfterDeduction().v());
			}
		}
	}
}
