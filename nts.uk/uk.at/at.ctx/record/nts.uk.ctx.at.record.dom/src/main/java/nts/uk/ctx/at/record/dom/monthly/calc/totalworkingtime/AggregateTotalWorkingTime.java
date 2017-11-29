package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.HolidayUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 集計総労働時間
 * @author shuichi_ishida
 */
@Getter
public class AggregateTotalWorkingTime {

	/** 就業時間 */
	private WorkTimeOfMonthly workTime;
	/** 残業時間 */
	private OverTimeOfMonthly overTime;
	/** 臨時時間 */
	//temporaryTime
	/** 休暇使用時間 */
	private HolidayUseTimeOfMonthly holidayUseTime;
	/** 休出時間 */
	private HolidayWorkTimeOfMonthly holidayWorkTime;
	/** 総労働時間 */
	private AttendanceTimeMonth totalWorkingTime;
	/** 所定労働時間 */
	private PrescribedWorkingTimeOfMonthly prescribedWorkingTime;
	
	/**
	 * コンストラクタ
	 */
	public AggregateTotalWorkingTime(){
		
		this.workTime = new WorkTimeOfMonthly();
		this.overTime = new OverTimeOfMonthly();
		this.holidayUseTime = new HolidayUseTimeOfMonthly();
		this.prescribedWorkingTime = new PrescribedWorkingTimeOfMonthly();
	}

	/**
	 * ファクトリー
	 * @param workTime 就業時間
	 * @param overTime 残業時間
	 * @param holidayUseTime 休暇使用時間
	 * @param holidayWorkTime 休出時間
	 * @param totalWorkingTime 総労働時間
	 * @param prescribedWorkingTime 所定労働時間
	 * @return 集計総労働時間
	 */
	public static AggregateTotalWorkingTime of(
			WorkTimeOfMonthly workTime,
			OverTimeOfMonthly overTime,
			HolidayUseTimeOfMonthly holidayUseTime,
			HolidayWorkTimeOfMonthly holidayWorkTime,
			AttendanceTimeMonth totalWorkingTime,
			PrescribedWorkingTimeOfMonthly prescribedWorkingTime){
		
		AggregateTotalWorkingTime domain = new AggregateTotalWorkingTime();
		domain.workTime = workTime;
		domain.overTime = overTime;
		domain.holidayUseTime = holidayUseTime;
		domain.holidayWorkTime = holidayWorkTime;
		domain.totalWorkingTime = totalWorkingTime;
		domain.prescribedWorkingTime = prescribedWorkingTime;
		return domain;
	}
	
	/**
	 * 共有項目を集計する
	 * @param attendanceTimeOfDailys リスト：日別実績の勤怠時間
	 */
	public void aggregateSharedItem(List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys){
		
		// 就業時間を集計する
		this.workTime.confirm(attendanceTimeOfDailys);
	
		// 休暇使用時間を集計する
		this.holidayUseTime.confirm(attendanceTimeOfDailys);
		
		// 所定労働時間を集計する
		this.prescribedWorkingTime.confirm(attendanceTimeOfDailys);
	}
	
	/**
	 * 日別実績を集計する
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 */
	public void aggregateDaily(AttendanceTimeOfDailyPerformance attendanceTimeOfDaily){

		// 残業時間を集計する
		
		// 休出時間を集計する
	}
	
	/**
	 * 実働時間の集計
	 */
	public void aggregateActualWorkingTime(){
		
		// 就業時間を集計する
		this.workTime.aggregate();
		
		// 残業合計時間を集計する
		//this.overTimeWork.aggregate();
		
		// 休出合計時間を集計する
		//this.holidayWorkTime.aggregate();
		
		// 休暇使用時間を集計する
		this.holidayUseTime.aggregate();
		
		// 所定労働時間を集計する
		this.prescribedWorkingTime.aggregate();
	}
}
