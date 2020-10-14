package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
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
@Value
public class ShiftTableDateSetting implements ShiftTableSetting, DomainValue {

	/** 締め日 */
	private final OneMonth closureDate;
	
	/** 勤務希望の締切日 */
	private final DateInMonth expectDeadLine;
	
	/**	希望休日の上限 */
	private final HolidayExpectationMaxdays holidayMaxDays;
	
	@Override
	public ShiftPeriodUnit getShiftPeriodUnit() {
		return ShiftPeriodUnit.MONTHLY;
	}
	
	@Override
	public int getMaxFromNoticeDays() {
		return 15;
	}

	@Override
	public boolean isOverDeadline(GeneralDate expectingDate) {
		
		GeneralDate startDate = this.getPeriodWhichIncludeExpectingDate(expectingDate).start();
		GeneralDate deadline = this.expectDeadLine.justBefore(startDate);
		
		return GeneralDate.today().after(deadline);
	}

	@Override
	public boolean isOverHolidayMaxDays(List<WorkExpectationOfOneDay> workExpectList) {
		
		List<WorkExpectationOfOneDay> holidayExpectations = workExpectList.stream()
																.filter( e -> e.isHolidayExpectation() )
																.collect(Collectors.toList());
		
		return holidayExpectations.size() > this.holidayMaxDays.v();
	}
	
	@Override
	public DeadlineAndPeriodOfExpectation getCorrespondingDeadlineAndPeriod(GeneralDate baseDate) {
		
		// get deadline
		GeneralDate mostRecentDeadline = this.expectDeadLine.after(baseDate);
		
		// get period
		GeneralDate nextDeadlineDate = mostRecentDeadline.addMonths(1);
		DatePeriod period =  this.getPeriodWhichIncludeExpectingDate(nextDeadlineDate);
		
		return new DeadlineAndPeriodOfExpectation(mostRecentDeadline, period);
	}

	@Override
	public DatePeriod getPeriodWhichIncludeExpectingDate(GeneralDate expectingDate) {
		
		return this.closureDate.periodOf(expectingDate);
	}

}
