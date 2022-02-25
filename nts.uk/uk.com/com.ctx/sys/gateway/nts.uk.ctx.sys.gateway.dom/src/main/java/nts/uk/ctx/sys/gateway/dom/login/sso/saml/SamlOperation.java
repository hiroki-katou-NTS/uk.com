
package nts.uk.ctx.sys.gateway.dom.login.sso.saml;

import lombok.Getter;
import nts.gul.security.saml.IdpEntryUrl;
import nts.gul.util.Either;

import java.util.Optional;

@Getter
public class SamlOperation {

	private String tenantCode;
	
	private boolean useSingleSignOn;
	
	private String idpRedirectUrl;
	
	public SamlOperation(String tenantCode, boolean useSingleSignOn, String idpRedirectUrl) {
		this.tenantCode = tenantCode;
		this.useSingleSignOn = useSingleSignOn;
		this.idpRedirectUrl = idpRedirectUrl;
	}

	public Optional<IdpEntryUrl> tryCreateIdpEntryUrl(UkRelayState relayState) {

		if (!useSingleSignOn) {
			return Optional.empty();
		}

		return Optional.of(new IdpEntryUrl(idpRedirectUrl, relayState.serialize()));
	}
}
