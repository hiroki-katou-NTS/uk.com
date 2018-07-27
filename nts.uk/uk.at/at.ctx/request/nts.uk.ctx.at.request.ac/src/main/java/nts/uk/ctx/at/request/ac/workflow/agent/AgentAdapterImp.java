package nts.uk.ctx.at.request.ac.workflow.agent;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.AgentAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentAppTypeRequestImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentDataRequestPubImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentInfoImport;
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
	

	/**
	 * 代行者、期間から承認代行情報を取得する			
	 */
	public List<AgentDataRequestPubImport> lstAgentData(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate) {
		List<AgentDataRequestPubImport> lstData = AgentPub.getBySidDate(companyId, employeeId, startDate, endDate)
				.stream()
				.map(x -> new AgentDataRequestPubImport(companyId, 
						x.getEmployeeId(), 
						x.getRequestId(), 
						x.getStartDate(),
						x.getEndDate(), 
						x.getAgentSid1(), 
						EnumAdaptor.valueOf(x.getAgentAppType1().value,AgentAppTypeRequestImport.class),
						x.getAgentSid2(),
						EnumAdaptor.valueOf(x.getAgentAppType2().value ,AgentAppTypeRequestImport.class), 
						x.getAgentSid3(), 
						EnumAdaptor.valueOf(x.getAgentAppType3().value ,AgentAppTypeRequestImport.class),
						x.getAgentSid4(), 
						EnumAdaptor.valueOf(x.getAgentAppType4().value ,AgentAppTypeRequestImport.class))).collect(Collectors.toList());
		return lstData;
	}
	
	@Override
	public List<AgentInfoImport> findAgentByPeriod(String companyID, List<String> listApprover, 
			GeneralDate startDate, GeneralDate endDate, Integer agentType) {
		List<AgentInfoImport> listAgentInfor = AgentPub.findAgentByPeriod(companyID, listApprover, startDate, endDate, agentType)
				.stream()
				.map(x -> new AgentInfoImport(x.getApproverID(), x.getAgentID(), x.getStartDate(), x.getEndDate())).collect(Collectors.toList());
		return listAgentInfor;
	}

	@Override
	public List<AgentDataRequestPubImport> lstAgentBySidData(String companyId, String employeeId, GeneralDate startDate,
			GeneralDate endDate) {
		List<AgentDataRequestPubImport> lstData = AgentPub.getAgentBySidDate(companyId, employeeId, startDate, endDate)
				.stream()
				.map(x -> new AgentDataRequestPubImport(companyId, 
						x.getEmployeeId(), 
						x.getRequestId(), 
						x.getStartDate(),
						x.getEndDate(), 
						x.getAgentSid1(), 
						EnumAdaptor.valueOf(x.getAgentAppType1().value,AgentAppTypeRequestImport.class),
						x.getAgentSid2(),
						EnumAdaptor.valueOf(x.getAgentAppType2().value ,AgentAppTypeRequestImport.class), 
						x.getAgentSid3(), 
						EnumAdaptor.valueOf(x.getAgentAppType3().value ,AgentAppTypeRequestImport.class),
						x.getAgentSid4(), 
						EnumAdaptor.valueOf(x.getAgentAppType4().value ,AgentAppTypeRequestImport.class))).collect(Collectors.toList());
		return lstData;
	}

}
