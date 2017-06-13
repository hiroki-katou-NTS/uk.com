package nts.uk.ctx.workflow.app.command.agent;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.workflow.dom.agent.Agent;
import nts.uk.ctx.workflow.dom.agent.AgentAppType;
import nts.uk.ctx.workflow.dom.agent.AgentRepository;
import nts.uk.ctx.workflow.dom.agent.AgentSid;
import nts.uk.shr.com.context.AppContexts;
import nts.arc.enums.EnumAdaptor;

public class AddAgentCommandHandler extends CommandHandler<AddAgentCommand> {
	@Inject
	private AgentRepository agentRepository;

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
				new AgentSid(addAgentCommand.getAgentSid1()),
				EnumAdaptor.valueOf(addAgentCommand.getAgentAppType1(), AgentAppType.class),
				new AgentSid(addAgentCommand.getAgentSid2()),
				EnumAdaptor.valueOf(addAgentCommand.getAgentAppType2(), AgentAppType.class),
				new AgentSid(addAgentCommand.getAgentSid3()),
				EnumAdaptor.valueOf(addAgentCommand.getAgentAppType3(), AgentAppType.class),
				new AgentSid(addAgentCommand.getAgentSid4()),
				EnumAdaptor.valueOf(addAgentCommand.getAgentAppType4(), AgentAppType.class));
		agentRepository.add(agentInfor);
	}

}
