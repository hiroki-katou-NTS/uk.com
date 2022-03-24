package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSettingOfWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktime.common.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutSetHelper;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimeRoundingMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayCalculation;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkSystemAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneExtraordTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateNightTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneShortTimeWorkSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSet;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class StaggerDiductionTimeSheetTest {

	/**
	 * 重複している→時刻がずれる
	 */
	@Test
	public void getForwardEndTest_duplicate() {
		StaggerDiductionTimeSheet target = new StaggerDiductionTimeSheet(
				new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 0)),	//8:00~17:00
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				Helper.createGoOuts(
						TimeRoundingSetting.oneMinDown(),
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(10, 0), TimeWithDayAttr.hourMinute(11, 0))));	//外出 10:00~11:00
		TimeWithDayAttr result = target.getForwardEnd(
				ActualWorkTimeSheetAtr.WithinWorkTime,
				Helper.createCommonSet(GoOutSetHelper.createWorkTimezoneGoOutSet(
						GoOutTimeRoundingMethod.IN_FRAME,
						new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN))),
				AddSettingOfWorkingTime.emptyHolidayCalcMethodSet());
		assertThat(result.valueAsMinutes()).isEqualTo(TimeWithDayAttr.hourMinute(18, 0).valueAsMinutes());	//終了時刻 18:00
	}
	
	/**
	 * 重複していない→時刻がずれない
	 */
	@Test
	public void getForwardEndTest_notDuplicate() {
		StaggerDiductionTimeSheet target = new StaggerDiductionTimeSheet(
				new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 0)),	//8:00~17:00
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				Helper.createGoOuts(
						TimeRoundingSetting.oneMinDown(),
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(7, 0), TimeWithDayAttr.hourMinute(8, 0)),		//外出 7:00~8:00
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(17, 0), TimeWithDayAttr.hourMinute(18, 0))));	//外出 17:00~18:00
		TimeWithDayAttr result = target.getForwardEnd(
				ActualWorkTimeSheetAtr.WithinWorkTime,
				Helper.createCommonSet(GoOutSetHelper.createWorkTimezoneGoOutSet(
						GoOutTimeRoundingMethod.IN_FRAME,
						new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN))),
				AddSettingOfWorkingTime.emptyHolidayCalcMethodSet());
		assertThat(result.valueAsMinutes()).isEqualTo(TimeWithDayAttr.hourMinute(17, 0).valueAsMinutes());	//終了時刻 17:00
	}
	
	/**
	 * 時刻をずらした後の時刻で次の控除時間帯と重複している→次の控除時間帯分も時刻がずれる
	 */
	@Test
	public void getForwardEndTest_nextDuplicate() {
		StaggerDiductionTimeSheet target = new StaggerDiductionTimeSheet(
				new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 0)),	//8:00~17:00
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				Helper.createGoOuts(
						TimeRoundingSetting.oneMinDown(),
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(10, 0), TimeWithDayAttr.hourMinute(11, 0)),		//外出 10:00~11:00
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(17, 59), TimeWithDayAttr.hourMinute(18, 59))));	//外出 17:59~18:59
		TimeWithDayAttr result = target.getForwardEnd(
				ActualWorkTimeSheetAtr.WithinWorkTime,
				Helper.createCommonSet(GoOutSetHelper.createWorkTimezoneGoOutSet(
						GoOutTimeRoundingMethod.IN_FRAME,
						new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN))),
				AddSettingOfWorkingTime.emptyHolidayCalcMethodSet());
		assertThat(result.valueAsMinutes()).isEqualTo(TimeWithDayAttr.hourMinute(19, 0).valueAsMinutes());	//終了時刻 19:00
	}
	
	/**
	 * 残業を想定。前後一つずつ重複しない控除時間帯がある→重複している分だけ時刻がずれる
	 */
	@Test
	public void getForwardEndTest_outside() {
		StaggerDiductionTimeSheet target = new StaggerDiductionTimeSheet(
				new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(17, 0), TimeWithDayAttr.hourMinute(19, 0)),	//17:00~19:00
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				Helper.createGoOuts(
						TimeRoundingSetting.oneMinDown(),
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(16, 30), TimeWithDayAttr.hourMinute(17, 0)),		//外出 16:30~17:00
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(17, 30), TimeWithDayAttr.hourMinute(18, 0)),		//外出 17:30~18:00
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(18, 30), TimeWithDayAttr.hourMinute(19, 0)),		//外出 18:30~19:00
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(19, 30), TimeWithDayAttr.hourMinute(20, 0)),		//外出 19:30~20:00
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(20, 30), TimeWithDayAttr.hourMinute(21, 0))));		//外出 20:30~21:00
		TimeWithDayAttr result = target.getForwardEnd(
				ActualWorkTimeSheetAtr.OverTimeWork,
				Helper.createCommonSet(GoOutSetHelper.createWorkTimezoneGoOutSet(
						GoOutTimeRoundingMethod.IN_FRAME,
						new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN))),
				AddSettingOfWorkingTime.emptyHolidayCalcMethodSet());
		assertThat(result.valueAsMinutes()).isEqualTo(TimeWithDayAttr.hourMinute(20, 30).valueAsMinutes());	//終了時刻 20:30
	}
	
	/**
	 * 実働時間帯の枠ごとに合計せず丸める→丸め後の時間で時刻がずれる
	 */
	@Test
	public void getForwardEndTest_IN_FRAME() {
		StaggerDiductionTimeSheet target = new StaggerDiductionTimeSheet(
				new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 0)),	//8:00~17:00
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				Helper.createGoOuts(
						TimeRoundingSetting.oneMinDown(),
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(10, 0), TimeWithDayAttr.hourMinute(10, 5)),		//外出 10:00~10:05
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(11, 0), TimeWithDayAttr.hourMinute(11, 5))));	//外出 11:00~11:05
		TimeWithDayAttr result = target.getForwardEnd(
				ActualWorkTimeSheetAtr.WithinWorkTime,
				Helper.createCommonSet(GoOutSetHelper.createWorkTimezoneGoOutSet(
						GoOutTimeRoundingMethod.IN_FRAME,	//実働時間帯の枠ごとに合計せず丸める
						new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP))),
				AddSettingOfWorkingTime.emptyHolidayCalcMethodSet());
		assertThat(result.valueAsMinutes()).isEqualTo(TimeWithDayAttr.hourMinute(17, 30).valueAsMinutes());	//終了時刻 17:30
	}
	
	/**
	 * 実働時間帯の枠ごとに合計してから丸める→丸め後の時間で時刻がずれる
	 */
	@Test
	public void getForwardEndTest_AFTER_TOTAL_IN_FRAME() {
		StaggerDiductionTimeSheet target = new StaggerDiductionTimeSheet(
				new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 0)),	//8:00~17:00
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				Helper.createGoOuts(
						TimeRoundingSetting.oneMinDown(),
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(10, 0), TimeWithDayAttr.hourMinute(10, 5)),		//外出 10:00~10:05
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(11, 0), TimeWithDayAttr.hourMinute(11, 5))));	//外出 11:00~11:05
		TimeWithDayAttr result = target.getForwardEnd(
				ActualWorkTimeSheetAtr.WithinWorkTime,
				Helper.createCommonSet(GoOutSetHelper.createWorkTimezoneGoOutSet(
						GoOutTimeRoundingMethod.AFTER_TOTAL_IN_FRAME,	//実働時間帯の枠ごとに合計してから丸める
						new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP))),
				AddSettingOfWorkingTime.emptyHolidayCalcMethodSet());
		assertThat(result.valueAsMinutes()).isEqualTo(TimeWithDayAttr.hourMinute(17, 15).valueAsMinutes());	//終了時刻 17:15
	}
	
	/**
	 * 実働時間帯ごとに合計して丸める→丸め後の時間（1分切り捨てにしかならない）で時刻がずれる
	 */
	@Test
	public void getForwardEndTest_AFTER_TOTAL() {
		StaggerDiductionTimeSheet target = new StaggerDiductionTimeSheet(
				new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 0)),	//8:00~17:00
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				Helper.createGoOuts(
						TimeRoundingSetting.oneMinDown(),
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(10, 00), TimeWithDayAttr.hourMinute(10, 5)),	//外出 10:00~10:05
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(11, 00), TimeWithDayAttr.hourMinute(11, 5))));	//外出 11:00~11:05
		TimeWithDayAttr result = target.getForwardEnd(
				ActualWorkTimeSheetAtr.WithinWorkTime,
				Helper.createCommonSet(GoOutSetHelper.createWorkTimezoneGoOutSet(
						GoOutTimeRoundingMethod.AFTER_TOTAL,	//実働時間帯ごとに合計して丸める
						new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP))),
				AddSettingOfWorkingTime.emptyHolidayCalcMethodSet());
		assertThat(result.valueAsMinutes()).isEqualTo(TimeWithDayAttr.hourMinute(17, 10).valueAsMinutes());	//終了時刻 17:10
	}
	
	/**
	 * 実働時間帯の枠ごとに合計せず丸める→外出がそれぞれ丸められる為、最後の外出と重複する
	 */
	@Test
	public void getForwardEndTest_nextDuplicate_IN_FRAME() {
		StaggerDiductionTimeSheet target = new StaggerDiductionTimeSheet(
				new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 0)),	//8:00~17:00
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				Helper.createGoOuts(
						TimeRoundingSetting.oneMinDown(),
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(10, 0), TimeWithDayAttr.hourMinute(10, 5)),		//外出 10:00~10:05
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(11, 0), TimeWithDayAttr.hourMinute(11, 5)),		//外出 11:00~11:05
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(17, 15), TimeWithDayAttr.hourMinute(17, 20))));	//外出 17:15~17:20
		TimeWithDayAttr result = target.getForwardEnd(
				ActualWorkTimeSheetAtr.WithinWorkTime,
				Helper.createCommonSet(GoOutSetHelper.createWorkTimezoneGoOutSet(
						GoOutTimeRoundingMethod.IN_FRAME,	//実働時間帯の枠ごとに合計せず丸める
						new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP))),
				AddSettingOfWorkingTime.emptyHolidayCalcMethodSet());
		assertThat(result.valueAsMinutes()).isEqualTo(TimeWithDayAttr.hourMinute(17, 45).valueAsMinutes());	//終了時刻 17:45
	}
	
	/**
	 * 実働時間帯の枠ごとに合計してから丸める→外出を合計して丸める為、最後の外出と重複しない
	 */
	@Test
	public void getForwardEndTest_nextDuplicate_AFTER_TOTAL_IN_FRAME() {
		StaggerDiductionTimeSheet target = new StaggerDiductionTimeSheet(
				new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 0)),	//8:00~17:00
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				Helper.createGoOuts(
						TimeRoundingSetting.oneMinDown(),
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(10, 0), TimeWithDayAttr.hourMinute(10, 5)),		//外出 10:00~10:05
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(11, 0), TimeWithDayAttr.hourMinute(11, 5)),		//外出 11:00~11:05
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(17, 15), TimeWithDayAttr.hourMinute(17, 20))));	//外出 17:15~17:20
		TimeWithDayAttr result = target.getForwardEnd(
				ActualWorkTimeSheetAtr.WithinWorkTime,
				Helper.createCommonSet(GoOutSetHelper.createWorkTimezoneGoOutSet(
						GoOutTimeRoundingMethod.AFTER_TOTAL_IN_FRAME,	//実働時間帯の枠ごとに合計してから丸める
						new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP))),
				AddSettingOfWorkingTime.emptyHolidayCalcMethodSet());
		assertThat(result.valueAsMinutes()).isEqualTo(TimeWithDayAttr.hourMinute(17, 15).valueAsMinutes());	//終了時刻 17:15
	}
	
	/**
	 * 実働時間帯ごとに合計して丸める→1分切り捨てにしかならない為、最後の外出と重複しない
	 */
	@Test
	public void getForwardEndTest_nextDuplicate_AFTER_TOTAL() {
		StaggerDiductionTimeSheet target = new StaggerDiductionTimeSheet(
				new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 0)),	//8:00~17:00
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				Helper.createGoOuts(
						TimeRoundingSetting.oneMinDown(),
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(10, 00), TimeWithDayAttr.hourMinute(10, 5)),	//外出 10:00~10:05
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(11, 0), TimeWithDayAttr.hourMinute(11, 5)),		//外出 11:00~11:05
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(17, 15), TimeWithDayAttr.hourMinute(17, 20))));	//外出 17:15~17:20
		TimeWithDayAttr result = target.getForwardEnd(
				ActualWorkTimeSheetAtr.WithinWorkTime,
				Helper.createCommonSet(GoOutSetHelper.createWorkTimezoneGoOutSet(
						GoOutTimeRoundingMethod.AFTER_TOTAL,	//実働時間帯ごとに合計して丸める
						new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP))),
				AddSettingOfWorkingTime.emptyHolidayCalcMethodSet());
		assertThat(result.valueAsMinutes()).isEqualTo(TimeWithDayAttr.hourMinute(17, 10).valueAsMinutes());	//終了時刻 17:10
	}
	
	/**
	 * 実働時間帯区分="就業時間内"→就業時間内の丸め設定を参照
	 */
	@Test
	public void getForwardEndTest_withinRounding() {
		StaggerDiductionTimeSheet target = new StaggerDiductionTimeSheet(
				new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 0)),	//8:00~17:00
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				Helper.createGoOuts(
						TimeRoundingSetting.oneMinDown(),
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(10, 0), TimeWithDayAttr.hourMinute(10, 02))));	//外出 10:00~10:02
		TimeWithDayAttr result = target.getForwardEnd(
				ActualWorkTimeSheetAtr.WithinWorkTime,
				Helper.createCommonSet(new WorkTimezoneGoOutSet(
						GoOutTimeRoundingMethod.IN_FRAME,
						GoOutSetHelper.createGoOutTimezoneRoundingSet(
								new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
								new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_UP),			//就業時間内のみ 5分切り上げ
								new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)))),
				AddSettingOfWorkingTime.emptyHolidayCalcMethodSet());
		assertThat(result.valueAsMinutes()).isEqualTo(TimeWithDayAttr.hourMinute(17, 05).valueAsMinutes());		//終了時刻 17:05
	}
	
	/**
	 * 実働時間帯区分="休出"→休出の丸め設定を参照
	 */
	@Test
	public void getForwardEndTest_holidayRounding() {
		StaggerDiductionTimeSheet target = new StaggerDiductionTimeSheet(
				new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 0)),	//8:00~17:00
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				Helper.createGoOuts(
						TimeRoundingSetting.oneMinDown(),
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(10, 0), TimeWithDayAttr.hourMinute(10, 02))));	//外出 10:00~10:02
		TimeWithDayAttr result = target.getForwardEnd(
				ActualWorkTimeSheetAtr.HolidayWork,
				Helper.createCommonSet(new WorkTimezoneGoOutSet(
						GoOutTimeRoundingMethod.IN_FRAME,
						GoOutSetHelper.createGoOutTimezoneRoundingSet(
								new TimeRoundingSetting(Unit.ROUNDING_TIME_10MIN, Rounding.ROUNDING_UP),		//休出のみ 10分切り上げ
								new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
								new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)))),
				AddSettingOfWorkingTime.emptyHolidayCalcMethodSet());
		assertThat(result.valueAsMinutes()).isEqualTo(TimeWithDayAttr.hourMinute(17, 10).valueAsMinutes());		//終了時刻 17:10
	}
	
	/**
	 * 実働時間帯区分="残業"→残業の丸め設定を参照
	 */
	@Test
	public void getForwardEndTest_overTimeRounding() {
		StaggerDiductionTimeSheet target = new StaggerDiductionTimeSheet(
				new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(17, 0), TimeWithDayAttr.hourMinute(19, 0)),	//17:00~19:00
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				Helper.createGoOuts(
						TimeRoundingSetting.oneMinDown(),
						new TimeSpanForDailyCalc(TimeWithDayAttr.hourMinute(18, 0), TimeWithDayAttr.hourMinute(18, 02))));	//外出 18:00~18:02
		TimeWithDayAttr result = target.getForwardEnd(
				ActualWorkTimeSheetAtr.OverTimeWork,
				Helper.createCommonSet(new WorkTimezoneGoOutSet(
						GoOutTimeRoundingMethod.IN_FRAME,
						GoOutSetHelper.createGoOutTimezoneRoundingSet(
								new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
								new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
								new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP)))),		//残業のみ 15分切り上げ
				AddSettingOfWorkingTime.emptyHolidayCalcMethodSet());
		assertThat(result.valueAsMinutes()).isEqualTo(TimeWithDayAttr.hourMinute(19, 15).valueAsMinutes());		//終了時刻 19:15
	}
	
	private static class Helper {
		private static TimeSheetOfDeductionItem createGoOut(TimeSpanForDailyCalc timeSheet, TimeRoundingSetting rounding) {
			return TimeSheetOfDeductionItem.createTimeSheetOfDeductionItem(
					timeSheet,
					rounding,
					new ArrayList<>(),
					new ArrayList<>(),
					WorkingBreakTimeAtr.NOTWORKING,
					Finally.of(GoingOutReason.PRIVATE),
					Finally.empty(),
					Optional.empty(),
					DeductionClassification.GO_OUT,
					Optional.empty(),
					false);
		}
		
		private static List<TimeSheetOfDeductionItem> createGoOuts(TimeRoundingSetting rounding, TimeSpanForDailyCalc... timeSheets) {
			List<TimeSheetOfDeductionItem> result = new ArrayList<>();
			for(TimeSpanForDailyCalc timeSheet : timeSheets) {
				result.add(createGoOut(timeSheet, rounding));
			}
			return result;
		}
		
		private static WorkTimezoneCommonSet createCommonSet(WorkTimezoneGoOutSet goOutSet) {
			List<WorkTimezoneOtherSubHolTimeSet> subHolTimeSet = new ArrayList<>();
			subHolTimeSet.add(WorkTimezoneOtherSubHolTimeSet.generateDefault(
					new WorkTimeCode("000"), CompensatoryOccurrenceDivision.FromOverTime));
			subHolTimeSet.add(WorkTimezoneOtherSubHolTimeSet.generateDefault(
					new WorkTimeCode("000"), CompensatoryOccurrenceDivision.WorkDayOffTime));
			List<WorkTimezoneMedicalSet> medicalSets = new ArrayList<>();
			medicalSets.add(WorkTimezoneMedicalSet.generateDefault(WorkSystemAtr.DAY_SHIFT));
			medicalSets.add(WorkTimezoneMedicalSet.generateDefault(WorkSystemAtr.NIGHT_SHIFT));
			return new WorkTimezoneCommonSet(
					false,
					subHolTimeSet,
					medicalSets,
					goOutSet,
					WorkTimezoneStampSet.generateDefault(),
					WorkTimezoneLateNightTimeSet.generateDefault(),
					WorkTimezoneShortTimeWorkSet.generateDefault(),
					WorkTimezoneExtraordTimeSet.generateDefault(),
					WorkTimezoneLateEarlySet.generateDefault(),
					HolidayCalculation.generateDefault(),
					Optional.empty());
		}
	}
}
