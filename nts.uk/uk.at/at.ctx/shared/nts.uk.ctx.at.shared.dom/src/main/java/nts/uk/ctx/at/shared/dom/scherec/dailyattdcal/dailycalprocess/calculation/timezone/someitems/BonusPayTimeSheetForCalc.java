package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.someitems;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 計算用加給時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class BonusPayTimeSheetForCalc extends CalculationTimeSheet{

	//sheet id
	//加給時間項目No
	private RaisingSalaryTimeItemNo raiseSalaryTimeItemNo;
	
	/**
	 *  Constructor
	 */ 
	public BonusPayTimeSheetForCalc(TimeSpanForDailyCalc timeSheet, TimeRoundingSetting rounding,
			List<TimeSheetOfDeductionItem> recorddeductionTimeSheets,
			List<TimeSheetOfDeductionItem> deductionTimeSheets,
			RaisingSalaryTimeItemNo raiseSalaryTimeItemNo) {
		super(timeSheet, rounding, recorddeductionTimeSheets, deductionTimeSheets);
		this.raiseSalaryTimeItemNo = raiseSalaryTimeItemNo;
	}
	
	/**
	 * 加給時間帯を計算用加給時間帯に変換する
	 * @return　計算用加給時間帯
	 */
	public static BonusPayTimeSheetForCalc convertForCalc(BonusPayTimesheet bonusPayTimeSheet) {
		return new BonusPayTimeSheetForCalc(new TimeSpanForDailyCalc(new TimeWithDayAttr(bonusPayTimeSheet.getStartTime().valueAsMinutes()),
											new TimeWithDayAttr(bonusPayTimeSheet.getEndTime().valueAsMinutes())),
											new TimeRoundingSetting(bonusPayTimeSheet.getRoundingTimeAtr().value,bonusPayTimeSheet.getRoundingAtr().value),
											Collections.emptyList(),
											Collections.emptyList(),
											new RaisingSalaryTimeItemNo(BigDecimal.valueOf((long)bonusPayTimeSheet.getTimeItemId())));
	}
	
	/**
	 * 受け取った計算範囲に補正しつつ加給時間帯を計算用加給時間帯に変換する
	 * @return　計算用加給時間帯
	 */
	public BonusPayTimeSheetForCalc convertForCalcCorrectRange(TimeSpanForDailyCalc timeSpan) {
		return new BonusPayTimeSheetForCalc(timeSpan,
											this.getRounding(),
											Collections.emptyList(),
											Collections.emptyList(),
											this.raiseSalaryTimeItemNo);
	}

	public Optional<BonusPayTimeSheetForCalc> createDuplicateRange(TimeSpanForDailyCalc timeSpan) {
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
	public BonusPayTimeSheetForCalc replaceTimeSpan(Optional<TimeSpanForDailyCalc> timeSpan) {
		if(timeSpan.isPresent()) {
			return new BonusPayTimeSheetForCalc(
											timeSpan.get(),
											this.rounding,
											this.recordedTimeSheet,
											this.deductionTimeSheet,
											this.getRaiseSalaryTimeItemNo()
											);
		}
		else {
			return new BonusPayTimeSheetForCalc(
					this.timeSheet,
					this.rounding,
					this.recordedTimeSheet,
					this.deductionTimeSheet,
					this.getRaiseSalaryTimeItemNo()
					);
		}
	}
	
}

