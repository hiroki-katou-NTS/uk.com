package nts.uk.ctx.sys.gateway.app.command.login.saml;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.operate.SamlOperation;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.operate.SamlRedirectUrl;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate.SamlClientId;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate.SamlIdpCertificate;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate.SamlIdpEntityId;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate.SamlResponseValidation;
import nts.uk.shr.com.context.AppContexts;

@Getter
public class RegisterSamlAuthSettingCommand {

	// SAMLによるシングルサインオンを使う
	private boolean useSingleSignOn;
	
	// リダイレクト先URL
	private String idpRedirectUrl;
	
	// クライアントID
	private String clientId;
	
	// IdPエンティティID
	private String idpEntityId;
	
	// IdP証明書
	private String idpCertificate;
	
	public SamlOperation getSamlOperation() {
		return new SamlOperation(AppContexts.user().contractCode(),
				this.useSingleSignOn,
				this.idpRedirectUrl.isEmpty()
						? Optional.empty()
						: Optional.of(new SamlRedirectUrl(this.idpRedirectUrl)));
	}
	
	public SamlResponseValidation getSamlResponseValidation() {
		return new SamlResponseValidation(AppContexts.user().contractCode(),
				new SamlClientId(this.clientId),
				new SamlIdpEntityId(this.idpEntityId),
				new SamlIdpCertificate(this.idpCertificate));
	}
}
