package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
@Getter
@AllArgsConstructor
public class AgentDataRequestPubImport {
	private String companyId;

	private String employeeId;
	
	private UUID requestId;

	private GeneralDate startDate;

	private GeneralDate endDate;

	private String agentSid1;

	private AgentAppTypeRequestImport agentAppType1;

	private String agentSid2;

	private AgentAppTypeRequestImport agentAppType2;

	private String agentSid3;

	private AgentAppTypeRequestImport agentAppType3;

	private String agentSid4;

	private AgentAppTypeRequestImport agentAppType4;
}
