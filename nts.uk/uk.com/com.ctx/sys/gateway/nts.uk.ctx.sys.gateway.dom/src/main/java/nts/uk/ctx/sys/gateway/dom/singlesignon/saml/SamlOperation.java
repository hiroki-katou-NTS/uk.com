
package nts.uk.ctx.sys.gateway.dom.singlesignon.saml;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class SamlOperation {
	
	private String tenantCode;
	
	private boolean useSingleSignOn;
	
	private String realmName;
	
	private String idpRedirectUrl;
}
