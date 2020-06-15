package nts.uk.ctx.at.request.dom.application.workchange;

import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

public interface IWorkChangeUpdateService {
	
	/**
	 * 勤務変更申請の更新処理
	 * @param app 申請
	 * @param workChange 勤務変更申請
	 * @return
	 */
	public ProcessResult updateWorkChange(Application_New app, AppWorkChange workChange);
}
