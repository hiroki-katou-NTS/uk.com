package nts.uk.ctx.sys.gateway.app.command.login.saml;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.onelogin.saml2.util.Constants;

import nts.gul.security.saml.SamlResponseValidator;
import nts.gul.security.saml.SamlResponseValidator.ValidateException;
import nts.gul.security.saml.SamlSetting;
import nts.gul.security.saml.ValidSamlResponse;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.app.command.loginold.dto.CheckChangePassDto;
import nts.uk.ctx.sys.gateway.dom.login.adapter.SysEmployeeAdapter;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;
import nts.uk.ctx.sys.gateway.dom.singlesignon.saml.IdpUserAssociation;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SamlValidateCommandHandler 
extends LoginCommandHandlerBase<SamlValidateCommand, SamlValidateCommandHandler.LoginState, CheckChangePassDto>{
	
	@Inject
	private  SysEmployeeAdapter employeeAdapter;
	
	// テナント認証失敗時
	@Override
	protected CheckChangePassDto getResultOnFailTenantAuth() {
		return CheckChangePassDto.failedToAuthTenant();
	}
	
	// 認証処理
	@Override
	protected LoginState processBeforeLogin(SamlValidateCommand command) {
		HttpServletRequest request = command.getRequest();

		SamlSetting samlSetting = new SamlSetting();
		// クライアント名(UKとかHLとか)
		samlSetting.SetSpEntityId("sso");
		// IdpのURL
		samlSetting.SetIdpEntityId("http://localhost:8180/auth/realms/my_territory");
		// 署名用アルゴリズム
		samlSetting.SetSignatureAlgorithm(Constants.RSA_SHA1);
		// X509証明書のFingerPrint
		// https://www.samltool.com/fingerprint.php ←ココで作れる
		samlSetting.SetIdpCertFingerprint("8dfc4a658496a05a3ed44357d97865007071b6e6");

		ValidSamlResponse validateResult;
		
		try {
			// SAMLResponseの検証処理
			validateResult = SamlResponseValidator.validate(request, samlSetting);
			
			// 認証失敗時
			if (!validateResult.isValid()) {
				return LoginState.failed();
				// 通常ログイン画面へ
			}

			// Idpユーザと社員の紐付けから社員を特定
			Optional<String> employeeID = IdpUserAssociation.getAssociateEmployee(validateResult.getIdpUser());

			// 社員特定できない
			if (!employeeID.isPresent()) {
				return LoginState.failed();
				// 通常紐付処理
			}
			Optional<EmployeeImport> optEmployeeImport = employeeAdapter.getCurrentInfoBySid(employeeID.get());
			return LoginState.success(optEmployeeImport.get());

		} catch (ValidateException e) {
			// 認証自体に失敗時
			// 通常ログイン画面へ
			return LoginState.failed();
		}
	}

	@Override
	protected CheckChangePassDto processSuccess(LoginState state) {
		/* ログインチェック  */
		/* ログインログ  */
		return CheckChangePassDto.successToAuthSaml();
	}

	@Override
	protected CheckChangePassDto processFailure(LoginState state) {
		return CheckChangePassDto.failedToAuthSaml();
	}

	
	static class LoginState implements LoginCommandHandlerBase.LoginState<SamlValidateCommand>{
		
		private boolean isSuccess;
		
		private EmployeeImport employeeImport;
		
		public LoginState(boolean isSuccess, EmployeeImport employeeImport) {
			this.isSuccess = isSuccess;
			this.employeeImport = employeeImport;
		}
		
		public static LoginState success(EmployeeImport employeeImport) {
			return new LoginState(true, employeeImport);
		}
		
		public static LoginState failed() {
			return new LoginState(false, null);
		}
		
		@Override
		public boolean isSuccess() {
			return isSuccess;
		}

		@Override
		public EmployeeImport getEmployee() {
			return employeeImport;
		}	
	}
}
