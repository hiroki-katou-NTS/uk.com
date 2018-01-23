package nts.uk.ctx.at.request.dom.application.common.service.newscreen.init;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;

/**
 * 1-5.新規画面起動時のエラーチェック
 * @author Doan Duy Hung
 *
 */
public interface StartupErrorCheckService {
	
	public void startupErrorCheck(GeneralDate baseDate, int appType, ApprovalRootContentImport_New approvalRootContentImport);
	
}
