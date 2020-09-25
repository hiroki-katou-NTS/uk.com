package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.DayOfWeek;
import nts.arc.time.calendar.OneMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.WorkExpectationOfOneDay;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * シフト表の日付設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.シフト表.シフト表の日付設定
 * @author hiroko_miura
 *
 */
@RequiredArgsConstructor
public class ShiftTableDateSetting implements ShiftTableSetting {

	/** 締め日 */
	private final OneMonth availClosingDay;
	
	/** 勤務希望の締切日 */
	private final Optional<DateInMonth> availDeadline;
	
	/**	希望休日の上限 */
	private final Optional<AvaliHolidayMaxdays> holidayMaxdays;
	
	/**
	 * "勤務希望運用しない" で作る
	 * @param oneMonth
	 * @return
	 */
	public static ShiftTableDateSetting createNotuseAvailability(OneMonth oneMonth) {
		return new ShiftTableDateSetting(oneMonth, Optional.empty(), Optional.empty());
	}
	
	/**
	 * "勤務希望運用する" で作る
	 * @param oneMonth
	 * @param dateInmonth
	 * @param availHdMax
	 * @return
	 */
	public static ShiftTableDateSetting createUseAvailability(OneMonth oneMonth, DateInMonth dateInmonth, AvaliHolidayMaxdays availHdMax) {
		return new ShiftTableDateSetting(oneMonth, Optional.of(dateInmonth), Optional.of(availHdMax));
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
