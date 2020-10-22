package nts.uk.ctx.sys.gateway.app.command.login.saml;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.onelogin.saml2.util.Constants;

import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.error.BusinessException;
import nts.gul.security.saml.RelayState;
import nts.gul.security.saml.SamlResponseValidator;
import nts.gul.security.saml.SamlResponseValidator.ValidateException;
import nts.gul.security.saml.ValidSamlResponse;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.dom.login.adapter.SysEmployeeAdapter;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;
import nts.uk.ctx.sys.gateway.dom.singlesignon.saml.FindSamlSetting;
import nts.uk.ctx.sys.gateway.dom.singlesignon.saml.IdpUserAssociation;
import nts.uk.ctx.sys.gateway.dom.singlesignon.saml.IdpUserAssociationRepository;
import nts.uk.ctx.sys.shared.dom.user.FindUser;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.UserRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SamlValidateCommandHandler 
extends LoginCommandHandlerBase<SamlValidateCommand, SamlValidateCommandHandler.LoginState, ValidateInfo>{
	
	@Inject
	private FindSamlSetting findSamlSetting;
	
	@Inject
	private IdpUserAssociationRepository idpUserAssociationRepository;
	
	@Inject
	private SysEmployeeAdapter employeeAdapter;
	
	@Inject
	private UserRepository userRepository;
	
	// テナント認証失敗時
	@Override
	protected ValidateInfo getResultOnFailTenantAuth() {
		return ValidateInfo.failedToAuthTenant();
	}
	
	// 認証処理
	@Override
	protected LoginState processBeforeLogin(SamlValidateCommand command) {
		HttpServletRequest request = command.getRequest();
		
		// RelayStateをクラスに変換
		RelayState relayState = RelayState.deserialize(request.getParameter("RelayState"));
		
		// RelayStateのテナント情報からSAMLSettingを取得
		val optSamlSetting = findSamlSetting.find(relayState.get("tenantCode"));
		if(!optSamlSetting.isPresent()) {
			// SAMLSettingが取得できなかった場合
			throw new BusinessException("Msg_1980");
		}
//		val samlSetting = new SamlSetting();
//		samlSetting.SetSpEntityId("uk");
//		samlSetting.SetIdpEntityId("http://localhost:8180/auth/realms/my_territory");
//		samlSetting.SetSignatureAlgorithm(Constants.RSA_SHA1);
//		samlSetting.SetIdpx509Certificate("MIICpzCCAY8CBgF0DssEOTANBgkqhkiG9w0BAQsFADAXMRUwEwYDVQQDDAxteV90ZXJyaXRvcnkwHhcNMjAwODIxMDIxMjAwWhcNMzAwODIxMDIxMzQwWjAXMRUwEwYDVQQDDAxteV90ZXJyaXRvcnkwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCLYd3KpTYzORjpqPUueBAmSw8eslcO5DVAx06uoh+Cg/0/srKTCTBHd4L+x/4SjbxAIal5F7km70lE/GUNTG1URCeK6FVXVHL+z2Aa0YRDJv373fh3uPfFSxbVMIPJ/sUTE+qiJ/iiF1ysvn0d4hB5zLA6Jhw0iwpM9EPZBIP7cLqIPDgJ1OJis0rh2iTSBihThF+8TW4ybCkhWjpzLP93TfsfoiDa0s4R/ZO2MZdTEt9gDjbmgnf5AJHyzND7zNpgUbZMzsP8et4MbJdYcarXih++Qjd6JPuc1ST7EPEcNQbIARDZqqkp/iL6fzDdNgzzMx+IYoQEsWfHoB02L4zLAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAG3JE5HFzeiAIiujoInhw71K2JqbF9jNAJxUxR0nJKPuvAQjZWkasHXxJargBquO32QJMCPlC4v8HwuJAuJM457UWkEU8rIPz7T6SZc9Ww3Wq38uizX+0s8O0JEKKLpQl00EmLSYHiBfs6snDbQxcPgifdHtC+G5upL16u3SeL6rIxnDPbhdLuLuiYoPg2WjNwkkkvtvZRUsdhi/8wYwZe46uRvsFQH4U/eFbIx/85Iu8Gnat0E0gU/dDvVuxGyn5YEU04SmBUOxmlkWebaLAlkCUKsBRCMz508jc4XcB/ziq7laGioEZbz06f3POTAtkC2pfocuy2q6vKI6RPT4sRQ=");
		
		try {
			// SAMLResponseの検証処理
			val samlSetting = optSamlSetting.get();
			samlSetting.SetSignatureAlgorithm(Constants.RSA_SHA1);
			
			ValidSamlResponse validateResult = SamlResponseValidator.validate(request, samlSetting);
			if (!validateResult.isValid()) {
				// 認証失敗時
				return LoginState.failed();
				// 通常ログイン画面へ
			}

			// Idpユーザと社員の紐付けから社員を特定
			Optional<IdpUserAssociation> optAssociation = idpUserAssociationRepository.findByIdpUser(validateResult.getIdpUser());
			if (!optAssociation.isPresent()) {
				// 社員特定できない
				return LoginState.failed();
			}
			Optional<EmployeeImport> optEmployee = employeeAdapter.getCurrentInfoBySid(optAssociation.get().getEmployeeId());
			if (!optEmployee.isPresent()) {
				// 社員が存在しない
				return LoginState.failed();
			}
			
			// 認証成功
			val employee = optEmployee.get();
			FindUser.Require require = EmbedStopwatch.embed(new RequireImpl());
			Optional<User> optUser = FindUser.byEmployeeCode(require, employee.getCompanyId(), employee.getEmployeeCode());
			return LoginState.success(optEmployee.get(), optUser.get(), relayState.get("requestUrl"));

		} catch (ValidateException e) {
			// 認証自体に失敗時
			// 通常ログイン画面へ
			return LoginState.failed();
		}
	}

	@Override
	protected ValidateInfo processSuccess(LoginState state) {
		/* ログインチェック  */
		/* ログインログ  */
		
		return ValidateInfo.successToValidSaml(state.requestUrl);
	}

	@Override
	protected ValidateInfo processFailure(LoginState state) {
		return ValidateInfo.failedToValidSaml();
	}
	
	static class LoginState implements LoginCommandHandlerBase.LoginState<SamlValidateCommand>{
		
		private boolean isSuccess;
		
		private EmployeeImport employeeImport;
		
		private User user;
		
		private String requestUrl;
		
		public LoginState(boolean isSuccess, EmployeeImport employeeImport, User user, String requestUrl) {
			this.isSuccess = isSuccess;
			this.employeeImport = employeeImport;
			this.user = user;
			this.requestUrl = requestUrl;
		}
		
		public static LoginState success(EmployeeImport employeeImport, User user, String requestUrl) {
			return new LoginState(true, employeeImport, user, requestUrl);
		}
		
		public static LoginState failed() {
			return new LoginState(false, null, null, null);
		}
		
		@Override
		public boolean isSuccess() {
			return isSuccess;
		}

		@Override
		public EmployeeImport getEmployee() {
			return employeeImport;
		}	
		
		@Override
		public User getUser() {
			return user;
		}
	}
	
	public class RequireImpl implements FindUser.Require {

		@Override
		public Optional<String> getPersonalId(String companyId, String employeeCode) {
			val empInfo = employeeAdapter.getCurrentInfoByScd(companyId, employeeCode);
			if(!empInfo.isPresent()) {
				return Optional.empty();
			}
			return Optional.of(empInfo.get().getPersonalId());
		}

		@Override
		public Optional<User> getUser(String personalId) {
			return userRepository.getByAssociatedPersonId(personalId);
		}
	}
}