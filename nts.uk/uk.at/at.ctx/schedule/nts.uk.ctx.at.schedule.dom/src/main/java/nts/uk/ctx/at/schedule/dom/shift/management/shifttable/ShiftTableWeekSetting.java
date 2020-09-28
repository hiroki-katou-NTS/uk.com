package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DayOfWeek;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.seek.DateSeek;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.WorkExpectationOfOneDay;

/**
 * シフト表の曜日設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.シフト表.シフト表の曜日設定
 * @author hiroko_miura
 *
 */
@RequiredArgsConstructor
public class ShiftTableWeekSetting implements ShiftTableSetting {

	/**
	 * 開始曜日
	 */
	private final DayOfWeek firstDayOfWeek;
	
	/**
	 * 勤務希望の締切曜日
	 */
	private final Optional<DeadlineDayOfWeek> expectDeadLine;
	
	/** 
	 * 締切日の何日前に通知するかの日数 
	 * */
	private final Optional<FromNoticeDaysWithWeekMode> fromNoticeDays;
	
	/**
	 * "勤務希望運用する" で作る
	 * @param firstDayofWeek
	 * @param deadlineDayofWeek
	 * @return
	 */
	public static ShiftTableWeekSetting createUseAvailability(
			DayOfWeek firstDayofWeek, 
			DeadlineDayOfWeek deadlineDayofWeek,
			FromNoticeDaysWithWeekMode fromNoticeDays
			) {
		
		return new ShiftTableWeekSetting(
				firstDayofWeek, 
				Optional.of(deadlineDayofWeek),
				Optional.of(fromNoticeDays)
				);
	}
	
	/**
	 * "勤務希望運用しない" で作る
	 * @param firstDayofWeek
	 * @return
	 */
	public static ShiftTableWeekSetting createNotuseAvailability(DayOfWeek firstDayofWeek) {
		
		return new ShiftTableWeekSetting(firstDayofWeek, Optional.empty(), Optional.empty());
	}
	
	@Override
	public ShiftPeriodUnit getShiftPeriodUnit() {
		return ShiftPeriodUnit.WEEKLY;
	}

	@Override
	public boolean isOverDeadline(GeneralDate expectingDate) {

		if ( !this.expectDeadLine.isPresent() ) {
			return false;
		}
		
		GeneralDate startDate = expectingDate.previous(DateSeek.dayOfWeek(this.firstDayOfWeek));
		GeneralDate deadline = this.expectDeadLine.get().getLastDeadlineWithWeekAtr(startDate);
		
		return GeneralDate.today().after(deadline);
	}

	@Override
	public boolean isOverHolidayMaxdays(List<WorkExpectationOfOneDay> workExpectList) {
		return false;
	}
	
	@Override
	public GeneralDate getMostRecentDeadlineDate(GeneralDate date) {
		
		return this.expectDeadLine.get().getMostRecentDeadlineIncludeTargetDate(date);
	}

	@Override
	public NotifyInformation isTodayTheNotify() {
		
		GeneralDate today = GeneralDate.today();

		// check whether today is notification date
		GeneralDate mostRecentDeadline = this.expectDeadLine.get().getMostRecentDeadlineIncludeTargetDate(today);
		GeneralDate notifyStartDate = mostRecentDeadline.addDays(- this.fromNoticeDays.get().v());
		if ( today.before(notifyStartDate)) {
			return NotifyInformation.notNotifyDate();
		}
		
		// get notification period
		GeneralDate nextDeadline = mostRecentDeadline.addDays(7);
		if (this.expectDeadLine.get().getWeekAtr() == DeadlineWeekAtr.TWO_WEEK_AGO) {
			nextDeadline = nextDeadline.addDays(7);
		}
		GeneralDate startDate = nextDeadline.previous(DateSeek.dayOfWeek(this.firstDayOfWeek));
		DatePeriod notifyPeriod = new DatePeriod(startDate, startDate.addDays(6));
		
		return NotifyInformation.create(notifyPeriod);
	}

}
