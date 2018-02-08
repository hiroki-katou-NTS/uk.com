package nts.uk.ctx.workflow.pubimp.agent;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.agent.Agent;
import nts.uk.ctx.workflow.dom.agent.ApprovalAgencyInfoService;
import nts.uk.ctx.workflow.dom.agent.output.ApprovalAgencyInfoOutput;
import nts.uk.ctx.workflow.pub.agent.AgentExport;
import nts.uk.ctx.workflow.pub.agent.AgentPub;
import nts.uk.ctx.workflow.pub.agent.AgentPubExport;
import nts.uk.ctx.workflow.pub.agent.ApproverRepresenterExport;
import nts.uk.ctx.workflow.pub.agent.RepresenterInformationExport;

@Stateless
public class AgentPubImpl implements AgentPub {

	@Inject
	private ApprovalAgencyInfoService approvalAgencyInfoService;

	@Override
	public AgentPubExport getApprovalAgencyInformation(String companyID, List<String> approver) {
		ApprovalAgencyInfoOutput agency = approvalAgencyInfoService.getApprovalAgencyInformation(companyID, approver);
		return new AgentPubExport(
				agency.getListApproverAndRepresenterSID().stream().map(x -> new ApproverRepresenterExport(
						x.getApprover(), 
						new RepresenterInformationExport(x.getRepresenter().getValue()))).collect(Collectors.toList()), 
				agency.getListRepresenterSID(),
				agency.isFlag()
				);
	}

	@Override
	public List<AgentExport> getApprovalAgencyInfoByPeriod(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate) {
		List<Agent> agents = approvalAgencyInfoService.getApprovalAgencyInfoByPeriod(companyId, employeeId, startDate, endDate);
		
		return agents.stream().map(x -> {
			return AgentExport.builder()
					.companyId(x.getCompanyId())
					.employeeId(x.getEmployeeId())
					.requestId(x.getRequestId().toString())
					.startDate(x.getStartDate())
					.endDate(x.getEndDate())
					.agentSid1(x.getAgentSid1())
					.agentAppType1(x.getAgentAppType1().value)
					.agentSid2(x.getAgentSid2())
					.agentAppType2(x.getAgentAppType2().value)
					.agentSid3(x.getAgentSid3())
					.agentAppType3(x.getAgentAppType3().value)
					.agentSid4(x.getAgentSid4())
					.agentAppType4(x.getAgentAppType4().value)
					.build();
		}).collect(Collectors.toList());
	}
}
