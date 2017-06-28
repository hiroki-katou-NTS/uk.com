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
		
		/**
		 * validate date
		 */
		List<AgentDto> agents = finder.findAllEmploy(employeeId);
		List<RangeDate> rangeDateList = agents.stream()
				.map(a -> new RangeDate(a.getStartDate(), a.getEndDate()))
				.collect(Collectors.toList());

		agentInfor.validateDate(rangeDateList);
		
		/**
		 * validate agent of approval
		 */
		List<Agent> agentSidList = agentRepository.findByCid(companyId);
		
		List<RangeDate> rangeDateList1 = agentSidList.stream()
				.filter(x->x.getAgentSid1().equals(agentCommandBase.getAgentSid1()))
				.map(a -> new RangeDate(a.getStartDate(), a.getEndDate()))
				.collect(Collectors.toList());
		
		List<RangeDate> rangeDateList2 = agentSidList.stream()
				.filter(x->x.getAgentSid2().equals(agentCommandBase.getAgentSid2()))
				.map(a -> new RangeDate(a.getStartDate(), a.getEndDate()))
				.collect(Collectors.toList());
		
		List<RangeDate> rangeDateList3 = agentSidList.stream()
				.filter(x->x.getAgentSid3().equals(agentCommandBase.getAgentSid3()))
				.map(a -> new RangeDate(a.getStartDate(), a.getEndDate()))
				.collect(Collectors.toList());
		
		List<RangeDate> rangeDateList4 = agentSidList.stream()
				.filter(x->x.getAgentSid4().equals(agentCommandBase.getAgentSid4()))
				.map(a -> new RangeDate(a.getStartDate(), a.getEndDate()))
				.collect(Collectors.toList());
		
		agentInfor.checkAgentSid(rangeDateList1, rangeDateList2, rangeDateList3, rangeDateList4);
		
		/**
		 * add agent
		 */
		agentRepository.add(agentInfor);
		
		return requestId.toString();
	}

}
