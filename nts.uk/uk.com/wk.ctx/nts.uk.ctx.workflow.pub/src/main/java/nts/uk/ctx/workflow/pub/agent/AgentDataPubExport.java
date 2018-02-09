package nts.uk.ctx.workflow.pub.agent;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
@Getter
@AllArgsConstructor
public class AgentDataPubExport {
	private String companyId;

	private String employeeId;
	
	private UUID requestId;

	private GeneralDate startDate;

	private GeneralDate endDate;

	private String agentSid1;

	private AgentAppTypeExport agentAppType1;

	private String agentSid2;

	private AgentAppTypeExport agentAppType2;

	private String agentSid3;

	private AgentAppTypeExport agentAppType3;

	private String agentSid4;

	private AgentAppTypeExport agentAppType4;

}
