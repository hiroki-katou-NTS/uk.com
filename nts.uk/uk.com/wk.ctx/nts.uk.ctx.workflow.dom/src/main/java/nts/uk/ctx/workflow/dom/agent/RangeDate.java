package nts.uk.ctx.workflow.dom.agent;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class RangeDate {
	
	private GeneralDate startDate;

	private GeneralDate endDate;
}
