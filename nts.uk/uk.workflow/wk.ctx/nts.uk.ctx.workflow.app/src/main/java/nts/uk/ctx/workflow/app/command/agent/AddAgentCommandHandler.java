package nts.uk.ctx.workflow.app.command.agent;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
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
public class AddAgentCommandHandler extends CommandHandler<AddAgentCommand> {
	@Inject
	private AgentRepository agentRepository;
	@Inject
	private AgentFinder finder;

	@Override
	protected void handle(CommandHandlerContext<AddAgentCommand> context) {

		AddAgentCommand addAgentCommand = context.getCommand();

		String employeeId = addAgentCommand.getEmployeeId();
		String companyId = AppContexts.user().companyId();

		Agent agentInfor = new Agent(
				companyId, 
				employeeId, 
				addAgentCommand.getStartDate(),
				addAgentCommand.getEndDate(), 
				addAgentCommand.getAgentSid1(),
				EnumAdaptor.valueOf(addAgentCommand.getAgentAppType1(), AgentAppType.class),
				addAgentCommand.getAgentSid2(),
				EnumAdaptor.valueOf(addAgentCommand.getAgentAppType2(), AgentAppType.class),
				addAgentCommand.getAgentSid3(),
				EnumAdaptor.valueOf(addAgentCommand.getAgentAppType3(), AgentAppType.class),
				addAgentCommand.getAgentSid4(),
				EnumAdaptor.valueOf(addAgentCommand.getAgentAppType4(), AgentAppType.class));
		
		List<AgentDto> agents = finder.findAll(employeeId);
		
		List<RangeDate> rangeDateList = agents.stream()
				.map(a -> new RangeDate(a.getStartDate(), a.getEndDate()))
				.collect(Collectors.toList());
		
		agentInfor.validateDate(rangeDateList);
		
		agentRepository.add(agentInfor);
	}

}
