package nts.uk.ctx.at.request.dom.application.common.appapprovalphase.service;

import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;

/**
 * 
 * @author hieult
 *
 */
public interface AppApprovalPhaseService {
		
	boolean isExist (String companyID , String appID , String appPhaseID);
	
	void createAppApprovalPhase(AppApprovalPhase appApprovalPhase);
	
	void updateAppApprovalPhase(AppApprovalPhase appApprovalPhase);
	
	void deleteAppApprovalPhase(String ID , String appID , String appPhaseID);
}
