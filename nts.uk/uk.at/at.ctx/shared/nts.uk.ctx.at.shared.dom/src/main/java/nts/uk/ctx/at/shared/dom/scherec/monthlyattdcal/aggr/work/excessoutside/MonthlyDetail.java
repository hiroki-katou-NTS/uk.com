package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.excessoutside;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkTimeAddtionTimeGetter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeFrameTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.getvacationaddtime.AddSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.timeseries.FlexTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.timeseries.MonthlyPremiumTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.timeseries.WeeklyPremiumTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.timeseries.WorkTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.overtime.AggregateOverTime;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset.GetHolidayWorkAndTransferOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset.GetOverTimeAndTransferOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset.HolidayWorkAndTransferAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset.OverTimeAndTransferAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 月次明細
 * @author shuichi_ishida
 */
@Getter
public class MonthlyDetail {

	/** 就業時間 */
	private Map<GeneralDate, WorkTimeOfTimeSeries> workTime;
//	/** 年休使用時間 */
//	private Map<GeneralDate, AnnualLeaveUseTimeOfTimeSeries> annualLeaveUseTime;
//	/** 積立年休使用時間 */
//	private Map<GeneralDate, RetentionYearlyUseTimeOfTimeSeries> retentionYearlyUseTime;
//	/** 特別休暇使用時間 */
//	private Map<GeneralDate, SpecialHolidayUseTimeOfTimeSeries> specialHolidayUseTime;
	/** 残業時間 */
	private Map<OverTimeFrameNo, AggregateOverTime> overTime;
	/** 休出時間 */
	private Map<HolidayWorkFrameNo, AggregateHolidayWorkTime> holidayWorkTime;
	/** フレックス時間 */
	private Map<GeneralDate, FlexTimeOfTimeSeries> flexTime;
	/** 週割増割り当て時間 */
	private ReverseWeeklyPremiumAssignedTime weeklyPremiumAssignedTime;

	/** 時間外超過管理 */
	private ExcessOutsideWorkMng excessOutsideWorkMng;
	/** エラー情報 */
	private List<MonthlyAggregationErrorInfo> errorInfos;
	
	/**
	 * コンストラクタ
	 */
	public MonthlyDetail(){
		
		this.workTime = new HashMap<>();
//		this.premiumAddTime = new HashMap<>();
//		this.retentionYearlyUseTime = new HashMap<>();
//		this.specialHolidayUseTime = new HashMap<>();
		this.overTime = new HashMap<>();
		this.holidayWorkTime = new HashMap<>();
		this.flexTime = new HashMap<>();
		this.weeklyPremiumAssignedTime = new ReverseWeeklyPremiumAssignedTime();
		this.errorInfos = new ArrayList<>();
	}
	
	/**
	 * 集計総労働時間から月次明細を設定
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 */
	public void setFromAggregateTotalWorkingTime(AggregateTotalWorkingTime aggregateTotalWorkingTime){
		
		this.workTime = aggregateTotalWorkingTime.getWorkTime().getTimeSeriesWorks();
		this.overTime = aggregateTotalWorkingTime.getOverTime().getAggregateOverTimeMap();
		this.holidayWorkTime = aggregateTotalWorkingTime.getHolidayWorkTime().getAggregateHolidayWorkTimeMap();
		
//		val vacationUseTime = aggregateTotalWorkingTime.getVacationUseTime();
//		this.annualLeaveUseTime = vacationUseTime.getAnnualLeave().getTimeSeriesWorks();
//		this.retentionYearlyUseTime = vacationUseTime.getRetentionYearly().getTimeSeriesWorks();
//		this.specialHolidayUseTime = vacationUseTime.getSpecialHoliday().getTimeSeriesWorks();
	}
	
	/**
	 * 週割増時間を日単位で割り当てる
	 * @param procDate 処理日
	 * @param weeklyPTForAssign 逆時系列割り当て用の週割増時間
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param workInformationOfDailyMap 日別実績の勤務情報リスト
	 * @param excessOutsideWorkMng 時間外超過管理
	 * @param repositories 月次集計が必要とするリポジトリ
	 * @return 逆時系列割り当て用の週割増時間　（割り当て後）
	 */
	public AttendanceTimeMonthWithMinus assignWeeklyPremiumTimeByDayUnit(RequireM5 require, GeneralDate procDate,
			AttendanceTimeMonthWithMinus weeklyPTForAssign, AggregateTotalWorkingTime aggregateTotalWorkingTime,
			Map<GeneralDate, WorkInformation> workInformationOfDailyMap, ExcessOutsideWorkMng excessOutsideWorkMng,
			String cid, String sid, MonthlyAggregateAtr aggregateAtr, WorkingSystem workingSystem,
			MonthlyCalculatingDailys monthlyCalcDailys) {

		AttendanceTimeMonthWithMinus weeklyPTAfterAssign = new AttendanceTimeMonthWithMinus(weeklyPTForAssign.v());
		
		// 各時系列ワーク　確認
		this.setFromAggregateTotalWorkingTime(aggregateTotalWorkingTime);
		
		this.excessOutsideWorkMng = excessOutsideWorkMng;
		
		// 休出時間を限度として割り当てる（週割）
		weeklyPTAfterAssign = this.assignWithHolidayWorkTimeAsLimitForWeek(require, 
					procDate, weeklyPTAfterAssign, workInformationOfDailyMap);
		
		// 残業時間を限度として割り当てる（週割）
		if (weeklyPTAfterAssign.greaterThan(0)){
			weeklyPTAfterAssign = this.assignWithOverTimeAsLimitForWeek(require, 
					procDate, weeklyPTAfterAssign, workInformationOfDailyMap);
		}
		
		// 就業時間を限度として割り当てる（週割）
		if (weeklyPTAfterAssign.greaterThan(0)){
			weeklyPTAfterAssign = this.assignWithWorkTimeAsLimitForWeek(require, 
					procDate, weeklyPTAfterAssign, workInformationOfDailyMap,
					excessOutsideWorkMng.getCompanySets());
		}
		
		// 休暇使用時間を限度として割り当てる（週割）
		if (weeklyPTAfterAssign.greaterThan(0)){
			weeklyPTAfterAssign = this.assignWithVacationUseTimeAsLimitForWeek(require, cid, 
					sid, procDate, aggregateAtr, workingSystem, weeklyPTAfterAssign, monthlyCalcDailys);
		}
		
		return weeklyPTAfterAssign;
	}
	
