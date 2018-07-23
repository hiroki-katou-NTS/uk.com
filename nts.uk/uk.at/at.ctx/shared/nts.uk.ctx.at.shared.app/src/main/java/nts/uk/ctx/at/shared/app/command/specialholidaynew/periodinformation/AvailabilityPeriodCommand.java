package nts.uk.ctx.at.shared.app.command.specialholidaynew.periodinformation;

import lombok.Value;

@Value
public class AvailabilityPeriodCommand {
	private String startDate;
	
	private String endDate;
}
