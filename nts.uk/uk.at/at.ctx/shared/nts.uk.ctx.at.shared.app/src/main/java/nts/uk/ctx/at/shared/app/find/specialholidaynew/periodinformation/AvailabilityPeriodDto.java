package nts.uk.ctx.at.shared.app.find.specialholidaynew.periodinformation;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class AvailabilityPeriodDto {
	private GeneralDate startDate;
	
	private GeneralDate endDate;
}
