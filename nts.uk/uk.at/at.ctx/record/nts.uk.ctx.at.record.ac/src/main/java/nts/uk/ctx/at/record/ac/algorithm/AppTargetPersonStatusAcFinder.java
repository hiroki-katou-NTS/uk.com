package nts.uk.ctx.at.record.ac.algorithm;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.algorithm.AppTargetPersonStatusAdapter;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.checkconfirm.StateConfirm;
import nts.uk.ctx.workflow.pub.service.ApprovalRootStatePub;
import nts.uk.ctx.workflow.pub.service.export.ApproveRootStatusForEmpExport;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class AppTargetPersonStatusAcFinder implements AppTargetPersonStatusAdapter {

	@Inject
	private ApprovalRootStatePub approvalRootStatePub; 
	
	@Override
	public List<StateConfirm> appTargetPersonStatus(String employeeID, GeneralDate startDate, GeneralDate endDate,
			int routeType) {
		
		List<ApproveRootStatusForEmpExport> listState = approvalRootStatePub.getApprovalByEmplAndDate(startDate, endDate, employeeID, AppContexts.user().companyId(), routeType);
		if(!listState.isEmpty()) {
			return listState.stream().map(c->convertToStateConfirm(c)).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
	
	private StateConfirm convertToStateConfirm(ApproveRootStatusForEmpExport export) {
		return new StateConfirm(
				export.getAppDate(),
				export.getApprovalStatus().value ==2?true:false
				);
	}
	
	
}