	/**
	 * 休出時間を限度として割り当てる（週割）
	 * @param procDate 処理日
	 * @param weeklyPTForAssign 逆時系列割り当て用の週割増時間
	 * @param workInformationOfDailyMap 日別実績の勤務情報リスト
	 * @param repositories 月次集計が必要とするリポジトリ
	 * @return 逆時系列割り当て用の週割増時間　（割り当て後）
	 */
	private AttendanceTimeMonthWithMinus assignWithHolidayWorkTimeAsLimitForWeek(RequireM1 require, GeneralDate procDate,
			AttendanceTimeMonthWithMinus weeklyPTForAssign,
			Map<GeneralDate, WorkInformation> workInformationOfDailyMap){
		
		AttendanceTimeMonthWithMinus weeklyPTAfterAssign = new AttendanceTimeMonthWithMinus(weeklyPTForAssign.v());
		
		// 「日別実績の勤務情報」を取得
		if (!workInformationOfDailyMap.containsKey(procDate)) return weeklyPTAfterAssign;
		val workInfo = workInformationOfDailyMap.get(procDate);
		if (workInfo.getWorkTimeCode() == null) return weeklyPTAfterAssign;
		val workTimeCode = workInfo.getWorkTimeCode().v();

		// 休出・振替の処理順序を取得する（逆時系列用）
		val companySets = this.excessOutsideWorkMng.getCompanySets();
		val holidayWorkAndTransferAtrs = GetHolidayWorkAndTransferOrder.get(require, companySets.getCompanyId(), 
																		workTimeCode, true);
		
		// 休出・振替のループ
		for (val holidayWorkAndTransferAtr : holidayWorkAndTransferAtrs){
		
			// 休出枠時間のループ処理
			weeklyPTAfterAssign = this.holidayWorkFrameTimeProcessForWeek(
					procDate, holidayWorkAndTransferAtr, weeklyPTAfterAssign);
			
			if (weeklyPTAfterAssign.lessThanOrEqualTo(0)) break;
		}
		return weeklyPTAfterAssign;
	}
	
	/**
	 * 休出枠時間のループ処理（逆時系列用・週割）
	 * @param procDate 処理日
	 * @param holidayWorkAndTransferAtr 休出振替区分
	 * @param weeklyPTForAssign 逆時系列割り当て用の週割増時間
	 * @return 逆時系列割り当て用の週割増時間　（割り当て後）
	 */
	private AttendanceTimeMonthWithMinus holidayWorkFrameTimeProcessForWeek(GeneralDate procDate,
			HolidayWorkAndTransferAtr holidayWorkAndTransferAtr,
			AttendanceTimeMonthWithMinus weeklyPTForAssign){
		
		AttendanceTimeMonthWithMinus weeklyPTAfterAssign = new AttendanceTimeMonthWithMinus(weeklyPTForAssign.v());
		val weeklyPremiumTime = this.excessOutsideWorkMng.getExcessOutsideWorkDetail().getWeeklyPremiumTime();
		val assignedDetail = this.weeklyPremiumAssignedTime.getHolidayWorkTime();
		
		// 振替順を取得する
		LegalTransferOrderSetOfAggrMonthly legalTransferOrderSet =
				this.excessOutsideWorkMng.getSettingsByReg().getLegalTransferOrderSet();
		if (this.excessOutsideWorkMng.getWorkingSystem() == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			legalTransferOrderSet = this.excessOutsideWorkMng.getSettingsByDefo().getLegalTransferOrderSet();
		}
		val holidayWorkTransferOrder = legalTransferOrderSet.getLegalHolidayWorkTransferOrder();
		
		// 休出枠時間分ループ
		for (val legalHolidayWorkTransferOrder : holidayWorkTransferOrder.getLegalHolidayWorkTransferOrders()){
			
			// 該当枠・年月日の「時系列の休出時間」を取得
			val holidayWorkFrameNo = legalHolidayWorkTransferOrder.getHolidayWorkFrameNo();
			if (!this.holidayWorkTime.containsKey(holidayWorkFrameNo)) continue;
			val timeSeriesWorks = this.holidayWorkTime.get(holidayWorkFrameNo).getTimeSeriesWorks();
			if (!timeSeriesWorks.containsKey(procDate)) continue;
			val legalHolidayWorkTime = timeSeriesWorks.get(procDate).getLegalHolidayWorkTime();

			Integer assignMinutes = weeklyPTAfterAssign.v();
			
			switch (holidayWorkAndTransferAtr){
			case HOLIDAY_WORK:
				
				// 月次明細を限度として「時系列の週割増時間」に割り当てる
				val holidayWorkTimeMinutes = legalHolidayWorkTime.getHolidayWorkTime().get().getTime().v(); 
				if (assignMinutes > holidayWorkTimeMinutes) assignMinutes = holidayWorkTimeMinutes;
				weeklyPremiumTime.putIfAbsent(procDate, new WeeklyPremiumTimeOfTimeSeries(procDate));
				weeklyPremiumTime.get(procDate).addMinutesToWeeklyPremiumTime(assignMinutes);
				
				// 「時系列の週割増時間」に入れた分を「逆時系列割り当て用の週割増時間」から引く
				weeklyPTAfterAssign = weeklyPTAfterAssign.minusMinutes(assignMinutes);

				// 「時系列の週割増時間」に入れた分を「逆時系列で週割増を割り当てた時間の明細」に入れる
				assignedDetail.putIfAbsent(holidayWorkFrameNo, new AggregateHolidayWorkTime(holidayWorkFrameNo));
				val targetHWTimeSeries = assignedDetail.get(holidayWorkFrameNo).getAndPutTimeSeriesWork(procDate);
				targetHWTimeSeries.addHolidayWorkTimeInLegalHolidayWorkTime(
						TimeDivergenceWithCalculation.createTimeWithCalculation(
								new AttendanceTime(assignMinutes), new AttendanceTime(0)));
				
				break;
				
			case TRANSFER:
				
				// 月次明細を限度として「時系列の週割増時間」に割り当てる
				val transferTimeMinutes = legalHolidayWorkTime.getTransferTime().get().getTime().v(); 
				if (assignMinutes > transferTimeMinutes) assignMinutes = transferTimeMinutes;
				weeklyPremiumTime.putIfAbsent(procDate, new WeeklyPremiumTimeOfTimeSeries(procDate));
				weeklyPremiumTime.get(procDate).addMinutesToWeeklyPremiumTime(assignMinutes);
				
				// 「時系列の週割増時間」に入れた分を「逆時系列割り当て用の週割増時間」から引く
				weeklyPTAfterAssign = weeklyPTAfterAssign.minusMinutes(assignMinutes);

				// 「時系列の週割増時間」に入れた分を「逆時系列で週割増を割り当てた時間の明細」に入れる
				assignedDetail.putIfAbsent(holidayWorkFrameNo, new AggregateHolidayWorkTime(holidayWorkFrameNo));
				val targetTransTimeSeries = assignedDetail.get(holidayWorkFrameNo).getAndPutTimeSeriesWork(procDate);
				targetTransTimeSeries.addTransferTimeInLegalHolidayWorkTime(
						TimeDivergenceWithCalculation.createTimeWithCalculation(
								new AttendanceTime(assignMinutes), new AttendanceTime(0)));
				
				break;
			}
			
			// 「逆時系列割り当て用の週割増時間」が残っているか確認する
			if (weeklyPTAfterAssign.lessThanOrEqualTo(0)) break;
		}
		
		return weeklyPTAfterAssign;
	}
	
