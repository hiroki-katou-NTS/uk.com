package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.addition.additionsetting.paytimeframe.SpecBonusPayNumber;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 計算用特定加給時間帯 
 * @author keisuke_hoshina
 *
 */
@Getter
public class SpecBonusPayTimeSheetForCalc extends CalculationTimeSheet{
	//特定日加給時間No
	private SpecBonusPayNumber specBonusPayNumber;

	/**
	 * Constructor 
	 */
	public SpecBonusPayTimeSheetForCalc(TimeSpanForDailyCalc timeSheet, TimeRoundingSetting rounding,
			List<TimeSheetOfDeductionItem> recorddeductionTimeSheets,
			List<TimeSheetOfDeductionItem> deductionTimeSheets,
			SpecBonusPayNumber specBonusPayNumber) {
		super(timeSheet, rounding, recorddeductionTimeSheets, deductionTimeSheets);
		this.specBonusPayNumber = specBonusPayNumber;
	}
	
	/**
	 * 特定加給時間帯を計算用特定加給時間帯に変換する
	 * @return　計算用加給時間帯
	 */
	public static SpecBonusPayTimeSheetForCalc convertForCalc(SpecBonusPayTimesheet bonusPayTimeSheet) {
		return new SpecBonusPayTimeSheetForCalc(new TimeSpanForDailyCalc(new TimeWithDayAttr(bonusPayTimeSheet.getStartTime().valueAsMinutes()),
													 			new TimeWithDayAttr(bonusPayTimeSheet.getEndTime().valueAsMinutes())),
											new TimeRoundingSetting(bonusPayTimeSheet.getRoundingTimeAtr().value,bonusPayTimeSheet.getRoundingAtr().value),
											Collections.emptyList(),
											Collections.emptyList(),
											new SpecBonusPayNumber(BigDecimal.valueOf((long)bonusPayTimeSheet.getTimeItemId())));
	}
	
	/**
	 * 受け取った計算範囲に補正しつつ加給時間帯を計算用加給時間帯に変換する
	 * @return　計算用加給時間帯
	 */
	public SpecBonusPayTimeSheetForCalc convertForCalcCorrectRange(TimeSpanForDailyCalc timeSpan) {
		return new SpecBonusPayTimeSheetForCalc(timeSpan,
											this.rounding,
											Collections.emptyList(),
											Collections.emptyList(),
											this.specBonusPayNumber);
	}

//	/**
//	 * 指定時間を内包しているか判定する
//	 * @param 指定時間
//	 * @return 内包している
//	 */
//	public boolean contains(TimeWithDayAttr baseTime) {
//		return this.calcrange.getStart().lessThan(baseTime) && this.calcrange.getEnd().greaterThan(baseTime);
//	}
	
//	/**
//	 * 終了時間と基準時間の早い方の時間を取得する
//	 * @param basePoint　基準時間
//	 * @return 時刻が早い方
//	 */
//	public TimeSpanForDailyCalc decisionNewSpan(TimeSpanForDailyCalc timeSpan,TimeWithDayAttr baseTime,boolean isDateBefore) {
//		if(isDateBefore) {
//			return new TimeSpanForDailyCalc(timeSpan.getStart(),baseTime);
//		}
//		else {
//			return new TimeSpanForDailyCalc(baseTime,timeSpan.getEnd());
//		}
//	}

	/**
	 * 自身の計算範囲との重複範囲の取得
	 * @param timeSpan 時間帯
	 * @return 重複範囲
	 */
	public Optional<SpecBonusPayTimeSheetForCalc> createDuplicateRange(TimeSpanForDailyCalc timeSpan) {
		//重複範囲取得
		val duplicateSpan = timeSpan.getDuplicatedWith(this.timeSheet);
		//重複有
		if(duplicateSpan.isPresent())
			return Optional.of(this.replaceTimeSpan(duplicateSpan));
		//重複無
		return Optional.empty();
	}

	
	/**
	 * 受けとった計算範囲で時間を入れ替える
	 * @param timeSpan　時間帯
	 * @return　控除項目の時間帯
	 */
	public SpecBonusPayTimeSheetForCalc replaceTimeSpan(Optional<TimeSpanForDailyCalc> timeSpan) {
		if(timeSpan.isPresent()) {
			return new SpecBonusPayTimeSheetForCalc(
											timeSpan.get(),
											this.rounding,
											this.recordedTimeSheet,
											this.deductionTimeSheet,
											this.getSpecBonusPayNumber()
											);
		}
		else {
			return new SpecBonusPayTimeSheetForCalc(
					this.timeSheet,
					this.rounding,
					this.recordedTimeSheet,
					this.deductionTimeSheet,
					this.getSpecBonusPayNumber()
					);
		}
	}
}
