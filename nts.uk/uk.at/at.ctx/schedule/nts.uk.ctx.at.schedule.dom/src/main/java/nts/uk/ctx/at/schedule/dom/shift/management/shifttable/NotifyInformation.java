package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.Optional;

import lombok.Value;
import nts.arc.time.calendar.period.DatePeriod;

@Value
public class NotifyInformation {
	
	private boolean notifyDate;
	
	private Optional<DatePeriod> notifyPeriod;
	
	public static NotifyInformation notNotifyDate() {
		return new NotifyInformation(false, Optional.empty());
	}
	
	public static NotifyInformation create(DatePeriod notifyPeriod) {
		return new NotifyInformation(true, Optional.of(notifyPeriod));
	}

}
