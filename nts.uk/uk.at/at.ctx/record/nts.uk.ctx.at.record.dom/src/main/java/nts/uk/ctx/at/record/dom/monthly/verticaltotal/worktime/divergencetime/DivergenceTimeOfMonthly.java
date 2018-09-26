package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.divergencetime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.SystemFixedErrorAlarm;

/**
 * 月別実績の乖離時間
 * @author shuichi_ishida
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
		if (divergenceTimeOfDaily.getDivergenceTime() == null) return;
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
		
		for (val employeeDailyPerError : employeeDailyPerErrors){
			val errorAlarmCode = employeeDailyPerError.getErrorAlarmWorkRecordCode().v();
			
			// 乖離時間のエラー
			if (errorAlarmCode == SystemFixedErrorAlarm.ERROR_OF_DIVERGENCE_TIME.value){
				for (val attendanceItemId : employeeDailyPerError.getAttendanceItemList()){
					// 乖離時間1=436。2以降は、5番飛び。
					int errorTimeNo = attendanceItemId - 431;
					int fraction = errorTimeNo % 5;
					errorTimeNo /= 5;
					if (fraction == 0 && errorTimeNo >= 1 && errorTimeNo <= 10){
						this.divergenceTimeList.putIfAbsent(errorTimeNo, new AggregateDivergenceTime(errorTimeNo));
						this.divergenceTimeList.get(errorTimeNo).setDivergenceAtr(DivergenceAtrOfMonthly.ERROR);
						continue;
					}
				}
			}
			int errorTimeNo = 0;
			switch (errorAlarmCode){
			case "D001":
				errorTimeNo = 1;
				break;
			case "D003":
				errorTimeNo = 2;
				break;
			case "D005":
				errorTimeNo = 3;
				break;
			case "D007":
				errorTimeNo = 4;
				break;
			case "D009":
				errorTimeNo = 5;
				break;
			case "D011":
				errorTimeNo = 6;
				break;
			case "D013":
				errorTimeNo = 7;
				break;
			case "D015":
				errorTimeNo = 8;
				break;
			case "D017":
				errorTimeNo = 9;
				break;
			case "D019":
				errorTimeNo = 10;
				break;
			}
			if (errorTimeNo >= 1 && errorTimeNo <= 10){
				this.divergenceTimeList.putIfAbsent(errorTimeNo, new AggregateDivergenceTime(errorTimeNo));
				this.divergenceTimeList.get(errorTimeNo).setDivergenceAtr(DivergenceAtrOfMonthly.ERROR);
				continue;
			}

			// 乖離時間のアラーム
			if (errorAlarmCode == SystemFixedErrorAlarm.ALARM_OF_DIVERGENCE_TIME.value){
				for (val attendanceItemId : employeeDailyPerError.getAttendanceItemList()){
					// 乖離時間1=436。2以降は、5番飛び。
					int alarmTimeNo = attendanceItemId - 431;
					int fraction = alarmTimeNo % 5;
					alarmTimeNo /= 5;
					if (fraction == 0 && alarmTimeNo >= 1 && alarmTimeNo <= 10){
						this.divergenceTimeList.putIfAbsent(alarmTimeNo, new AggregateDivergenceTime(alarmTimeNo));
						val divergenceAtr = this.divergenceTimeList.get(alarmTimeNo).getDivergenceAtr();
						if (divergenceAtr == DivergenceAtrOfMonthly.NORMAL) {
							this.divergenceTimeList.get(alarmTimeNo).setDivergenceAtr(DivergenceAtrOfMonthly.ALARM);
						}
						continue;
					}
				}
			}
			int alarmTimeNo = 0;
			switch (errorAlarmCode){
			case "D002":
				alarmTimeNo = 1;
				break;
			case "D004":
				alarmTimeNo = 2;
				break;
			case "D006":
				alarmTimeNo = 3;
				break;
			case "D008":
				alarmTimeNo = 4;
				break;
			case "D010":
				alarmTimeNo = 5;
				break;
			case "D012":
				alarmTimeNo = 6;
				break;
			case "D014":
				alarmTimeNo = 7;
				break;
			case "D016":
				alarmTimeNo = 8;
				break;
			case "D018":
				alarmTimeNo = 9;
				break;
			case "D020":
				alarmTimeNo = 10;
				break;
			}
			if (alarmTimeNo >= 1 && alarmTimeNo <= 10){
				this.divergenceTimeList.putIfAbsent(alarmTimeNo, new AggregateDivergenceTime(alarmTimeNo));
				val divergenceAtr = this.divergenceTimeList.get(alarmTimeNo).getDivergenceAtr();
				if (divergenceAtr == DivergenceAtrOfMonthly.NORMAL) {
					this.divergenceTimeList.get(alarmTimeNo).setDivergenceAtr(DivergenceAtrOfMonthly.ALARM);
				}
				continue;
			}
		}
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(DivergenceTimeOfMonthly target){
		
		for (val divergenceTime : this.divergenceTimeList.values()){
			val timeNo = divergenceTime.getDivergenceTimeNo();
			if (target.divergenceTimeList.containsKey(timeNo)){
				val targetDivergenceTime = target.divergenceTimeList.get(timeNo);
				divergenceTime.addMinutesToDivergenceTime(targetDivergenceTime.getDivergenceTime().v());
				divergenceTime.addMinutesToDeductionTime(targetDivergenceTime.getDeductionTime().v());
				divergenceTime.addMinutesToDivergenceTimeAfterDeduction(
						targetDivergenceTime.getDivergenceTimeAfterDeduction().v());
				switch (targetDivergenceTime.getDivergenceAtr()){
				case NORMAL:
					break;
				case ALARM:
					if (divergenceTime.getDivergenceAtr() == DivergenceAtrOfMonthly.NORMAL){
						divergenceTime.setDivergenceAtr(DivergenceAtrOfMonthly.ALARM);
					}
					break;
				case ERROR:
					divergenceTime.setDivergenceAtr(DivergenceAtrOfMonthly.ERROR);
					break;
				}
			}
		}
		for (val targetDivergenceTime : target.divergenceTimeList.values()){
			val timeNo = targetDivergenceTime.getDivergenceTimeNo();
			this.divergenceTimeList.putIfAbsent(timeNo, targetDivergenceTime);
		}
	}
}
