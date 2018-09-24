package nts.uk.ctx.at.record.dom.weekly;

import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.byperiod.FlexTimeByPeriod;
import nts.uk.ctx.at.record.dom.monthly.AttendanceItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWork;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.SettingRequiredByDefo;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.SettingRequiredByReg;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 週別の計算
 * @author shuichi_ishida
 */
@Getter
public class WeeklyCalculation implements Cloneable {

	/** 通常変形時間 */
	private RegAndIrgTimeOfWeekly regAndIrgTime;
	/** フレックス時間 */
	private FlexTimeByPeriod flexTime;
	/** 総労働時間 */
	private AggregateTotalWorkingTime totalWorkingTime;
	/** 総拘束時間 */
	private AggregateTotalTimeSpentAtWork totalSpentTime;
	/** 36協定時間 */
	private AgreementTimeOfWeekly agreementTime;

	/** 会社ID */
	private String companyId;
	/** 社員ID */
	private String employeeId;
	/** 労働制 */
	private WorkingSystem workingSystem;
	
	/**
	 * コンストラクタ
	 */
	public WeeklyCalculation(){

		this.regAndIrgTime = new RegAndIrgTimeOfWeekly();
		this.flexTime = new FlexTimeByPeriod();
		this.totalWorkingTime = new AggregateTotalWorkingTime();
		this.totalSpentTime = new AggregateTotalTimeSpentAtWork();
		this.agreementTime = new AgreementTimeOfWeekly();
	}

	/**
	 * ファクトリー
	 * @param regAndIrgTime 通常変形時間
	 * @param flexTime フレックス時間
	 * @param totalWorkingTime 総労働時間
	 * @param totalSpentTime 総拘束時間
	 * @param agreementTime 36協定時間
	 * @return 月別実績の月の計算
	 */
	public static WeeklyCalculation of(
			RegAndIrgTimeOfWeekly regAndIrgTime,
			FlexTimeByPeriod flexTime,
			AggregateTotalWorkingTime totalWorkingTime,
			AggregateTotalTimeSpentAtWork totalSpentTime,
			AgreementTimeOfWeekly agreementTime){
		
		WeeklyCalculation domain = new WeeklyCalculation();
		domain.regAndIrgTime = regAndIrgTime;
		domain.flexTime = flexTime;
		domain.totalWorkingTime = totalWorkingTime;
		domain.totalSpentTime = totalSpentTime;
		domain.agreementTime = agreementTime;
		return domain;
	}
	
	@Override
	public WeeklyCalculation clone() {
		WeeklyCalculation cloned = new WeeklyCalculation();
		try {
			cloned.regAndIrgTime = this.regAndIrgTime.clone();
			cloned.flexTime = this.flexTime.clone();
			cloned.totalWorkingTime = this.totalWorkingTime.clone();
			cloned.totalSpentTime = this.totalSpentTime.clone();
			cloned.agreementTime = this.agreementTime.clone();
		}
		catch (Exception e){
			throw new RuntimeException("WeeklyCalculation clone error.");
		}
		return cloned;
	}
	
