package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSettingOfWorkingTime;
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
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.ShortTimeWorkSheetWithoutWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeFrameTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 日別実績の短時間勤務時間
 * @author ken_takasu
 */

@Getter
@AllArgsConstructor
public class ShortWorkTimeOfDaily {
	
	/** 回数: 勤務回数 */
	private WorkTimes workTimes;
	/** 合計時間: 控除合計時間 */
	private DeductionTotalTime totalTime;
	/** 合計控除時間: 控除合計時間 */
	private DeductionTotalTime totalDeductionTime;
	/** 育児介護区分: 育児介護区分 */
	private ChildCareAtr childCareAttribute;
	
	/** 加算時間: 勤怠時間 */
	private AttendanceTime addTime;
	
	/**
	 * Constructor 
	 */
	public ShortWorkTimeOfDaily(WorkTimes workTimes, DeductionTotalTime totalTime,
			DeductionTotalTime totalDeductionTime, ChildCareAtr childCareAttribute) {
		super();
		this.workTimes = workTimes;
		this.totalTime = totalTime;
		this.totalDeductionTime = totalDeductionTime;
		this.childCareAttribute = childCareAttribute;
		this.addTime = new AttendanceTime(0);
	}
	
	public ShortWorkTimeOfDaily() {
		this.workTimes = new WorkTimes(1);
		this.totalTime = DeductionTotalTime.defaultValue();
		this.totalDeductionTime = DeductionTotalTime.defaultValue();
		this.childCareAttribute = ChildCareAtr.CHILD_CARE;
		this.addTime = new AttendanceTime(0);
	}
	
	/**
	 * 日別実績の短時間勤務時間
	 * @param recordClass 実績
	 * @param premiumAtr 割増区分
	 * @return 日別実績の短時間勤務時間
	 */
	public static ShortWorkTimeOfDaily calcShortWorkTime(
			ManageReGetClass recordClass,
			PremiumAtr premiumAtr) {
		
		WorkTimes workTimes = new WorkTimes(0);
		DeductionTotalTime totalTime = DeductionTotalTime.defaultValue();
		DeductionTotalTime totalDeductionTime = DeductionTotalTime.defaultValue();
		ChildCareAtr careAtr = getChildCareAttributeToDaily(recordClass.getIntegrationOfDaily());
		ShortWorkTimeOfDaily zeroValue = new ShortWorkTimeOfDaily(workTimes, totalTime, totalDeductionTime, careAtr);
		AttendanceTime addTime = AttendanceTime.ZERO;
		
		// 勤務種類を確認
		if (!recordClass.getWorkType().isPresent()) return zeroValue;
		WorkType workType = recordClass.getWorkType().get();
		// 出勤系かどうかの判断
		if (workType.isWorkingDay() == false) return zeroValue;
		
		if(recordClass.getCalculatable() && recordClass.getIntegrationOfDaily().getShortTime().isPresent()){
			//回数の計算
			workTimes = new WorkTimes(recordClass.getCalculationRangeOfOneDay().getDeductionCount(
					careAtr.isCare() ? ConditionAtr.Care : ConditionAtr.Child,
					DeductionAtr.Appropriate));
			
			//計上時間の計算
			totalTime = calcTotalShortWorkTime(recordClass, DeductionAtr.Appropriate, careAtr, premiumAtr);
			
			//控除時間の計算
			totalDeductionTime = calcTotalShortWorkTime(recordClass, DeductionAtr.Deduction, careAtr, premiumAtr);
			
			/** 加算時間を補正する */
			addTime = correctAddTime(recordClass.getPersonDailySetting().getAddSetting().getAddSetOfWorkingTime(),
									recordClass.getCalculationRangeOfOneDay(), careAtr);
		}
		return new ShortWorkTimeOfDaily(workTimes, totalTime, totalDeductionTime, careAtr, addTime);
	}
	
