package nts.uk.ctx.workflow.pub.agent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
@Builder
public class AgentExport {
	private String companyId;

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
