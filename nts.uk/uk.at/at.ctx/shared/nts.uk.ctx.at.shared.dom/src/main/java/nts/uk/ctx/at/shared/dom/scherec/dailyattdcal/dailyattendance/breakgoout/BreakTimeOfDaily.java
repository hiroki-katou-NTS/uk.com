package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.DeductionTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSheetRoundingAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;

/**
 * 日別実績の休憩時間
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.休憩・外出.日別勤怠の休憩時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class BreakTimeOfDaily {
	/** 計上用合計時間: 控除合計時間 */
	private DeductionTotalTime toRecordTotalTime;
	/** 控除用合計時間: 控除合計時間 */
	private DeductionTotalTime deductionTotalTime;
	/** 休憩回数: 休憩外出回数 */
	private BreakTimeGoOutTimes gooutTimes;
	/** 勤務間時間: 勤怠時間 */
	private AttendanceTime workTime;
	/** 補正後時間帯: 休憩時間帯 */
	private List<BreakTimeSheet> breakTimeSheet;
	
	
	/**
	 * Constructor
	 * @param record 計上用
	 * @param deduction 控除用
	 */
	private BreakTimeOfDaily(DeductionTotalTime record,DeductionTotalTime deduction) {
		this.toRecordTotalTime = record;
		this.deductionTotalTime = deduction;
	}
	
	public BreakTimeOfDaily(DeductionTotalTime toRecordTotalTime, DeductionTotalTime deductionTotalTime,
			BreakTimeGoOutTimes gooutTimes, AttendanceTime workTime, List<BreakTimeSheet> breakTimeSheet) {
		super();
		this.toRecordTotalTime = toRecordTotalTime;
		this.deductionTotalTime = deductionTotalTime;
		this.gooutTimes = gooutTimes;
		this.workTime = workTime;
		this.breakTimeSheet = breakTimeSheet;
	}
	
	/**
	 * 控除、計上用両方を受け取った時間に入れ替える
	 * @return 日別実績の休憩時間
	 */
	public static BreakTimeOfDaily sameTotalTime(DeductionTotalTime deductionTime) {
		return new BreakTimeOfDaily(deductionTime,deductionTime);
	}
	
	/**
	 * 全ての休憩時間を算出する指示クラス
	 * アルゴリズム：日別実績の休憩時間
	 * @param oneDay 1日の計算範囲
	 * @param breakTimeCount 休憩回数
	 * @param boolean 計算処理に入ることができるかフラグ
	 * @return 日別実績の休憩時間
	 */
	public static BreakTimeOfDaily calcTotalBreakTime(CalculationRangeOfOneDay oneDay, int breakTimeCount, Boolean isCalculatable,PremiumAtr premiumAtr,HolidayCalcMethodSet holidayCalcMethodSet,Optional<WorkTimezoneCommonSet> commonSetting) {
		DeductionTotalTime recordCalcTime = DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)));
		DeductionTotalTime dedCalcTime =  DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)), TimeWithCalculation.sameTime(new AttendanceTime(0)));
		BreakTimeGoOutTimes goOutTimes = new BreakTimeGoOutTimes(0);
		AttendanceTime duringTime = new AttendanceTime(0);
		List<BreakTimeSheet> breakTimeSheets = Collections.emptyList();
		if(isCalculatable) {
			//計上用計算時間
			recordCalcTime = calculationDedBreakTime(DeductionAtr.Appropriate,oneDay,premiumAtr,holidayCalcMethodSet,commonSetting);
			//控除用計算時間
			dedCalcTime = calculationDedBreakTime(DeductionAtr.Deduction,oneDay,premiumAtr,holidayCalcMethodSet,commonSetting);
			//休憩回数
			goOutTimes = new BreakTimeGoOutTimes(breakTimeCount);
			//勤務間時間
			duringTime = new AttendanceTime(0);
			//補正後時間帯
			breakTimeSheets = new ArrayList<>();
		}
		return new BreakTimeOfDaily(recordCalcTime,dedCalcTime,goOutTimes,duringTime,breakTimeSheets);
	}

	/**
	 * 休憩合計時間の計算
	 * @param dedAtr 控除区分
	 * @param oneDay 1日の計算範囲
	 * @param premiumAtr 割増区分
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param commonSetting 就業時間帯の共通設定
	 * @return 控除合計時間
	 */
	public static DeductionTotalTime calculationDedBreakTime(
			DeductionAtr dedAtr,
			CalculationRangeOfOneDay oneDay,
			PremiumAtr premiumAtr,
			HolidayCalcMethodSet holidayCalcMethodSet,
			Optional<WorkTimezoneCommonSet> commonSetting) {
		return createDudAllTime(ConditionAtr.BREAK,dedAtr,TimeSheetRoundingAtr.PerTimeSheet,oneDay,premiumAtr,holidayCalcMethodSet,commonSetting);
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
		val withinDedTime = oneDay.calcWithinTotalTime(conditionAtr,dedAtr,StatutoryAtr.Statutory,pertimesheet,premiumAtr,holidayCalcMethodSet,commonSetting);
		val excessDedTime = oneDay.calcWithinTotalTime(conditionAtr,dedAtr,StatutoryAtr.Excess,pertimesheet,premiumAtr,holidayCalcMethodSet,commonSetting);
		return DeductionTotalTime.of(withinDedTime.addMinutes(excessDedTime.getTime(), excessDedTime.getCalcTime()), withinDedTime, excessDedTime);
	}
	
	/**
	 * 休憩未使用時間の計算
	 * @param fixRestTimezoneSet 固定勤務の休憩時間帯
	 * @param fixWoSetting 就業時間の時間帯設定（固定勤務）
	 * @param timeLeavingOfDailyPerformance 日別実績の出退勤 
	 * @return 休憩未使用時間
	 */
	public AttendanceTime calcUnUseBrekeTime(
			FixRestTimezoneSet fixRestTimezoneSet,
			List<EmTimeZoneSet> fixWoSetting,
			TimeLeavingOfDailyAttd timeLeavingOfDailyPerformance) {
		
		//実績の休憩時間を取得
		val recordTotalTime = this.getToRecordTotalTime().getWithinStatutoryTotalTime();
		//出退勤リスト取得
		val timeSpans = timeLeavingOfDailyPerformance.getTimeLeavingWorks().stream().map(tc -> tc.getTimespan()).collect(Collectors.toList());
		//就業時間帯の時間帯取得
		val workSpans = fixWoSetting.stream().map(tc -> tc.getTimezone().getTimeSpan()).collect(Collectors.toList());
		//出退勤、就業時間帯の時間帯、休憩時間帯から就業時間帯の休憩時間を算出する
		val totalBreakTime = fixRestTimezoneSet.calcTotalTimeDuplicatedAttLeave(timeSpans, workSpans);
		
		return totalBreakTime.minusMinutes(recordTotalTime.getCalcTime().valueAsMinutes());
	}
}
