package nts.uk.ctx.at.request.app.find.application.common;


import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootAdapter;

@Stateless
public class GetDataApprovalRootOfSubjectRequest {
	
	@Inject 
	private ApprovalRootAdapter approvalRootRepo;
	
	@Inject
	private EmployeeAdapter employeeAdapter;
	
	public List<ApprovalRootOfSubjectRequestDto> getApprovalRootOfSubjectRequest(ObjApprovalRootInput objApprovalRootInput){
		
//		 List<ApprovalRootOfSubjectRequestDto> list =  approvalRootRepo.getApprovalRootOfSubjectRequest(objApprovalRootInput.getCid(), 
//				objApprovalRootInput.getSid(), objApprovalRootInput.getEmploymentRootAtr(), 
//				objApprovalRootInput.getAppType(),objApprovalRootInput.getStandardDate() )
//				.stream()
//				.map(c->ApprovalRootOfSubjectRequestDto.fromDomain(c))
//				.collect(Collectors.toList());
//		 for(ApprovalPhaseOutput a :  list.get(0).getAfterApprovers()) {
//			 List<ApproverInfo> listApprover =  a.getApprovers();
//			 for(ApproverInfo approverInfo:listApprover) {
//				 String nameApprover  = employeeAdapter.getEmployeeName(approverInfo.getSid());
//				 approverInfo.setSid(nameApprover);
//			 }
//		 }
		return approvalRootRepo.getApprovalRootOfSubjectRequest(objApprovalRootInput.getCid(), 
				objApprovalRootInput.getSid(), objApprovalRootInput.getEmploymentRootAtr(), 
				objApprovalRootInput.getAppType(),objApprovalRootInput.getStandardDate() )
				.stream()
				.map(c->ApprovalRootOfSubjectRequestDto.fromDomain(c))
				.collect(Collectors.toList());
		
	}

}
