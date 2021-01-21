package nts.uk.ctx.at.request.dom.application.workchange;


import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;

public interface IWorkChangeUpdateService {
	/**
	 * 勤務変更申請の更新処理
	 * @param companyId
	 * @param app
	 * @param workChange
	 * @return
	 */
	public ProcessResult updateWorkChange(String companyId, Application app, AppWorkChange workChange, AppDispInfoStartupOutput appDispInfoStartup);
}
