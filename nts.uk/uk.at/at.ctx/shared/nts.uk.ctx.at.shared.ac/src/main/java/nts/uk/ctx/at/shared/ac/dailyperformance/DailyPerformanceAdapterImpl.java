package nts.uk.ctx.at.shared.ac.dailyperformance;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.dailyperformance.DailyPerformanceAdapter;
import nts.uk.ctx.at.shared.dom.adapter.dailyperformance.PresenceDataApprovedImport;
import nts.uk.ctx.workflow.pub.service.ApprovalRootStatePub;
@Stateless
public class DailyPerformanceAdapterImpl implements DailyPerformanceAdapter {
/*
	@Inject
	private ApprovalRootStatePub approvalRootStatePub;*/
	
	@Inject
	private ApprovalRootStatePub approvalRootStatePub;
	
	@Override
	public PresenceDataApprovedImport findByIdDateAndType(String employeeID, GeneralDate startDate, GeneralDate endDate, int rootType) {
		// TODO Auto-generated method stub
		return new PresenceDataApprovedImport(false);
	}

	@Override
	public boolean checkDataApproveed(GeneralDate startDate, GeneralDate endDate, String approverID, Integer rootType,String companyID) {
		return approvalRootStatePub.checkDataApproveed(startDate, endDate, approverID, rootType, companyID);
	}

}
