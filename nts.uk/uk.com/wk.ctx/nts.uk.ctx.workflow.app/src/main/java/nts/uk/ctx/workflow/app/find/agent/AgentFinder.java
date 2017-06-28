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
	public List<AgentDto> findAllEmploy(String employeeId) {
		
		String companyId = AppContexts.user().companyId();
		return agentRepository.findAllAgent(companyId, employeeId).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}
	
	/**
	 * Find all agent by Date
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<AgentDto> findAll(GeneralDate startDate, GeneralDate endDate) {
		String companyId = AppContexts.user().companyId();
		return agentRepository.findAll(companyId, startDate,endDate).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList()); 
	}
	
	/**
	 * Find all agent by Company Id
	 * @return
	 */
	public List<AgentDto> findByCid() {
		String companyId = AppContexts.user().companyId();
		return agentRepository.findByCid(companyId).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param employeeId
	 * @param requestId
	 * @return
	 */
	public AgentDto getAgentDto(String employeeId, String requestId) {
		String companyId = AppContexts.user().companyId();
		Optional<AgentDto> agent = this.agentRepository.find(companyId, employeeId, requestId).map(c -> convertToDbType(c));
		if(agent.isPresent()){
			return agent.get();
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
		agentDto.setEmployeeId(agent.getEmployeeId());
		agentDto.setStartDate(agent.getStartDate());
		agentDto.setEndDate(agent.getEndDate());
		agentDto.setAgentSid1(agent.getAgentSid1());
		agentDto.setAgentAppType1(agent.getAgentAppType1().value);
		agentDto.setAgentSid2(agent.getAgentSid2());
		agentDto.setAgentAppType2(agent.getAgentAppType2().value);
		agentDto.setAgentSid3(agent.getAgentSid3());
		agentDto.setAgentAppType3(agent.getAgentAppType3().value);
		agentDto.setAgentSid4(agent.getAgentSid4());
		agentDto.setAgentAppType4(agent.getAgentAppType4().value);
		agentDto.setRequestId(agent.getRequestId().toString());
	
		return agentDto;
	}	
}
