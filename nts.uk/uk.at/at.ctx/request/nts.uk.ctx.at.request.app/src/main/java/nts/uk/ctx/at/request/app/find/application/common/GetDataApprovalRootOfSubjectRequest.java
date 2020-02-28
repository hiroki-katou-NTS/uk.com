package nts.uk.ctx.at.request.app.find.application.common;

/*import java.util.Collections;
import java.util.Comparator;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.ConcurrentEmployeeRequest;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;*/
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootAdapter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetDataApprovalRootOfSubjectRequest {
	
	@Inject 
	private ApprovalRootAdapter approvalRootRepo;
//	@Inject
//	private EmployeeRequestAdapter employeeAdapter;
	
	public List<ApprovalRootOfSubjectRequestDto> getApprovalRootOfSubjectRequest(ObjApprovalRootInput objApprovalRootInput){
		String companyID = AppContexts.user().companyId();
		String sid = "";
		if(Strings.isBlank(objApprovalRootInput.getSid())){
			sid = AppContexts.user().employeeId();
		} else {
			sid = objApprovalRootInput.getSid();
		}
		GeneralDate generalDate = GeneralDate.fromString(objApprovalRootInput.getStandardDate(), "yyyy/MM/dd");
		List<ApprovalRootOfSubjectRequestDto> data =  this.approvalRootRepo.getApprovalRootOfSubjectRequest(companyID,
				sid, objApprovalRootInput.getEmploymentRootAtr(), 
				objApprovalRootInput.getAppType(),generalDate, 0)
				.stream()
				.map(c->ApprovalRootOfSubjectRequestDto.fromDomain(c))
				.collect(Collectors.toList());
		return data;
	}

}
