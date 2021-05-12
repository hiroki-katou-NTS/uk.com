package nts.uk.ctx.at.function.app.nrl.request;

import javax.ejb.Stateless;

import nts.uk.shr.com.system.property.UKServerSystemProperties;
import nts.uk.shr.infra.data.TenantLocatorService;

/**
 * @author thanh_nx
 *
 *         タイムレコーダの通信を認証する
 */
@Stateless
public class AuthenticateNRCommunicationQuery {

	public boolean process(String contractCode) {

		if (!UKServerSystemProperties.isCloud())
			return false;

		TenantLocatorService.connect(contractCode);
		return true;
	}

}
