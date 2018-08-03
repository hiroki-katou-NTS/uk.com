package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.MidNightTimeSheetForCalc;
import nts.uk.ctx.at.record.dom.raisesalarytime.primitivevalue.RaisingSalaryTimeItemNo;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 計算用加給時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class BonusPayTimeSheetForCalc extends CalculationTimeSheet{

	//sheet id
	private RaisingSalaryTimeItemNo raiseSalaryTimeItemNo;
	
	/**
	 *  Constructor
	 */ 
	public BonusPayTimeSheetForCalc(TimeZoneRounding timeSheet, TimeSpanForCalc calcrange,
			List<TimeSheetOfDeductionItem> recorddeductionTimeSheets,
			List<TimeSheetOfDeductionItem> deductionTimeSheets, List<BonusPayTimeSheetForCalc> bonusPayTimeSheet,
			List<SpecBonusPayTimeSheetForCalc> specifiedBonusPayTimeSheet, Optional<MidNightTimeSheetForCalc> midNighttimeSheet,
			RaisingSalaryTimeItemNo raiseSalaryTimeItemNo) {
		super(timeSheet, calcrange, recorddeductionTimeSheets, deductionTimeSheets, bonusPayTimeSheet,
				specifiedBonusPayTimeSheet, midNighttimeSheet);
		this.raiseSalaryTimeItemNo = raiseSalaryTimeItemNo;
	}
	
	/**
	 * 加給時間帯を計算用加給時間帯に変換する
	 * @return　計算用加給時間帯
	 */
	public static BonusPayTimeSheetForCalc convertForCalc(BonusPayTimesheet bonusPayTimeSheet) {
		return new BonusPayTimeSheetForCalc(new TimeZoneRounding(new TimeWithDayAttr(bonusPayTimeSheet.getStartTime().valueAsMinutes()),
																 new TimeWithDayAttr(bonusPayTimeSheet.getEndTime().valueAsMinutes()), 
																 null),
											new TimeSpanForCalc(new TimeWithDayAttr(bonusPayTimeSheet.getStartTime().valueAsMinutes()),
													 			new TimeWithDayAttr(bonusPayTimeSheet.getEndTime().valueAsMinutes())),
											Collections.emptyList(),
											Collections.emptyList(),
											Collections.emptyList(),
											Collections.emptyList(),
											Optional.empty(),
											new RaisingSalaryTimeItemNo(BigDecimal.valueOf((long)bonusPayTimeSheet.getTimeItemId())));
	}
	
	/**
	 * 受け取った計算範囲に補正しつつ加給時間帯を計算用加給時間帯に変換する
	 * @return　計算用加給時間帯
	 */
	public BonusPayTimeSheetForCalc convertForCalcCorrectRange(TimeSpanForCalc timeSpan) {
		return new BonusPayTimeSheetForCalc(new TimeZoneRounding(timeSpan.getStart(),
																 timeSpan.getEnd(), 
																 null),
											timeSpan,
											Collections.emptyList(),
											Collections.emptyList(),
											Collections.emptyList(),
											Collections.emptyList(),
											Optional.empty(),
											this.raiseSalaryTimeItemNo);
	}
	
	public AttendanceTime testSAIKI(DeductionAtr dedAtr,ConditionAtr conditionAtr) {
		//自分が持つ集計対象の時間帯の合計
		val includeForcsValue = super.forcs(conditionAtr, dedAtr);
		//自分自身が集計対象外の場合、自分自身が持つ集計対象の時間帯の合計時間のみを返す
		return includeForcsValue;
	}
	
}

