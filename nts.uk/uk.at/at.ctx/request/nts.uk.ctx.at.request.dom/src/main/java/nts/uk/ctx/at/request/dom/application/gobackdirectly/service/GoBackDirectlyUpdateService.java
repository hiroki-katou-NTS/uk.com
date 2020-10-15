package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly_Old;

public interface GoBackDirectlyUpdateService {
	
	/**
	 * アルゴリズム「直行直帰更新前チェック」を実行する
	 * @return
	 */
	public void checkErrorBeforeUpdate(GoBackDirectly_Old goBackDirectly, String companyID, String appID, Long version);
	/**
	 * アルゴリズム「直行直帰更新」を実行する
	 * @param goBackDirectly
	 */
	public ProcessResult updateGoBackDirectly(GoBackDirectly_Old goBackDirectly, Application application, Long version);
}
