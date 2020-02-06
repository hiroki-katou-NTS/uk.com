package nts.uk.ctx.at.record.dom.workinformation.service.specifiedworktype;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Getter
@Setter
public class RemoveRetirementDto {
	
	private DatePeriod period;
	
	private Boolean status;

}
