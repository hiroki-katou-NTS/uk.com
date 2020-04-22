package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.MidNightTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSpanForDailyCalc;

/**
 * 実働時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
@Setter
public abstract class ActualWorkingTimeSheet extends CalculationTimeSheet{

	//時間休暇溢れ時間
	protected Optional<AttendanceTime> timeVacationOverflowTime = Optional.empty();
	
	public ActualWorkingTimeSheet(TimeSpanForDailyCalc timeSheet, TimeRoundingSetting rounding) {
		super(timeSheet, rounding);
	}
	
	public ActualWorkingTimeSheet(
			TimeSpanForDailyCalc timeSheet,
			TimeRoundingSetting rounding,
			List<TimeSheetOfDeductionItem> recorddeductionTimeSheets,
			List<TimeSheetOfDeductionItem> deductionTimeSheets,
			List<BonusPayTimeSheetForCalc> bonusPayTimeSheet,
			List<SpecBonusPayTimeSheetForCalc> specifiedBonusPayTimeSheet,
			Optional<MidNightTimeSheetForCalc> midNighttimeSheet) {
		
		super(timeSheet, rounding, recorddeductionTimeSheets,deductionTimeSheets,bonusPayTimeSheet,specifiedBonusPayTimeSheet,midNighttimeSheet);
	}
	
	public AttendanceTime calcTotalTime() {
		return super.calcTotalTime().addMinutes(timeVacationOverflowTime.orElse(AttendanceTime.ZERO).valueAsMinutes());
	}
}
