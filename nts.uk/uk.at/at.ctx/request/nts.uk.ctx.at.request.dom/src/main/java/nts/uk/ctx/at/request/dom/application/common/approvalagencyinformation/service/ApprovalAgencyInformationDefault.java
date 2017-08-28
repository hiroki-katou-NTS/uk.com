package nts.uk.ctx.at.request.dom.application.common.approvalagencyinformation.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.AgentAdaptorDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.AgentRequestAdaptor;
import nts.uk.ctx.at.request.dom.application.common.agentadapter.AgentAdapterDto;
import nts.uk.ctx.at.request.dom.application.common.agentadapter.AgentAppType;
import nts.uk.ctx.at.request.dom.application.common.approvalagencyinformation.ApprovalAgencyInformationOutput;
import nts.uk.ctx.at.request.dom.application.common.approvalagencyinformation.ObjApproverRepresenter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApprovalAgencyInformationDefault implements ApprovalAgencyInformationService {

	
//	@Inject
//	AgentRepository agentRepository;
	@Inject 
	private AgentRequestAdaptor agentRequestAdaptor;
	
	
	@Override
	public ApprovalAgencyInformationOutput getApprovalAgencyInformation(String companyID, List<String> approver) {
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		GeneralDate generalDate = GeneralDate.today(); 
		
		boolean outputFlag = true; 
		List<String>  outputListRepresenterSID = new ArrayList<>();
		List<ObjApproverRepresenter> outputListApproverAndRepresenterSID = new  ArrayList<>();
		// neu list nguoi xác nhận =  null
		if(approver.size()==0)
		{
			return new ApprovalAgencyInformationOutput(outputListApproverAndRepresenterSID, outputListRepresenterSID, outputFlag);
		}
		List<AgentAdaptorDto> listAgenta = agentRequestAdaptor.find(companyID, employeeId, generalDate,generalDate );
		//listAgent lấy từ domain Agent 
		List<AgentAdapterDto> listAgent = new ArrayList<>();
		//duyệt list người xác nhận
		for(String approveItem : approver ) {
			//duyệt list lấy được trong domain Agent
			for(AgentAdapterDto agentAdapterDto : listAgent) {
				//nếu người xác nhận có trong list Agent
				if(approveItem == agentAdapterDto.getEmployeeId()) {
					//ktra xem AgentAppType = No_Settings hay k
					if(agentAdapterDto.getAgentAppType1() == AgentAppType.NO_SETTINGS ) {
						outputFlag = false;
						// add nguoi xac nhan vao 
						ObjApproverRepresenter obj = new ObjApproverRepresenter(approveItem,"Empty");
						outputListApproverAndRepresenterSID.add(obj);
						
					}
					//ktra xem AgentAppType = PATH hay k
					if(agentAdapterDto.getAgentAppType1() == AgentAppType.PATH) {
						ObjApproverRepresenter obj = new ObjApproverRepresenter(approveItem,"Pass");
						outputListApproverAndRepresenterSID.add(obj);
					}
					
					//ktra xem AgentAppType = SUBSTITUTE_DESIGNATION hay k
					if(agentAdapterDto.getAgentAppType1() == AgentAppType.SUBSTITUTE_DESIGNATION) {
						outputFlag = false;
						ObjApproverRepresenter obj = new ObjApproverRepresenter(approveItem,agentAdapterDto.getAgentSid1());
						outputListApproverAndRepresenterSID.add(obj);
						//add data in list representerSID
						outputListRepresenterSID.add(approveItem);
					}
						
				}
			}
				
		}
		return new ApprovalAgencyInformationOutput(outputListApproverAndRepresenterSID, outputListRepresenterSID, outputFlag);
	}

	@Override
	public List<AgentAdapterDto> getAgentInfor(String requestId, GeneralDate startDate, GeneralDate endDate) {
		List<AgentAdapterDto> lstAgent =  new ArrayList<>();
		return lstAgent;
	}

}
