package nts.uk.ctx.at.request.ac.workflow.agent;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.AgentAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentPubImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverRepresenterImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.RepresenterInformationImport;
import nts.uk.ctx.workflow.pub.agent.AgentPub;
import nts.uk.ctx.workflow.pub.agent.AgentPubExport;
import nts.uk.ctx.workflow.pub.agent.ApproverRepresenterExport;

@Stateless
public class AgentAdapterImp implements AgentAdapter {
	
	@Inject
	private AgentPub AgentPub;

	@Override
	public AgentPubImport getApprovalAgencyInformation(String companyID, List<String> approver) {
		// TODO Auto-generated method stub
		return convertAgentPubImport(this.AgentPub.getApprovalAgencyInformation(companyID, approver));
	}
	
	private AgentPubImport convertAgentPubImport(AgentPubExport agentPubExport) {
		return new AgentPubImport(
				agentPubExport.getListApproverAndRepresenterSID().stream()
				.map(x -> this.covertApproverImport(x)).collect(Collectors.toList()),
				agentPubExport.getListRepresenterSID(),
				agentPubExport.isFlag()
				);
		
	}
	
	private ApproverRepresenterImport covertApproverImport(ApproverRepresenterExport approverRepresenterExport) {
		return new  ApproverRepresenterImport(
				approverRepresenterExport.getApprover(),
				new RepresenterInformationImport(approverRepresenterExport.getRepresenter().getValue()) 
				);
	}

}
