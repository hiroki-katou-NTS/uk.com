package nts.uk.ctx.at.record.ac.request.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.request.service.ApprovalStatusRequestAdapter;
import nts.uk.ctx.at.record.dom.adapter.request.service.RealityStatusEmployeeImport;
import nts.uk.ctx.at.request.pub.service.approvalstatus.ApprovalStatusPub;

@Stateless
public class ApprovalStatusRequestImpl implements ApprovalStatusRequestAdapter {
	@Inject
	ApprovalStatusPub approvalStatusPub;

	@Override
	public List<RealityStatusEmployeeImport> getApprovalStatusEmployee(String wkpId, GeneralDate closureStart,
			GeneralDate closureEnd, List<String> listEmpCd) {
		return approvalStatusPub.getApprovalStatusEmployee(wkpId, closureStart, closureEnd, listEmpCd).stream()
				.map(x -> new RealityStatusEmployeeImport(x.getSId(), x.getClosureStart(), x.getClosureEnd()))
				.collect(Collectors.toList());
	}

}
