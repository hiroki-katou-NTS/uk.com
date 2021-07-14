package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ActualWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;

/**
 * 計算用所定内割増時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class WithinPremiumTimeSheetForCalc extends ActualWorkingTimeSheet{
	//時間帯
	private TimeSpanForDailyCalc withinPremiumtimeSheet;
	
	/**
	 * Constructor
	 * @param withinPremiumtimeSheet
	 */
	public WithinPremiumTimeSheetForCalc(TimeSpanForDailyCalc timeSheet) {
		super(timeSheet, new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN));
		this.withinPremiumtimeSheet = timeSheet;
	}
	
	/**
	 * 所定内時間帯を指定した時間帯に絞り込む
	 * @param timeSpan 時間帯
	 */
	public void reduceRange(TimeSpanForDailyCalc timeSpan) {
		Optional<TimeSpanForDailyCalc> duplicates = this.withinPremiumtimeSheet.getDuplicatedWith(timeSheet);
		if(!duplicates.isPresent()) {
			return;
		}
		this.withinPremiumtimeSheet = duplicates.get();
	}
}
