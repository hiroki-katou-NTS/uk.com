package nts.uk.ctx.sys.gateway.app.command.login.password;

import lombok.Data;

import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;

@Data
public class PasswordAuthenticateCommand implements LoginCommandHandlerBase.TenantAuth {

	private String tenantCode;

	private String tenantPassword;
	
	private String companyCode;
	
	private String employeeCode;
	
	private String password;

	@Override
	public String getTenantPasswordPlainText() {
		return this.tenantPassword;
	}

}
