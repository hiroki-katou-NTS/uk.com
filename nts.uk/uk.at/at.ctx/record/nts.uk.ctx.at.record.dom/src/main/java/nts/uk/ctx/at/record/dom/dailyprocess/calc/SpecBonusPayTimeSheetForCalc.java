package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.MidNightTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.SpecBonusPayNumber;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 計算用特定加給時間帯 
 * @author keisuke_hoshina
 *
 */
@Getter
public class SpecBonusPayTimeSheetForCalc extends CalculationTimeSheet{
	
	private SpecBonusPayNumber specBonusPayNumber;

	/**
	 * Constructor 
	 */
	public SpecBonusPayTimeSheetForCalc(TimeZoneRounding timeSheet, TimeSpanForCalc calcrange,
			List<TimeSheetOfDeductionItem> recorddeductionTimeSheets,
			List<TimeSheetOfDeductionItem> deductionTimeSheets, List<BonusPayTimeSheetForCalc> bonusPayTimeSheet,
			List<SpecBonusPayTimeSheetForCalc> specifiedBonusPayTimeSheet, Optional<MidNightTimeSheetForCalc> midNighttimeSheet,
			SpecBonusPayNumber specBonusPayNumber) {
		super(timeSheet, calcrange, recorddeductionTimeSheets, deductionTimeSheets, bonusPayTimeSheet,
				specifiedBonusPayTimeSheet, midNighttimeSheet);
		this.specBonusPayNumber = specBonusPayNumber;
	}
	
	/**
	 * 特定加給時間帯を計算用特定加給時間帯に変換する
	 * @return　計算用加給時間帯
	 */
	public static SpecBonusPayTimeSheetForCalc convertForCalc(SpecBonusPayTimesheet bonusPayTimeSheet) {
		return new SpecBonusPayTimeSheetForCalc(new TimeZoneRounding(new TimeWithDayAttr(bonusPayTimeSheet.getStartTime().valueAsMinutes()),
																 new TimeWithDayAttr(bonusPayTimeSheet.getEndTime().valueAsMinutes()), 
																 null),
											new TimeSpanForCalc(new TimeWithDayAttr(bonusPayTimeSheet.getStartTime().valueAsMinutes()),
													 			new TimeWithDayAttr(bonusPayTimeSheet.getEndTime().valueAsMinutes())),
											Collections.emptyList(),
											Collections.emptyList(),
											Collections.emptyList(),
											Collections.emptyList(),
											Optional.empty(),
											new SpecBonusPayNumber(BigDecimal.valueOf((long)bonusPayTimeSheet.getTimeItemId())));
	}
	
	/**
	 * 受け取った計算範囲に補正しつつ加給時間帯を計算用加給時間帯に変換する
	 * @return　計算用加給時間帯
	 */
	public SpecBonusPayTimeSheetForCalc convertForCalcCorrectRange(TimeSpanForCalc timeSpan) {
		return new SpecBonusPayTimeSheetForCalc(new TimeZoneRounding(timeSpan.getStart(),
																 timeSpan.getEnd(), 
																 null),
											timeSpan,
											Collections.emptyList(),
											Collections.emptyList(),
											Collections.emptyList(),
											Collections.emptyList(),
											Optional.empty(),
											this.specBonusPayNumber);
	}

	/**
	 * 指定時間を内包しているか判定する
	 * @param 指定時間
	 * @return 内包している
	 */
	public boolean contains(TimeWithDayAttr baseTime) {
		return this.calcrange.getStart().lessThan(baseTime) && this.calcrange.getEnd().greaterThan(baseTime);
	}
	
	/**
	 * 終了時間と基準時間の早い方の時間を取得する
	 * @param basePoint　基準時間
	 * @return 時刻が早い方
	 */
	public TimeSpanForCalc decisionNewSpan(TimeSpanForCalc timeSpan,TimeWithDayAttr baseTime,boolean isDateBefore) {
		if(isDateBefore) {
			return new TimeSpanForCalc(timeSpan.getStart(),baseTime);
		}
		else {
			return new TimeSpanForCalc(baseTime,timeSpan.getEnd());
		}
	}
	
	public AttendanceTime testSAIKI(DeductionAtr dedAtr,ConditionAtr conditionAtr) {
		//計上or控除用かを判断する
		val dedList = dedAtr.isDeduction()?this.deductionTimeSheet:this.recordedTimeSheet;
		//自分が持つ集計対象の時間帯の合計
		val includeForcsValue = super.forcs(conditionAtr, dedAtr);
		//自分自身が集計対象外の場合、自分自身が持つ集計対象の時間帯の合計時間のみを返す
		return includeForcsValue;
	}
}
