package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.HolidayUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtimework.OverTimeWorkOfMonthly;
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
	private OverTimeWorkOfMonthly overTimeWork;
	/** 臨時時間 */
	//TemporaryTime
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
		this.overTimeWork = new OverTimeWorkOfMonthly();
		this.holidayUseTime = new HolidayUseTimeOfMonthly();
		this.prescribedWorkingTime = new PrescribedWorkingTimeOfMonthly();
	}

	/**
	 * ファクトリー
	 * @param workTime 就業時間
	 * @param overTimeWork 残業時間
	 * @param holidayUseTime 休暇使用時間
	 * @param holidayWorkTime 休出時間
	 * @param totalWorkingTime 総労働時間
	 * @param prescribedWorkingTime 所定労働時間
	 * @return 集計総労働時間
	 */
	public static AggregateTotalWorkingTime of(
			WorkTimeOfMonthly workTime,
			OverTimeWorkOfMonthly overTimeWork,
			HolidayUseTimeOfMonthly holidayUseTime,
			HolidayWorkTimeOfMonthly holidayWorkTime,
			AttendanceTimeMonth totalWorkingTime,
			PrescribedWorkingTimeOfMonthly prescribedWorkingTime){
		
		AggregateTotalWorkingTime domain = new AggregateTotalWorkingTime();
		domain.workTime = workTime;
		domain.overTimeWork = overTimeWork;
		domain.holidayUseTime = holidayUseTime;
		domain.holidayWorkTime = holidayWorkTime;
		domain.totalWorkingTime = totalWorkingTime;
		domain.prescribedWorkingTime = prescribedWorkingTime;
		return domain;
	}
	
	/**
	 * 共有項目を集計する
	 * @param attendanceTime 日別実績の勤怠時間
	 */
	public void aggregateSharedItem(AttendanceTimeOfDailyPerformance attendanceTime){
		
		// 就業時間を集計する
		this.workTime.confirm(attendanceTime);
	
		// 休暇使用時間を集計する
		this.holidayUseTime.confirm(attendanceTime);
		
		// 所定労働時間を集計する
		this.prescribedWorkingTime.confirm(attendanceTime);
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
