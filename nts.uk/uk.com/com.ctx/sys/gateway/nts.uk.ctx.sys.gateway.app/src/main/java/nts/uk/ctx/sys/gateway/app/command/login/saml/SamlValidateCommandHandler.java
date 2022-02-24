package nts.uk.ctx.sys.gateway.app.command.login.saml;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import nts.uk.ctx.sys.gateway.dom.singlesignon.saml.UkRelayState;
import org.apache.commons.codec.net.URLCodec;

import com.onelogin.saml2.util.Constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.task.tran.AtomTask;
import nts.gul.security.saml.SamlResponseValidator;
import nts.gul.security.saml.SamlResponseValidator.ValidateException;
import nts.gul.security.saml.SamlSetting;
import nts.gul.security.saml.ValidSamlResponse;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.app.command.login.LoginRequire;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.singlesignon.saml.FindSamlSetting;
import nts.uk.ctx.sys.gateway.dom.singlesignon.saml.IdpUserAssociation;
import nts.uk.ctx.sys.gateway.dom.singlesignon.saml.IdpUserAssociationRepository;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataManageInfoAdapter;
import nts.uk.ctx.sys.shared.dom.user.UserRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SamlValidateCommandHandler extends LoginCommandHandlerBase<
													SamlValidateCommand,
													SamlAuthenticationResult,
													ValidateInfo, 
													SamlValidateCommandHandler.Require>{
	
	@Inject
	private FindSamlSetting findSamlSetting;
	
	@Inject
	private IdpUserAssociationRepository idpUserAssociationRepository;
	
	@Inject
	private EmployeeDataManageInfoAdapter employeeDataManageInfoAdapter;

	@Inject
	private UserRepository userRepository;
	
	// テナント認証失敗時
	@Override
	protected ValidateInfo tenantAuthencationFailed() {
		return ValidateInfo.failedToAuthTenant();
	}
	
	// 認証処理本体
	@Override
	@SneakyThrows
	protected SamlAuthenticationResult authenticate(Require require, SamlValidateCommand command) {
		HttpServletRequest request = command.getRequest();

		// URLエンコードされているのでデコード
		URLCodec codec = new URLCodec("UTF-8");
		String serializedRelayState = codec.decode(request.getParameter("RelayState"));
		
		// RelayStateをオブジェクトに変換
		UkRelayState relayState = UkRelayState.deserialize(serializedRelayState);
		
		// RelayStateのテナント情報からSAMLSettingを取得
		SamlSetting samlSetting;
		{
			val opt = findSamlSetting.find(relayState.getTenantCode());
			if(!opt.isPresent()) {
				return SamlAuthenticationResult.noSamlSettingFailure();
			}
			samlSetting = opt.get();
		}

		// SAMLResponseの検証処理
		samlSetting.setSignatureAlgorithm(Constants.RSA_SHA1);
		ValidSamlResponse validateResult;
		try {
			validateResult = SamlResponseValidator.validate(request, samlSetting);
		} catch (ValidateException e) {
			return SamlAuthenticationResult.samlInvalidFailure();
		}

		// Idpユーザと社員の紐付けから社員を特定
		Optional<IdpUserAssociation> optAssociation = idpUserAssociationRepository.findByIdpUser(validateResult.getIdpUser());
		if (!optAssociation.isPresent()) {
			return SamlAuthenticationResult.identificationFailure("社員が特定できませんでした");
		}
		
		// 識別
		IdentifiedEmployeeInfo identified;
		{
			val opt = identify(optAssociation.get().getEmployeeId());
			if (!opt.isPresent()) {
				return SamlAuthenticationResult.identificationFailure("Msg_1990");
			}
			identified = opt.get();
		}
		
		return SamlAuthenticationResult.success(identified, relayState.getRequestUrl());
	}
	
	/**
	 * 識別
	 * @param employeeId
	 * @return
	 */
	private Optional<IdentifiedEmployeeInfo> identify(String employeeId) {

		val employee = employeeDataManageInfoAdapter.findByEmployeeId(employeeId);
		if (!employee.isPresent() || employee.get().isDeleted()) {
			return Optional.empty();
		}
		
		val user = userRepository.getByAssociatedPersonId(employee.get().getPersonId());
		if (!user.isPresent()) {
			return Optional.empty();
		}
		
		return Optional.of(new IdentifiedEmployeeInfo(employee.get(), user.get()));
	}

	// 社員認証失敗時の処理
	@Override
	protected ValidateInfo authenticationFailed(Require require, SamlAuthenticationResult state) {
		return ValidateInfo.failedToValidSaml(state.getErrorMessage());
	}
	
	// ログイン成功時の処理
	@Override
	protected ValidateInfo loginCompleted(Require require, SamlAuthenticationResult state, Optional<String> msg) {
		return ValidateInfo.successToValidSaml(state.getRequestUrl(), msg);
	}
	
	public static interface Require extends LoginCommandHandlerBase.Require {
	}
	
	@Override
	protected Require getRequire(SamlValidateCommand command) {
		return EmbedStopwatch.embed(new RequireImpl());
	}
	
	public class RequireImpl extends LoginRequire.BaseImpl implements Require {

	}
}