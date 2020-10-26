package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
//import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.FlexWithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.HolidayWorkFrameTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.OutsideWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.TimeSheetRoundingAtr;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.WithinOutingTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.DeductionTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeFrameTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.ManageReGetClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.enums.AdditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.enums.GoOutReason;
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
	 * 日別実績の外出時間
	 * @param recordClass 時間帯作成、時間計算で再取得が必要になっているクラスたちの管理クラス
	 * @return 日別実績の外出時間(List)
	 */
	public static List<OutingTimeOfDaily> calcList(ManageReGetClass recordClass) {
		List<OutingTimeOfDaily> outingList = new ArrayList<OutingTimeOfDaily>();
		if(recordClass.getIntegrationOfDaily().getOutingTime().isPresent()) {
			for(OutingTimeSheet outingOfDaily : recordClass.getIntegrationOfDaily().getOutingTime().get().getOutingTimeSheets()) {
				outingList.add(OutingTimeOfDaily.calcOutingTime(
						outingOfDaily,
						recordClass.getCalculationRangeOfOneDay(),
						recordClass.getCalculatable(),
						recordClass.getFlexCalcSetting(),
						PremiumAtr.RegularWork,
						recordClass.getHolidayCalcMethodSet(),
						recordClass.getWorkTimezoneCommonSet(),
						recordClass));
			}
		}
		return outingList;
	}
	
	/**
	 * 全ての外出時間を計算する指示を出すクラス
	 * アルゴリズム：日別実績の外出時間
	 * @param outingOfDaily 外出時間帯
	 * @param oneDay 1日の計算範囲
	 * @param isCalculatable 計算処理に入ることができるかフラグ
	 * @param flexCalcSet フレックス計算設定
	 * @param premiumAtr 割増区分
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param commonSetting 就業時間帯の共通設定
	 * @param recordClass 実績
	 * @return 日別実績の外出時間
	 */
	public static OutingTimeOfDaily calcOutingTime(
			OutingTimeSheet outingOfDaily,
			CalculationRangeOfOneDay oneDay,
			boolean isCalculatable,
			Optional<FlexCalcSetting> flexCalcSet,
			PremiumAtr premiumAtr,
			HolidayCalcMethodSet holidayCalcMethodSet,
			Optional<WorkTimezoneCommonSet> commonSetting,
			ManageReGetClass recordClass) {
		BreakTimeGoOutTimes goOutTimes = new BreakTimeGoOutTimes(0);
		//休暇使用時間
		TimevacationUseTimeOfDaily useVacationTime = new TimevacationUseTimeOfDaily(
				new AttendanceTime(0),
				new AttendanceTime(0),
				new AttendanceTime(0),
				new AttendanceTime(0),
				Optional.empty(),
				new AttendanceTime(0),
				new AttendanceTime(0));
		
		OutingTotalTime recordTotalTime = OutingTotalTime.of(
				TimeWithCalculation.sameTime(new AttendanceTime(0)),
				WithinOutingTotalTime.of(
						TimeWithCalculation.sameTime(new AttendanceTime(0)),
						TimeWithCalculation.sameTime(new AttendanceTime(0)),
						TimeWithCalculation.sameTime(new AttendanceTime(0))),
				TimeWithCalculation.sameTime(new AttendanceTime(0))); 
		
		OutingTotalTime dedTotalTime = OutingTotalTime.of(
				TimeWithCalculation.sameTime(new AttendanceTime(0)),
				WithinOutingTotalTime.of(
						TimeWithCalculation.sameTime(new AttendanceTime(0)),
						TimeWithCalculation.sameTime(new AttendanceTime(0)),
						TimeWithCalculation.sameTime(new AttendanceTime(0))),
				TimeWithCalculation.sameTime(new AttendanceTime(0)));
		
		//補正後時間帯
		List<OutingTimeSheet> correctedTimeSheet = new ArrayList<>();
		
		if(isCalculatable) {
			//外出回数
			goOutTimes = calcGoOutTimes(recordClass, ConditionAtr.convertFromGoOutReason(outingOfDaily.getReasonForGoOut()));
			//休暇使用時間
			
			//計上用合計時間
			recordTotalTime = calcOutingTime(oneDay, DeductionAtr.Appropriate, outingOfDaily, flexCalcSet, premiumAtr, holidayCalcMethodSet, commonSetting); 
			//控除用合計時間
			dedTotalTime = calcOutingTime(oneDay, DeductionAtr.Deduction, outingOfDaily, flexCalcSet, premiumAtr, holidayCalcMethodSet, commonSetting);
			//補正後時間帯 
		}
		return new OutingTimeOfDaily(
				goOutTimes,
				GoOutReason.OFFICAL, 
				useVacationTime, 
				recordTotalTime, 
				dedTotalTime,
				correctedTimeSheet);
	}
	
	/**
	 * 外出回数計算
	 * @param recordClass
	 * @return 休憩外出回数
	 */
	public static BreakTimeGoOutTimes calcGoOutTimes(ManageReGetClass recordClass, ConditionAtr conditionAtr) {
		
		//控除時間帯一覧の取得
		List<TimeSheetOfDeductionItem> list = new ArrayList<>();
		//就業時間内時間帯
		WithinWorkTimeSheet withinWorkTimeSheet = recordClass.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get();
		for(WithinWorkTimeFrame withinWorkTimeFrame:withinWorkTimeSheet.getWithinWorkTimeFrame()) {
			list.addAll(withinWorkTimeFrame.getDedTimeSheetByAtr(DeductionAtr.Appropriate, conditionAtr));
			//遅刻
			if(withinWorkTimeFrame.getLateTimeSheet().isPresent()&&withinWorkTimeFrame.getLateTimeSheet().get().getForDeducationTimeSheet().isPresent()) {
				list.addAll(withinWorkTimeFrame.getLateTimeSheet().get().getForDeducationTimeSheet().get().getDedTimeSheetByAtr(DeductionAtr.Appropriate, conditionAtr));
			}
			//早退
			if(withinWorkTimeFrame.getLeaveEarlyTimeSheet().isPresent()&&withinWorkTimeFrame.getLeaveEarlyTimeSheet().get().getForDeducationTimeSheet().isPresent()) {
				list.addAll(withinWorkTimeFrame.getLeaveEarlyTimeSheet().get().getForDeducationTimeSheet().get().getDedTimeSheetByAtr(DeductionAtr.Appropriate, conditionAtr));
			}
		}
		//就業時間外時間帯
		OutsideWorkTimeSheet outsideWorkTimeSheet = recordClass.getCalculationRangeOfOneDay().getOutsideWorkTimeSheet().get();
		//残業
		if(outsideWorkTimeSheet.getOverTimeWorkSheet().isPresent()) {
			for(OverTimeFrameTimeSheetForCalc overTimeFrameTimeSheetForCalc:outsideWorkTimeSheet.getOverTimeWorkSheet().get().getFrameTimeSheets()) {
				list.addAll(overTimeFrameTimeSheetForCalc.getDedTimeSheetByAtr(DeductionAtr.Appropriate, conditionAtr));
			}
		}
		//休出
		if(outsideWorkTimeSheet.getHolidayWorkTimeSheet().isPresent()) {
			for(HolidayWorkFrameTimeSheetForCalc holidayWorkFrameTimeSheetForCalc:outsideWorkTimeSheet.getHolidayWorkTimeSheet().get().getWorkHolidayTime()) {
				list.addAll(holidayWorkFrameTimeSheetForCalc.getDedTimeSheetByAtr(DeductionAtr.Appropriate, conditionAtr));
			}
		}
		
		List<TimeSheetOfDeductionItem> result = new ArrayList<>();
		for(TimeSheetOfDeductionItem timeSheetOfDeductionItem:list){
			//時間の計算
			if(timeSheetOfDeductionItem.calcTotalTime().greaterThan(0)) {
				result.add(timeSheetOfDeductionItem);
			}
		}
		return new BreakTimeGoOutTimes(result.size());
	}
	
	/**
	 * 外出時間の計算
	 * @param oneDay 1日の計算範囲
	 * @param dedAtr 控除区分
	 * @param outingOfDaily 外出時間帯
	 * @param flexCalcSet フレックス計算設定
	 * @param premiumAtr 割増区分
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param commonSetting 就業時間帯の共通設定
	 * @return 外出合計時間
	 */
	private static OutingTotalTime calcOutingTime(
			CalculationRangeOfOneDay oneDay,
			DeductionAtr dedAtr,
			OutingTimeSheet outingOfDaily,
			Optional<FlexCalcSetting> flexCalcSet,
			PremiumAtr premiumAtr,
			HolidayCalcMethodSet holidayCalcMethodSet,
			Optional<WorkTimezoneCommonSet> commonSetting) {
		//外出合計時間の計算
		DeductionTotalTime outingTotal = calculationDedBreakTime(dedAtr, oneDay,outingOfDaily,premiumAtr,holidayCalcMethodSet,commonSetting);
		//コア内と外を分けて計算するかどうか判定
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
		return OutingTotalTime.of(
				outingTotal.getTotalTime(),
				WithinOutingTotalTime.of(withinDedTime, TimeWithCalculation.sameTime(withinFlex), TimeWithCalculation.sameTime(excessFlex)),
				outingTotal.getExcessOfStatutoryTotalTime());
	}
	
	/**
	 * 合計時間算出
	 * @param dedAtr 控除区分
	 * @param oneDay 1日の計算範囲
	 * @param outingOfDaily 外出時間帯
	 * @param premiumAtr 割増区分
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param commonSetting 就業時間帯の共通設定
	 * @return 控除合計時間
	 */
	public static DeductionTotalTime calculationDedBreakTime(
			DeductionAtr dedAtr,
			CalculationRangeOfOneDay oneDay,
			OutingTimeSheet outingOfDaily,
			PremiumAtr premiumAtr,
			HolidayCalcMethodSet holidayCalcMethodSet,
			Optional<WorkTimezoneCommonSet> commonSetting) {
		return createDudAllTime(
				ConditionAtr.convertFromGoOutReason(outingOfDaily.getReasonForGoOut()),
				dedAtr,
				TimeSheetRoundingAtr.PerTimeSheet,
				oneDay,
				premiumAtr,
				holidayCalcMethodSet,
				commonSetting);
	}
	
	/**
	 * 控除合計時間の計算
	 * @param conditionAtr 条件
	 * @param dedAtr 控除区分
	 * @param pertimesheet 丸め区分(時間帯で丸めるかの区分)
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
		//所定内合計時間の計算
		val withinDedTime = oneDay.calcWithinTotalTime(conditionAtr,dedAtr,StatutoryAtr.Statutory,pertimesheet,premiumAtr,holidayCalcMethodSet,commonSetting);
		//所定外合計時間の計算
		val excessDedTime = oneDay.calcWithinTotalTime(conditionAtr,dedAtr,StatutoryAtr.Excess,pertimesheet,premiumAtr,holidayCalcMethodSet,commonSetting);
		//設定間休憩を取得
		
		//合計時間の計算&return 
		return DeductionTotalTime.of(
				withinDedTime.addMinutes(excessDedTime.getTime(),
				excessDedTime.getCalcTime()),
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
