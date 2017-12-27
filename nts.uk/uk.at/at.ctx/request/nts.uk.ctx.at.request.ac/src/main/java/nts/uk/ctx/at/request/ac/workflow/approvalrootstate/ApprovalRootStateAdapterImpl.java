package nts.uk.ctx.at.request.ac.workflow.approvalrootstate;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.workflow.pub.service.ApprovalRootStatePub;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ApprovalRootStateAdapterImpl implements ApprovalRootStateAdapter {
	
	@Inject
	private ApprovalRootStatePub approvalRootStatePub;

	@Override
	public void insertByAppType(String companyID, String employeeID, Integer appTypeValue, GeneralDate date,
			String historyID, String appID) {
		approvalRootStatePub.insertAppRootType(companyID, employeeID, appTypeValue, date, historyID, appID);
	}
	
}
