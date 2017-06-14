package nts.uk.ctx.workflow.app.command.agent;

import java.util.List;
import java.util.Optional;
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

@Stateless
@Transactional
public class UpdateAgentCommandHandler extends CommandHandler<UpdateAgentCommand> {
	@Inject
	private AgentRepository agentRepository;
	@Inject
	private AgentFinder finder;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateAgentCommand> context) {
		UpdateAgentCommand updateAgentCommand = context.getCommand();
		String employeeId = updateAgentCommand.getEmployeeId();
		String companyId = AppContexts.user().companyId();
			
		Optional<Agent> upAgent = agentRepository.getAgentByStartDate(companyId, employeeId, updateAgentCommand.getStartDate());
		if(!upAgent.isPresent()){
			throw new BusinessException("ER026");
		}
		
		Agent agentInfor = new Agent(
				companyId, 
				employeeId, 
				updateAgentCommand.getStartDate(),
				updateAgentCommand.getEndDate(), 
				(updateAgentCommand.getAgentSid1()),
				EnumAdaptor.valueOf(updateAgentCommand.getAgentAppType1(), AgentAppType.class),
				(updateAgentCommand.getAgentSid2()),
				EnumAdaptor.valueOf(updateAgentCommand.getAgentAppType2(), AgentAppType.class),
				(updateAgentCommand.getAgentSid3()),
				EnumAdaptor.valueOf(updateAgentCommand.getAgentAppType3(), AgentAppType.class),
				(updateAgentCommand.getAgentSid4()),
				EnumAdaptor.valueOf(updateAgentCommand.getAgentAppType4(), AgentAppType.class));
		
		List<AgentDto> agents = finder.findAll(employeeId);
		
		List<RangeDate> rangeDateList = agents.stream()
				.map(a -> new RangeDate(a.getStartDate(), a.getEndDate()))
				.collect(Collectors.toList());
		
		agentInfor.validateDate(rangeDateList);
		
		agentRepository.add(agentInfor);
	}
}
