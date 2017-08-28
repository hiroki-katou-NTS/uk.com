package nts.uk.ctx.at.request.ac.workflow.agent;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.AgentRequestAdaptor;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentAdaptorDto;
import nts.uk.ctx.workflow.pub.agent.AgentPub;
import nts.uk.ctx.workflow.pub.agent.AgentPubDto;

@Stateless
public class AgentAdapterImp implements AgentRequestAdaptor{
	
	@Inject
	private AgentPub agentPub;

	@Override
	public List<AgentAdaptorDto> find(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate) {
		List<AgentPubDto> empDto = agentPub.find(companyId, employeeId, startDate, endDate);
		List<AgentAdaptorDto> lstEmployees = new ArrayList<>();
		for(AgentPubDto agentPubDto:empDto) {
			AgentAdaptorDto agentDto = new AgentAdaptorDto();
			agentDto.setEmployeeId(agentPubDto.getEmployeeId());
			agentDto.setRequestId(agentPubDto.getRequestId());
			agentDto.setStartDate(agentPubDto.getStartDate());
			agentDto.setEndDate(agentPubDto.getEndDate());
			agentDto.setEmployeeId(agentPubDto.getEmployeeId());
			agentDto.setEmployeeId(agentPubDto.getEmployeeId());
			
		}
		
		return null;
	}
}
