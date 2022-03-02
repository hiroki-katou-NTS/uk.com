package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ActualWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;

/**
 * 計算用所定内割増時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class WithinPremiumTimeSheetForCalc extends ActualWorkingTimeSheet implements Cloneable{
	//時間帯
	private TimeSpanForDailyCalc withinPremiumtimeSheet;
	
	/**
	 * Constructor
	 * @param withinPremiumtimeSheet
	 */
	public WithinPremiumTimeSheetForCalc(TimeSpanForDailyCalc timeSheet, TimeRoundingSetting rounding) {
		super(timeSheet, rounding);
		this.withinPremiumtimeSheet = timeSheet;
	}
	
	/**
	 * 重複する時間帯で作り直す
	 * @param timeSpan 時間帯
	 */
	public Optional<WithinPremiumTimeSheetForCalc> recreateWithDuplicate(TimeSpanForDailyCalc timeSpan) {
		Optional<TimeSpanForDailyCalc> duplicate = this.withinPremiumtimeSheet.getDuplicatedWith(timeSheet);
		if(!duplicate.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(new WithinPremiumTimeSheetForCalc(duplicate.get(), this.rounding.clone()));
	}
	
	public WithinPremiumTimeSheetForCalc clone() {
		WithinPremiumTimeSheetForCalc clone = new WithinPremiumTimeSheetForCalc(this.timeSheet, this.rounding);
		try {
			clone.timeSheet = this.timeSheet.clone();
			clone.rounding = this.rounding.clone();
		}
		catch (Exception e) {
			throw new RuntimeException("WithinPremiumTimeSheetForCalc clone error.");
		}
		return clone;
	}
}
