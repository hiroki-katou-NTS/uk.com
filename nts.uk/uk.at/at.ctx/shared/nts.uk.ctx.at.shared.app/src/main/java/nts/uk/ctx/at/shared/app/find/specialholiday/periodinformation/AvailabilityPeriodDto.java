package nts.uk.ctx.at.shared.app.find.specialholiday.periodinformation;

import lombok.Value;

@Value
public class AvailabilityPeriodDto {

	public AvailabilityPeriodDto() {
		startDate = 0;
		endDate = 0;
	}

	private Integer startDate;

	private Integer endDate;
}
