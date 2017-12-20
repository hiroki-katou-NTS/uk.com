package nts.uk.ctx.workflow.pub.agent;

import java.util.List;

public interface AgentPub {
	
	AgentPubExport getApprovalAgencyInformation(String companyID, List<String> approverList);
}
