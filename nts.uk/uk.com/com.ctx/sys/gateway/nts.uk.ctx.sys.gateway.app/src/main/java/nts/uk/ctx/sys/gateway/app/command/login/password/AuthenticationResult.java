package nts.uk.ctx.sys.gateway.app.command.login.password;

import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.PasswordAuthenticationResult;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.EmployeeIdentify.IdentificationResult;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.ValidationResultOnLogin;

@Value
public class AuthenticationResult implements LoginCommandHandlerBase.AuthenticationResultBase {

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
	
	public static AuthenticationResult identificationFailure(IdentificationResult idenResult) {
		return new AuthenticationResult(
				false, 
				Optional.empty(), 
				Optional.empty(), 
				false, null, null);
	}
	
	public static AuthenticationResult passAuthenticateFailure(IdentificationResult idenResult, PasswordAuthenticationResult authResult) {
		return new AuthenticationResult(
				false, 
				Optional.of(idenResult.getEmployeeInfo()), 
				Optional.of(authResult.getPasswordValidation()), 
				false, null, null);
	}
	
	public static AuthenticationResult success(IdentificationResult idenResult, PasswordAuthenticationResult authResult) {
		return new AuthenticationResult(
				true, 
				Optional.of(idenResult.getEmployeeInfo()), 
				Optional.of(authResult.getPasswordValidation()), 
				false, null, null);
	}
	
	public static AuthenticationResult asBuiltInUser(String tenantCode, String companyId) {
		return new AuthenticationResult(
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
