package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
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
	public ProcessResult updateGoBackDirectly(GoBackDirectly_Old goBackDirectly, Application_New application, Long version);
}
