package nts.uk.ctx.at.request.app.find.application.common;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.ConcurrentEmployeeRequest;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverInfoImport;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetDataApprovalRootOfSubjectRequest {
	
	@Inject 
	private ApprovalRootAdapter approvalRootRepo;
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	
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
					y.getApprovers().stream().forEach(z ->{
						if(Strings.isNotBlank(z.getJobId())) {
							List<ConcurrentEmployeeRequest> lstEmployeeByJob = employeeAdapter.getConcurrentEmployee(companyID, z.getJobId(), generalDate);
							String employeeName = "";
							for(ConcurrentEmployeeRequest concurr: lstEmployeeByJob) {
								employeeName += ", " + concurr.getPersonName();
							}
							if(!employeeName.isEmpty()) {
								employeeName = employeeName.substring(0, 2);
							}
							z.setName(employeeName);
						}
					});
				});
			});
			
		}
		return data;
	}

}
