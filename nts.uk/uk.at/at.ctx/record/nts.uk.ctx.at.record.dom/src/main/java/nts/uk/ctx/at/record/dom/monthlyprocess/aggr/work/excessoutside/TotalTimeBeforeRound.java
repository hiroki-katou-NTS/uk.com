package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 丸め前合計時間
 * @author shuichi_ishida
 */
@Getter
public class TotalTimeBeforeRound {

	/** 就業時間 */
	private AttendanceTimeMonth workTime;
	/** 残業時間 */
	private Map<OverTimeFrameNo, OverTimeFrameTotalTime> overTime;
	/** 休出時間 */
	private Map<HolidayWorkFrameNo, HolidayWorkFrameTotalTime> holidayWorkTime;
	/** フレックス超過時間 */
	private AttendanceTimeMonth flexExcessTime;
	/** 所定内割増時間 */
	private AttendanceTimeMonth withinPrescribedPremiumTime;
	/** 週割増合計時間 */
	private AttendanceTimeMonth weeklyTotalPremiumTime;
	/** 月割増合計時間 */
	private AttendanceTimeMonth monthlyTotalPremiumTime;

	/**
	 * コンストラクタ
	 */
	public TotalTimeBeforeRound(){
		
		this.workTime = new AttendanceTimeMonth(0);
		this.overTime = new HashMap<>();
		this.holidayWorkTime = new HashMap<>();
		this.flexExcessTime = new AttendanceTimeMonth(0);
		this.withinPrescribedPremiumTime = new AttendanceTimeMonth(0);
		this.weeklyTotalPremiumTime = new AttendanceTimeMonth(0);
		this.monthlyTotalPremiumTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * 集計総労働時間の値をコピーする
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param regAndIrgTimeOfMonthly 月別実績の通常変形時間
	 * @param flexTimeOfMonthly 月別実績のフレックス時間
	 * @param flexAggrSet フレックス時間勤務の月の集計設定
	 * @param outsideOTBDItems 時間外超過設定：内訳項目一覧（積上番号順）
	 */
	public void copyValues(
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			RegularAndIrregularTimeOfMonthly regAndIrgTimeOfMonthly,
			FlexTimeOfMonthly flexTimeOfMonthly,
			FlexMonthWorkTimeAggrSet flexAggrSet,
			List<OutsideOTBRDItem> outsideOTBDItems){
		
		// 就業時間をコピーする
		val workTimeOfMonthly = aggregateTotalWorkingTime.getWorkTime();
		this.workTime = workTimeOfMonthly.getWorkTime();
		this.withinPrescribedPremiumTime = workTimeOfMonthly.getWithinPrescribedPremiumTime();
		
		// 残業時間をコピーする
		val overTimeOfMonthly = aggregateTotalWorkingTime.getOverTime();
		for (val aggrOverTime : overTimeOfMonthly.getAggregateOverTimeMap().values()){
			this.overTime.putIfAbsent(
					aggrOverTime.getOverTimeFrameNo(),
					OverTimeFrameTotalTime.of(
							aggrOverTime.getOverTimeFrameNo(),
							aggrOverTime.getOverTime(),
							aggrOverTime.getTransferOverTime()));
		}
		
		// 休出時間をコピーする
		val holidayWorkTimeOfMonthly = aggregateTotalWorkingTime.getHolidayWorkTime();
		for (val aggrHolidayWorkTime : holidayWorkTimeOfMonthly.getAggregateHolidayWorkTimeMap().values()){
			this.holidayWorkTime.putIfAbsent(
					aggrHolidayWorkTime.getHolidayWorkFrameNo(),
					HolidayWorkFrameTotalTime.of(
							aggrHolidayWorkTime.getHolidayWorkFrameNo(),
							aggrHolidayWorkTime.getHolidayWorkTime(),
							aggrHolidayWorkTime.getTransferTime()));
		}
		
		// 法定内フレックス時間を含めるか判断する
		if (ExcessOutsideWorkMng.isIncludeLegalFlexTime(outsideOTBDItems)){
			
			// 法定内フレックスを含んでフレックス時間をコピーする
			this.flexExcessTime = new AttendanceTimeMonth(
					flexTimeOfMonthly.getFlexTime().getIllegalFlexTime().v() +
					flexTimeOfMonthly.getFlexTime().getLegalFlexTime().v() +
					flexTimeOfMonthly.getFlexCarryforwardTime().getFlexCarryforwardWorkTime().v());
		}
		else {

			// 法定外フレックスのみでフレックス時間をコピーする
			this.flexExcessTime = new AttendanceTimeMonth(
					flexTimeOfMonthly.getFlexTime().getIllegalFlexTime().v() +
					flexTimeOfMonthly.getFlexCarryforwardTime().getFlexCarryforwardWorkTime().v());
		}
		
		// 週割増・月割増をコピーする
		this.weeklyTotalPremiumTime = regAndIrgTimeOfMonthly.getWeeklyTotalPremiumTime();
		this.monthlyTotalPremiumTime = regAndIrgTimeOfMonthly.getMonthlyTotalPremiumTime();
	}
	
	/**
	 * 時間外超過明細から集計する
	 * @param excessOutsideWorkDetail 時間外超過明細
	 * @param datePeriod 期間
	 */
	public void aggregateValues(ExcessOutsideWorkDetail excessOutsideWorkDetail, DatePeriod datePeriod){
		
		// 就業時間・所定内割増時間
		int workTimeMinutes = 0;
		int withinPrescribedPTMinutes = 0;
		for (val workTimeEachDay : excessOutsideWorkDetail.getWorkTime().values()){
			workTimeMinutes += workTimeEachDay.getLegalTime().getWorkTime().v();
			withinPrescribedPTMinutes += workTimeEachDay.getLegalTime().getWithinPrescribedPremiumTime().v();
		}
		this.workTime = new AttendanceTimeMonth(workTimeMinutes);
		this.withinPrescribedPremiumTime = new AttendanceTimeMonth(withinPrescribedPTMinutes);
		
		// 残業時間
		for (val overTimeEachFrameNo : excessOutsideWorkDetail.getOverTime().values()){
			val overTimeFrameNo = overTimeEachFrameNo.getOverTimeFrameNo();
			overTimeEachFrameNo.aggregate(datePeriod);
			this.overTime.putIfAbsent(overTimeFrameNo, OverTimeFrameTotalTime.of(
					overTimeFrameNo,
					overTimeEachFrameNo.getOverTime(),
					overTimeEachFrameNo.getTransferOverTime()));
		}
		
		// 休出時間
		for (val holidayWorkTimeEachFrameNo : excessOutsideWorkDetail.getHolidayWorkTime().values()){
			val holidayWorkFrameNo = holidayWorkTimeEachFrameNo.getHolidayWorkFrameNo();
			holidayWorkTimeEachFrameNo.aggregate(datePeriod);
			this.holidayWorkTime.putIfAbsent(holidayWorkFrameNo, HolidayWorkFrameTotalTime.of(
					holidayWorkFrameNo,
					holidayWorkTimeEachFrameNo.getHolidayWorkTime(),
					holidayWorkTimeEachFrameNo.getTransferTime()));
		}
		
		// フレックス超過時間
		int flexExcessTimeMinutes = 0;
		for (val flexExcessTimeEachDay : excessOutsideWorkDetail.getFlexExcessTime().values()){
			flexExcessTimeMinutes += flexExcessTimeEachDay.getFlexTime().getFlexTime().getTime().v();
		}
		this.flexExcessTime = new AttendanceTimeMonth(flexExcessTimeMinutes);
		
		// 週割増合計時間
		int weekPremiumTimeMinutes = 0;
		for (val weekPremiumTimeEachDay : excessOutsideWorkDetail.getWeeklyPremiumTime().values()){
			weekPremiumTimeMinutes += weekPremiumTimeEachDay.getWeeklyPremiumTime().v();
		}
		this.weeklyTotalPremiumTime = new AttendanceTimeMonth(weekPremiumTimeMinutes);
		
		// 月割増合計時間
		int monthPremiumTimeMinutes = 0;
		for (val monthPremiumTimeEachDay : excessOutsideWorkDetail.getMonthlyPremiumTime().values()){
			monthPremiumTimeMinutes += monthPremiumTimeEachDay.getMonthlyTotalPremiumTime().v();
		}
		this.monthlyTotalPremiumTime = new AttendanceTimeMonth(monthPremiumTimeMinutes);
	}
}
