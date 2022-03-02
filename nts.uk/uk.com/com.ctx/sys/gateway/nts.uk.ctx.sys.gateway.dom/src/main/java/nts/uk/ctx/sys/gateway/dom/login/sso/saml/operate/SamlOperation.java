
package nts.uk.ctx.sys.gateway.dom.login.sso.saml.operate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.gul.security.saml.IdpEntryUrl;
import nts.gul.util.Either;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.UkRelayState;

import java.util.Optional;

@Getter
public class SamlOperation {

	private String tenantCode;
	
	private boolean useSingleSignOn;
	
	private Optional<SamlRedirectUrl> idpRedirectUrl;
	
	public SamlOperation(String tenantCode, boolean useSingleSignOn, Optional<SamlRedirectUrl> idpRedirectUrl) {
		this.tenantCode = tenantCode;
		this.useSingleSignOn = useSingleSignOn;

		if (useSingleSignOn && !idpRedirectUrl.isPresent()) {
			throw new RuntimeException("idpRedirectUrl is required");
		}
		this.idpRedirectUrl = idpRedirectUrl;
	}

	public Optional<IdpEntryUrl> tryCreateIdpEntryUrl(UkRelayState relayState) {

		if (!useSingleSignOn) {
			return Optional.empty();
		}

		return Optional.of(new IdpEntryUrl(idpRedirectUrl.get().v(), relayState.serialize()));
	}
}
