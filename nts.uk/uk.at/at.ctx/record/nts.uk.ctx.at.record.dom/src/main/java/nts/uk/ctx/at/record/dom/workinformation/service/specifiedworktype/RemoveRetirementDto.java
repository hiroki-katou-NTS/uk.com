package nts.uk.ctx.at.record.dom.workinformation.service.specifiedworktype;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;

@Getter
@Setter
public class RemoveRetirementDto {
	
	private DatePeriod period;
	
	private Boolean status;

}
