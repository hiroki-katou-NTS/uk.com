package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 深夜時間帯
 * @author keisuke_hoshina
 *
 */
public class MidNightTimeSheetForCalc extends CalculationTimeSheet{

	public MidNightTimeSheetForCalc(TimeSpanForDailyCalc timeSheet, TimeRoundingSetting rounding, List<TimeSheetOfDeductionItem> recorddeductionSheets,List<TimeSheetOfDeductionItem> deductionSheets) {
		super(timeSheet, rounding, recorddeductionSheets,deductionSheets);
	}
	
	
	public MidNightTimeSheetForCalc replaceTime(TimeSpanForCalc timeSpan) {
		return new MidNightTimeSheetForCalc(new TimeSpanForDailyCalc(timeSpan.getStart(), timeSpan.getEnd()),
											this.rounding,
											this.recordedTimeSheet,
											this.deductionTimeSheet);
	}
	
	
	public boolean contains(TimeWithDayAttr baseTime) {
		return ((CalculationTimeSheet)this).getTimeSheet().contains(baseTime);
	}
	/**
	 * 終了時間と基準時間の早い方の時間を取得する
	 * @param basePoint　基準時間
	 * @return 時刻が早い方
	 */
	public TimeSpanForDailyCalc decisionNewSpan(TimeSpanForDailyCalc timeSpan,TimeWithDayAttr baseTime,boolean isDateBefore) {
		if(isDateBefore) {
			val endOclock = timeSpan.getEnd().lessThan(baseTime) ? timeSpan.getEnd() : baseTime; 
			return new TimeSpanForDailyCalc(timeSpan.getStart(),endOclock);
		}
		else {
			val startOclock = baseTime.lessThan(timeSpan.getStart()) ? timeSpan.getStart() : baseTime ;
			return new TimeSpanForDailyCalc(startOclock,timeSpan.getEnd());
		}
		
	}
	/**
	 * 再帰中に自分自身を作り直す処理
	 * @param baseTime
	 * @return
	 */
	public Optional<MidNightTimeSheetForCalc> reCreateOwn(TimeWithDayAttr baseTime,boolean isDateBefore) {
		List<TimeSheetOfDeductionItem> deductionTimeSheets = this.recreateDeductionItemBeforeBase(baseTime,isDateBefore,DeductionAtr.Appropriate);
		List<TimeSheetOfDeductionItem> recordTimeSheets = this.recreateDeductionItemBeforeBase(baseTime,isDateBefore,DeductionAtr.Deduction);
		TimeSpanForDailyCalc renewSpan = decisionNewSpan(this.timeSheet,baseTime,isDateBefore);
		return Optional.of(new MidNightTimeSheetForCalc(renewSpan,this.rounding,deductionTimeSheets,recordTimeSheets));
	}
	
	
	
	/**
	 * 深夜時間帯から計算用時間帯への変更
	 * @param midNightTimeSheet 深夜時間帯
	 * @return 計算用深夜時間帯
	 */
	public static MidNightTimeSheetForCalc convertForCalc(MidNightTimeSheet midNightTimeSheet,Optional<WorkTimezoneCommonSet> commonSetting) {
		TimeRoundingSetting timeRoundingSetting = commonSetting.isPresent()?commonSetting.get().getLateNightTimeSet().getRoundingSetting():new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN,Rounding.ROUNDING_DOWN);
		return new MidNightTimeSheetForCalc(new TimeSpanForDailyCalc(midNightTimeSheet.getStart(), midNightTimeSheet.getEnd()),
											timeRoundingSetting, 
											Collections.emptyList(), 
											Collections.emptyList());
	}
	
	/**
	 * 重複範囲の時間帯を持つ計算用深夜時間帯に作り直す
	 * @param timeSpan　重複を調べたい時間帯
	 * @return　重複範囲の時間帯深夜時間帯
	 */
	public Optional<MidNightTimeSheetForCalc> getDuplicateRangeTimeSheet(TimeSpanForDailyCalc timeSpan) {
		
		val dupTimeSpan = timeSpan.getDuplicatedWith(this.timeSheet);
		if(dupTimeSpan.isPresent()) {
			return Optional.of(new MidNightTimeSheetForCalc(dupTimeSpan.get(), 
											this.rounding, 
											this.recordedTimeSheet, 
											this.deductionTimeSheet));
		}
		else {
			return Optional.empty();
		}
	}
	
	public AttendanceTime testSAIKI(DeductionAtr dedAtr,ConditionAtr conditionAtr) {
		//自分が持つ集計対象の時間帯の合計
		val includeForcsValue = super.forcs(conditionAtr, dedAtr);
		//自分自身が集計対象外の場合、自分自身が持つ集計対象の時間帯の合計時間のみを返す
		return includeForcsValue;
	}
}
