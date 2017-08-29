package nts.uk.ctx.at.request.dom.application.common.detailedscreenafterdenialprocess.service;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;

public interface DetailedScreenAfterDenialProcessService {
	/**
	 * 否認できるかチェックする true：否認できる false：否認できない
	 * 
	 */
	public boolean canDeniedCheck(String companyID, String appID, int startOrderNum, List<AppApprovalPhase> listPhase);
	/**
	 * 
	 * @param application
	 */
	public void detailedScreenAfterDenialProcess(String companyID, String appID);
}
