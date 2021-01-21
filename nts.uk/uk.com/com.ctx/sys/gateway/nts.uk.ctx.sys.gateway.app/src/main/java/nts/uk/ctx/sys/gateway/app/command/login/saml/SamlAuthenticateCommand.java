package nts.uk.ctx.sys.gateway.app.command.login.saml;

import lombok.Data;

import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;

@Data
public class SamlAuthenticateCommand implements LoginCommandHandlerBase.TenantAuth {

	private String tenantCode;

	private String tenantPassword;
	
	private String issueUrl;

	private String requestUrl;

	@Override
	public String getTenantPasswordPlainText() {
		return this.tenantPassword;
	}
}
