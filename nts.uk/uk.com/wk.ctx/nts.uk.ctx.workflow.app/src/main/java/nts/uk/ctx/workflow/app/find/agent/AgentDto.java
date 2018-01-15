package nts.uk.ctx.workflow.app.find.agent;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class AgentDto {
	
	private String employeeId;
	
	private String requestId;

	private GeneralDate startDate;

	private GeneralDate endDate;

	private String agentSid1;

	private int agentAppType1;

	private String agentSid2;

	private int agentAppType2;

	private String agentSid3;

	private int agentAppType3;

	private String agentSid4;

	private int agentAppType4;
}
