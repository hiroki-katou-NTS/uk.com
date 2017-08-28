package nts.uk.ctx.at.request.dom.application.common.newscreenregisteratapprovereflectionInfo.service;

import nts.uk.ctx.at.request.dom.application.common.Application;

/**
 * 2-2.新規画面登録時承認反映情報の整理
 */
public interface NewScreenRegisterAtApproveReflectionInfoService {
	/**
	 * 新規画面登録時承認反映情報の整理
	 * @param empID
	 * @param application
	 */
	public void newScreenRegisterAtApproveInfoReflect(String empID, Application application);

	/**
	 * 承認情報の整理
	 * 
	 * @param appID
	 */
	public void organizationOfApprovalInfo(String appID);
	
	/**
	 *実績反映状態の判断 
	 * @param appID
	 */
	public void performanceReflectedStateJudgment (String appID);

}
