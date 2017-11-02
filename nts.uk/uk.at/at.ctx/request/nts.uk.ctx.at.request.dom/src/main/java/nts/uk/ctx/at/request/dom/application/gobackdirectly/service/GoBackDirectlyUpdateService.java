package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;

public interface GoBackDirectlyUpdateService {
	
	/**
	 * アルゴリズム「直行直帰更新前チェック」を実行する
	 * @return
	 */
	public void checkErrorBeforeUpdate(String employeeID, GeneralDate appDate, int employeeRouteAtr, String appID, PrePostAtr postAtr);
	/**
	 * アルゴリズム「直行直帰更新」を実行する
	 * @param goBackDirectly
	 */
	public void updateGoBackDirectly(GoBackDirectly goBackDirectly, Application application);
}
