package nts.uk.ctx.at.request.dom.application.workchange;


import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

public interface IWorkChangeUpdateService {
	/**
	 * 勤務変更申請の更新処理
	 * @param companyId
	 * @param app
	 * @param workChange
	 * @return
	 */
	public ProcessResult updateWorkChange(String companyId, Application app, AppWorkChange workChange);
}
