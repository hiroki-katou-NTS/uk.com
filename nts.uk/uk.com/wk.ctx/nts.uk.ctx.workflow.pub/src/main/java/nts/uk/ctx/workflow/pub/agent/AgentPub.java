package nts.uk.ctx.workflow.pub.agent;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface AgentPub {
	
	AgentPubExport getApprovalAgencyInformation(String companyID, List<String> approverList);
	
	List<AgentDataPubExport> getBySidDate(String companyId, String employeeId, GeneralDate baseDate);
}
