package nts.uk.ctx.workflow.pub.agent;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface AgentPub {
	
	AgentPubExport getApprovalAgencyInformation(String companyID, List<String> approverList);
	
	List<AgentExport> getApprovalAgencyInfoByPeriod(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate);
}
