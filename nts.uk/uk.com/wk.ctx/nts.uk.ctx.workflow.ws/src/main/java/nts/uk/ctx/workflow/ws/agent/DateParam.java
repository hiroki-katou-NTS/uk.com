package nts.uk.ctx.workflow.ws.agent;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@NoArgsConstructor
public class DateParam {
	
	private GeneralDate startDate;
	
	private GeneralDate endDate;
	
}
