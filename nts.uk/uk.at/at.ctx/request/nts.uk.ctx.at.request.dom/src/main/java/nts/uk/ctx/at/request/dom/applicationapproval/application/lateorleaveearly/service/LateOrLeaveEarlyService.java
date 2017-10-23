package nts.uk.ctx.at.request.dom.applicationapproval.application.lateorleaveearly.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.applicationapproval.application.Application;
import nts.uk.ctx.at.request.dom.applicationapproval.application.lateorleaveearly.LateOrLeaveEarly;

/**
 * 
 * @author hieult
 *
 */


public interface LateOrLeaveEarlyService {
	
	boolean isExist(String companyID, String appID);
	
	void createLateOrLeaveEarly(LateOrLeaveEarly lateOrLeaveEarly);
	
	void updateLateOrLeaveEarly(LateOrLeaveEarly lateOrLeaveEarly);
	
	void deleteLateOrLeaveEarly (String companyID, String appID);
	
    void registerLateOrLeaveEarly (String companyID);
    
    public String getApplicantName(String employeeID);
	
    void changeApplication (String companyID , String appID, GeneralDate applicationDate, int actualCancelAtr, int early1,
			int earlyTime1, int late1, int lateTime1, int early2, int earlyTime2, int late2, int lateTime2,
			String reasonTemp, String appReason);
}