	/**
	 * 残業時間を限度として割り当てる（週割）
	 * @param procDate 処理日
	 * @param weeklyPTForAssign 逆時系列割り当て用の週割増時間
	 * @param workInformationOfDailyMap 日別実績の勤務情報リスト
	 * @param repositories 月次集計が必要とするリポジトリ
	 * @return 逆時系列割り当て用の週割増時間　（割り当て後）
	 */
	private AttendanceTimeMonthWithMinus assignWithOverTimeAsLimitForWeek(RequireM2 require, GeneralDate procDate,
			AttendanceTimeMonthWithMinus weeklyPTForAssign,
			Map<GeneralDate, WorkInformation> workInformationOfDailyMap){
		
		AttendanceTimeMonthWithMinus weeklyPTAfterAssign = new AttendanceTimeMonthWithMinus(weeklyPTForAssign.v());
		
		// 「日別実績の勤務情報」を取得
		if (!workInformationOfDailyMap.containsKey(procDate)) return weeklyPTAfterAssign;
		val workInfo = workInformationOfDailyMap.get(procDate);
		if (workInfo.getWorkTimeCode() == null) return weeklyPTAfterAssign;
		val workTimeCode = workInfo.getWorkTimeCode().v();

		// 残業・振替の処理順序を取得する（逆時系列用）
		val companySets = this.excessOutsideWorkMng.getCompanySets();
		val overTimeAndTransferAtrs = GetOverTimeAndTransferOrder.get(require, companySets.getCompanyId(), 
																workTimeCode, true);
		
		// 残業・振替のループ
		for (val overTimeAndTransferAtr : overTimeAndTransferAtrs){
		
			// 残業枠時間のループ処理
			weeklyPTAfterAssign = this.overTimeFrameTimeProcessForWeek(
					procDate, overTimeAndTransferAtr, weeklyPTAfterAssign);
			
			if (weeklyPTAfterAssign.lessThanOrEqualTo(0)) break;
		}
		return weeklyPTAfterAssign;
	}
	
