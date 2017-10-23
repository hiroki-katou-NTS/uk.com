package nts.uk.ctx.at.request.app.find.application.common;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.adapter.workflow.ApprovalRootAdapter;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.adapter.workflow.dto.ApproverInfoImport;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetDataApprovalRootOfSubjectRequest {
	
	@Inject 
	private ApprovalRootAdapter approvalRootRepo;

	
	public List<ApprovalRootOfSubjectRequestDto> getApprovalRootOfSubjectRequest(ObjApprovalRootInput objApprovalRootInput){
		String companyID = AppContexts.user().companyId();
		GeneralDate generalDate = GeneralDate.fromString(objApprovalRootInput.getStandardDate(), "yyyy/MM/dd");
		List<ApprovalRootOfSubjectRequestDto> data =  this.approvalRootRepo.getApprovalRootOfSubjectRequest( companyID,
				objApprovalRootInput.getSid(), objApprovalRootInput.getEmploymentRootAtr(), 
				objApprovalRootInput.getAppType(),generalDate)
				.stream()
				.map(c->ApprovalRootOfSubjectRequestDto.fromDomain(c))
				.collect(Collectors.toList());
		if(!CollectionUtil.isEmpty(data)) {
			
			data.forEach(x -> {
				x.getBeforeApprovers().stream().forEach(y -> {
					Collections.sort(y.getApprovers(), Comparator.comparing(ApproverInfoImport :: getOrderNumber));					
				});
			});
			
		}
		return data;
	}

}
