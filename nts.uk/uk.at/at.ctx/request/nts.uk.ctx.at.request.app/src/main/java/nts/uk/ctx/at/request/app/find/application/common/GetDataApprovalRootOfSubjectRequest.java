package nts.uk.ctx.at.request.app.find.application.common;


import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.approvalroot.ApprovalRootService;

@Stateless
public class GetDataApprovalRootOfSubjectRequest {
	
	@Inject 
	private ApprovalRootService approvalRootRepo;
	
	public List<ApprovalRootOfSubjectRequestDto> getApprovalRootOfSubjectRequest(ObjApprovalRootInput objApprovalRootInput){
		
		return approvalRootRepo.getApprovalRootOfSubjectRequest(objApprovalRootInput.getCid(), 
				objApprovalRootInput.getSid(), objApprovalRootInput.getEmploymentRootAtr(), 
				objApprovalRootInput.getAppType(),objApprovalRootInput.getStandardDate() )
				.stream()
				.map(c->ApprovalRootOfSubjectRequestDto.fromDomain(c))
				.collect(Collectors.toList());
		
	}

}
