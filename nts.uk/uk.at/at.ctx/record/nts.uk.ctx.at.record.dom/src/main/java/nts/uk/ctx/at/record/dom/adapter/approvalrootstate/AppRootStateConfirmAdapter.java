package nts.uk.ctx.at.record.dom.adapter.approvalrootstate;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface AppRootStateConfirmAdapter {
	
	AppRootStateConfirmImport appRootStateConfirmFinder(String companyID, String employeeID, 
			Integer confirmAtr, Integer appType, GeneralDate date);

	void clearAppRootstate(String rootId);
	
	void deleteApprovalByEmployeeIdAndDate(String employeeID, GeneralDate date);
	
	Request113Import getAppRootStatusByEmpPeriod(String employeeID, DatePeriod period, Integer rootType);
}
