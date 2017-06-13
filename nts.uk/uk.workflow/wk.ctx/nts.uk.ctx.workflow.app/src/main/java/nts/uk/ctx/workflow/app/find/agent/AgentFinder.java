package nts.uk.ctx.workflow.app.find.agent;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.agent.Agent;
import nts.uk.ctx.workflow.dom.agent.AgentRepository;

import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AgentFinder {

	@Inject
	private AgentRepository agentRepository;

	public List<AgentDto> init() {
		String employeeId = AppContexts.user().companyId();
		String companyId = AppContexts.user().companyId();
		return agentRepository.findAllAgent(companyId, employeeId).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}

	public AgentDto getAgentDto(GeneralDate startDate) {
		String companyId = AppContexts.user().companyCode();
		String employeeId = AppContexts.user().companyId();
		Optional<AgentDto> payClassification = this.agentRepository.getAgentByStartDate(companyId, employeeId, startDate).map(c -> convertToDbType(c));
		if(payClassification.isPresent()){
			return payClassification.get();
		}else{
			return null;
		}
	}

	private AgentDto convertToDbType(Agent agent) {
		AgentDto agentDto = new AgentDto();
		 
				agentDto.setAgentSid1(agent.getAgentSid1().v());
				agentDto.setAgentAppType1(agent.getAgentAppType1().value);
				agentDto.setAgentSid2(agent.getAgentSid2().v());
				agentDto.setAgentAppType2(agent.getAgentAppType2().value);
				agentDto.setAgentSid3(agent.getAgentSid3().v());
				agentDto.setAgentAppType3(agent.getAgentAppType3().value);
				agentDto.setAgentSid4(agent.getAgentSid4().v());
				agentDto.setAgentAppType4(agent.getAgentAppType4().value);
	
		return agentDto;
	}
	
}
