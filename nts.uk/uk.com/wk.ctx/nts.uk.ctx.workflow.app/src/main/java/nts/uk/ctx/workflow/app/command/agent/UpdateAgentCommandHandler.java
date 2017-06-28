package nts.uk.ctx.workflow.app.command.agent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.workflow.app.find.agent.AgentDto;
import nts.uk.ctx.workflow.app.find.agent.AgentFinder;
import nts.uk.ctx.workflow.dom.agent.Agent;
import nts.uk.ctx.workflow.dom.agent.AgentAppType;
import nts.uk.ctx.workflow.dom.agent.AgentRepository;
import nts.uk.ctx.workflow.dom.agent.RangeDate;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
@Transactional
public class UpdateAgentCommandHandler extends CommandHandler<AgentCommandBase> {
	@Inject
	private AgentRepository agentRepository;
	@Inject
	private AgentFinder finder;
	
	@Override
	protected void handle(CommandHandlerContext<AgentCommandBase> context) {
		
		AgentCommandBase agentCommandBase = context.getCommand();
		String employeeId = agentCommandBase.getEmployeeId();
		String companyId = AppContexts.user().companyId();
		String requestId = agentCommandBase.getRequestId();			
		Optional<Agent> upAgent = agentRepository.find(companyId, employeeId, requestId);
		if(!upAgent.isPresent()){
			throw new BusinessException("");
		}
		
		Agent agentInfor = new Agent(
				companyId, 
				employeeId,
				UUID.fromString(requestId),
				agentCommandBase.getStartDate(),
				agentCommandBase.getEndDate(), 
				(agentCommandBase.getAgentSid1()),
				EnumAdaptor.valueOf(agentCommandBase.getAgentAppType1(), AgentAppType.class),
				(agentCommandBase.getAgentSid2()),
				EnumAdaptor.valueOf(agentCommandBase.getAgentAppType2(), AgentAppType.class),
				(agentCommandBase.getAgentSid3()),
				EnumAdaptor.valueOf(agentCommandBase.getAgentAppType3(), AgentAppType.class),
				(agentCommandBase.getAgentSid4()),
				EnumAdaptor.valueOf(agentCommandBase.getAgentAppType4(), AgentAppType.class));
		
		List<AgentDto> agents = finder.findAllEmploy(employeeId);
		
		List<RangeDate> rangeDateList = agents.stream()
				.filter(x -> !requestId.equals(x.getRequestId()))
				.map(a -> new RangeDate(a.getStartDate(), a.getEndDate()))
				.collect(Collectors.toList());
		
		agentInfor.validateDate(rangeDateList);
		
		//validate agent of approval
		List<Agent> agentSidList = agentRepository.findByCid(companyId);
				
		agentInfor.checkAgentSid(agentSidList);
		
		//update agent
		agentRepository.update(agentInfor);
	}
}
