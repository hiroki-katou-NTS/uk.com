package nts.uk.ctx.at.request.dom.application.lateorleaveearly.service;

import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;

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
	
}
