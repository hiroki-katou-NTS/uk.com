package nts.uk.ctx.at.function.ac.agentapproval;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.agent.AgentApprovalAdapter;
import nts.uk.ctx.at.function.dom.adapter.agent.AgentApprovalImport;
import nts.uk.ctx.workflow.pub.agent.AgentPub;

@Stateless
public class AgentApprovalAdapterImpl implements AgentApprovalAdapter {

	@Inject
	private AgentPub agentPub;
	
	@Override
	public List<AgentApprovalImport> findByAgentApproverAndPeriod(String companyId, List<String> agentApproverIds,
			DatePeriod period, int agentType) {
		return agentPub.findAgentByPeriod(companyId, agentApproverIds, period.start(), period.end(), agentType)
					.stream().map(a -> 
						new AgentApprovalImport(a.getApproverID(), a.getAgentID(), a.getStartDate(), a.getEndDate()))
					.collect(Collectors.toList());
	}

}
