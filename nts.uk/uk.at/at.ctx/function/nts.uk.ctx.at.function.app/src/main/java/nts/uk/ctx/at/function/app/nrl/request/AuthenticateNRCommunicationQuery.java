package nts.uk.ctx.at.function.app.nrl.request;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.RQEmpInfoTerminalAdapter;
import nts.uk.shr.com.system.property.UKServerSystemProperties;
import nts.uk.shr.infra.data.TenantLocatorService;

/**
 * @author thanh_nx
 *
 *         タイムレコーダの通信を認証する
 */
@Stateless
public class AuthenticateNRCommunicationQuery {

	@Inject
	private RQEmpInfoTerminalAdapter rqEmpInfoTerminalAdapter;
	
	public boolean process(String contractCode, String macAddr) {

		if (UKServerSystemProperties.usesTenantLocator()) {
			TenantLocatorService.connect(contractCode);
		}
		
		return rqEmpInfoTerminalAdapter.getEmpInfoTerminalCode(contractCode, macAddr).isPresent();

	}

}
