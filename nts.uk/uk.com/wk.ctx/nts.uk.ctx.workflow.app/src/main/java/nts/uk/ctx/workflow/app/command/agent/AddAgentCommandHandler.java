package nts.uk.ctx.workflow.app.command.agent;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.workflow.app.find.agent.AgentDto;
import nts.uk.ctx.workflow.app.find.agent.AgentFinder;
import nts.uk.ctx.workflow.dom.agent.Agent;
import nts.uk.ctx.workflow.dom.agent.AgentAppType;
import nts.uk.ctx.workflow.dom.agent.AgentRepository;
import nts.uk.ctx.workflow.dom.agent.AgentType;
import nts.uk.ctx.workflow.dom.agent.RangeDate;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddAgentCommandHandler extends CommandHandlerWithResult<AgentCommandBase, String> {
	
	@Inject
	private AgentRepository agentRepository;
	@Inject
	private AgentFinder finder;

	@Override
	protected String handle(CommandHandlerContext<AgentCommandBase> context) {

		AgentCommandBase agentCommandBase = context.getCommand();

		String employeeId = agentCommandBase.getEmployeeId();
		String companyId = AppContexts.user().companyId();
		UUID requestId = Agent.createRequestId();	

		Agent agentInfor = new Agent(
				companyId, 
				employeeId,
				requestId,
				agentCommandBase.getStartDate(),
				agentCommandBase.getEndDate(), 
				agentCommandBase.getAgentSid1(),
				EnumAdaptor.valueOf(agentCommandBase.getAgentAppType1(), AgentAppType.class),
				agentCommandBase.getAgentSid2(),
				EnumAdaptor.valueOf(agentCommandBase.getAgentAppType2(), AgentAppType.class),
				agentCommandBase.getAgentSid3(),
				EnumAdaptor.valueOf(agentCommandBase.getAgentAppType3(), AgentAppType.class),
				agentCommandBase.getAgentSid4(),
				EnumAdaptor.valueOf(agentCommandBase.getAgentAppType4(), AgentAppType.class));
		
		//Validate Date
		List<AgentDto> agents = finder.findAllEmploy(employeeId);
		List<RangeDate> rangeDateList = agents.stream()
				.map(a -> new RangeDate(a.getStartDate(), a.getEndDate()))
				.collect(Collectors.toList());
//		List<AgentDto> agent = finder.findByCid();
//		List<AgentType> agentTypeList = agent.stream()
//				.map(a -> new AgentType(a.getAgentAppType1(),a.getAgentAppType2(),a.getAgentAppType3(),a.getAgentAppType4()))
//				.collect(Collectors.toList());
		agentInfor.validateDate(rangeDateList);
		//agentInfor.agentOfApp(agentTypeList);		
		agentRepository.add(agentInfor);
		
		return requestId.toString();
	}

}
