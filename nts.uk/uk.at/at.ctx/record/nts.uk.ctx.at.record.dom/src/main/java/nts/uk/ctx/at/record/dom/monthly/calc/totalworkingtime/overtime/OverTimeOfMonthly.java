package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 月別実績の残業時間
 * @author shuichi_ishida
 */
@Getter
public class OverTimeOfMonthly {

	/** 残業合計時間 */
	private TimeMonthWithCalculation totalOverTime;
	/** 事前残業時間 */
	private AttendanceTimeMonth beforeOverTime;
	/** 振替残業合計時間 */
	private TimeMonthWithCalculation totalTransferOverTime;
	/** 集計残業時間 */
	private List<AggregateOverTime> aggregateOverTimes;

	/**
	 * コンストラクタ
	 */
	public OverTimeOfMonthly(){
		
		this.aggregateOverTimes = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param totalOverTime 残業合計時間
	 * @param beforeOverTime 事前残業時間
	 * @param totalTransferOverTime 振替残業時間
	 * @param aggregateOverTimes 集計残業時間
	 * @return 月別実績の残業時間
	 */
	public static OverTimeOfMonthly of(
			TimeMonthWithCalculation totalOverTime,
			AttendanceTimeMonth beforeOverTime,
			TimeMonthWithCalculation totalTransferOverTime,
			List<AggregateOverTime> aggregateOverTimes){
		
		OverTimeOfMonthly domain = new OverTimeOfMonthly();
		domain.totalOverTime = totalOverTime;
		domain.beforeOverTime = beforeOverTime;
		domain.totalTransferOverTime = totalTransferOverTime;
		domain.aggregateOverTimes = aggregateOverTimes;
		return domain;
	}
	
	/**
	 * 集計する
	 * @param attendanceTimeOfDailys
	 */
	public void aggregate(List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys){
	
		// 「１日の基準時間未満の残業時間の扱い」を取得する
		
			// 自動計算して残業時間を集計する
			this.aggregateByAutoCalc();
		
			// 自動計算せず残業時間を集計する
			this.aggregateWithoutAutoCalc();
	}
	
	/**
	 * 自動計算して集計する
	 */
	private void aggregateByAutoCalc(){
		
		// 法定内残業にできる時間を計算する
		
		// 「１日の基準時間未満の残業時間の扱い」を取得する
		
		// 「残業枠時間」を取得する
		
		// 残業・振替の処理順序を取得する
		
		// 残業・振替のループ
		
			// 残業枠時間のループ処理
			
			// 処理をする残業・振替があるか確認する
		
		// ループ終了
	}

	/**
	 * 自動計算せず集計する
	 */
	private void aggregateWithoutAutoCalc(){
		
	}
}
