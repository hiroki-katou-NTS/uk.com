package nts.uk.ctx.at.request.dom.application.common.service.newscreen;

import nts.uk.ctx.at.request.dom.application.Application;

/**
 * 2-2.新規画面登録時承認反映情報の整理
 */
public interface RegisterAtApproveReflectionInfoService_New {
	/**
	 * 新規画面登録時承認反映情報の整理
	 * @param empID
	 * @param application
	 */
	public void newScreenRegisterAtApproveInfoReflect(String SID, Application application);

}
