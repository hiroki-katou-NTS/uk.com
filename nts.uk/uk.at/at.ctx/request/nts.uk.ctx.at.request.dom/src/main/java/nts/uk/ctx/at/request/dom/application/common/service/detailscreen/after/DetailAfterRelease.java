package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;

/**
 * 
 * @author hieult
 *
 */
/**
 * 
 * 10-2.詳細画面解除後の処理
 *
 */
public interface DetailAfterRelease {
	
	/**
	 * 10-2.詳細画面解除後の処理
	 * @param application Application
	 * @param loginID Login ID
	 */
	public void detailAfterRelease(Application application, String loginID);

	/**
	 * 1.解除できるかチェックする
	 * @param appApprovalPhase AppApprovalPhase
	 * @param loginID Login ID
	 * @return
	 */
	public boolean cancelCheck(AppApprovalPhase appApprovalPhase, String loginID);
	
}
