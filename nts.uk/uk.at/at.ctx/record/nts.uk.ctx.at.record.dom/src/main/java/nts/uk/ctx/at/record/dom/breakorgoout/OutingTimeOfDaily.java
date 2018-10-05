package nts.uk.ctx.at.record.dom.breakorgoout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.daily.AdditionAtr;
import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ConditionAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.FlexWithinWorkTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.HolidayWorkFrameTimeSheetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ManageReGetClass;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OutingTotalTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OutsideWorkTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OverTimeFrameTimeSheetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetRoundingAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.WithinOutingTotalTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeFrame;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.record.dom.stamp.GoOutReason;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexCalcSetting;

/**
 * 日別実績の外出時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class OutingTimeOfDaily {
	
	//回数：休憩外出回数
	private BreakTimeGoOutTimes workTime;
	//外出理由：外出理由
	private GoOutReason reason;
	//休暇使用時間：日別実績の時間休暇使用時間
	private TimevacationUseTimeOfDaily timeVacationUseOfDaily;
	//計上用合計時間：外出合計時間
	private OutingTotalTime recordTotalTime;
	//控除用合計時間：外出合計時間
	private OutingTotalTime deductionTotalTime;
	//補正後時間帯:外出時間帯(List)
	private List<OutingTimeSheet> outingTimeSheets;
	
	
	/**
	 * Constcutor
	 */
	public OutingTimeOfDaily(BreakTimeGoOutTimes workTime, GoOutReason reason,
			TimevacationUseTimeOfDaily timeVacationUseOfDaily, OutingTotalTime recordTotalTime,
			OutingTotalTime deductionTotalTime, List<OutingTimeSheet> outingTimeSheets) {
		super();
		this.workTime = workTime;
		this.reason = reason;
		this.timeVacationUseOfDaily = timeVacationUseOfDaily;
		this.recordTotalTime = recordTotalTime;
		this.deductionTotalTime = deductionTotalTime;
		this.outingTimeSheets = outingTimeSheets;
	}
	
	/**
	 * 全ての外出時間を計算する指示を出すクラス
	 * @param outingOfDaily 
	 * @return
	 */
	public static OutingTimeOfDaily calcOutingTime(OutingTimeSheet outingOfDaily, CalculationRangeOfOneDay oneDay,boolean isCalculatable,Optional<FlexCalcSetting> flexCalcSet
			,PremiumAtr premiumAtr,HolidayCalcMethodSet holidayCalcMethodSet,Optional<WorkTimezoneCommonSet> commonSetting,ManageReGetClass recordClass) {
		BreakTimeGoOutTimes goOutTimes = new BreakTimeGoOutTimes(0);
		//休暇使用時間
		TimevacationUseTimeOfDaily useVacationTime = new TimevacationUseTimeOfDaily(new AttendanceTime(0),
																					new AttendanceTime(0),
																					new AttendanceTime(0),
																					new AttendanceTime(0));
		OutingTotalTime recordTotalTime = OutingTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)),
															 WithinOutingTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)),
																	 				  TimeWithCalculation.sameTime(new AttendanceTime(0)),
																	 				  TimeWithCalculation.sameTime(new AttendanceTime(0))),
															 TimeWithCalculation.sameTime(new AttendanceTime(0))); 
		
		OutingTotalTime dedTotalTime = OutingTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)),
														  WithinOutingTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)),
																  				   TimeWithCalculation.sameTime(new AttendanceTime(0)),
																  				   TimeWithCalculation.sameTime(new AttendanceTime(0))),
				   										  TimeWithCalculation.sameTime(new AttendanceTime(0)));
		//補正後時間帯
		List<OutingTimeSheet> correctedTimeSheet = new ArrayList<>();
		
		if(isCalculatable) {
			//回数
			goOutTimes = calcGoOutTimes(recordClass);
			//休暇使用時間
			
			//計上用合計時間
			recordTotalTime = calcOutingTime(oneDay, DeductionAtr.Appropriate,outingOfDaily,flexCalcSet,premiumAtr,holidayCalcMethodSet,commonSetting); 
			//控除用合計時間
			dedTotalTime = calcOutingTime(oneDay, DeductionAtr.Deduction,outingOfDaily,flexCalcSet,premiumAtr,holidayCalcMethodSet,commonSetting);
			//補正後時間帯 
		}
		return new OutingTimeOfDaily(goOutTimes, 
									 GoOutReason.OFFICAL, 
									 useVacationTime, 
									 recordTotalTime, 
									 dedTotalTime,
									 correctedTimeSheet);
	}
	
	public static BreakTimeGoOutTimes calcGoOutTimes(ManageReGetClass recordClass) {
		
		List<TimeSheetOfDeductionItem> list = new ArrayList<>();
		//就業時間内時間帯
		WithinWorkTimeSheet withinWorkTimeSheet = recordClass.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get();
		for(WithinWorkTimeFrame withinWorkTimeFrame:withinWorkTimeSheet.getWithinWorkTimeFrame()) {
			list.addAll(withinWorkTimeFrame.getDedTimeSheetByAtr(DeductionAtr.Deduction, ConditionAtr.Care));
			//遅刻
			if(withinWorkTimeFrame.getLateTimeSheet().isPresent()&&withinWorkTimeFrame.getLateTimeSheet().get().getForDeducationTimeSheet().isPresent()) {
				list.addAll(withinWorkTimeFrame.getLateTimeSheet().get().getForDeducationTimeSheet().get().getDedTimeSheetByAtr(DeductionAtr.Deduction, ConditionAtr.Care));
			}
			//早退
			if(withinWorkTimeFrame.getLeaveEarlyTimeSheet().isPresent()&&withinWorkTimeFrame.getLeaveEarlyTimeSheet().get().getForDeducationTimeSheet().isPresent()) {
				list.addAll(withinWorkTimeFrame.getLeaveEarlyTimeSheet().get().getForDeducationTimeSheet().get().getDedTimeSheetByAtr(DeductionAtr.Deduction, ConditionAtr.Care));
			}
		}
		//就業時間外時間帯
		OutsideWorkTimeSheet outsideWorkTimeSheet = recordClass.getCalculationRangeOfOneDay().getOutsideWorkTimeSheet().get();
		//残業
		if(outsideWorkTimeSheet.getOverTimeWorkSheet().isPresent()) {
			for(OverTimeFrameTimeSheetForCalc overTimeFrameTimeSheetForCalc:outsideWorkTimeSheet.getOverTimeWorkSheet().get().getFrameTimeSheets()) {
				list.addAll(overTimeFrameTimeSheetForCalc.getDedTimeSheetByAtr(DeductionAtr.Deduction, ConditionAtr.Care));
			}
		}
		//休出
		if(outsideWorkTimeSheet.getHolidayWorkTimeSheet().isPresent()) {
			for(HolidayWorkFrameTimeSheetForCalc holidayWorkFrameTimeSheetForCalc:outsideWorkTimeSheet.getHolidayWorkTimeSheet().get().getWorkHolidayTime()) {
				list.addAll(holidayWorkFrameTimeSheetForCalc.getDedTimeSheetByAtr(DeductionAtr.Deduction, ConditionAtr.Care));
			}
		}
		
		List<TimeSheetOfDeductionItem> result = new ArrayList<>();
		for(TimeSheetOfDeductionItem timeSheetOfDeductionItem:list){
			if(timeSheetOfDeductionItem.calcTotalTime(DeductionAtr.Deduction).greaterThan(0)) {
				result.add(timeSheetOfDeductionItem);
			}
		}
		return new BreakTimeGoOutTimes(result.size());
	}
	
	/**
	 * 外出時間の計算
	 * @param oneDay
	 * @return
	 */
	private static OutingTotalTime calcOutingTime(CalculationRangeOfOneDay oneDay,DeductionAtr dedAtr,OutingTimeSheet outingOfDaily,
													 Optional<FlexCalcSetting> flexCalcSet,PremiumAtr premiumAtr,HolidayCalcMethodSet holidayCalcMethodSet,Optional<WorkTimezoneCommonSet> commonSetting) {
		//外出合計時間の計算
		DeductionTotalTime outingTotal = calculationDedBreakTime(dedAtr, oneDay,outingOfDaily,premiumAtr,holidayCalcMethodSet,commonSetting);
		//コア内と害を分けて計算するかどうか判定
		//YES 所定内外出をコア内と外で分けて計算
		TimeWithCalculation withinDedTime = TimeWithCalculation.sameTime(new AttendanceTime(0));
		AttendanceTime withinFlex = new AttendanceTime(0);
		AttendanceTime excessFlex = new AttendanceTime(0);
		if(flexCalcSet.isPresent()) {
			//所定内
			withinDedTime = oneDay.calcWithinTotalTime(ConditionAtr.convertFromGoOutReason(outingOfDaily.getReasonForGoOut()),dedAtr,StatutoryAtr.Statutory,TimeSheetRoundingAtr.PerTimeSheet,premiumAtr,holidayCalcMethodSet,commonSetting);
			FlexWithinWorkTimeSheet changedFlexTimeSheet = (FlexWithinWorkTimeSheet)oneDay.getWithinWorkingTimeSheet().get();
			withinFlex = changedFlexTimeSheet.calcOutingTimeInFlex(true);
			excessFlex = changedFlexTimeSheet.calcOutingTimeInFlex(false);
					
		}
		//控除合計時間を返す return
		return OutingTotalTime.of (outingTotal.getTotalTime(),
								   WithinOutingTotalTime.of(withinDedTime, 
										   					TimeWithCalculation.sameTime(withinFlex), 
										   					TimeWithCalculation.sameTime(excessFlex)),
								   outingTotal.getExcessOfStatutoryTotalTime());
	}
	/**
	 *　合計時間算出
	 * @param oneDay 
	 * @return
	 */
	public static DeductionTotalTime calculationDedBreakTime(DeductionAtr dedAtr, CalculationRangeOfOneDay oneDay,OutingTimeSheet outingOfDaily,PremiumAtr premiumAtr,HolidayCalcMethodSet holidayCalcMethodSet,Optional<WorkTimezoneCommonSet> commonSetting) {
		return createDudAllTime(ConditionAtr.convertFromGoOutReason(outingOfDaily.getReasonForGoOut()),dedAtr,TimeSheetRoundingAtr.PerTimeSheet,oneDay,premiumAtr,holidayCalcMethodSet,commonSetting);
	}
	
	private static DeductionTotalTime createDudAllTime(ConditionAtr conditionAtr, DeductionAtr dedAtr,
			TimeSheetRoundingAtr pertimesheet, CalculationRangeOfOneDay oneDay,PremiumAtr premiumAtr,HolidayCalcMethodSet holidayCalcMethodSet,Optional<WorkTimezoneCommonSet> commonSetting) {
		//所定内
		val withinDedTime = oneDay.calcWithinTotalTime(conditionAtr,dedAtr,StatutoryAtr.Statutory,pertimesheet,premiumAtr,holidayCalcMethodSet,commonSetting);
		//所定外
		val excessDedTime = oneDay.calcWithinTotalTime(conditionAtr,dedAtr,StatutoryAtr.Excess,pertimesheet,premiumAtr,holidayCalcMethodSet,commonSetting);
		//設定間休憩
		
		//合計計算&return 
		return DeductionTotalTime.of(withinDedTime.addMinutes(excessDedTime.getTime(), excessDedTime.getCalcTime()),
									  withinDedTime,
									  excessDedTime);
	}
	
	/**
	 * 休暇加算時間の計算
	 * @return
	 */
	public int calcVacationAddTime(Optional<HolidayAddtionSet> holidayAddtionSet) {
		int result = 0;	
		int totalAddTime = this.timeVacationUseOfDaily.calcTotalVacationAddTime(holidayAddtionSet, AdditionAtr.WorkingHoursOnly);	
		if(this.recordTotalTime.getTotalTime().getCalcTime().lessThanOrEqualTo(totalAddTime)) {
			result = this.recordTotalTime.getTotalTime().getCalcTime().valueAsMinutes();
		}else {
			result = totalAddTime;
		}
		return result;
	}
	
}
