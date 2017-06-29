package nts.uk.ctx.workflow.app.command.agent;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.workflow.dom.agent.AgentRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteAgentCommandHandler extends CommandHandler<DeleteAgentCommand> {
	@Inject
	private AgentRepository agentRepository;

	@Override
	protected void handle(CommandHandlerContext<DeleteAgentCommand> context) {
		DeleteAgentCommand deleteAgentCommand = context.getCommand();
		String employeeId = deleteAgentCommand.getEmployeeId();
		String companyId = AppContexts.user().companyId();
		String requestId = deleteAgentCommand.getRequestId();

		
		//delete agent
		agentRepository.delete(companyId, employeeId, requestId);

	}
}
