package nts.uk.ctx.at.request.pubimp.service.approvalstatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.ApprovalStatusService;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalStatusEmployeeOutput;
import nts.uk.ctx.at.request.pub.service.approvalstatus.ApprovalStatusEmployeeExport;
import nts.uk.ctx.at.request.pub.service.approvalstatus.ApprovalStatusPub;

@Stateless
public class ApprovalStatusPubImpl implements ApprovalStatusPub {
	@Inject
	ApprovalStatusService approvalStatusService;

	@Override
	public List<ApprovalStatusEmployeeExport> getApprovalStatusEmployee(String wkpId, GeneralDate closureStart,
			GeneralDate closureEnd, List<String> listEmpCd) {
		List<ApprovalStatusEmployeeExport> result = new ArrayList<>();
		List<ApprovalStatusEmployeeOutput> data = approvalStatusService.getApprovalStatusEmployee(wkpId, closureStart,
				closureEnd, listEmpCd);
		if (data != null) {
			for (ApprovalStatusEmployeeOutput item : data) {
				ApprovalStatusEmployeeExport empExport = new ApprovalStatusEmployeeExport();
				empExport.setSId(item.getSId());
				empExport.setClosureStart(item.getClosureStart());
				empExport.setClosureEnd(item.getClosureEnd());
				result.add(empExport);
			}
			return result;
		}
		return Collections.emptyList();
	}
}
