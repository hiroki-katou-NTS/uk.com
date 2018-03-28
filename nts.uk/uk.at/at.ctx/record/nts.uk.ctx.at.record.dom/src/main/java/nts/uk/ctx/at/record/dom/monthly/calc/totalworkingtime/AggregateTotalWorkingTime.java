package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime;

import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTime;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.VacationUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.AggrSettingMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggrSettingMonthlyOfFlx;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.AggregateTimeSet;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.ExcessOutsideTimeSet;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.TreatHolidayWorkTimeOfLessThanCriteriaPerWeek;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.TreatOverTimeOfLessThanCriteriaPerDay;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	/** 休出時間 */
	private HolidayWorkTimeOfMonthly holidayWorkTime;
	/** 臨時時間 */
	//temporaryTime
	/** 休暇使用時間 */
	private VacationUseTimeOfMonthly vacationUseTime;
	/** 所定労働時間 */
	private PrescribedWorkingTimeOfMonthly prescribedWorkingTime;
	
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
	
	/**
	 * 共有項目を集計する
	 * @param datePeriod 期間
	 * @param attendanceTimeOfDailyMap 日別実績の勤怠時間リスト
	 */
	public void aggregateSharedItem(DatePeriod datePeriod,
			Map<GeneralDate, AttendanceTimeOfDailyPerformance> attendanceTimeOfDailyMap){

		// 就業時間を集計する
		this.workTime.confirm(datePeriod, attendanceTimeOfDailyMap);
	
		// 休暇使用時間を集計する
		this.vacationUseTime.confirm(datePeriod, attendanceTimeOfDailyMap);
		
		// 所定労働時間を集計する
		this.prescribedWorkingTime.confirm(datePeriod, attendanceTimeOfDailyMap);
	}
	
	/**
	 * 共有項目をコピーする
	 * @param aggregateTime 集計時間
	 */
	public void copySharedItem(AggregateTotalWorkingTime aggregateTime){
		
		this.workTime = aggregateTime.getWorkTime();
		this.vacationUseTime = aggregateTime.getVacationUseTime();
		this.prescribedWorkingTime = aggregateTime.getPrescribedWorkingTime();
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
	 * @param aggrSettingMonthly 月別実績集計設定
	 * @param legalTransferOrderSet 法定内振替順設定
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void aggregateDailyForRegAndIrreg(
			AttendanceTimeOfDailyPerformance attendanceTimeOfDaily,
			String companyId, String workplaceId, String employmentCd,
			WorkingSystem workingSystem, MonthlyAggregateAtr aggregateAtr,
			WorkInformation workInfo,
			AggrSettingMonthly aggrSettingMonthly, LegalTransferOrderSetOfAggrMonthly legalTransferOrderSet,
			RepositoriesRequiredByMonthlyAggr repositories){

		// 労働制・集計区分を元に、該当する「～残業時間の扱い」「～休出時間の扱い」を取得する
		AggregateTimeSet aggregateTimeSet;							// 集計時間設定
		ExcessOutsideTimeSet excessOutsideTimeSet;					// 時間外超過設定
		TreatOverTimeOfLessThanCriteriaPerDay treatOverTimeOfLessThanCriteriaPerDay;	// 1日の基準時間未満の残業時間の扱い
		TreatHolidayWorkTimeOfLessThanCriteriaPerWeek treatHolidayWorkTimeOfLessThanCriteriaPerWeek;	// 1週間の基準時間未満の休日出勤時間の扱い
		if (workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK) {
			// 変形労働の時
			val legalAggrSetOfIrregular = aggrSettingMonthly.getIrregularWork();
			aggregateTimeSet = legalAggrSetOfIrregular.getAggregateTimeSet();
			excessOutsideTimeSet = legalAggrSetOfIrregular.getExcessOutsideTimeSet();
		}
		else {
			// 通常勤務の時
			val legalAggrSetOfRegular = aggrSettingMonthly.getRegularWork();
			aggregateTimeSet = legalAggrSetOfRegular.getAggregateTimeSet();
			excessOutsideTimeSet = legalAggrSetOfRegular.getExcessOutsideTimeSet();
		}
		if (aggregateAtr == MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK){
			// 集計区分＝時間外超過の時、時間外超過設定から参照
			treatOverTimeOfLessThanCriteriaPerDay = excessOutsideTimeSet.getTreatOverTimeOfLessThanCriteriaPerDay();
			treatHolidayWorkTimeOfLessThanCriteriaPerWeek =
					excessOutsideTimeSet.getTreatHolidayWorkTimeOfLessThanCriteriaPerWeek();
		}
		else {
			// 集計区分＝月の集計の時、集計時間設定から参照
			treatOverTimeOfLessThanCriteriaPerDay = aggregateTimeSet.getTreatOverTimeOfLessThanCriteriaPerDay();
			treatHolidayWorkTimeOfLessThanCriteriaPerWeek =
					aggregateTimeSet.getTreatHolidayWorkTimeOfLessThanCriteriaPerWeek();
		}
		
		// 残業時間を集計する　（通常・変形労働時間勤務用）
		this.overTime.aggregateForRegAndIrreg(attendanceTimeOfDaily, companyId, workplaceId, employmentCd,
				workingSystem, workInfo, legalTransferOrderSet.getLegalOverTimeTransferOrder(),
				treatOverTimeOfLessThanCriteriaPerDay, repositories);
		
		// 休出時間を集計する　（通常・変形労働時間勤務用）
		this.holidayWorkTime.aggregateForRegAndIrreg(attendanceTimeOfDaily, companyId, workplaceId, employmentCd,
				workingSystem, aggregateAtr, workInfo, legalTransferOrderSet.getLegalHolidayWorkTransferOrder(),
				excessOutsideTimeSet, treatHolidayWorkTimeOfLessThanCriteriaPerWeek, repositories);
	}
	
	/**
	 * 日別実績を集計する　（フレックス時間勤務用）
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 * @param companyId 会社ID
	 * @param workplaceId 職場ID
	 * @param employmentCd 雇用コード
	 * @param workingSystem 労働制
	 * @param aggregateAtr 集計区分
	 * @param aggrSetOfFlex フレックス時間勤務の月の集計設定
	 * @param repositories 月次集計が必要とするリポジトリ
	 * @return フレックス時間　（当日分のみ）
	 */
	public FlexTime aggregateDailyForFlex(
			AttendanceTimeOfDailyPerformance attendanceTimeOfDaily,
			String companyId, String workplaceId, String employmentCd,
			WorkingSystem workingSystem, MonthlyAggregateAtr aggregateAtr,
			AggrSettingMonthlyOfFlx aggrSetOfFlex,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		FlexTime returnClass = new FlexTime();
		
		// 残業時間を集計する　（フレックス時間勤務用）
		returnClass = this.overTime.aggregateForFlex(attendanceTimeOfDaily, companyId, aggregateAtr, aggrSetOfFlex);
		
		// 休出時間を集計する　（フレックス時間勤務用）
		this.holidayWorkTime.aggregateForFlex(attendanceTimeOfDaily, companyId, aggregateAtr, aggrSetOfFlex);
		
		return returnClass;
	}
	
	/**
	 * 実働時間の集計
	 * @param datePeriod 期間
	 * @param workingSystem 労働制
	 * @param actualWorkingTime 実働時間
	 * @param flexTime フレックス時間
	 */
	public void aggregateActualWorkingTime(
			DatePeriod datePeriod,
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
	 * 総労働対象時間の取得
	 * @return 総労働対象時間
	 */
	public AttendanceTimeMonth getTotalWorkingTargetTime(){
		
		return new AttendanceTimeMonth(this.workTime.getTotalWorkingTargetTime().v() +
				this.overTime.getTotalWorkingTargetTime().v() +
				this.holidayWorkTime.getTotalWorkingTargetTime().v());
	}
}
