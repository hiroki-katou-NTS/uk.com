package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggrSettingMonthlyOfFlx;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * 丸め前合計時間
 * @author shuichu_ishida
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
	 * @param aggrSetOfFlex フレックス時間勤務の月の集計設定
	 */
	public void copyValues(
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			RegularAndIrregularTimeOfMonthly regAndIrgTimeOfMonthly,
			FlexTimeOfMonthly flexTimeOfMonthly,
			AggrSettingMonthlyOfFlx aggrSetOfFlex){
		
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
		
		// 時間外超過対象設定を確認する
		val excessOutsideTimeTargerSet =
				aggrSetOfFlex.getLegalAggregateSet().getExcessOutsideTimeSet().getExcessOutsideTimeTargetSet();
		switch (excessOutsideTimeTargerSet){
		case ONLY_ILLEGAL_FLEX:
			
			// 法定外フレックスのみでフレックス時間をコピーする
			this.flexExcessTime = flexTimeOfMonthly.getFlexExcessTime().addMinutes(
					flexTimeOfMonthly.getFlexCarryforwardTime().getFlexCarryforwardWorkTime().v());
			break;
			
		case INCLUDE_LEGAL_FLEX:
			
			// 法定内フレックスを含んでフレックス時間をコピーする
			this.flexExcessTime = flexTimeOfMonthly.getFlexExcessTime().addMinutes(
					flexTimeOfMonthly.getFlexCarryforwardTime().getFlexCarryforwardWorkTime().v());
			this.flexExcessTime = this.flexExcessTime.addMinutes(
					flexTimeOfMonthly.getFlexTime().getLegalFlexTime().v());
			break;
		}
		
		// 週割増・月割増をコピーする
		this.weeklyTotalPremiumTime = regAndIrgTimeOfMonthly.getWeeklyTotalPremiumTime();
		this.monthlyTotalPremiumTime = regAndIrgTimeOfMonthly.getMonthlyTotalPremiumTime();
	}
}