	/**
	 * 残業枠時間のループ処理（逆時系列用・週割）
	 * @param procDate 処理日
	 * @param overTimeAndTransferAtr 残業振替区分
	 * @param weeklyPTForAssign 逆時系列割り当て用の週割増時間
	 * @return 逆時系列割り当て用の週割増時間　（割り当て後）
	 */
	private AttendanceTimeMonthWithMinus overTimeFrameTimeProcessForWeek(GeneralDate procDate,
			OverTimeAndTransferAtr overTimeAndTransferAtr,
			AttendanceTimeMonthWithMinus weeklyPTForAssign){
		
		AttendanceTimeMonthWithMinus weeklyPTAfterAssign = new AttendanceTimeMonthWithMinus(weeklyPTForAssign.v());
		val weeklyPremiumTime = this.excessOutsideWorkMng.getExcessOutsideWorkDetail().getWeeklyPremiumTime();
		val assignedDetail = this.weeklyPremiumAssignedTime.getOverTime();
		
		// 振替順を取得する
		LegalTransferOrderSetOfAggrMonthly legalTransferOrderSet =
				this.excessOutsideWorkMng.getSettingsByReg().getLegalTransferOrderSet();
		if (this.excessOutsideWorkMng.getWorkingSystem() == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			legalTransferOrderSet = this.excessOutsideWorkMng.getSettingsByDefo().getLegalTransferOrderSet();
		}
		val overTimeTransferOrder = legalTransferOrderSet.getLegalOverTimeTransferOrder();
		
		// 残業枠時間分ループ
		for (val legalOverTimeTransferOrder : overTimeTransferOrder.getLegalOverTimeTransferOrders()){
			
			// 該当枠・年月日の「時系列の残業時間」を取得
			val overTimeFrameNo = legalOverTimeTransferOrder.getOverTimeFrameNo();
			if (!this.overTime.containsKey(overTimeFrameNo)) continue;
			val timeSeriesWorks = this.overTime.get(overTimeFrameNo).getTimeSeriesWorks();
			if (!timeSeriesWorks.containsKey(procDate)) continue;
			val legalOverTime = timeSeriesWorks.get(procDate).getLegalOverTime();

			Integer assignMinutes = weeklyPTAfterAssign.v();
			
			switch (overTimeAndTransferAtr){
			case OVER_TIME:
				
				// 月次明細を限度として「時系列の週割増時間」に割り当てる
				val overTimeMinutes = legalOverTime.getOverTimeWork().getTime().v(); 
				if (assignMinutes > overTimeMinutes) assignMinutes = overTimeMinutes;
				weeklyPremiumTime.putIfAbsent(procDate, new WeeklyPremiumTimeOfTimeSeries(procDate));
				weeklyPremiumTime.get(procDate).addMinutesToWeeklyPremiumTime(assignMinutes);
				
				// 「時系列の週割増時間」に入れた分を「逆時系列割り当て用の週割増時間」から引く
				weeklyPTAfterAssign = weeklyPTAfterAssign.minusMinutes(assignMinutes);

				// 「時系列の週割増時間」に入れた分を「逆時系列で週割増を割り当てた時間の明細」に入れる
				assignedDetail.putIfAbsent(overTimeFrameNo, new AggregateOverTime(overTimeFrameNo));
				val targetOTTimeSeries = assignedDetail.get(overTimeFrameNo).getAndPutTimeSeriesWork(procDate);
				targetOTTimeSeries.addOverTimeInLegalOverTime(
						TimeDivergenceWithCalculation.createTimeWithCalculation(
								new AttendanceTime(assignMinutes), new AttendanceTime(0)));
				
				break;
				
			case TRANSFER:
				
				// 月次明細を限度として「時系列の週割増時間」に割り当てる
				val transferTimeMinutes = legalOverTime.getTransferTime().getTime().v(); 
				if (assignMinutes > transferTimeMinutes) assignMinutes = transferTimeMinutes;
				weeklyPremiumTime.putIfAbsent(procDate, new WeeklyPremiumTimeOfTimeSeries(procDate));
				weeklyPremiumTime.get(procDate).addMinutesToWeeklyPremiumTime(assignMinutes);
				
				// 「時系列の週割増時間」に入れた分を「逆時系列割り当て用の週割増時間」から引く
				weeklyPTAfterAssign = weeklyPTAfterAssign.minusMinutes(assignMinutes);

				// 「時系列の週割増時間」に入れた分を「逆時系列で週割増を割り当てた時間の明細」に入れる
				assignedDetail.putIfAbsent(overTimeFrameNo, new AggregateOverTime(overTimeFrameNo));
				val targetTransTimeSeries = assignedDetail.get(overTimeFrameNo).getAndPutTimeSeriesWork(procDate);
				targetTransTimeSeries.addTransferTimeInLegalOverTime(
						TimeDivergenceWithCalculation.createTimeWithCalculation(
								new AttendanceTime(assignMinutes), new AttendanceTime(0)));
				
				break;
			}
			
			// 「逆時系列割り当て用の週割増時間」が残っているか確認する
			if (weeklyPTAfterAssign.lessThanOrEqualTo(0)) break;
		}
		
		return weeklyPTAfterAssign;
	}
	
	/**
	 * 就業時間を限度として割り当てる（週割）
	 * @param procDate 処理日
	 * @param weeklyPTForAssign 逆時系列割り当て用の週割増時間
	 * @param workInformationOfDailyMap 日別実績の勤務情報リスト
	 * @param companySets 月別集計で必要な会社別設定
	 * @param repositories 月次集計が必要とするリポジトリ
	 * @return 逆時系列割り当て用の週割増時間　（割り当て後）
	 */
	private AttendanceTimeMonthWithMinus assignWithWorkTimeAsLimitForWeek(RequireM4 require, 
			GeneralDate procDate, AttendanceTimeMonthWithMinus weeklyPTForAssign,
			Map<GeneralDate, WorkInformation> workInformationOfDailyMap, MonAggrCompanySettings companySets){
		
		AttendanceTimeMonthWithMinus weeklyPTAfterAssign = new AttendanceTimeMonthWithMinus(weeklyPTForAssign.v());
		val weeklyPremiumTime = this.excessOutsideWorkMng.getExcessOutsideWorkDetail().getWeeklyPremiumTime();
		val assignedDetail = this.weeklyPremiumAssignedTime.getWorkTime();
		
		// 該当年月日の就業時間を取得
		val timeSeriesWorks = this.workTime;
		if (!timeSeriesWorks.containsKey(procDate)) return weeklyPTAfterAssign;
		val workTimeMinutes = timeSeriesWorks.get(procDate).getLegalTime().getWorkTime().v();
		
		// 該当年月日の勤務種類を確認する
		WorkType workType = null;
		if (workInformationOfDailyMap.containsKey(procDate)) {
			if (workInformationOfDailyMap.get(procDate).getWorkTypeCode() != null) {
				String workTypeCode = workInformationOfDailyMap.get(procDate).getWorkTypeCode().v();
				workType = companySets.getWorkTypeMap(require, workTypeCode);
			}
		}
		
		// 月次明細を限度として「時系列の週割増時間」に割り当てる
		Integer assignMinutes = weeklyPTAfterAssign.v();
		if (assignMinutes > workTimeMinutes) assignMinutes = workTimeMinutes;
		weeklyPremiumTime.putIfAbsent(procDate, new WeeklyPremiumTimeOfTimeSeries(procDate));
		weeklyPremiumTime.get(procDate).addMinutesToWeeklyPremiumTime(assignMinutes);
		
		// 「時系列の週割増時間」に入れた分を「逆時系列割り当て用の週割増時間」から引く
		weeklyPTAfterAssign = weeklyPTAfterAssign.minusMinutes(assignMinutes);

		// 「時系列の週割増時間」に入れた分を「逆時系列で週割増を割り当てた時間の明細」に入れる
		assignedDetail.putIfAbsent(procDate, WorkTimeOfTimeSeries.of(
				procDate,
				WithinStatutoryTimeOfDaily.defaultValue(),
				new AttendanceTime(0),
				workType
				));
		
		return weeklyPTAfterAssign;
	}
	
