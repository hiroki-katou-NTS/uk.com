package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DayOfWeek;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.WorkExpectationOfOneDay;

/**
 * シフト表の曜日設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.シフト表.シフト表の曜日設定
 * @author hiroko_miura
 *
 */
@RequiredArgsConstructor
public class ShiftTableWeekSetting implements ShiftTableSetting {

	private final DayOfWeek firstDayofWeek;
	
	private final Optional<DeadlineDayofWeek> deadlineDayofWeek;
	
	/**
	 * "勤務希望運用しない" で作る
	 * @param firstDayofWeek
	 * @return
	 */
	public static ShiftTableWeekSetting createNotuseAvailability(DayOfWeek firstDayofWeek) {
		return new ShiftTableWeekSetting(firstDayofWeek, Optional.empty());
	}
	
	/**
	 * "勤務希望運用する" で作る
	 * @param firstDayofWeek
	 * @param deadlineDayofWeek
	 * @return
	 */
	public static ShiftTableWeekSetting createUseAvailability(DayOfWeek firstDayofWeek, DeadlineDayofWeek deadlineDayofWeek) {
		return new ShiftTableWeekSetting(firstDayofWeek, Optional.of(deadlineDayofWeek));
	}
	
	@Override
	public ShiftPeriodUnit getShiftPeriodUnit() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean isDeadlinePast() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean isOverHolidayMaxdays(List<WorkExpectationOfOneDay> workExpectList) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public GeneralDate getMostRecentDeadlineDate(GeneralDate date) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public DatePeriod getAgainstDeadlinePeriod(GeneralDate date) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public DatePeriod getAgainstAvailabilityPeriod(GeneralDate date) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
