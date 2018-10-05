package nts.uk.ctx.at.shared.app.command.specialholiday.periodinformation;

import lombok.Value;
import nts.uk.shr.com.time.calendar.MonthDay;

@Value
public class AvailabilityPeriodCommand {
	private Integer startDate;
	
	private Integer endDate;
	
	public MonthDay getStartDateValue() {
		return startDate != null ? new MonthDay(startDate / 100, startDate % 100) : null;
	}
	
	public MonthDay getEndDateValue() {
		return endDate != null ? new MonthDay(endDate / 100, endDate % 100) : null;
	}
}
