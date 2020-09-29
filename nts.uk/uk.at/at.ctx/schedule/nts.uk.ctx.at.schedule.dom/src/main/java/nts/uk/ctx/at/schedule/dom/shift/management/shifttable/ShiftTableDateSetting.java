package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.OneMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.WorkExpectationOfOneDay;

/**
 * シフト表の日付設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.シフト表.シフト表の日付設定
 * @author hiroko_miura
 *
 */
@RequiredArgsConstructor
public class ShiftTableDateSetting implements ShiftTableSetting {

	/** 締め日 */
	private final OneMonth closureDate;
	
	/** 勤務希望の締切日 */
	private final Optional<DateInMonth> expectDeadLine;
	
	/**	希望休日の上限 */
	private final Optional<HolidayExpectationMaxdays> holidayMaxDays;
	
	/**
	 * "勤務希望運用する" で作る
	 * @param oneMonth
	 * @param dateInmonth
	 * @param availHdMax
	 * @return
	 */
	public static ShiftTableDateSetting createWithExpectationMode(
			OneMonth oneMonth, 
			DateInMonth expectDeadLine, 
			HolidayExpectationMaxdays holidayMaxDays, 
			FromNoticeDays fromNoticeDays) {
		
		return new ShiftTableDateSetting(
				oneMonth, 
				Optional.of(expectDeadLine), 
				Optional.of(holidayMaxDays));
	}
	
	/**
	 * "勤務希望運用しない" で作る
	 * @param oneMonth
	 * @return
	 */
	public static ShiftTableDateSetting createWithoutExpectationMode(OneMonth oneMonth) {
		
		return new ShiftTableDateSetting(oneMonth, Optional.empty(), Optional.empty());
	}
	
	@Override
	public ShiftPeriodUnit getShiftPeriodUnit() {
		return ShiftPeriodUnit.MONTHLY;
	}

	@Override
	public boolean isOverDeadline(GeneralDate expectingDate) {
		
		if ( !this.expectDeadLine.isPresent() ) {
			return false;
		}

		GeneralDate startDate = this.closureDate.periodOf(expectingDate).start();
		GeneralDate deadline = this.expectDeadLine.get().justBefore(startDate);
		
		return GeneralDate.today().after(deadline);
	}

	@Override
	public boolean isOverHolidayMaxdays(List<WorkExpectationOfOneDay> workExpectList) {
		
		if ( !this.holidayMaxDays.isPresent() ) {
			return false;
		}
		
		List<WorkExpectationOfOneDay> holidayExpectations = workExpectList.stream()
																.filter( e -> e.isHolidayExpectation() )
																.collect(Collectors.toList());
		
		return holidayExpectations.size() > this.holidayMaxDays.get().v();
	}
	
	@Override
	public ShiftTableRuleInfo getcorrespondingDeadlineAndPeriod(GeneralDate baseDate) {
		
		// get deadline
		GeneralDate mostRecentDeadline = this.expectDeadLine.get().after(baseDate);
		
		// get period
		GeneralDate nextDeadlineDate = mostRecentDeadline.addMonths(1);
		DatePeriod period =  this.closureDate.periodOf(nextDeadlineDate);
		
		return new ShiftTableRuleInfo(mostRecentDeadline, period);
	}

	@Override
	public DatePeriod getPeriodWhichIncludeExpectingDate(GeneralDate expectingDate) {
		
		return this.closureDate.periodOf(expectingDate);
	}

}
