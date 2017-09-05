package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
/**
 * 詳細画面否認後の処理
 * @author ducPM
 *
 */
public interface AfterDenialProcess {
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
