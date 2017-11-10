package nts.uk.ctx.workflow.pubimp.agent;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.workflow.dom.agent.ApprovalAgencyInfoService;
import nts.uk.ctx.workflow.dom.agent.output.ApprovalAgencyInfoOutput;
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

}
