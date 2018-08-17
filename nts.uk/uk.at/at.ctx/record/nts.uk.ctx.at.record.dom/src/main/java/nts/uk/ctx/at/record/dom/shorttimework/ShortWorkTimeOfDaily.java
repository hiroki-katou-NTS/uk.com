package nts.uk.ctx.at.record.dom.shorttimework;

import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeSheet;
import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ConditionAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ManageReGetClass;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetRoundingAtr;
import nts.uk.ctx.at.record.dom.shorttimework.enums.ChildCareAttribute;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;

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
			totalTime = calculationDedBreakTime(careAtr.isChildCare()?ConditionAtr.Child:ConditionAtr.Care,
												DeductionAtr.Appropriate,
												recordClass.getCalculationRangeOfOneDay(),premiumAtr,holidayCalcMethodSet,commonSetting);
			
			totalDeductionTime = calculationDedBreakTime(careAtr.isChildCare()?ConditionAtr.Child:ConditionAtr.Care,
														 DeductionAtr.Deduction,
														 recordClass.getCalculationRangeOfOneDay(),premiumAtr,holidayCalcMethodSet,commonSetting);
			
		}
		return new ShortWorkTimeOfDaily(workTimes, 
										totalTime, 
										totalDeductionTime, 
										careAtr);
		
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
		//設定間休憩
		
		//合計計算&return 
		return DeductionTotalTime.of(withinDedTime.addMinutes(excessDedTime.getTime(), excessDedTime.getCalcTime()),
									  withinDedTime,
									  excessDedTime);
	}
}