	/**
	 * 割増加算時間を限度として割り当てる(週割)
	 * @param procDate 処理日
	 * @param weeklyPTForAssign 逆時系列割り当て用の週割増時間
	 * @return 逆時系列割り当て用の週割増時間　（割り当て後）
	 */
	private AttendanceTimeMonthWithMinus assignWithVacationUseTimeAsLimitForWeek(RequireM6 require, String cid, 
			String sid, GeneralDate procDate, MonthlyAggregateAtr aggregateAtr, WorkingSystem workingSystem,
			AttendanceTimeMonthWithMinus weeklyPTForAssign, MonthlyCalculatingDailys monthlyCalcDailys) {
		
		/** 日ごと割増の加算時間を計算する */
		val dailies = monthlyCalcDailys.getDailyWorks(sid, new DatePeriod(procDate, procDate));
		val assignMinutes = WorkTimeAddtionTimeGetter.getForPremiumTime(require, dailies, workingSystem, 
																		cid, aggregateAtr, Optional.empty());
		
		// 「時系列の週割増時間」に入れた分を「逆時系列割り当て用の週割増時間」から引く
		weeklyPTForAssign = weeklyPTForAssign.minusMinutes(assignMinutes.valueAsMinutes());

		// 「時系列の週割増時間」に入れた分を「逆時系列で週割増を割り当てた時間の明細」に入れる
		this.weeklyPremiumAssignedTime.getPremiumAddTime().putIfAbsent(procDate, assignMinutes);
		
		return weeklyPTForAssign;
	}
	
	/**
	 * 月割増時間を日単位で割り当てる
	 * @param procDate 処理日
	 * @param weeklyPTForAssign 逆時系列割り当て用の週割増時間
	 * @param addSet 加算設定
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param workInformationOfDailyMap 日別実績の勤務情報リスト
	 * @param excessOutsideWorkMng 時間外超過管理
	 * @param repositories 月次集計が必要とするリポジトリ
	 * @return 逆時系列割り当て用の週割増時間　（割り当て後）
	 */
	public AttendanceTimeMonthWithMinus assignMonthlyPremiumTimeByDayUnit(RequireM3 require, GeneralDate procDate, 
			AttendanceTimeMonthWithMinus weeklyPTForAssign, AddSet addSet, AggregateTotalWorkingTime aggregateTotalWorkingTime,
			Map<GeneralDate, WorkInformation> workInformationOfDailyMap, ExcessOutsideWorkMng excessOutsideWorkMng){

		AttendanceTimeMonthWithMinus monthlyPTAfterAssign = new AttendanceTimeMonthWithMinus(weeklyPTForAssign.v());
		
		// 各時系列ワーク　確認
		this.setFromAggregateTotalWorkingTime(aggregateTotalWorkingTime);
		
		this.excessOutsideWorkMng = excessOutsideWorkMng;
		
		// 休出時間を限度として割り当てる（月割）
		monthlyPTAfterAssign = this.assignWithHolidayWorkTimeAsLimitForMonth(require, procDate, monthlyPTAfterAssign,
				workInformationOfDailyMap);
		
		// 残業時間を限度として割り当てる（月割）
		if (monthlyPTAfterAssign.greaterThan(0)){
			monthlyPTAfterAssign = this.assignWithOverTimeAsLimitForMonth(require, procDate, monthlyPTAfterAssign,
					workInformationOfDailyMap);
		}
		
		// 就業時間を限度として割り当てる（月割）
		if (monthlyPTAfterAssign.greaterThan(0)){
			monthlyPTAfterAssign = this.assignWithWorkTimeAsLimitForMonth(procDate, monthlyPTAfterAssign);
		}
		
		/**　○割増加算時間を限度として割り当てる(月割)　*/
		monthlyPTAfterAssign = this.assignWithPremiumAddTimeAsLimitForMonth(procDate, monthlyPTAfterAssign);
		
		return monthlyPTAfterAssign;
	}
	
	/**
	 * 休出時間を限度として割り当てる（月割）
	 * @param procDate 処理日
	 * @param monthlyPTForAssign 逆時系列割り当て用の月割増時間
	 * @param workInformationOfDailyMap 日別実績の勤務情報リスト
	 * @param repositories 月次集計が必要とするリポジトリ
	 * @return 逆時系列割り当て用の月割増時間　（割り当て後）
	 */
	private AttendanceTimeMonthWithMinus assignWithHolidayWorkTimeAsLimitForMonth(RequireM1 require, GeneralDate procDate,
			AttendanceTimeMonthWithMinus monthlyPTForAssign,
			Map<GeneralDate, WorkInformation> workInformationOfDailyMap){
		
		AttendanceTimeMonthWithMinus monthlyPTAfterAssign = new AttendanceTimeMonthWithMinus(monthlyPTForAssign.v());
		
		// 「日別実績の勤務情報」を取得
		if (!workInformationOfDailyMap.containsKey(procDate)) return monthlyPTAfterAssign;
		val workInfo = workInformationOfDailyMap.get(procDate);
		if (workInfo.getWorkTimeCode() == null) return monthlyPTAfterAssign;
		val workTimeCode = workInfo.getWorkTimeCode().v();

		// 休出・振替の処理順序を取得する（逆時系列用）
		val companySets = this.excessOutsideWorkMng.getCompanySets();
		val holidayWorkAndTransferAtrs = GetHolidayWorkAndTransferOrder.get(require,
				companySets.getCompanyId(), workTimeCode, true);
		
		// 休出・振替のループ
		for (val holidayWorkAndTransferAtr : holidayWorkAndTransferAtrs){
		
			// 休出枠時間のループ処理
			monthlyPTAfterAssign = this.holidayWorkFrameTimeProcessForMonth(
					procDate, holidayWorkAndTransferAtr, monthlyPTAfterAssign);
			
			if (monthlyPTAfterAssign.lessThanOrEqualTo(0)) break;
		}
		return monthlyPTAfterAssign;
	}
	
