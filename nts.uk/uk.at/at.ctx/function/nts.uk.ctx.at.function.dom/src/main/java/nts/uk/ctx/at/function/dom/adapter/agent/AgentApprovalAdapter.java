package nts.uk.ctx.at.function.dom.adapter.agent;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface AgentApprovalAdapter {
	
	List<AgentApprovalImport> findByAgentApproverAndPeriod(String companyId, List<String> agentApproverIds,
			DatePeriod period, int agentType);
}
