package nts.uk.ctx.at.request.dom.application.common.adapter.workflow;

import nts.arc.time.GeneralDate;

public interface ApprovalRootStateAdapter {
	
	public void insertByAppType(String companyID, String employeeID, Integer appTypeValue, GeneralDate date, String historyID, String appID);
	
}
