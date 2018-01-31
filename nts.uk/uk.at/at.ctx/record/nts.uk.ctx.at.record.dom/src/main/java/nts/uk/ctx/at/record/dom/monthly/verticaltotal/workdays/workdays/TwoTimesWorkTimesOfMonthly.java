package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimesMonth;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.vtotalwork.AttendanceStatusMap;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の二回勤務回数
 * @author shuichu_ishida
 */
@Getter
public class TwoTimesWorkTimesOfMonthly {

	/** 回数 */
	private AttendanceTimesMonth times;
	
	/**
	 * コンストラクタ
	 */
	public TwoTimesWorkTimesOfMonthly(){
		
		this.times = new AttendanceTimesMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param times 回数
	 * @return 月別実績の二回勤務回数
	 */
	public static TwoTimesWorkTimesOfMonthly of(AttendanceTimesMonth times){
		
		val domain = new TwoTimesWorkTimesOfMonthly();
		domain.times = times;
		return domain;
	}
	
	/**
	 * 集計
	 * @param datePeriod 期間
	 * @param workInfoOfDailys 日別実績の勤務情報リスト
	 * @param predetermineTimeSetMap 所定時間設定マップ
	 * @param attendanceStatusMap 出勤状態マップ
	 */
	public void aggregate(
			DatePeriod datePeriod,
			List<WorkInfoOfDailyPerformance> workInfoOfDailys,
			Map<String, PredetemineTimeSetting> predetermineTimeSetMap,
			AttendanceStatusMap attendanceStatusMap){
		
		this.times = new AttendanceTimesMonth(0);
		for (val workInfo : workInfoOfDailys){
			val ymd = workInfo.getYmd();
			if (!datePeriod.contains(ymd)) continue;
			
			// 所定時間設定を取得
			val workTimeCd = workInfo.getRecordWorkInformation().getWorkTimeCode().v();
			if (!predetermineTimeSetMap.containsKey(workTimeCd)) continue;
			val predetermineTimeSet = predetermineTimeSetMap.get(workTimeCd);
			
			// 2回勤務かどうかの判断処理　（2回勤務でなければ、次の日へ）
			if (!predetermineTimeSet.getPrescribedTimezoneSetting().isUseShiftTwo()) continue;
			
			// 2回目の打刻が存在するか
			if (attendanceStatusMap.isTwoTimesStampExists(ymd)){
				this.times = this.times.addTimes(1);
			}
		}
	}
}
