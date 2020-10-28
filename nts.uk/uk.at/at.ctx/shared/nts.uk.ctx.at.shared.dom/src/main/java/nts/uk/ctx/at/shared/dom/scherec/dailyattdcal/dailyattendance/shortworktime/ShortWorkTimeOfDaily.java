package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.PremiumCalcMethodDetailOfHoliday;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkTimeCalcMethodDetailOfHoliday;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.DeductionTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.HolidayWorkFrameTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManageReGetClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.OutsideWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSheetRoundingAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeFrameTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeSheet;
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
	
	
	/**
	 * 日別実績の短時間勤務時間
	 * @param recordClass 実績
	 * @param premiumAtr 割増区分
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param commonSetting 就業時間帯の共通設定
	 * @return 日別実績の短時間勤務時間
	 */
	public static ShortWorkTimeOfDaily calcShortWorkTime(
			ManageReGetClass recordClass,
			PremiumAtr premiumAtr,
			HolidayCalcMethodSet holidayCalcMethodSet,
			Optional<WorkTimezoneCommonSet> commonSetting) {
		
		WorkTimes workTimes = new WorkTimes(0);
		
		DeductionTotalTime totalTime = DeductionTotalTime.of(
				TimeWithCalculation.sameTime(new AttendanceTime(0)),
				TimeWithCalculation.sameTime(new AttendanceTime(0)),
				TimeWithCalculation.sameTime(new AttendanceTime(0)));
		
		DeductionTotalTime totalDeductionTime = DeductionTotalTime.of(
				TimeWithCalculation.sameTime(new AttendanceTime(0)),
				TimeWithCalculation.sameTime(new AttendanceTime(0)),
				TimeWithCalculation.sameTime(new AttendanceTime(0)));
		
		ChildCareAttribute careAtr = getChildCareAttributeToDaily(recordClass.getIntegrationOfDaily());
		
		if(recordClass.getCalculatable() && recordClass.getIntegrationOfDaily().getShortTime().isPresent()){
			//短時間勤務回数
			workTimes = new WorkTimes(recordClass.getIntegrationOfDaily().getShortTime().get().getShortWorkingTimeSheets().stream()
					.filter(tc -> tc.getChildCareAttr().equals(careAtr))
					.collect(Collectors.toList())
					.size());
			
			//計上時間の計算
			totalTime = calculationDedBreakTime(
					careAtr.isChildCare()?ConditionAtr.Child:ConditionAtr.Care,
					DeductionAtr.Appropriate,
					recordClass.getCalculationRangeOfOneDay(),
					premiumAtr,
					holidayCalcMethodSet,
					commonSetting);
			
			if(decisionDeductChild(premiumAtr,recordClass.getHolidayCalcMethodSet())) {
				//控除時間の計算
				totalDeductionTime = calculationDedBreakTime(
						careAtr.isChildCare()?ConditionAtr.Child:ConditionAtr.Care,
						DeductionAtr.Deduction,
						recordClass.getCalculationRangeOfOneDay(),
						premiumAtr,
						holidayCalcMethodSet,
						commonSetting);
			}
		}
		return new ShortWorkTimeOfDaily(workTimes, totalTime, totalDeductionTime, careAtr);
	}
	
	public static WorkTimes calcWorkTimes(ManageReGetClass recordClass,ConditionAtr condition) {
		
		List<TimeSheetOfDeductionItem> list = new ArrayList<>();
		DeductionAtr dedAtr = DeductionAtr.Appropriate;
		//就業時間内時間帯
		WithinWorkTimeSheet withinWorkTimeSheet = recordClass.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get();
		//就業時間帯育児時間帯
		list.addAll(withinWorkTimeSheet.getShortTimeSheet().stream().filter(tc -> tc.calcTotalTime().greaterThan(0)).collect(Collectors.toList()));
		
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
			if(timeSheetOfDeductionItem.calcTotalTime().greaterThan(0)) {
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
	 * 合計時間算出
	 * @param conditionAtr 条件
	 * @param dedAtr 控除区分
	 * @param oneDay 1日の計算範囲
	 * @param premiumAtr 割増区分
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param commonSetting 就業時間帯の共通設定
	 * @return 控除合計時間
	 */
	public static DeductionTotalTime calculationDedBreakTime(
			ConditionAtr conditionAtr,
			DeductionAtr dedAtr,
			CalculationRangeOfOneDay oneDay,
			PremiumAtr premiumAtr,
			HolidayCalcMethodSet holidayCalcMethodSet,
			Optional<WorkTimezoneCommonSet> commonSetting) {
		return createDudAllTime(conditionAtr,dedAtr,TimeSheetRoundingAtr.PerTimeSheet,oneDay,premiumAtr,holidayCalcMethodSet,commonSetting);
	}
	
	/**
	 * 短時間勤務時間の計算
	 * @param conditionAtr 条件
	 * @param dedAtr 控除区分
	 * @param pertimesheet
	 * @param oneDay 1日の計算範囲
	 * @param premiumAtr 割増区分
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param commonSetting 就業時間帯の共通設定
	 * @return 控除合計時間
	 */
	private static DeductionTotalTime createDudAllTime(
			ConditionAtr conditionAtr,
			DeductionAtr dedAtr,
			TimeSheetRoundingAtr pertimesheet,
			CalculationRangeOfOneDay oneDay,
			PremiumAtr premiumAtr,
			HolidayCalcMethodSet holidayCalcMethodSet,
			Optional<WorkTimezoneCommonSet> commonSetting) {
		//所定内育児時間の計算
		val withinDedTime = oneDay.calcWithinTotalTime(conditionAtr,dedAtr,StatutoryAtr.Statutory,pertimesheet,premiumAtr,holidayCalcMethodSet,commonSetting);
		//所定外育児時間の計算
		val excessDedTime = oneDay.calcWithinTotalTime(conditionAtr,dedAtr,StatutoryAtr.Excess,pertimesheet,premiumAtr,holidayCalcMethodSet,commonSetting);
		//合計時間の計算&return 
		return DeductionTotalTime.of(withinDedTime.addMinutes(excessDedTime.getTime(), excessDedTime.getCalcTime()), withinDedTime, excessDedTime);
	}
	
	/**
	 * 日別実績(Work)から育児介護区分を取得する
	 * @param integrationOfDaily 日別実績(Work)
	 * @return 育児介護区分
	 */
	private static ChildCareAttribute getChildCareAttributeToDaily(IntegrationOfDaily integrationOfDaily) {
		if(integrationOfDaily.getShortTime().isPresent()) {
			val firstTimeSheet = integrationOfDaily.getShortTime().get().getShortWorkingTimeSheets().stream().findFirst();
			if(firstTimeSheet.isPresent()) {
				return firstTimeSheet.get().getChildCareAttr();
			}
		}
		if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()) {
			return integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getShotrTimeOfDaily().getChildCareAttribute();
		}
		return ChildCareAttribute.CHILD_CARE;
	}
	
}
