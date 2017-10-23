package nts.uk.ctx.at.request.dom.applicationapproval.application.common.service.detailscreen.after;

import nts.uk.ctx.at.request.dom.applicationapproval.application.Application;
/**
 * 9-2.詳細画面否認後の処理
 * 
 * @author ducPM
 *
 */
public interface AfterDenialProcess {
	/**
	 * 否認できるかチェックする true：否認できる false：否認できない
	 * 
	 */
	public boolean canDeniedCheck(Application application, int startOrderNum);
	/**
	 * 
	 * @param application
	 */
	public String detailedScreenAfterDenialProcess(Application application, String memo);
}
