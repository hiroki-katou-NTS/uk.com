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

/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class AgentFinder {

	@Inject
	private AgentRepository agentRepository;

	/**
	 * Find agent by employee
	 * @param employeeId
	 * @return
	 */
	public List<AgentDto> findAll(String employeeId) {
		
		String companyId = AppContexts.user().companyId();
		return agentRepository.findAllAgent(companyId, employeeId).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}
	
	/**
	 * Find all agent by company
	 * @return
	 */
	public List<AgentDto> findAll(GeneralDate startDate, GeneralDate endDate) {
		String companyId = AppContexts.user().companyId();
		return agentRepository.findAll(companyId,startDate,endDate).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param employeeId
	 * @param startDate
	 * @return
	 */
	public AgentDto getAgentDto(String employeeId, GeneralDate startDate) {
		String companyId = AppContexts.user().companyId();
		Optional<AgentDto> payClassification = this.agentRepository.getAgentByStartDate(companyId, employeeId, startDate).map(c -> convertToDbType(c));
		if(payClassification.isPresent()){
			return payClassification.get();
		}else{
			return null;
		}
	}

	/**
	 * 
	 * @param agent
	 * @return
	 */
	private AgentDto convertToDbType(Agent agent) {
		AgentDto agentDto = new AgentDto();
		 
				agentDto.setAgentSid1(agent.getAgentSid1());
				agentDto.setAgentAppType1(agent.getAgentAppType1().value);
				agentDto.setAgentSid2(agent.getAgentSid2());
				agentDto.setAgentAppType2(agent.getAgentAppType2().value);
				agentDto.setAgentSid3(agent.getAgentSid3());
				agentDto.setAgentAppType3(agent.getAgentAppType3().value);
				agentDto.setAgentSid4(agent.getAgentSid4());
				agentDto.setAgentAppType4(agent.getAgentAppType4().value);
	
		return agentDto;
	}
	
}
