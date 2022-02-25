package nts.uk.ctx.sys.gateway.app.command.login.saml.validate;

import com.onelogin.saml2.util.Constants;
import lombok.SneakyThrows;
import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.gul.security.saml.SamlResponseValidator;
import nts.gul.security.saml.SamlResponseValidator.ValidateException;
import nts.gul.security.saml.SamlSetting;
import nts.gul.security.saml.ValidSamlResponse;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.app.command.login.LoginRequire;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.IdpUserAssociation;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.IdpUserAssociationRepository;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.SamlSettingRepository;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.UkRelayState;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataManageInfoAdapter;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.UserRepository;
import nts.uk.shr.com.program.ProgramsManager;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SamlValidateCommandHandler extends LoginCommandHandlerBase<
													SamlValidateCommand,
													SamlAuthenticationResult,
													ValidateInfo, 
													SamlValidateCommandHandler.Require>{

	
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

		UkRelayState relayState = UkRelayState.deserialize(request);

		// RelayStateのテナント情報からSAMLSettingを取得
		SamlSetting samlSetting;
		{
			val opt = require.getSamlSetting(relayState.getTenantCode());
			if (!opt.isPresent()) {
				return SamlAuthenticationResult.noSamlSettingFailure();
			}
			samlSetting = opt.get();
			samlSetting.setSignatureAlgorithm(Constants.RSA_SHA1);
		}

		// SAMLResponseの検証処理
		ValidSamlResponse validateResult;
		try {
			validateResult = SamlResponseValidator.validate(request, samlSetting);
		} catch (ValidateException e) {
			return SamlAuthenticationResult.samlInvalidFailure();
		}

		String topPage = ProgramsManager.CCG008A.getRootRelativePath();
		val either = IdentifySamlUser.identify(require, validateResult.getIdpUser()).map(
				error -> SamlAuthenticationResult.identificationFailure(error.getId()),
				identified -> SamlAuthenticationResult.success(identified, topPage));

		return either.isRight() ? either.getRight() : either.getLeft();
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
	
	@Override
	protected Require getRequire(SamlValidateCommand command) {
		return EmbedStopwatch.embed(new RequireImpl());
	}

	public interface Require extends LoginCommandHandlerBase.Require, IdentifySamlUser.Require {
		Optional<SamlSetting> getSamlSetting(String tenantCode);
	}

	@Inject
	private IdpUserAssociationRepository idpUserAssociationRepo;

	@Inject
	private EmployeeDataManageInfoAdapter employeeDataManageInfoAdapter;

	@Inject
	private UserRepository userRepo;

	@Inject
	private SamlSettingRepository samlSettingRepo;
	
	public class RequireImpl extends LoginRequire.BaseImpl implements Require {

		@Override
		public Optional<IdpUserAssociation> getIdpUserAssociation(String idpUserId) {
			return idpUserAssociationRepo.findByIdpUser(idpUserId);
		}

		@Override
		public Optional<EmployeeDataMngInfoImport> getEmployeeDataMngInfoImportByEmployeeId(String employeeId) {
			return employeeDataManageInfoAdapter.findByEmployeeId(employeeId);
		}

		@Override
		public Optional<User> getUserByPersonId(String personId) {
			return userRepo.getByAssociatedPersonId(personId);
		}

		@Override
		public Optional<SamlSetting> getSamlSetting(String tenantCode) {
			return samlSettingRepo.find(tenantCode);
		}
	}
}