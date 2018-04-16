package nts.uk.ctx.at.request.app.find.application.proxy;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.proxy.ProxyApplicationService;

@Stateless
public class ProxyApplicationFinder {
	
	@Inject
	private ProxyApplicationService proxyApplicationService;

	/**
	 * 申請種類選択
	 * @param employeeId
	 * @param applicationType
	 */
	public void selectApplicationByType(ProxyParamFind proxyParamFind) {
		this.proxyApplicationService.selectApplicationByType(proxyParamFind.getEmployeeIds(), proxyParamFind.getApplicationType());
	}
}
