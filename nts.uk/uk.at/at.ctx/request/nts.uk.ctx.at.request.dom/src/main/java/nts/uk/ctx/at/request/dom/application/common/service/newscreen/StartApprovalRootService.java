package nts.uk.ctx.at.request.dom.application.common.service.newscreen;

import nts.arc.time.GeneralDate;

/**
 * 1-4.新規画面起動時の承認ルート取得パターン
 */
public interface StartApprovalRootService {
	
	public void getApprovalRootPattern(String cid, String sid, int employmentRootAtr, int appType, GeneralDate appDate);
	
}
