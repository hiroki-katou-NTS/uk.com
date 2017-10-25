package nts.uk.ctx.at.request.dom.application.common.service.other;
//package nts.uk.ctx.at.request.dom.application.common.service.other;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import nts.arc.time.GeneralDate;
//
//import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.AgentRequestAdapter;
//import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentImport;
//import nts.uk.ctx.at.request.dom.application.common.service.other.output.AgentApplicationType;
//import nts.uk.ctx.at.request.dom.application.common.service.other.output.ApprovalAgencyInformationOutput;
//import nts.uk.ctx.at.request.dom.application.common.service.other.output.ObjApproverRepresenterOutput;
//
///**
// * 
// * @author tutk
// *
// */
//@Stateless
//public class ApprovalAgencyInformationImpl implements ApprovalAgencyInformation {
//
//	
////	@Inject
////	AgentRepository agentRepository;
//	@Inject 
//	private AgentRequestAdapter agentRequestAdaptor;
//	
//	
//	@Override
//	public ApprovalAgencyInformationOutput getApprovalAgencyInformation(String companyID, List<String> approver) {
//		
//		GeneralDate generalDate = GeneralDate.today(); 
//		
//		boolean outputFlag = true; 
//		List<String>  outputListRepresenterSID = new ArrayList<>();
//		List<ObjApproverRepresenterOutput> outputListApproverAndRepresenterSID = new  ArrayList<>();
//		// neu list nguoi xác nhận =  null
//		if(approver.size()==0)
//		{
//			return new ApprovalAgencyInformationOutput(outputListApproverAndRepresenterSID, outputListRepresenterSID, outputFlag);
//		}
//		//ドメインモデル「代行承認」を取得する(lấy thông tin domain 「代行承認」)
//		List<AgentImport> listAgenta = agentRequestAdaptor.findAll(companyID, approver, generalDate,generalDate );
//		
//		//duyệt list người xác nhận
//		for(String approveItem : approver ) {
//			//duyệt list lấy được trong domain Agent
//			for(AgentImport agentAdapterDto : listAgenta) {
//				//nếu người xác nhận có trong list Agent
//				if(approveItem.equals(agentAdapterDto.getEmployeeId())) {
//					//ktra xem AgentAppType = No_Settings hay k
//					if(agentAdapterDto.getAgentSid1() == null || agentAdapterDto.getAgentAppType1() == AgentApplicationType.NO_SETTINGS ) {
//					//if(agentAdapterDto.getAgentAppType1() == AgentApplicationType.NO_SETTINGS ) {
//						outputFlag = false;
//						// add nguoi xac nhan vao 
//						ObjApproverRepresenterOutput obj = new ObjApproverRepresenterOutput(approveItem,"Empty");
//						outputListApproverAndRepresenterSID.add(obj);
//						
//					}
//					//ktra xem AgentAppType = PATH hay k
//					if(agentAdapterDto.getAgentAppType1() == AgentApplicationType.PATH) {
//						ObjApproverRepresenterOutput obj = new ObjApproverRepresenterOutput(approveItem,"Pass");
//						outputListApproverAndRepresenterSID.add(obj);
//					}
//					//ktra xem AgentAppType = SUBSTITUTE_DESIGNATION hay k
//					if(agentAdapterDto.getAgentAppType1() == AgentApplicationType.SUBSTITUTE_DESIGNATION) {
//						outputFlag = false;
//						ObjApproverRepresenterOutput obj = new ObjApproverRepresenterOutput(approveItem,agentAdapterDto.getAgentSid1());
//						outputListApproverAndRepresenterSID.add(obj);
//						//add data in list representerSID
//						outputListRepresenterSID.add(agentAdapterDto.getAgentSid1());
//					}
//						
//				}
//			}
//				
//		}
//		return new ApprovalAgencyInformationOutput(outputListApproverAndRepresenterSID, outputListRepresenterSID, outputFlag);
//	}
//
//
//}
