package nts.uk.ctx.sys.gateway.app.command.login.password;

import lombok.Data;

import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;

@Data
public class PasswordAuthenticateCommand implements LoginCommandHandlerBase.TenantAuth {

	private String contractCode;

	private String contractPassword;
	
	private String companyCode;
	
	private String employeeCode;
	
	private String password;

	@Override
	public String getTenantCode() {
		return this.contractCode;
	}

	@Override
	public String getTenantPasswordPlainText() {
		return this.contractPassword;
	}
}
