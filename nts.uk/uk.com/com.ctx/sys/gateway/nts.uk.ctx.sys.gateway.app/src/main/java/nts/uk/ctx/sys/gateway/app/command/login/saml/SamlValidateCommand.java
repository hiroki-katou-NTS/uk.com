package nts.uk.ctx.sys.gateway.app.command.login.saml;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;
import nts.gul.security.saml.RelayState;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;

@Data
public class SamlValidateCommand implements LoginCommandHandlerBase.TenantAuth {
	
	private HttpServletRequest request;
	
	public SamlValidateCommand(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public String getTenantCode() {
		RelayState rs = RelayState.deserialize(request.getParameter("RelayState"));
		return rs.get("tenantCode");
	}

	@Override
	public String getTenantPasswordPlainText() {
		RelayState rs = RelayState.deserialize(request.getParameter("RelayState"));
		return rs.get("tenantPassword");
	}
}
