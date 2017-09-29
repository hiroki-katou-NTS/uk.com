package nts.uk.ctx.at.request.dom.application.common.adapter.workflow;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentPubImport;

public interface AgentAdapter {
	
	AgentPubImport getApprovalAgencyInformation(String companyID, List<String> approver);
}
