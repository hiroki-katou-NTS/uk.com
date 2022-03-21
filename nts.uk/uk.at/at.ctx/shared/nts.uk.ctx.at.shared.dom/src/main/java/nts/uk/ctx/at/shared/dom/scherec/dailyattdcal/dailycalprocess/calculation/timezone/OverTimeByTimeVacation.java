package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeFrame;

/**
 * 休暇加算によって就業時間から溢れて残業になる時間帯
 * @author daiki_ichioka
 */
public class OverTimeByTimeVacation extends CalculationTimeSheet {

	private OverTimeByTimeVacation(TimeSpanForDailyCalc timeSheet, TimeRoundingSetting rounding,
			List<TimeSheetOfDeductionItem> recorddeductionTimeSheets, List<TimeSheetOfDeductionItem> deductionTimeSheets) {
		super(timeSheet, rounding, recorddeductionTimeSheets, deductionTimeSheets);
	}
	
	/**
	 * 作る
	 * @param within
	 * @return 休暇加算によって就業時間から溢れて残業になる時間帯
	 */
	public static OverTimeByTimeVacation create(WithinWorkTimeFrame within) {
		//就業時間内時間枠の終了～早退時間帯の開始(丸め後)
		TimeSpanForDailyCalc timeSheet = new TimeSpanForDailyCalc(
				within.getTimeSheet().getEnd(),
				within.getLeaveEarlyTimeSheet().flatMap(
						l -> l.getForDeducationTimeSheet()).map(d -> d.getAfterRoundingAsLeaveEarly().getStart()).orElse(within.getTimeSheet().getEnd()));
		if(timeSheet.isReverse()) {
			timeSheet = new TimeSpanForDailyCalc(timeSheet.getStart(), timeSheet.getStart());
		}
		OverTimeByTimeVacation result = new OverTimeByTimeVacation(
				timeSheet,
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				within.recordedTimeSheet.stream().map(r -> r.clone()).collect(Collectors.toList()),
				within.deductionTimeSheet.stream().map(d -> d.clone()).collect(Collectors.toList()));
		result.shiftTimeSheet(timeSheet);
		return result;
	}
}
