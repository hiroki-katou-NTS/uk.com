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
import nts.gul.security.saml.SamlSetting;
import nts.gul.security.saml.ValidSamlResponse;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.dom.login.adapter.SysEmployeeAdapter;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;
import nts.uk.ctx.sys.gateway.dom.singlesignon.saml.FindIdpUserAssociation;
import nts.uk.ctx.sys.gateway.dom.singlesignon.saml.FindSamlSetting;
import nts.uk.ctx.sys.gateway.dom.singlesignon.saml.IdpUserAssociation;
import nts.uk.ctx.sys.shared.dom.user.FindUser;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.UserRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SamlValidateCommandHandler 
extends LoginCommandHandlerBase<SamlValidateCommand, SamlValidateCommandHandler.LoginState, ValidateInfo>{
	
	@Inject
	private SysEmployeeAdapter employeeAdapter;
	
	@Inject
	private UserRepository userRepository;
	
	@Inject
	private FindIdpUserAssociation findIdpUserAssociation;
	
	@Inject
	private FindSamlSetting findSamlSetting;

	// テナント認証失敗時
	@Override
	protected ValidateInfo getResultOnFailTenantAuth() {
		return ValidateInfo.failedToAuthTenant();
	}
	
	// 認証処理
	@Override
	protected LoginState processBeforeLogin(SamlValidateCommand command) {
		HttpServletRequest request = command.getRequest();

		RelayState relayState = RelayState.deserialize(request.getParameter("RelayState"));
		
		val optSamlSetting = findSamlSetting.find(relayState.get("tenantCode"));
		
		if(!optSamlSetting.isPresent()) {
			// SAMLSettingが取得できなかった場合
			throw new BusinessException("Msg_1980");
		}
		try {
			// SAMLResponseの検証処理
			ValidSamlResponse validateResult = SamlResponseValidator.validate(request, optSamlSetting.get());
			if (!validateResult.isValid()) {
				// 認証失敗時
				return LoginState.failed();
				// 通常ログイン画面へ
			}

			// Idpユーザと社員の紐付けから社員を特定
			Optional<IdpUserAssociation> optAssociation = findIdpUserAssociation.byIdpUser(validateResult.getIdpUser());
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
			return LoginState.success(optEmployee.get(), optUser.get(), relayState.get("RequestUrl"));

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