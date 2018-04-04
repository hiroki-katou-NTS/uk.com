package nts.uk.ctx.at.shared.ac.dailyperformance;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.dailyperformance.DailyPerformanceAdapter;
import nts.uk.ctx.workflow.pub.service.ApprovalRootStatePub;
@Stateless
public class DailyPerformanceAdapterImpl implements DailyPerformanceAdapter {

	
	@Inject
	private ApprovalRootStatePub approvalRootStatePub;
	


	@Override
	public boolean checkDataApproveed(GeneralDate startDate, GeneralDate endDate, String approverID, Integer rootType,String companyID) {
		return approvalRootStatePub.checkDataApproveed(startDate, endDate, approverID, rootType, companyID);
	}

}
