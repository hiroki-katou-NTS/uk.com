package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.FlexTimeByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWork;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.SettingRequiredByDefo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.SettingRequiredByFlex;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.SettingRequiredByReg;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.hdwkandcompleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.vacationusetime.VacationUseTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.RegAndIrgTimeOfWeekly;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRole;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/**
 * 集計総労働時間
 * @author shuichi_ishida
 */
@Getter
public class AggregateTotalWorkingTime implements Cloneable, Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

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
	public AggregateTotalWorkingTime(){
		
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
	public static AggregateTotalWorkingTime of(
			WorkTimeOfMonthly workTime,
			OverTimeOfMonthly overTime,
			HolidayWorkTimeOfMonthly holidayWorkTime,
			VacationUseTimeOfMonthly vacationUseTime,
			PrescribedWorkingTimeOfMonthly prescribedWorkingTime){
		
		val domain = new AggregateTotalWorkingTime();
		domain.workTime = workTime;
		domain.overTime = overTime;
		domain.holidayWorkTime = holidayWorkTime;
		domain.vacationUseTime = vacationUseTime;
		domain.prescribedWorkingTime = prescribedWorkingTime;
		return domain;
	}
	
	@Override
	public AggregateTotalWorkingTime clone() {
		AggregateTotalWorkingTime cloned = new AggregateTotalWorkingTime();
		try {
			cloned.workTime = this.workTime.clone();
			cloned.overTime = this.overTime.clone();
			cloned.holidayWorkTime = this.holidayWorkTime.clone();
			cloned.vacationUseTime = this.vacationUseTime.clone();
			cloned.prescribedWorkingTime = this.prescribedWorkingTime.clone();
		}
		catch (Exception e){
			throw new RuntimeException("AggregateTotalWorkingTime clone error.");
		}
		return cloned;
	}
	
	/**
	 * 共有項目を集計する
	 * @param datePeriod 期間
	 * @param attendanceTimeOfDailyMap 日別実績の勤怠時間リスト
	 * @param workInfoOfDailyMap 日別実績の勤務情報リスト
	 * @param snapshots 日別実績のスナップショット
	 */
	public void aggregateSharedItem(
			RequireM3 require, DatePeriod datePeriod,
			Map<GeneralDate, AttendanceTimeOfDailyAttendance> attendanceTimeOfDailyMap,
			Map<GeneralDate, WorkInfoOfDailyAttendance> workInfoOfDailyMap,
			Map<GeneralDate, SnapShot> snapshots){
	
		// 休暇使用時間を集計する
		this.vacationUseTime.confirm(require, datePeriod, attendanceTimeOfDailyMap, workInfoOfDailyMap);
		
		// 所定労働時間を集計する
		this.prescribedWorkingTime.confirm(datePeriod, attendanceTimeOfDailyMap, snapshots);

		// 就業時間を集計する
		this.workTime.confirm(require, datePeriod, attendanceTimeOfDailyMap, workInfoOfDailyMap);
	}
	
	/**
	 * 共有項目をコピーする
	 * @param aggregateTime 集計時間
	 */
	public void copySharedItem(AggregateTotalWorkingTime aggregateTime){
		
		this.workTime = aggregateTime.workTime.clone();
		this.vacationUseTime = aggregateTime.vacationUseTime.clone();
		this.prescribedWorkingTime = aggregateTime.prescribedWorkingTime.clone();
	}
	
	/**
	 * 日別実績を集計する　（通常・変形労働時間勤務用）
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 * @param companyId 会社ID
	 * @param workplaceId 職場ID
	 * @param employmentCd 雇用コード
	 * @param workingSystem 労働制
	 * @param aggregateAtr 集計区分
	 * @param workInfo 勤務情報
	 * @param settingsByReg 通常勤務が必要とする設定
	 * @param settingsByDefo 変形労働勤務が必要とする設定
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 */
	public void aggregateDailyForRegAndIrreg(
			RequireM2 require, GeneralDate ymd,
			AttendanceTimeOfDailyAttendance attendanceTimeOfDaily,
			String companyId, String workplaceId, String employmentCd,
			WorkingSystem workingSystem, MonthlyAggregateAtr aggregateAtr,
			WorkInformation workInfo,
			SettingRequiredByReg settingsByReg,
			SettingRequiredByDefo settingsByDefo,
			MonAggrCompanySettings companySets,
			MonAggrEmployeeSettings employeeSets){

		// 労働制を元に、該当する設定を取得する
		LegalTransferOrderSetOfAggrMonthly legalTransferOrderSet = new LegalTransferOrderSetOfAggrMonthly(companyId);
		Map<Integer, RoleOvertimeWork> roleOverTimeFrameMap = new HashMap<>();
		Map<Integer, WorkdayoffFrameRole> roleHolidayWorkFrameMap = new HashMap<>();
		List<RoleOvertimeWork> autoExceptOverTimeFrames = new ArrayList<>();
		List<Integer> autoExceptHolidayWorkFrames = new ArrayList<>();
		ExcessOutsideTimeSetReg excessOutsideTimeSet = new ExcessOutsideTimeSetReg(false, false, false, false);
		if (workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK) {
			// 変形労働の時
			legalTransferOrderSet = settingsByDefo.getLegalTransferOrderSet();
			roleOverTimeFrameMap = settingsByDefo.getRoleOverTimeFrameMap();
			roleHolidayWorkFrameMap = settingsByDefo.getRoleHolidayWorkFrameMap();
			autoExceptOverTimeFrames = settingsByDefo.getAutoExceptOverTimeFrames();
			autoExceptHolidayWorkFrames = settingsByDefo.getAutoExceptHolidayWorkFrames();
			
			// 「割増集計方法」を取得する
			if (aggregateAtr == MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK){
				excessOutsideTimeSet = settingsByDefo.getDeforAggrSet().getExcessOutsideTimeSet();
			}
			else {
				excessOutsideTimeSet = settingsByDefo.getDeforAggrSet().getAggregateTimeSet();
			}
		}
		else {
			// 通常勤務の時
			legalTransferOrderSet = settingsByReg.getLegalTransferOrderSet();
			roleOverTimeFrameMap = settingsByReg.getRoleOverTimeFrameMap();
			roleHolidayWorkFrameMap = settingsByReg.getRoleHolidayWorkFrameMap();
			autoExceptOverTimeFrames = settingsByReg.getAutoExceptOverTimeFrames();
			autoExceptHolidayWorkFrames = settingsByReg.getAutoExceptHolidayWorkFrames();
			
			// 「割増集計方法」を取得する
			if (aggregateAtr == MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK){
				excessOutsideTimeSet = settingsByReg.getRegularAggrSet().getExcessOutsideTimeSet();
			}
			else {
				excessOutsideTimeSet = settingsByReg.getRegularAggrSet().getAggregateTimeSet();
			}
		}
		
		// 残業時間を集計する　（通常・変形労働時間勤務用）
		this.overTime.aggregateForRegAndIrreg(require, ymd, attendanceTimeOfDaily, companyId, workplaceId, employmentCd,
				workingSystem, workInfo, legalTransferOrderSet.getLegalOverTimeTransferOrder(),
				excessOutsideTimeSet, roleOverTimeFrameMap, autoExceptOverTimeFrames,
				companySets, employeeSets);
		
		// 休出時間を集計する　（通常・変形労働時間勤務用）
		this.holidayWorkTime.aggregateForRegAndIrreg(require, ymd, attendanceTimeOfDaily, companyId, workplaceId, employmentCd,
				workingSystem, aggregateAtr, workInfo, legalTransferOrderSet.getLegalHolidayWorkTransferOrder(),
				excessOutsideTimeSet, roleHolidayWorkFrameMap, autoExceptHolidayWorkFrames,
				companySets, employeeSets);
	}
	
	/**
	 * 日別実績を集計する　（フレックス時間勤務用）
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 * @param companyId 会社ID
	 * @param workplaceId 職場ID
	 * @param employmentCd 雇用コード
	 * @param workingSystem 労働制
	 * @param aggregateAtr 集計区分
	 * @param workInfo 勤務情報
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 * @param companySets 月別集計で必要な会社別設定
	 * @return フレックス時間　（当日分のみ）
	 */
	public FlexTime aggregateDailyForFlex(RequireM1 require, GeneralDate ymd,
			AttendanceTimeOfDailyAttendance attendanceTimeOfDaily,
			String companyId, String workplaceId, String employmentCd,
			WorkingSystem workingSystem, MonthlyAggregateAtr aggregateAtr,
			WorkInformation workInfo,
			SettingRequiredByFlex settingsByFlex,
			MonAggrCompanySettings companySets){
		
		FlexTime flexTime = new FlexTime();
		
		// 残業時間を集計する　（フレックス時間勤務用）
		flexTime = this.overTime.aggregateForFlex(ymd, attendanceTimeOfDaily, companyId, aggregateAtr,
				flexTime, settingsByFlex);
		
		// 休出時間を集計する　（フレックス時間勤務用）
		flexTime = this.holidayWorkTime.aggregateForFlex(require, ymd, attendanceTimeOfDaily, companyId, aggregateAtr,
				flexTime, settingsByFlex, workInfo, companySets);
		
		return flexTime;
	}
	
	/**
	 * 実働時間の集計
	 * @param datePeriod 期間
	 * @param workingSystem 労働制
	 * @param actualWorkingTime 実働時間
	 * @param flexTime フレックス時間
	 */
	public void aggregateActualWorkingTime(DatePeriod datePeriod,
			WorkingSystem workingSystem,
			RegularAndIrregularTimeOfMonthly actualWorkingTime,
			FlexTimeOfMonthly flexTime){
		
		// 残業合計時間を集計する
		this.overTime.aggregateTotal(datePeriod);
		
		// 休出合計時間を集計する
		this.holidayWorkTime.aggregateTotal(datePeriod);
		
		// 休暇使用時間を集計する
		this.vacationUseTime.aggregate(datePeriod);
		
		// 所定労働時間を集計する
		this.prescribedWorkingTime.aggregate(datePeriod);
		
		// 就業時間を集計する
		this.workTime.aggregate(datePeriod, workingSystem, actualWorkingTime, flexTime,
				this.overTime, this.holidayWorkTime);
	}
	
	/**
	 * 実働時間の集計　（週別集計用）
	 * @param datePeriod 期間
	 * @param workingSystem 労働制
	 * @param actualWorkingTime 実働時間
	 * @param flexTime フレックス時間
	 */
	public void aggregateActualWorkingTimeForWeek(DatePeriod datePeriod,
			WorkingSystem workingSystem,
			RegAndIrgTimeOfWeekly actualWorkingTime,
			FlexTimeByPeriod flexTime){
		
		// 残業合計時間を集計する
		this.overTime.aggregateTotal(datePeriod);
		
		// 休出合計時間を集計する
		this.holidayWorkTime.aggregateTotal(datePeriod);
		
		// 休暇使用時間を集計する
		this.vacationUseTime.aggregate(datePeriod);
		
		// 所定労働時間を集計する
		this.prescribedWorkingTime.aggregate(datePeriod);
		
		// 就業時間を集計する
		this.workTime.aggregateForWeek(datePeriod, workingSystem, actualWorkingTime, flexTime,
				this.overTime, this.holidayWorkTime);
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
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(AggregateTotalWorkingTime target){
		
		this.workTime.sum(target.workTime);
		this.overTime.sum(target.overTime);
		this.holidayWorkTime.sum(target.holidayWorkTime);
		this.vacationUseTime.sum(target.vacationUseTime);
		this.prescribedWorkingTime.sum(target.prescribedWorkingTime);
	}
	
	public static interface RequireM1 extends HolidayWorkTimeOfMonthly.RequireM1 {

	}
	
	public static interface RequireM2 extends HolidayWorkTimeOfMonthly.RequireM3, OverTimeOfMonthly.RequireM1 {

	}
	
	public static interface RequireM3 extends VacationUseTimeOfMonthly.RequireM1, WorkTimeOfMonthly.RequireM1 {

	}
}