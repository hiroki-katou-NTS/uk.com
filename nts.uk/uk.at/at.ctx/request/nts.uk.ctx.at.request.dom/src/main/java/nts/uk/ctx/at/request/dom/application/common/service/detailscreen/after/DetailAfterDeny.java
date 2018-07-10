package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface DetailAfterDeny {
	// 9-2.詳細画面否認後の処理
	public ProcessResult doDeny(String companyID, String appID, String employeeID, String memo);

}
