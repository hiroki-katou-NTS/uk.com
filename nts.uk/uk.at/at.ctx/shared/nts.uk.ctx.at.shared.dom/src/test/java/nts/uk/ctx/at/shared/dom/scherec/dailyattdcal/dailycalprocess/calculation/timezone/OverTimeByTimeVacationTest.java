package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.BreakClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.WorkingBreakTimeAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.LateLeaveEarlyTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.LeaveEarlyTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeFrame;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class OverTimeByTimeVacationTest {

	@Test
	public void create_leaveEarly_30MIN_UP() {
		WithinWorkTimeFrame input = Helper.createWithin(
				new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(8, 30), TimeWithDayAttr.hourMinute(16, 30)), //就業時間内時間枠 8:30～16:30
				Optional.of(new LateLeaveEarlyTimeSheet(
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(17, 10), TimeWithDayAttr.hourMinute(17, 30)), //早退時間帯 17:10～17:30
						new TimeRoundingSetting(Unit.ROUNDING_TIME_30MIN, Rounding.ROUNDING_UP)))); //早退丸め 30分切り上げ
		OverTimeByTimeVacation result = OverTimeByTimeVacation.create(input);
		assertThat(result.getTimeSheet().getStart()).isEqualTo(TimeWithDayAttr.hourMinute(16, 30));
		assertThat(result.getTimeSheet().getEnd()).isEqualTo(TimeWithDayAttr.hourMinute(17, 0));
	}
	
	@Test
	public void create_notLeaveEarly() {
		WithinWorkTimeFrame input = Helper.createWithin(
				new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(8, 30), TimeWithDayAttr.hourMinute(17, 30)), //就業時間内時間枠 8:30～17:30
				Optional.empty()); //早退時間帯 なし
		OverTimeByTimeVacation result = OverTimeByTimeVacation.create(input);
		assertThat(result.getTimeSheet().getStart()).isEqualTo(TimeWithDayAttr.hourMinute(17, 30));
		assertThat(result.getTimeSheet().getEnd()).isEqualTo(TimeWithDayAttr.hourMinute(17, 30));
	}
	
	@Test
	public void create_reverse() {
		WithinWorkTimeFrame input = Helper.createWithin(
				new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(8, 30), TimeWithDayAttr.hourMinute(17, 30)), //就業時間内時間枠 8:30～17:30
				Optional.of(new LateLeaveEarlyTimeSheet(
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(17, 10), TimeWithDayAttr.hourMinute(17, 30)), //早退時間帯 17:10～17:30
						new TimeRoundingSetting(Unit.ROUNDING_TIME_30MIN, Rounding.ROUNDING_UP)))); //早退丸め 30分切り上げ
		OverTimeByTimeVacation result = OverTimeByTimeVacation.create(input);
		assertThat(result.getTimeSheet().getStart()).isEqualTo(TimeWithDayAttr.hourMinute(17, 30));
		assertThat(result.getTimeSheet().getEnd()).isEqualTo(TimeWithDayAttr.hourMinute(17, 30));
	}
	
	@Test
	public void create_filterBreak() {
		WithinWorkTimeFrame input = Helper.createWithin(
				new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(8, 30), TimeWithDayAttr.hourMinute(12, 45)), //就業時間内時間枠 8:30～12:45
				Optional.of(new LateLeaveEarlyTimeSheet(
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(13, 0), TimeWithDayAttr.hourMinute(17, 30)), //早退時間帯 13:00～17:30
						new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)))); //早退丸め 1分切り捨て
		List<TimeSheetOfDeductionItem> deductionItems = new ArrayList<>();
		deductionItems.add(Helper.createBreak(new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(12, 0), TimeWithDayAttr.hourMinute(13, 0)))); //休憩時間帯 12:00～13:00
		deductionItems.add(Helper.createBreak(new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(17, 30), TimeWithDayAttr.hourMinute(18, 0)))); //休憩時間帯 17:30～18:00
		input.setDeductionTimeSheet(deductionItems);
		OverTimeByTimeVacation result = OverTimeByTimeVacation.create(input);
		assertThat(result.getTimeSheet().getStart()).isEqualTo(TimeWithDayAttr.hourMinute(12, 45));
		assertThat(result.getTimeSheet().getEnd()).isEqualTo(TimeWithDayAttr.hourMinute(13, 0));
		assertThat(result.getDeductionTimeSheet())
			.extracting(
					t -> t.getTimeSheet().getStart().valueAsMinutes(),
					t -> t.getTimeSheet().getEnd().valueAsMinutes())
			.containsExactly(
				tuple(TimeWithDayAttr.hourMinute(12, 45).valueAsMinutes(), TimeWithDayAttr.hourMinute(13, 0).valueAsMinutes())); //休憩時間帯 12:45～13:00
	}
	
	private static class Helper {
		private static WithinWorkTimeFrame createWithin(TimeSpanForDailyCalc witnin, Optional<LateLeaveEarlyTimeSheet> leaveEarly) {
			return new WithinWorkTimeFrame(
					new EmTimeFrameNo(1),
					new WorkNo(1),
					witnin,
					witnin,
					new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
					new ArrayList<>(),
					new ArrayList<>(),
					new ArrayList<>(),
					MidNightTimeSheetForCalcList.createEmpty(),
					new ArrayList<>(),
					Optional.empty(),
					Optional.of(new LeaveEarlyTimeSheet(
							Optional.empty(),
							leaveEarly,
							1)));
		}
		
		private static TimeSheetOfDeductionItem createBreak(TimeSpanForDailyCalc breakTime) {
			return TimeSheetOfDeductionItem.createTimeSheetOfDeductionItem(
					breakTime,
					new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
					Collections.emptyList(),
					Collections.emptyList(),
					WorkingBreakTimeAtr.NOTWORKING,
					Finally.empty(),
					Finally.of(BreakClassification.BREAK),
					Optional.empty(),
					DeductionClassification.BREAK,
					Optional.empty(),
					false);
		}
	}
}
