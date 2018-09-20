package nts.uk.ctx.at.record.dom.shorttimework;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ConditionAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.HolidayWorkFrameTimeSheetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ManageReGetClass;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OutsideWorkTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OverTimeFrameTimeSheetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetRoundingAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeFrame;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.record.dom.shorttimework.enums.ChildCareAttribute;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.PremiumCalcMethodDetailOfHoliday;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.WorkTimeCalcMethodDetailOfHoliday;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 日別実績の短時間勤務時間
 * @author ken_takasu
 *
 */

@Getter
public class ShortWorkTimeOfDaily {
	
	private WorkTimes workTimes;
	private DeductionTotalTime totalTime;
	private DeductionTotalTime totalDeductionTime;
	private ChildCareAttribute childCareAttribute;
	
	/**
	 * Constructor 
	 */
	public ShortWorkTimeOfDaily(WorkTimes workTimes, DeductionTotalTime totalTime,
			DeductionTotalTime totalDeductionTime, ChildCareAttribute childCareAttribute) {
		super();
		this.workTimes = workTimes;
		this.totalTime = totalTime;
		this.totalDeductionTime = totalDeductionTime;
		this.childCareAttribute = childCareAttribute;
	}
	
	
	public static ShortWorkTimeOfDaily calcShortWorkTime(ManageReGetClass recordClass,ChildCareAttribute careAtr,PremiumAtr premiumAtr,HolidayCalcMethodSet holidayCalcMethodSet,Optional<WorkTimezoneCommonSet> commonSetting) {
		WorkTimes workTimes = new WorkTimes(0);
		DeductionTotalTime totalTime = DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)),
							  								 TimeWithCalculation.sameTime(new AttendanceTime(0)),
							  								 TimeWithCalculation.sameTime(new AttendanceTime(0)));
		DeductionTotalTime totalDeductionTime = DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)),
											   				 		  TimeWithCalculation.sameTime(new AttendanceTime(0)),
											   				 		  TimeWithCalculation.sameTime(new AttendanceTime(0)));
		if(recordClass.getCalculatable()
		 &&recordClass.getIntegrationOfDaily().getShortTime().isPresent()){
			//短時間勤務回数
			workTimes = new WorkTimes(recordClass.getIntegrationOfDaily().getShortTime().get().getShortWorkingTimeSheets().stream()
																														  .filter(tc -> tc.getChildCareAttr().equals(careAtr))
																														  .collect(Collectors.toList())
																														  .size());
//			workTimes = calcWorkTimes(recordClass,careAtr.isChildCare()?ConditionAtr.Child:ConditionAtr.Care);
			totalTime = calculationDedBreakTime(careAtr.isChildCare()?ConditionAtr.Child:ConditionAtr.Care,
												DeductionAtr.Appropriate,
												recordClass.getCalculationRangeOfOneDay(),premiumAtr,holidayCalcMethodSet,commonSetting);
			
			if(decisionDeductChild(premiumAtr,recordClass.getHolidayCalcMethodSet())) {
				totalDeductionTime = calculationDedBreakTime(careAtr.isChildCare()?ConditionAtr.Child:ConditionAtr.Care,
						 DeductionAtr.Deduction,
						 recordClass.getCalculationRangeOfOneDay(),premiumAtr,holidayCalcMethodSet,commonSetting);
			}
			
		}
		return new ShortWorkTimeOfDaily(workTimes, 
										totalTime, 
										totalDeductionTime, 
										careAtr);
		
	}
	
	public static WorkTimes calcWorkTimes(ManageReGetClass recordClass,ConditionAtr condition) {
		
		List<TimeSheetOfDeductionItem> list = new ArrayList<>();
		DeductionAtr dedAtr = DeductionAtr.Appropriate;
		//就業時間内時間帯
		WithinWorkTimeSheet withinWorkTimeSheet = recordClass.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get();
		//就業時間帯育児時間帯
		list.addAll(withinWorkTimeSheet.getShortTimeSheet().stream().filter(tc -> tc.calcTotalTime(dedAtr).greaterThan(0)).collect(Collectors.toList()));
		
		for(WithinWorkTimeFrame withinWorkTimeFrame:withinWorkTimeSheet.getWithinWorkTimeFrame()) {
			list.addAll(withinWorkTimeFrame.getDedTimeSheetByAtr(dedAtr, condition));
			//遅刻
			if(withinWorkTimeFrame.getLateTimeSheet().isPresent()&&withinWorkTimeFrame.getLateTimeSheet().get().getForDeducationTimeSheet().isPresent()) {
				list.addAll(withinWorkTimeFrame.getLateTimeSheet().get().getForDeducationTimeSheet().get().getDedTimeSheetByAtr(dedAtr, condition));
			}
			//早退
			if(withinWorkTimeFrame.getLeaveEarlyTimeSheet().isPresent()&&withinWorkTimeFrame.getLeaveEarlyTimeSheet().get().getForDeducationTimeSheet().isPresent()) {
				list.addAll(withinWorkTimeFrame.getLeaveEarlyTimeSheet().get().getForDeducationTimeSheet().get().getDedTimeSheetByAtr(dedAtr, condition));
			}
		}
		//就業時間外時間帯
		Optional<OutsideWorkTimeSheet> outsideWorkTimeSheet = Optional.empty();
		if(recordClass.getCalculationRangeOfOneDay().getOutsideWorkTimeSheet().isPresent()) {
			recordClass.getCalculationRangeOfOneDay().getOutsideWorkTimeSheet().get();
		}
		//残業
		if(outsideWorkTimeSheet.isPresent()) {
			if(outsideWorkTimeSheet.get().getOverTimeWorkSheet().isPresent()) {
				for(OverTimeFrameTimeSheetForCalc overTimeFrameTimeSheetForCalc:outsideWorkTimeSheet.get().getOverTimeWorkSheet().get().getFrameTimeSheets()) {
					list.addAll(overTimeFrameTimeSheetForCalc.getDedTimeSheetByAtr(dedAtr, condition));
				}
			}
			//休出
			if(outsideWorkTimeSheet.get().getHolidayWorkTimeSheet().isPresent()) {
				for(HolidayWorkFrameTimeSheetForCalc holidayWorkFrameTimeSheetForCalc:outsideWorkTimeSheet.get().getHolidayWorkTimeSheet().get().getWorkHolidayTime()) {
					list.addAll(holidayWorkFrameTimeSheetForCalc.getDedTimeSheetByAtr(dedAtr, condition));
				}
			}
		}
		
		List<TimeSheetOfDeductionItem> result = new ArrayList<>();
		for(TimeSheetOfDeductionItem timeSheetOfDeductionItem:list){
			if(timeSheetOfDeductionItem.calcTotalTime(dedAtr).greaterThan(0)) {
				result.add(timeSheetOfDeductionItem);
			}
		}
		return new WorkTimes(result.size());
	}
	
	
	/**
	 * 控除育児時間を計算するか判定
	 * @param premiumAtr
	 * @param holidayCalcMethodSet
	 * @param commonSetting
	 * @return
	 */
	private static boolean decisionDeductChild(PremiumAtr premiumAtr,HolidayCalcMethodSet holidayCalcMethodSet) {
		boolean decisionDeductChild = true;
		if(premiumAtr.isRegularWork()) {			
			Optional<WorkTimeCalcMethodDetailOfHoliday> advancedSet = holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet();				
				if(advancedSet.isPresent()&&advancedSet.get().getCalculateIncludCareTime()==NotUseAtr.USE) {
					decisionDeductChild = false;
				}
		}else {
			Optional<PremiumCalcMethodDetailOfHoliday> advanceSet = holidayCalcMethodSet.getPremiumCalcMethodOfHoliday().getAdvanceSet();
				if(advanceSet.isPresent()&&advanceSet.get().getCalculateIncludCareTime()==NotUseAtr.USE) {
					decisionDeductChild = true;
				}
		}
		return decisionDeductChild;
	}
	
	/**
	 *　合計時間算出
	 * @param oneDay 
	 * @return
	 */
	public static DeductionTotalTime calculationDedBreakTime(ConditionAtr conditionAtr,DeductionAtr dedAtr, CalculationRangeOfOneDay oneDay,PremiumAtr premiumAtr,HolidayCalcMethodSet holidayCalcMethodSet,Optional<WorkTimezoneCommonSet> commonSetting) {
		return createDudAllTime(conditionAtr,dedAtr,TimeSheetRoundingAtr.PerTimeSheet,oneDay,premiumAtr,holidayCalcMethodSet,commonSetting);
	}
	
	private static DeductionTotalTime createDudAllTime(ConditionAtr conditionAtr, DeductionAtr dedAtr,
			TimeSheetRoundingAtr pertimesheet, CalculationRangeOfOneDay oneDay,PremiumAtr premiumAtr,HolidayCalcMethodSet holidayCalcMethodSet,Optional<WorkTimezoneCommonSet> commonSetting) {
		//所定内
		val withinDedTime = oneDay.calcWithinTotalTime(conditionAtr,dedAtr,StatutoryAtr.Statutory,pertimesheet,premiumAtr,holidayCalcMethodSet,commonSetting);
		//所定外
		val excessDedTime = oneDay.calcWithinTotalTime(conditionAtr,dedAtr,StatutoryAtr.Excess,pertimesheet,premiumAtr,holidayCalcMethodSet,commonSetting);
		//合計計算&return 
		return DeductionTotalTime.of(withinDedTime.addMinutes(excessDedTime.getTime(), excessDedTime.getCalcTime()),
									  withinDedTime,
									  excessDedTime);
	}
}
