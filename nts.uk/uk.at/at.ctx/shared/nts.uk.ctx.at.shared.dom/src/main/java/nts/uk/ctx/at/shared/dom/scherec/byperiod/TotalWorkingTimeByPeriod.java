package nts.uk.ctx.at.shared.dom.scherec.byperiod;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWork;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.PrescribedWorkingTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.WorkTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.hdwkandcompleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.vacationusetime.VacationUseTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRole;

/**
 * 期間別の総労働時間
 * @author shuichi_ishida
 */
@Getter
public class TotalWorkingTimeByPeriod implements Cloneable {

	/** 就業時間 */
	private WorkTimeOfMonthly workTime;
	/** 残業時間 */
	private OverTimeOfMonthly overTime;
	/** 休出時間 */
	private HolidayWorkTimeOfMonthly holidayWorkTime;
	/** 休暇使用時間 */
	private VacationUseTimeOfMonthly vacationUseTime;
	/** 所定労働時間 */
	private PrescribedWorkingTimeOfMonthly prescribedWorkingTime;
	/** 臨時時間 */
	//temporaryTime
	
	/**
	 * コンストラクタ
	 */
	public TotalWorkingTimeByPeriod(){
		
		this.workTime = new WorkTimeOfMonthly();
		this.overTime = new OverTimeOfMonthly();
		this.holidayWorkTime = new HolidayWorkTimeOfMonthly();
		this.vacationUseTime = new VacationUseTimeOfMonthly();
		this.prescribedWorkingTime = new PrescribedWorkingTimeOfMonthly();
	}

	/**
	 * ファクトリー
	 * @param workTime 就業時間
	 * @param overTime 残業時間
	 * @param holidayWorkTime 休出時間
	 * @param vacationUseTime 休暇使用時間
	 * @param prescribedWorkingTime 所定労働時間
	 * @return 集計総労働時間
	 */
	public static TotalWorkingTimeByPeriod of(
			WorkTimeOfMonthly workTime,
			OverTimeOfMonthly overTime,
			HolidayWorkTimeOfMonthly holidayWorkTime,
			VacationUseTimeOfMonthly vacationUseTime,
			PrescribedWorkingTimeOfMonthly prescribedWorkingTime){
		
		TotalWorkingTimeByPeriod domain = new TotalWorkingTimeByPeriod();
		domain.workTime = workTime;
		domain.overTime = overTime;
		domain.holidayWorkTime = holidayWorkTime;
		domain.vacationUseTime = vacationUseTime;
		domain.prescribedWorkingTime = prescribedWorkingTime;
		return domain;
	}
	
	@Override
	public TotalWorkingTimeByPeriod clone() {
		TotalWorkingTimeByPeriod cloned = new TotalWorkingTimeByPeriod();
		try {
			cloned.workTime = this.workTime.clone();
			cloned.overTime = this.overTime.clone();
			cloned.holidayWorkTime = this.holidayWorkTime.clone();
			cloned.vacationUseTime = this.vacationUseTime.clone();
			cloned.prescribedWorkingTime = this.prescribedWorkingTime.clone();
		}
		catch (Exception e){
			throw new RuntimeException("TotalWorkingTimeByPeriod clone error.");
		}
		return cloned;
	}
	
	/**
	 * 集計処理
	 * @param datePeriod 期間
	 * @param attendanceTimeOfDailyMap 日別実績の勤怠時間リスト
	 * @param workInfoOfDailyMap 日別実績の勤務情報リスト
	 * @param companySets 月別集計で必要な会社別設定
	 */
	public void aggregate(RequireM1 require,
			DatePeriod datePeriod,
			Map<GeneralDate, AttendanceTimeOfDailyAttendance> attendanceTimeOfDailyMap,
			Map<GeneralDate, WorkInfoOfDailyAttendance> workInfoOfDailyMap,
			MonAggrCompanySettings companySets){
		
		// 就業時間の集計
		{
			// 日別実績の集計処理
			this.workTime.aggregateForByPeriod(require, 
					datePeriod, attendanceTimeOfDailyMap, workInfoOfDailyMap);
			
			// 就業時間の合計処理
			this.workTime.totalizeWorkTime(datePeriod);
		}
		
		// 残業の集計
		Map<Integer, RoleOvertimeWork> roleOverTimeFrameMap = new HashMap<>();
		for (val roleOverTimeFrame : companySets.getRoleOverTimeFrameList()){
			int frameNo = roleOverTimeFrame.getOvertimeFrNo().v();
			roleOverTimeFrameMap.putIfAbsent(frameNo, roleOverTimeFrame);
		}
		this.overTime.aggregateForByPeriod(datePeriod, attendanceTimeOfDailyMap, roleOverTimeFrameMap);
		
		// 休日出勤の集計
		Map<Integer, WorkdayoffFrameRole> roleHolidayWorkFrameMap = new HashMap<>();
		for (val workdayoffFrame : companySets.getWorkDayoffFrameList()){
			int frameNo = workdayoffFrame.getWorkdayoffFrNo().v().intValue();
			roleHolidayWorkFrameMap.putIfAbsent(frameNo, workdayoffFrame.getRole());
		}
		this.holidayWorkTime.aggregateForByPeriod(datePeriod, attendanceTimeOfDailyMap, roleHolidayWorkFrameMap);
		
		// 休暇使用時間を集計する
		this.vacationUseTime.confirm(require, datePeriod, attendanceTimeOfDailyMap, workInfoOfDailyMap);
		this.vacationUseTime.aggregate(datePeriod);
		
		// 所定労働時間を集計する
		this.prescribedWorkingTime.confirm(datePeriod, attendanceTimeOfDailyMap);
		this.prescribedWorkingTime.aggregate(datePeriod);
	}
	
	/**
	 * 総労働対象時間の取得
	 * @return 総労働対象時間
	 */
	public AttendanceTimeMonth getTotalWorkingTargetTime(){
		
		return new AttendanceTimeMonth(this.workTime.getTotalWorkingTargetTime().v() +
				this.overTime.getTotalWorkingTargetTime().v() +
				this.holidayWorkTime.getTotalWorkingTargetTime().v());
	}
	
	public static interface RequireM1 extends WorkTimeOfMonthly.RequireM1, VacationUseTimeOfMonthly.RequireM1 {
		
	}
}