	/**
	 * 休出枠時間のループ処理（逆時系列用・月割）
	 * @param procDate 処理日
	 * @param holidayWorkAndTransferAtr 休出振替区分
	 * @param monthlyPTForAssign 逆時系列割り当て用の月割増時間
	 * @return 逆時系列割り当て用の月割増時間　（割り当て後）
	 */
	private AttendanceTimeMonthWithMinus holidayWorkFrameTimeProcessForMonth(GeneralDate procDate,
			HolidayWorkAndTransferAtr holidayWorkAndTransferAtr,
			AttendanceTimeMonthWithMinus monthlyPTForAssign){
		
		AttendanceTimeMonthWithMinus monthlyPTAfterAssign = new AttendanceTimeMonthWithMinus(monthlyPTForAssign.v());
		val monthlyPremiumTime = this.excessOutsideWorkMng.getExcessOutsideWorkDetail().getMonthlyPremiumTime();
		
		// 振替順を取得する
		LegalTransferOrderSetOfAggrMonthly legalTransferOrderSet =
				this.excessOutsideWorkMng.getSettingsByReg().getLegalTransferOrderSet();
		if (this.excessOutsideWorkMng.getWorkingSystem() == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			legalTransferOrderSet = this.excessOutsideWorkMng.getSettingsByDefo().getLegalTransferOrderSet();
		}
		val holidayWorkTransferOrder = legalTransferOrderSet.getLegalHolidayWorkTransferOrder();
		
		// 休出枠時間分ループ
		for (val legalHolidayWorkTransferOrder : holidayWorkTransferOrder.getLegalHolidayWorkTransferOrders()){
			
			// 月次明細の該当枠・年月日の休出時間を取得
			val holidayWorkFrameNo = legalHolidayWorkTransferOrder.getHolidayWorkFrameNo();
			if (!this.holidayWorkTime.containsKey(holidayWorkFrameNo)) continue;
			val timeSeriesWorks = this.holidayWorkTime.get(holidayWorkFrameNo).getTimeSeriesWorks();
			if (!timeSeriesWorks.containsKey(procDate)) continue;
			val monthHolidayWorkTime = timeSeriesWorks.get(procDate).getLegalHolidayWorkTime();

			// 週割増の該当枠・年月日の休出時間を取得
			HolidayWorkFrameTime weekHolidayWorkTime = new HolidayWorkFrameTime(
					holidayWorkFrameNo,
					Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
					Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
					Finally.of(new AttendanceTime(0)));
			if (this.weeklyPremiumAssignedTime.getHolidayWorkTime().containsKey(holidayWorkFrameNo)){
				val weekWorks = this.weeklyPremiumAssignedTime.getHolidayWorkTime().get(holidayWorkFrameNo).getTimeSeriesWorks();
				if (weekWorks.containsKey(procDate)) weekHolidayWorkTime = weekWorks.get(procDate).getLegalHolidayWorkTime();
			}
			
			Integer assignMinutes = monthlyPTAfterAssign.v();
			
			switch (holidayWorkAndTransferAtr){
			case HOLIDAY_WORK:
				
				// 月次明細－週割り当て時間を限度として「時系列の月割増時間」に割り当てる
				val monthHolidayWorkTimeMinutes = monthHolidayWorkTime.getHolidayWorkTime().get().getTime().v();
				val weekHolidayWorkTimeMinutes = weekHolidayWorkTime.getHolidayWorkTime().get().getTime().v();
				if (assignMinutes > monthHolidayWorkTimeMinutes - weekHolidayWorkTimeMinutes){
					assignMinutes = monthHolidayWorkTimeMinutes - weekHolidayWorkTimeMinutes;
				}
				monthlyPremiumTime.putIfAbsent(procDate, new MonthlyPremiumTimeOfTimeSeries(procDate));
				monthlyPremiumTime.get(procDate).addMinutesToMonthlyPremiumTime(assignMinutes);
				
				// 「時系列の月割増時間」に入れた分を「逆時系列割り当て用の月割増時間」から引く
				monthlyPTAfterAssign = monthlyPTAfterAssign.minusMinutes(assignMinutes);
				
				break;
				
			case TRANSFER:
				
				// 月次明細－週割り当て時間を限度として「時系列の月割増時間」に割り当てる
				val monthTransferTimeMinutes = monthHolidayWorkTime.getTransferTime().get().getTime().v();
				val weekTransferTimeMinutes = weekHolidayWorkTime.getTransferTime().get().getTime().v();
				if (assignMinutes > monthTransferTimeMinutes - weekTransferTimeMinutes){
					assignMinutes = monthTransferTimeMinutes - weekTransferTimeMinutes;
				}
				monthlyPremiumTime.putIfAbsent(procDate, new MonthlyPremiumTimeOfTimeSeries(procDate));
				monthlyPremiumTime.get(procDate).addMinutesToMonthlyPremiumTime(assignMinutes);
				
				// 「時系列の月割増時間」に入れた分を「逆時系列割り当て用の月割増時間」から引く
				monthlyPTAfterAssign = monthlyPTAfterAssign.minusMinutes(assignMinutes);
				
				break;
			}
			
			// 「逆時系列割り当て用の月割増時間」が残っているか確認する
			if (monthlyPTAfterAssign.lessThanOrEqualTo(0)) break;
		}
		
		return monthlyPTAfterAssign;
	}
	
