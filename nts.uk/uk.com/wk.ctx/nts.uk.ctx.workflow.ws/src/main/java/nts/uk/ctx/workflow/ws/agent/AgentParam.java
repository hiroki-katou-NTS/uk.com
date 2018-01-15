package nts.uk.ctx.workflow.ws.agent;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@NoArgsConstructor
public class AgentParam {
	private String employeeId;
	
	private String requestId;
}