	/**
	 * 週の計算
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月（度）
	 * @param weekPeriod 週期間
	 * @param workingSystem 労働制
	 * @param aggregateAtr 集計区分
	 * @param settingsByReg 通常勤務が必要とする設定
	 * @param settingsByDefo 変形労働勤務が必要とする設定
	 * @param aggregateTotalWorkingTime 総労働時間
	 * @param weekStart 週開始
	 * @param premiumTimeOfPrevMonLast 前月の最終週の週割増対象時間
	 * @param attendanceTimeOfDailyMap 日別実績の勤怠時間リスト
	 * @param companySets 月別集計で必要な会社別設定
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void aggregate(
			String companyId,
			String employeeId,
			YearMonth yearMonth,
			DatePeriod weekPeriod,
			WorkingSystem workingSystem,
			MonthlyAggregateAtr aggregateAtr,
			SettingRequiredByReg settingsByReg,
			SettingRequiredByDefo settingsByDefo,
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			WeekStart weekStart,
			AttendanceTimeMonth premiumTimeOfPrevMonLast,
			Map<GeneralDate, AttendanceTimeOfDailyPerformance> attendanceTimeOfDailyMap,
			MonAggrCompanySettings companySets,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		if (workingSystem == WorkingSystem.EXCLUDED_WORKING_CALCULATE) return;
		
		this.totalWorkingTime = aggregateTotalWorkingTime.clone();
		
		this.companyId = companyId;
		this.employeeId = employeeId;
		this.workingSystem = workingSystem;
		
		// 「労働制」を判断する
		switch (workingSystem){
		case REGULAR_WORK:
		case VARIABLE_WORKING_TIME_WORK:
			
			// 週割増時間を集計する
			this.regAndIrgTime.aggregatePremiumTime(companyId, employeeId, weekPeriod,
					workingSystem, aggregateAtr, settingsByReg, settingsByDefo,
					aggregateTotalWorkingTime, weekStart, premiumTimeOfPrevMonLast);
			break;
			
		case FLEX_TIME_WORK:
			
			// フレックス
			this.flexTime.aggregate(weekPeriod, attendanceTimeOfDailyMap);
			break;
			
		case EXCLUDED_WORKING_CALCULATE:
			break;
		}
		
		// 実働時間の集計
		this.totalWorkingTime.aggregateActualWorkingTimeForWeek(
				weekPeriod, workingSystem, this.regAndIrgTime, this.flexTime);
		
		// 36協定時間
		// ※　一旦、不要とする。2018.7.22 del shuichi_ishida
		//this.agreementTime.aggregate(yearMonth, weekPeriod, aggregateAtr, this, companySets, repositories);
	}
	
	/**
	 * 勤怠項目IDに対応する時間を取得する　（丸め処理付き）
	 * @param attendanceItemId 勤怠項目ID
	 * @param roundingSet 月別実績の丸め設定
	 * @param isExcessOutside 時間外超過設定で丸めるかどうか
	 * @return 勤怠月間時間
	 */
	public AttendanceTimeMonth getTimeOfAttendanceItemId(
			int attendanceItemId,
			RoundingSetOfMonthly roundingSet,
			boolean isExcessOutside){

		AttendanceTimeMonth notExistTime = new AttendanceTimeMonth(0);

		val overTimeMap = this.totalWorkingTime.getOverTime().getAggregateOverTimeMap();
		val hdwkTimeMap = this.totalWorkingTime.getHolidayWorkTime().getAggregateHolidayWorkTimeMap();
		
		// 就業時間
		if (attendanceItemId == AttendanceItemOfMonthly.WORK_TIME.value){
			val workTime = this.totalWorkingTime.getWorkTime().getWorkTime();
			if (isExcessOutside) return roundingSet.excessOutsideRound(attendanceItemId, workTime);
			return roundingSet.itemRound(attendanceItemId, workTime);
		}
		
		// 残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.OVER_TIME_01.value + 1);
			if (!overTimeMap.containsKey(overTimeFrameNo)) return notExistTime;
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId,
						overTimeMap.get(overTimeFrameNo).getOverTime().getTime());
			}
			return roundingSet.itemRound(attendanceItemId,
					overTimeMap.get(overTimeFrameNo).getOverTime().getTime());
		}
		
		// 計算残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_OVER_TIME_01.value + 1);
			if (!overTimeMap.containsKey(overTimeFrameNo)) return notExistTime;
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId,
						overTimeMap.get(overTimeFrameNo).getOverTime().getCalcTime());
			}
			return roundingSet.itemRound(attendanceItemId,
					overTimeMap.get(overTimeFrameNo).getOverTime().getCalcTime());
		}
		
		// 振替残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.TRANSFER_OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.TRANSFER_OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.TRANSFER_OVER_TIME_01.value + 1);
			if (!overTimeMap.containsKey(overTimeFrameNo)) return notExistTime;
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId,
						overTimeMap.get(overTimeFrameNo).getTransferOverTime().getTime());
			}
			return roundingSet.itemRound(attendanceItemId,
					overTimeMap.get(overTimeFrameNo).getTransferOverTime().getTime());
		}
		
		// 計算振替残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_TRANSFER_OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_TRANSFER_OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_TRANSFER_OVER_TIME_01.value + 1);
			if (!overTimeMap.containsKey(overTimeFrameNo)) return notExistTime;
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId,
						overTimeMap.get(overTimeFrameNo).getTransferOverTime().getCalcTime());
			}
			return roundingSet.itemRound(attendanceItemId,
					overTimeMap.get(overTimeFrameNo).getTransferOverTime().getCalcTime());
		}
		
		// 休出時間
		if (attendanceItemId >= AttendanceItemOfMonthly.HOLIDAY_WORK_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.HOLIDAY_WORK_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.HOLIDAY_WORK_TIME_01.value + 1);
			if (!hdwkTimeMap.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId,
						hdwkTimeMap.get(holidayWorkTimeFrameNo).getHolidayWorkTime().getTime());
			}
			return roundingSet.itemRound(attendanceItemId,
					hdwkTimeMap.get(holidayWorkTimeFrameNo).getHolidayWorkTime().getTime());
		}
		
		// 計算休出時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_HOLIDAY_WORK_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_HOLIDAY_WORK_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_HOLIDAY_WORK_TIME_01.value + 1);
			if (!hdwkTimeMap.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId,
						hdwkTimeMap.get(holidayWorkTimeFrameNo).getHolidayWorkTime().getCalcTime());
			}
			return roundingSet.itemRound(attendanceItemId,
					hdwkTimeMap.get(holidayWorkTimeFrameNo).getHolidayWorkTime().getCalcTime());
		}
		
		// 振替時間
		if (attendanceItemId >= AttendanceItemOfMonthly.TRANSFER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.TRANSFER_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.TRANSFER_TIME_01.value + 1);
			if (!hdwkTimeMap.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId,
						hdwkTimeMap.get(holidayWorkTimeFrameNo).getTransferTime().getTime());
			}
			return roundingSet.itemRound(attendanceItemId,
					hdwkTimeMap.get(holidayWorkTimeFrameNo).getTransferTime().getTime());
		}
		
		// 計算振替時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_TRANSFER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_TRANSFER_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_TRANSFER_TIME_01.value + 1);
			if (!hdwkTimeMap.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId,
						hdwkTimeMap.get(holidayWorkTimeFrameNo).getTransferTime().getCalcTime());
			}
			return roundingSet.itemRound(attendanceItemId,
					hdwkTimeMap.get(holidayWorkTimeFrameNo).getTransferTime().getCalcTime());
		}
		
		// フレックス法定内時間
		if (attendanceItemId == AttendanceItemOfMonthly.FLEX_LEGAL_TIME.value){
			int flexLegalMinutes = 0;
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId, new AttendanceTimeMonth(flexLegalMinutes));
			}
			return roundingSet.itemRound(attendanceItemId, new AttendanceTimeMonth(flexLegalMinutes));
		}
		
		// フレックス法定外時間
		if (attendanceItemId == AttendanceItemOfMonthly.FLEX_ILLEGAL_TIME.value){
			val flexIllegalMinutes = this.flexTime.getFlexExcessTime().v();
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId, new AttendanceTimeMonth(flexIllegalMinutes));
			}
			return roundingSet.itemRound(attendanceItemId, new AttendanceTimeMonth(flexIllegalMinutes));
		}
		
		// フレックス超過時間　（フレックス時間のプラス分）
		if (attendanceItemId == AttendanceItemOfMonthly.FLEX_EXCESS_TIME.value){
			val flexExcessMinutes = this.flexTime.getFlexTime().v();
			if (flexExcessMinutes <= 0) return notExistTime;
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId, new AttendanceTimeMonth(flexExcessMinutes));
			}
			return roundingSet.itemRound(attendanceItemId, new AttendanceTimeMonth(flexExcessMinutes));
		}
		
		// 所定内割増時間
		if (attendanceItemId == AttendanceItemOfMonthly.WITHIN_PRESCRIBED_PREMIUM_TIME.value){
			val withinPrescribedPremiumTime = this.totalWorkingTime.getWorkTime().getWithinPrescribedPremiumTime();
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId, withinPrescribedPremiumTime);
			}
			return roundingSet.itemRound(attendanceItemId, withinPrescribedPremiumTime);
		}
		
		// 週割増合計時間
		if (attendanceItemId == AttendanceItemOfMonthly.WEEKLY_TOTAL_PREMIUM_TIME.value){
			val weeklyTotalPremiumTime = this.regAndIrgTime.getWeeklyTotalPremiumTime();
			if (isExcessOutside){
				return roundingSet.excessOutsideRound(attendanceItemId, weeklyTotalPremiumTime);
			}
			return roundingSet.itemRound(attendanceItemId, weeklyTotalPremiumTime);
		}
		
		return notExistTime;
	}
}