	/**
	 * 残業時間を限度として割り当てる（月割）
	 * @param procDate 処理日
	 * @param monthlyPTForAssign 逆時系列割り当て用の月割増時間
	 * @param workInformationOfDailyMap 日別実績の勤務情報リスト
	 * @return 逆時系列割り当て用の月割増時間　（割り当て後）
	 */
	private AttendanceTimeMonthWithMinus assignWithOverTimeAsLimitForMonth(RequireM2 require, GeneralDate procDate,
			AttendanceTimeMonthWithMinus monthlyPTForAssign,
			Map<GeneralDate, WorkInformation> workInformationOfDailyMap){
		
		AttendanceTimeMonthWithMinus monthlyPTAfterAssign = new AttendanceTimeMonthWithMinus(monthlyPTForAssign.v());
		
		// 「日別実績の勤務情報」を取得
		if (!workInformationOfDailyMap.containsKey(procDate)) return monthlyPTAfterAssign;
		val workInfo = workInformationOfDailyMap.get(procDate);
		if (workInfo.getWorkTimeCode() == null) return monthlyPTAfterAssign;
		val workTimeCode = workInfo.getWorkTimeCode().v();

		// 残業・振替の処理順序を取得する（逆時系列用）
		val companySets = this.excessOutsideWorkMng.getCompanySets();
		val overTimeAndTransferAtrs = GetOverTimeAndTransferOrder.get(require, companySets.getCompanyId(), 
																	workTimeCode, true);
		
		// 残業・振替のループ
		for (val overTimeAndTransferAtr : overTimeAndTransferAtrs){
		
			// 残業枠時間のループ処理
			monthlyPTAfterAssign = this.overTimeFrameTimeProcessForMonth(
					procDate, overTimeAndTransferAtr, monthlyPTAfterAssign);
			
			if (monthlyPTAfterAssign.lessThanOrEqualTo(0)) break;
		}
		
		return monthlyPTAfterAssign;
	}
	