	/** 加算時間を補正する */
	private static AttendanceTime correctAddTime(AddSettingOfWorkingTime addSet, CalculationRangeOfOneDay oneDay,
			ChildCareAtr careAtr) {
		
		/** 育児介護時間を含めて計算するか判断する */
		if (addSet.isCalculateIncludCareTime(PremiumAtr.RegularWork)) {
			
			/** 控除短時間勤務時間の計算 */
			DeductionTotalTime time = calcShortTime(DeductionAtr.Deduction, careAtr, oneDay);
			
			/** 〇合計時間の計算 */
			return time.getTotalTime().getCalcTime();
		}
		
		return AttendanceTime.ZERO;
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
	 * 短時間勤務時間の計算
	 * @param recordClass 実績
	 * @param dedAtr 控除区分
	 * @param careAtr 育児介護区分
	 * @param premiumAtr 割増区分
	 * @return 控除合計時間
	 */
	public static DeductionTotalTime calcTotalShortWorkTime(
			ManageReGetClass recordClass,
			DeductionAtr dedAtr,
			ChildCareAtr careAtr,
			PremiumAtr premiumAtr){
		
		DeductionTotalTime result = DeductionTotalTime.defaultValue();
		CalculationRangeOfOneDay oneDay = recordClass.getCalculationRangeOfOneDay();
		
		/** 短時間勤務時間の計算 */
		result = calcShortTime(dedAtr, careAtr, premiumAtr, oneDay, recordClass.getHolidayCalcMethodSet());
		
		if (dedAtr.isAppropriate()){
			if (recordClass.getCalculationRangeOfOneDay().getShortTimeWSWithoutWork().isPresent()){
				ShortTimeWorkSheetWithoutWork withoutWork =
						recordClass.getCalculationRangeOfOneDay().getShortTimeWSWithoutWork().get();
				// 勤務外短時間勤務時間を累計する（所定内）
				ConditionAtr conditionAtr = ConditionAtr.Child;
				if (careAtr.isCare()) conditionAtr = ConditionAtr.Care;
				AttendanceTime withinTime = withoutWork.sumShortWorkTimeWithoutWork(
						conditionAtr, TimeSheetRoundingAtr.ALL, Optional.empty(), true);
				// 勤務外短時間勤務時間を累計する（所定外）
				AttendanceTime withoutTime = withoutWork.sumShortWorkTimeWithoutWork(
						conditionAtr, TimeSheetRoundingAtr.ALL, Optional.empty(), false);
				
				AttendanceTime totalTime = withinTime.addMinutes(withoutTime.valueAsMinutes());
				result = DeductionTotalTime.of(
						result.getTotalTime().addMinutes(totalTime, totalTime),
						result.getWithinStatutoryTotalTime().addMinutes(withinTime, withinTime),
						result.getExcessOfStatutoryTotalTime().addMinutes(withoutTime, withoutTime));
			}
		}
		// 控除合計時間を返す
		return result;
	}

	/**
	 * 控除短時間勤務時間の計算
	 * @param dedAtr 控除区分
	 * @param careAtr 育児介護区分
	 * @param premiumAtr 割増区分
	 * @param oneDay 1日の計算範囲
	 * @param addSet 労働時間の加算設定
	 * @return 控除合計時間
	 */
	private static DeductionTotalTime calcShortTime(DeductionAtr dedAtr, ChildCareAtr careAtr, PremiumAtr premiumAtr,
			CalculationRangeOfOneDay oneDay, AddSettingOfWorkingTime addSet) {
		ConditionAtr conditionAtr = (careAtr.isChildCare() ? ConditionAtr.Child : ConditionAtr.Care);
		TimeWithCalculation withinTime = TimeWithCalculation.sameTime(AttendanceTime.ZERO);
		// 短時間勤務を控除するかどうかの設定を確認
		if(decisionDeductChild(dedAtr, premiumAtr, addSet)) {
			// 所定内育児時間の計算
			withinTime = oneDay.getDeductionTime(
					conditionAtr, dedAtr, StatutoryAtr.Statutory, Optional.empty());
		}
		// 所定外育児時間の計算
		TimeWithCalculation excessTime = oneDay.getDeductionTime(
				conditionAtr, dedAtr, StatutoryAtr.Excess, Optional.empty());
		// 合計時間の計算
		return DeductionTotalTime.of(
				withinTime.addMinutes(excessTime.getTime(), excessTime.getCalcTime()),
				withinTime,
				excessTime);
	}
	
	/**
	 * 短時間勤務を控除するかどうかの設定を確認
	 * @param dedAtr 控除区分
	 * @param premiumAtr 割増区分
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @return true:控除する,false:控除しない
	 */
	private static boolean decisionDeductChild(
			DeductionAtr dedAtr,
			PremiumAtr premiumAtr,
			AddSettingOfWorkingTime holidayCalcMethodSet) {

		// 計上なら、控除する
		if (dedAtr.isAppropriate()) return true;
		// 育児介護時間を含めて計算するか判断する
		return !holidayCalcMethodSet.isCalculateIncludCareTime(premiumAtr);
	}
	
	/**
	 * 日別実績(Work)から育児介護区分を取得する
	 * @param integrationOfDaily 日別実績(Work)
	 * @return 育児介護区分
	 */
	public static ChildCareAtr getChildCareAttributeToDaily(IntegrationOfDaily integrationOfDaily) {
		if(integrationOfDaily.getShortTime().isPresent()) {
			val firstTimeSheet = integrationOfDaily.getShortTime().get().getShortWorkingTimeSheets().stream().findFirst();
			if(firstTimeSheet.isPresent()) {
				return firstTimeSheet.get().getChildCareAttr();
			}
		}
		if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent()) {
			return integrationOfDaily.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getShotrTimeOfDaily().getChildCareAttribute();
		}
		return ChildCareAtr.CHILD_CARE;
	}
	
}
