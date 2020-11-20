package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.OneMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDay;

/**
 * シフト表の日付設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.シフト表.シフト表の日付設定
 * @author hiroko_miura
 *
 */
@Value
public class WorkAvailabilityRuleDateSetting implements WorkAvailabilityRule, DomainValue {

	/** 締め日 */
	private final OneMonth closureDate;
	
	/** 勤務希望の締切日 */
	private final DateInMonth availabilityDeadLine;
	
	/**	希望休日の上限 */
	private final HolidayAvailabilityMaxdays holidayMaxDays;
	
	@Override
	public WorkAvailabilityPeriodUnit getShiftPeriodUnit() {
		return WorkAvailabilityPeriodUnit.MONTHLY;
	}
	
	@Override
	public int getMaxFromNoticeDays() {
		return 15;
	}

	@Override
	public boolean isOverDeadline(GeneralDate availabilityDate) {
		
		GeneralDate startDate = this.getPeriodWhichIncludeAvailabilityDate(availabilityDate).start();
		GeneralDate deadline = this.availabilityDeadLine.justBefore(startDate);
		
		return GeneralDate.today().after(deadline);
	}

	@Override
	public boolean isOverHolidayMaxDays(List<WorkAvailabilityOfOneDay> workAvailabilityList) {
		
		List<WorkAvailabilityOfOneDay> holidayAvailability = workAvailabilityList.stream()
																.filter( e -> e.isHolidayAvailability() )
																.collect(Collectors.toList());
		
		return holidayAvailability.size() > this.holidayMaxDays.v();
	}
	
	@Override
	public DeadlineAndPeriodOfWorkAvailability getCorrespondingDeadlineAndPeriod(GeneralDate baseDate) {
		
		// get deadline
		GeneralDate mostRecentDeadline = this.availabilityDeadLine.after(baseDate);
		
		// get period
		GeneralDate nextDeadlineDate = mostRecentDeadline.addMonths(1);
		DatePeriod period =  this.getPeriodWhichIncludeAvailabilityDate(nextDeadlineDate);
		
		return new DeadlineAndPeriodOfWorkAvailability(mostRecentDeadline, period);
	}

	@Override
	public DatePeriod getPeriodWhichIncludeAvailabilityDate(GeneralDate availabilityDate) {
		
		return this.closureDate.periodOf(availabilityDate);
	}

}