	/**
	 * 残業枠時間のループ処理（逆時系列用・月割）
	 * @param procDate 処理日
	 * @param overTimeAndTransferAtr 残業振替区分
	 * @param monthlyPTForAssign 逆時系列割り当て用の月割増時間
	 * @return 逆時系列割り当て用の月割増時間　（割り当て後）
	 */
	private AttendanceTimeMonthWithMinus overTimeFrameTimeProcessForMonth(
			GeneralDate procDate,
			OverTimeAndTransferAtr overTimeAndTransferAtr,
			AttendanceTimeMonthWithMinus monthlyPTForAssign){
		
		AttendanceTimeMonthWithMinus monthlyPTAfterAssign = new AttendanceTimeMonthWithMinus(monthlyPTForAssign.v());
		val monthlyPremiumTime = this.excessOutsideWorkMng.getExcessOutsideWorkDetail().getMonthlyPremiumTime();
		
		// 振替順を取得する
		LegalTransferOrderSetOfAggrMonthly legalTransferOrderSet =
				this.excessOutsideWorkMng.getSettingsByReg().getLegalTransferOrderSet();
		if (this.excessOutsideWorkMng.getWorkingSystem() == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			legalTransferOrderSet = this.excessOutsideWorkMng.getSettingsByDefo().getLegalTransferOrderSet();
		}
		val overTimeTransferOrder = legalTransferOrderSet.getLegalOverTimeTransferOrder();
		
		// 残業枠時間分ループ
		for (val legalOverTimeTransferOrder : overTimeTransferOrder.getLegalOverTimeTransferOrders()){
			
			// 月次明細の該当枠・年月日の残業時間を取得
			val overTimeFrameNo = legalOverTimeTransferOrder.getOverTimeFrameNo();
			if (!this.overTime.containsKey(overTimeFrameNo)) continue;
			val timeSeriesWorks = this.overTime.get(overTimeFrameNo).getTimeSeriesWorks();
			if (!timeSeriesWorks.containsKey(procDate)) continue;
			val monthOverTime = timeSeriesWorks.get(procDate).getLegalOverTime();

			// 週割増の該当枠・年月日の残業時間を取得
			OverTimeFrameTime weekOverTime = new OverTimeFrameTime(overTimeFrameNo,
					TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)),
					TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)),
					new AttendanceTime(0),
					new AttendanceTime(0));
			if (this.weeklyPremiumAssignedTime.getOverTime().containsKey(overTimeFrameNo)){
				val weekWorks = this.weeklyPremiumAssignedTime.getOverTime().get(overTimeFrameNo).getTimeSeriesWorks();
				if (weekWorks.containsKey(procDate)) weekOverTime = weekWorks.get(procDate).getLegalOverTime();
			}
			
			Integer assignMinutes = monthlyPTAfterAssign.v();
			
			switch (overTimeAndTransferAtr){
			case OVER_TIME:
				
				// 月次明細－週割り当て時間を限度として「時系列の月割増時間」に割り当てる
				val monthOverTimeMinutes = monthOverTime.getOverTimeWork().getTime().v();
				val weekOverTimeMinutes = weekOverTime.getOverTimeWork().getTime().v();
				if (assignMinutes > monthOverTimeMinutes - weekOverTimeMinutes){
					assignMinutes = monthOverTimeMinutes - weekOverTimeMinutes;
				}
				monthlyPremiumTime.putIfAbsent(procDate, new MonthlyPremiumTimeOfTimeSeries(procDate));
				monthlyPremiumTime.get(procDate).addMinutesToMonthlyPremiumTime(assignMinutes);
				
				// 「時系列の月割増時間」に入れた分を「逆時系列割り当て用の月割増時間」から引く
				monthlyPTAfterAssign = monthlyPTAfterAssign.minusMinutes(assignMinutes);
				
				break;
				
			case TRANSFER:
				
				// 月次明細－週割り当て時間を限度として「時系列の月割増時間」に割り当てる
				val monthTransferTimeMinutes = monthOverTime.getTransferTime().getTime().v();
				val weekTransferTimeMinutes = weekOverTime.getTransferTime().getTime().v();
				if (assignMinutes > monthTransferTimeMinutes - weekTransferTimeMinutes){
					assignMinutes = monthTransferTimeMinutes - weekTransferTimeMinutes;
				}
				monthlyPremiumTime.putIfAbsent(procDate, new MonthlyPremiumTimeOfTimeSeries(procDate));
				monthlyPremiumTime.get(procDate).addMinutesToMonthlyPremiumTime(assignMinutes);
				
				// 「時系列の月割増時間」に入れた分を「逆時系列割り当て用の月割増時間」から引く
				monthlyPTAfterAssign = monthlyPTAfterAssign.minusMinutes(assignMinutes);

				break;
			}
			
			// 「逆時系列割り当て用の月割増時間」が残っているか確認する
			if (monthlyPTAfterAssign.lessThanOrEqualTo(0)) break;
		}
		
		return monthlyPTAfterAssign;
	}
	
	/**
	 * 就業時間を限度として割り当てる（月割）
	 * @param procDate 処理日
	 * @param monthlyPTForAssign 逆時系列割り当て用の月割増時間
	 * @return 逆時系列割り当て用の月割増時間　（割り当て後）
	 */
	private AttendanceTimeMonthWithMinus assignWithWorkTimeAsLimitForMonth(
			GeneralDate procDate,
			AttendanceTimeMonthWithMinus monthlyPTForAssign){
		
		AttendanceTimeMonthWithMinus monthlyPTAfterAssign = new AttendanceTimeMonthWithMinus(monthlyPTForAssign.v());
		val monthlyPremiumTime = this.excessOutsideWorkMng.getExcessOutsideWorkDetail().getMonthlyPremiumTime();
		
		// 月次明細の該当年月日の就業時間を取得
		val timeSeriesWorks = this.workTime;
		if (!timeSeriesWorks.containsKey(procDate)) return monthlyPTAfterAssign;
		val monthWorkTimeMinutes = timeSeriesWorks.get(procDate).getLegalTime().getWorkTime().v();

		// 週割増の該当年月日の就業時間を取得
		int weekWorkTimeMinutes = 0;
		val assignTimes = this.weeklyPremiumAssignedTime.getWorkTime();
		if (assignTimes.containsKey(procDate)){
			weekWorkTimeMinutes = assignTimes.get(procDate).getLegalTime().getWorkTime().v();
		}
		
		// 月次明細－週割増割り当て時間を限度として「時系列の月割増時間」に割り当てる
		Integer assignMinutes = monthlyPTAfterAssign.v();
		if (assignMinutes > monthWorkTimeMinutes - weekWorkTimeMinutes){
			assignMinutes = monthWorkTimeMinutes - weekWorkTimeMinutes;
		}
		monthlyPremiumTime.putIfAbsent(procDate, new MonthlyPremiumTimeOfTimeSeries(procDate));
		monthlyPremiumTime.get(procDate).addMinutesToMonthlyPremiumTime(assignMinutes);
		
		// 「時系列の月割増時間」に入れた分を「逆時系列割り当て用の月割増時間」から引く
		monthlyPTAfterAssign = monthlyPTAfterAssign.minusMinutes(assignMinutes);

		return monthlyPTAfterAssign;
	}
	
	/**
	 * 割増加算時間を限度として割り当てる(月割)
	 * @param procDate 処理日
	 * @param monthlyPTForAssign 逆時系列割り当て用の月割増時間
	 * @param addSet 加算設定
	 * @return 逆時系列割り当て用の月割増時間　（割り当て後）
	 */
	private AttendanceTimeMonthWithMinus assignWithPremiumAddTimeAsLimitForMonth(
			GeneralDate procDate, AttendanceTimeMonthWithMinus monthlyPTForAssign){
		
		AttendanceTimeMonthWithMinus monthlyPTAfterAssign = monthlyPTForAssign;
		val monthlyPremiumTime = this.excessOutsideWorkMng.getExcessOutsideWorkDetail().getMonthlyPremiumTime();

		// 週割増の該当年月日の割増加算時間を取得
		int weekAnnualLeaveMinutes = 0;
		val assignTimes = this.weeklyPremiumAssignedTime.getPremiumAddTime();
		if (assignTimes.containsKey(procDate)){
			weekAnnualLeaveMinutes = assignTimes.get(procDate).v();
		}
		
		// 週割増割り当て時間を限度として「時系列の月割増時間」に割り当てる
		monthlyPremiumTime.putIfAbsent(procDate, new MonthlyPremiumTimeOfTimeSeries(procDate));
		monthlyPremiumTime.get(procDate).addMinutesToMonthlyPremiumTime(weekAnnualLeaveMinutes);
		
		// 「時系列の月割増時間」に入れた分を「逆時系列割り当て用の月割増時間」から引く
		return monthlyPTAfterAssign.minusMinutes(weekAnnualLeaveMinutes);
	}
	
	public static interface RequireM6 extends WorkTimeAddtionTimeGetter.Require {}

	public static interface RequireM1 extends MonAggrCompanySettings.RequireM3,
		GetHolidayWorkAndTransferOrder.RequireM1 {
	}
	
	public static interface RequireM2 extends MonAggrCompanySettings.RequireM3, 
		GetOverTimeAndTransferOrder.RequireM1 {
	}
	
	public static interface RequireM3 extends RequireM1, RequireM2 {
	}
	
	public static interface RequireM4 extends MonAggrCompanySettings.RequireM4 {
	}
	
	public static interface RequireM5 extends RequireM3, RequireM4, RequireM6 {
	}
}