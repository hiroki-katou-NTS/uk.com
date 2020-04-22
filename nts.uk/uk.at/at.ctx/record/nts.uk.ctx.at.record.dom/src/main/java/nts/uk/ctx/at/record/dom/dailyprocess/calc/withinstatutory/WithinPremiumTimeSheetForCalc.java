package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ActualWorkingTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;

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
}
