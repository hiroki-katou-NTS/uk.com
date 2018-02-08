package nts.uk.ctx.workflow.pubimp.agent;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.agent.AgentRepository;
import nts.uk.ctx.workflow.dom.agent.ApprovalAgencyInfoService;
import nts.uk.ctx.workflow.dom.agent.output.ApprovalAgencyInfoOutput;
import nts.uk.ctx.workflow.pub.agent.AgentAppTypeExport;
import nts.uk.ctx.workflow.pub.agent.AgentDataPubExport;
import nts.uk.ctx.workflow.pub.agent.AgentPub;
import nts.uk.ctx.workflow.pub.agent.AgentPubExport;
import nts.uk.ctx.workflow.pub.agent.ApproverRepresenterExport;
import nts.uk.ctx.workflow.pub.agent.RepresenterInformationExport;

@Stateless
public class AgentPubImpl implements AgentPub {

	@Inject
	private ApprovalAgencyInfoService approvalAgencyInfoService;
	
	private AgentRepository agentRepository;

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
	public List<AgentDataPubExport> getBySidDate(String companyId, String employeeId, GeneralDate baseDate) {
		List<AgentDataPubExport> lstData = agentRepository.findBySidDate(companyId, employeeId, baseDate)
				.stream()
				.map(x -> new AgentDataPubExport(companyId, 
						x.getEmployeeId(), 
						x.getRequestId(), 
						x.getStartDate(), 
						x.getEndDate(), 
						x.getAgentSid1(), 
						EnumAdaptor.valueOf(x.getAgentAppType1().value, AgentAppTypeExport.class),
						x.getAgentSid2(),
						EnumAdaptor.valueOf(x.getAgentAppType2().value, AgentAppTypeExport.class), 
						x.getAgentSid3(),
						EnumAdaptor.valueOf(x.getAgentAppType3().value, AgentAppTypeExport.class),
						x.getAgentSid4(), 
						EnumAdaptor.valueOf(x.getAgentAppType4().value, AgentAppTypeExport.class)))
				.collect(Collectors.toList());
		return lstData;
	}

}
