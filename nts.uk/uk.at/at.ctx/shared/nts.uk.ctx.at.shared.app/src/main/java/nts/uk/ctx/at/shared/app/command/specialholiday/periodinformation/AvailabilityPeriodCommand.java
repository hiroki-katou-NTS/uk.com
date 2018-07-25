package nts.uk.ctx.at.shared.app.command.specialholiday.periodinformation;

import lombok.Value;

@Value
public class AvailabilityPeriodCommand {
	private String startDate;
	
	private String endDate;
}
