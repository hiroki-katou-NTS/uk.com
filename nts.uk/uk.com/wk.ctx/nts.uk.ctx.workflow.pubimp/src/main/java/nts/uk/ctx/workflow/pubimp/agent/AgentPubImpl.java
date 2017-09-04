package nts.uk.ctx.workflow.pubimp.agent;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.agent.AgentRepository;
import nts.uk.ctx.workflow.pub.agent.AgentPub;
import nts.uk.ctx.workflow.pub.agent.AgentPubDto;

@Stateless
public class AgentPubImpl implements AgentPub {
	
	@Inject
	private AgentRepository agentRepo;
	
	@Override
	public List<AgentPubDto> find(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate) {
		return agentRepo.find(companyId, employeeId, startDate, endDate)
				.stream().map(x -> new AgentPubDto(
							employeeId, 
							x.getRequestId().toString(), 
							x.getStartDate(), 
							x.getEndDate(), 
							x.getAgentSid1(), 
							x.getAgentAppType1().value, 
							x.getAgentSid2(), 
							x.getAgentAppType2().value, 
							x.getAgentSid3(), 
							x.getAgentAppType3().value, 
							x.getAgentSid3(), 
							x.getAgentAppType4().value)
				).collect(Collectors.toList());
	}

}
