package nts.uk.ctx.workflow.app.command.agent;

import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.workflow.dom.agent.Agent;
import nts.uk.ctx.workflow.dom.agent.AgentAppType;
import nts.uk.ctx.workflow.dom.agent.AgentRepository;
import nts.uk.ctx.workflow.dom.agent.AgentSid;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateAgentCommandHandler extends CommandHandler<UpdateAgentCommand> {
	@Inject
	private AgentRepository agentRepository;

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
				new AgentSid(updateAgentCommand.getAgentSid1()),
				EnumAdaptor.valueOf(updateAgentCommand.getAgentAppType1(), AgentAppType.class),
				new AgentSid(updateAgentCommand.getAgentSid2()),
				EnumAdaptor.valueOf(updateAgentCommand.getAgentAppType2(), AgentAppType.class),
				new AgentSid(updateAgentCommand.getAgentSid3()),
				EnumAdaptor.valueOf(updateAgentCommand.getAgentAppType3(), AgentAppType.class),
				new AgentSid(updateAgentCommand.getAgentSid4()),
				EnumAdaptor.valueOf(updateAgentCommand.getAgentAppType4(), AgentAppType.class));
		agentRepository.add(agentInfor);
	//	agentRepository.update(agentInfor);
		
	}
}
