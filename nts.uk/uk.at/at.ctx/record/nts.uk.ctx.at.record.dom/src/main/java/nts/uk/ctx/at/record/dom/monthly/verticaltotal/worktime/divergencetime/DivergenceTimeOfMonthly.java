package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.divergencetime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;

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
			domain.divergenceTimeList.putIfAbsent(divergenceTimeNo, divergenceTime);
		}
		return domain;
	}
	
	/**
	 * 集計
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 */
	public void aggregate(AttendanceTimeOfDailyPerformance attendanceTimeOfDaily){

		if (attendanceTimeOfDaily == null) return;
		
		// 日別実績の「乖離時間」「控除時間」「控除後乖離時間」を集計
		val actualWorkingTime = attendanceTimeOfDaily.getActualWorkingTimeOfDaily();
		val divergenceTimeOfDaily = actualWorkingTime.getDivTime();
		for (val divergenceTime : divergenceTimeOfDaily.getDivergenceTime()){
			val divTimeNo = Integer.valueOf(divergenceTime.getDivTimeId());
			this.divergenceTimeList.putIfAbsent(divTimeNo, new AggregateDivergenceTime(divTimeNo.intValue()));
			val targetDivergenceTime = this.divergenceTimeList.get(divTimeNo);
			
			targetDivergenceTime.addMinutesToDivergenceTime(divergenceTime.getDivTime().v());
			targetDivergenceTime.addMinutesToDeductionTime(divergenceTime.getDeductionTime().v());
			targetDivergenceTime.addMinutesToDivergenceTimeAfterDeduction(
					divergenceTime.getDivTimeAfterDeduction().v());
		}
	}
	
	/**
	 * 乖離フラグの集計
	 * @param employeeDailyPerErrors 社員の日別実績エラー一覧リスト
	 */
	public void aggregateDivergenceAtr(List<EmployeeDailyPerError> employeeDailyPerErrors){
		
		//*****（未）　ドメインの構成、DB設計、リポジトリでのfindの管理単位が不整合かもしれない。確認要。
		for (val employeeDailyPerError : employeeDailyPerErrors){
			
			// 乖離時間のエラー
			if (employeeDailyPerError.getErrorAlarmWorkRecordCode().v() == "S017"){
				
			}
			
			// 乖離時間のアラーム
			if (employeeDailyPerError.getErrorAlarmWorkRecordCode().v() == "S018"){
				
			}
		}
	}
}
