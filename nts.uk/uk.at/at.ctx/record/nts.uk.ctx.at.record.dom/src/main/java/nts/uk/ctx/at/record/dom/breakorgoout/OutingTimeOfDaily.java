package nts.uk.ctx.at.record.dom.breakorgoout;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeSheet;
import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ConditionAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetRoundingAtr;
import nts.uk.ctx.at.record.dom.stamp.GoOutReason;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.StatutoryAtr;

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
	private DeductionTotalTime recordTotalTime;
	//控除用合計時間：外出合計時間
	private DeductionTotalTime deductionTotalTime;
	//補正後時間帯:外出時間帯(List)
	private List<OutingTimeSheet> outingTimeSheets;
	
	
	/**
	 * Constcutor
	 */
	public OutingTimeOfDaily(BreakTimeGoOutTimes workTime, GoOutReason reason,
			TimevacationUseTimeOfDaily timeVacationUseOfDaily, DeductionTotalTime recordTotalTime,
			DeductionTotalTime deductionTotalTime, List<OutingTimeSheet> outingTimeSheets) {
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
	 * @return
	 */
	public static OutingTimeOfDaily calcOutingTime(CalculationRangeOfOneDay oneDay,boolean isCalculatable) {
		BreakTimeGoOutTimes goOutTimes = new BreakTimeGoOutTimes(0);
		//休暇使用時間
		TimevacationUseTimeOfDaily useVacationTime = new TimevacationUseTimeOfDaily(new AttendanceTime(0),
																					new AttendanceTime(0),
																					new AttendanceTime(0),
																					new AttendanceTime(0));
		DeductionTotalTime recordTotalTime = DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)),
																   TimeWithCalculation.sameTime(new AttendanceTime(0)),
																   TimeWithCalculation.sameTime(new AttendanceTime(0))); 
		
		DeductionTotalTime dedTotalTime = DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)),
				   												TimeWithCalculation.sameTime(new AttendanceTime(0)),
				   												TimeWithCalculation.sameTime(new AttendanceTime(0)));
		//補正後時間帯
		List<OutingTimeSheet> correctedTimeSheet = new ArrayList<>();
		
		if(isCalculatable) {
			//回数
			//goOutTimes = new BreakTimeGoOutTimes(1);
			//休暇使用時間
			
			//計上用合計時間
			recordTotalTime = calcOutingTime(oneDay, DeductionAtr.Appropriate); 
			//控除用合計時間
			dedTotalTime = calcOutingTime(oneDay, DeductionAtr.Deduction);
			//補正後時間帯 
		}
		return new OutingTimeOfDaily(goOutTimes, 
									 GoOutReason.OFFICAL, 
									 useVacationTime, 
									 recordTotalTime, 
									 dedTotalTime,
									 correctedTimeSheet);
	}
	
	/**
	 * 外出時間の計算
	 * @param oneDay
	 * @return
	 */
	private static DeductionTotalTime calcOutingTime(CalculationRangeOfOneDay oneDay,DeductionAtr dedAtr) {
		//外出合計時間の計算
		//calculationDedBreakTime(dedAtr, oneDay);
		//就業時間帯を取得
		//コア内と害を分けて計算するかどうか判定
		//YES 所定内外出をコア内と外で分けて計算
		//控除合計時間を返す return
		return DeductionTotalTime.of (TimeWithCalculation.sameTime(new AttendanceTime(0)),
									  TimeWithCalculation.sameTime(new AttendanceTime(0)),
									  TimeWithCalculation.sameTime(new AttendanceTime(0)));
	}
//	/**
//	 *　合計時間算出
//	 * @param oneDay 
//	 * @return
//	 */
//	public static DeductionTotalTime calculationDedBreakTime(DeductionAtr dedAtr, CalculationRangeOfOneDay oneDay) {
//		return createDudAllTime(/*外出理由によってここは変える必要有*/,dedAtr,TimeSheetRoundingAtr.PerTimeSheet,oneDay);
//	}
	
	private static DeductionTotalTime createDudAllTime(ConditionAtr conditionAtr, DeductionAtr dedAtr,
			TimeSheetRoundingAtr pertimesheet, CalculationRangeOfOneDay oneDay) {
		//所定内
		val withinDedTime = oneDay.calcWithinTotalTime(conditionAtr,dedAtr,StatutoryAtr.Statutory,pertimesheet);
		//所定外
		val excessDedTime = oneDay.calcWithinTotalTime(conditionAtr,dedAtr,StatutoryAtr.Excess,pertimesheet);
		//設定間休憩
		
		//合計計算&return 
		return DeductionTotalTime.of(withinDedTime.addMinutes(excessDedTime.getTime(), excessDedTime.getCalcTime()),
									  withinDedTime,
									  excessDedTime);
	}
	
}
