package nts.uk.ctx.sys.gateway.app.command.login.saml.validate;

import lombok.Data;
import lombok.SneakyThrows;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.UkRelayState;

import javax.servlet.http.HttpServletRequest;

@Data
public class SamlValidateCommand implements LoginCommandHandlerBase.TenantAuth {
	
	private HttpServletRequest request;
	
	public SamlValidateCommand(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	@SneakyThrows
	public String getTenantCode() {
		return UkRelayState.deserialize(request).getTenantCode();
	}

	@Override
	@SneakyThrows
	public String getTenantPasswordPlainText() {
		return UkRelayState.deserialize(request).getTenantPassword();
	}
}
