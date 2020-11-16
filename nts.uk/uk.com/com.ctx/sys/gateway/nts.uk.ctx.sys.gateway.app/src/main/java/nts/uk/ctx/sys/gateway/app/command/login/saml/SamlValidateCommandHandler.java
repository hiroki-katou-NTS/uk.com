package nts.uk.ctx.sys.gateway.app.command.login.saml;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.net.URLCodec;

import com.onelogin.saml2.util.Constants;

import lombok.SneakyThrows;
import lombok.Value;
import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.task.tran.AtomTask;
import nts.gul.security.saml.SamlResponseValidator;
import nts.gul.security.saml.SamlResponseValidator.ValidateException;
import nts.gul.security.saml.ValidSamlResponse;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.app.command.login.LoginRequire;
import nts.uk.ctx.sys.gateway.dom.singlesignon.saml.FindSamlSetting;
import nts.uk.ctx.sys.gateway.dom.singlesignon.saml.IdpUserAssociation;
import nts.uk.ctx.sys.gateway.dom.singlesignon.saml.IdpUserAssociationRepository;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeImport;
import nts.uk.ctx.sys.shared.dom.employee.SysEmployeeAdapter;
import nts.uk.ctx.sys.shared.dom.user.FindUser;
import nts.uk.ctx.sys.shared.dom.user.User;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SamlValidateCommandHandler extends LoginCommandHandlerBase<
													SamlValidateCommand, 
													SamlValidateCommandHandler.AuthenResult, 
													SamlValidateCommandHandler.AuthorResult, 
													ValidateInfo, 
													SamlValidateCommandHandler.Require>{
	
	@Inject
	private FindSamlSetting findSamlSetting;
	
	@Inject
	private IdpUserAssociationRepository idpUserAssociationRepository;
	
	@Inject
	private SysEmployeeAdapter employeeAdapter;
	
	// テナント認証失敗時
	@Override
	protected ValidateInfo getResultOnFailTenantAuth() {
		return ValidateInfo.failedToAuthTenant();
	}
	
	// 認証処理本体
	@Override
	@SneakyThrows
	protected AuthenResult authenticate(Require require, SamlValidateCommand command) {
		HttpServletRequest request = command.getRequest();

		// URLエンコードされているのでデコード
		URLCodec codec = new URLCodec("UTF-8");
		String serializedRelayState = codec.decode(request.getParameter("RelayState"));
		
		// RelayStateをオブジェクトに変換
		UkRelayState relayState = UkRelayState.deserialize(serializedRelayState);
		
		// RelayStateのテナント情報からSAMLSettingを取得
		val optSamlSetting = findSamlSetting.find(relayState.getTenantCode());
		if(!optSamlSetting.isPresent()) {
			// SAMLSettingが取得できなかった場合
			return AuthenResult.failed("Msg_1980");
		}
	
		// SAMLResponseの検証処理
		val samlSetting = optSamlSetting.get();
		samlSetting.setSignatureAlgorithm(Constants.RSA_SHA1);
		ValidSamlResponse validateResult;
		try {
			validateResult = SamlResponseValidator.validate(request, samlSetting);
		} catch (ValidateException e) {
			// 認証に失敗
			return AuthenResult.failed("Msg_1988");
		}

		// Idpユーザと社員の紐付けから社員を特定
		Optional<IdpUserAssociation> optAssociation = idpUserAssociationRepository.findByIdpUser(validateResult.getIdpUser());
		if (!optAssociation.isPresent()) {
			// 社員特定できない
			return AuthenResult.failed("Msg_1989");
		}
		
		Optional<EmployeeImport> optEmployee = employeeAdapter.getCurrentInfoBySid(optAssociation.get().getEmployeeId());
		if (!optEmployee.isPresent()) {
			// 社員が存在しない
			return AuthenResult.failed("Msg_1990");
		}
		val employee = optEmployee.get();
		if(employee.isDeleted()) {
			// 社員が削除されている
			return AuthenResult.failed("Msg_1993");
		}
		
		// 認証成功
		Optional<User> optUser = FindUser.byEmployeeId(require, employee.getEmployeeId());
		return AuthenResult.success(optEmployee.get(), optUser.get(), relayState.getRequestUrl());
	}
	
	// 認証成功時の処理
	@Override
	protected AuthorResult processSuccess(Require require, AuthenResult state) {
		/* ログインチェック  */
		return AuthorResult.of(ValidateInfo.successToValidSaml(state.requestUrl));
	}
	
	// 認証失敗時の処理
	@Override
	protected AuthorResult processFailure(Require require, AuthenResult state) {
		return AuthorResult.of(ValidateInfo.failedToValidSaml(state.errorMessage));
	}
	
	@Value
	static class AuthenResult implements LoginCommandHandlerBase.AuthenticationResult{
		
		private boolean isSuccess;
		private EmployeeImport employee;
		private User user;
		private String requestUrl;
		private String errorMessage;
		private Optional<AtomTask> atomTask;
		
		public static AuthenResult success(EmployeeImport employeeImport, User user, String requestUrl) {
			return new AuthenResult(true, employeeImport, user, requestUrl, null, Optional.empty());
		}
		
		public static AuthenResult failed(String errorMessage) {
			return new AuthenResult(false, null, null, null, errorMessage, Optional.empty());
		}
	}

	@Value
	static class AuthorResult implements LoginCommandHandlerBase.AuthorizationResult<ValidateInfo> {
		Optional<AtomTask> atomTask;
		ValidateInfo loginResult;
		
		public static AuthorResult of(ValidateInfo loginResult) {
			return new AuthorResult(Optional.empty(), loginResult);
		}
	}
	
	public static interface Require extends FindUser.RequireByEmployeeId, 
											LoginCommandHandlerBase.Require {
	}
	
	@Override
	protected Require getRequire(SamlValidateCommand command) {
		return EmbedStopwatch.embed(new RequireImpl());
	}
	
	public class RequireImpl extends LoginRequire.BaseImpl implements Require {

		@Override
		public Optional<String> getPersonalIdByEmployeeId(String employeeId) {
			return employeeAdapter.getCurrentInfoBySid(employeeId)
					.map(e -> e.getPersonalId());
		}
	}
}
