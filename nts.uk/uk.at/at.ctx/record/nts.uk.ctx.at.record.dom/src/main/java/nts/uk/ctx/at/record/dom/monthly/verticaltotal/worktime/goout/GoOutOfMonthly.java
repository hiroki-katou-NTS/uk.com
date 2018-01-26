package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.goout;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;

/**
 * 月別実績の外出
 * @author shuichu_ishida
 */
@Getter
public class GoOutOfMonthly {

	/** 外出 */
	private List<AggregateGoOut> goOuts;
	/** 育児外出 */
	private List<GoOutForChildCare> goOutForChildCares;
	
	/**
	 * コンストラクタ
	 */
	public GoOutOfMonthly(){
		
		this.goOuts = new ArrayList<>();
		this.goOutForChildCares = new ArrayList<>();
	}

	/**
	 * ファクトリー
	 * @param goOuts 外出
	 * @param goOutForChildCares 育児外出
	 * @return 月別実績の外出
	 */
	public static GoOutOfMonthly of(
			List<AggregateGoOut> goOuts,
			List<GoOutForChildCare> goOutForChildCares){
		
		val domain = new GoOutOfMonthly();
		domain.goOuts = goOuts;
		domain.goOutForChildCares = goOutForChildCares;
		return domain;
	}
	
	/**
	 * 集計
	 * @param attendanceTimeOfDailys 日別実績の勤怠時間リスト
	 */
	public void aggregate(List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys){
		
		this.goOuts = new ArrayList<>();
		this.goOutForChildCares = new ArrayList<>();

		for (val attendanceTimeOfDaily : attendanceTimeOfDailys){
			val totalWorkingTime = attendanceTimeOfDaily.getActualWorkingTimeOfDaily().getTotalWorkingTime();
			//*****（未）　誤って外出時間帯クラスがメンバになっているので、その修正待ち。
			//val goOutTimeOfDailys = totalWorkingTime.getOutingTimeOfDailyPerformance();
			//*****（未）　短時間勤務時間のメンバが、まだ実装されていない。shortWorkTime
			
			// 日別実績の「外出時間・回数」を集計する
			
			// 日別実績の「短時間・回数」を集計する
		}
	}
}
