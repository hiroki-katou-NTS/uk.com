package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;

public interface GoBackDirectlyService {
	/**
	 * 直行直帰登録
	 * 
	 * @param employeeID
	 * @param application
	 * @param goBackDirectly
	 */
	public void register(String employeeID, Application application, GoBackDirectly goBackDirectly);
}
