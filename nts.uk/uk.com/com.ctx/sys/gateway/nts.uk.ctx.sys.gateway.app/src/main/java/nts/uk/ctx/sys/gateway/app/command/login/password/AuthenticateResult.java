package nts.uk.ctx.sys.gateway.app.command.login.password;

import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.PasswordAuthenticateResult;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.EmployeeIdentify.IdentificationResult;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.ValidationResultOnLogin;

@Value
public class AuthenticateResult implements LoginCommandHandlerBase.AuthenticationResult {

	/** 認証成功 */
	boolean success;
	
	/** 識別された社員 */
	Optional<IdentifiedEmployeeInfo> employeeInfo;
	
	/** パスワードポリシーの検証結果 */
	Optional<ValidationResultOnLogin> passwordValidation;
	
	/** ビルトインユーザ用 */
	private boolean isBuiltInUser;
	private String tenantCodeForBuiltInUser;
	private String companyIdForBuiltInUser;
	
	public static AuthenticateResult identificationFailure(IdentificationResult idenResult) {
		return new AuthenticateResult(
				false, 
				Optional.empty(), 
				Optional.empty(), 
				false, null, null);
	}
	
	public static AuthenticateResult passAuthenticateFailure(IdentificationResult idenResult, PasswordAuthenticateResult authResult) {
		return new AuthenticateResult(
				false, 
				idenResult.getEmployeeInfo(), 
				authResult.getPasswordValidation(), 
				false, null, null);
	}
	
	public static AuthenticateResult success(IdentificationResult idenResult, PasswordAuthenticateResult authResult) {
		return new AuthenticateResult(
				true, 
				idenResult.getEmployeeInfo(), 
				authResult.getPasswordValidation(), 
				false, null, null);
	}
	
	public static AuthenticateResult asBuiltInUser(String tenantCode, String companyId) {
		return new AuthenticateResult(
				true, 
				Optional.empty(), 
				Optional.empty(), 
				true, tenantCode, companyId);
	}

	@Override
	public boolean isSuccess() {
		return isBuiltInUser || success;
	}

	@Override
	public IdentifiedEmployeeInfo getIdentified() {
		return employeeInfo.get();
	}

}
