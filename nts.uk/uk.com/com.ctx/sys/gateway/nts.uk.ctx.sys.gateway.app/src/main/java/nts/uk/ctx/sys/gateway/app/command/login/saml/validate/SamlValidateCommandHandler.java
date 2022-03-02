package nts.uk.ctx.sys.gateway.app.command.login.saml.validate;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.gul.security.saml.ValidSamlResponse;
import nts.gul.util.Either;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.app.command.login.LoginRequire;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.assoc.IdpUserAssociation;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.assoc.IdpUserAssociationRepository;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.UkRelayState;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.assoc.SamlIdpUserName;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate.SamlResponseValidation;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate.SamlResponseValidationRepository;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataManageInfoAdapter;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.UserRepository;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static nts.uk.ctx.sys.gateway.app.command.login.saml.validate.SamlAuthenticationState.INVALID_SAML_RESPONSE;
import static nts.uk.ctx.sys.gateway.app.command.login.saml.validate.SamlAuthenticationState.NO_SAML_SETTING;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SamlValidateCommandHandler extends LoginCommandHandlerBase<
		SamlValidateCommand,
		SamlAuthenticationResult,
		SamlLoginResult,
		SamlValidateCommandHandler.Require> {

	
	// テナント認証失敗時
	@Override
	protected SamlLoginResult tenantAuthencationFailed() {
		// SAMLログインの開始時にテナント認証できた認証情報を使っているのに、ここで失敗するはずがない
		throw new RuntimeException("テナント認証に失敗");
	}
	
	// 認証処理本体
	@Override
	protected SamlAuthenticationResult authenticate(Require require, SamlValidateCommand command) {

		// SAMLResponseの検証処理
		ValidSamlResponse validated;
		{
			val validateEither = validate(require, command.getRequest());
			if (validateEither.isLeft()) {
				return validateEither.getLeft();
			}
			validated = validateEither.getRight();
		}

		val either = IdentifySamlUser.identify(require, validated.getIdpUser()).map(
				error -> SamlAuthenticationResult.failed(SamlAuthenticationState.of(error), Optional.of(validated.getIdpUser())),
				identified -> SamlAuthenticationResult.succeeded(identified));

		return either.isRight() ? either.getRight() : either.getLeft();
	}

	private static Either<SamlAuthenticationResult, ValidSamlResponse> validate(Require require, HttpServletRequest request) {

		UkRelayState relayState = UkRelayState.deserialize(request);

		val validation = require.getSamlResponseValidation(relayState.getTenantCode());
		if (!validation.isPresent()) {
			return Either.left(SamlAuthenticationResult.failed(NO_SAML_SETTING, Optional.empty()));
		}

		return Either.rightOptional(
				validation.get().validate(request),
				() -> SamlAuthenticationResult.failed(INVALID_SAML_RESPONSE, Optional.empty()));
	}

	// 社員認証失敗時の処理
	@Override
	protected SamlLoginResult authenticationFailed(Require require, SamlAuthenticationResult state) {
		return SamlLoginResult.failed(state);
	}
	
	// ログイン成功時の処理
	@Override
	protected SamlLoginResult loginCompleted(Require require, SamlAuthenticationResult state, Optional<String> msg) {
		return SamlLoginResult.succeeded();
	}

	@Inject
	private LoginRequire loginRequire;
	
	@Override
	protected Require getRequire(SamlValidateCommand command) {
		RequireImpl require = new RequireImpl(command.getTenantCode());
		loginRequire.setup(require);
		return EmbedStopwatch.embed(require);
	}

	public interface Require extends LoginCommandHandlerBase.Require, IdentifySamlUser.Require {

		Optional<SamlResponseValidation> getSamlResponseValidation(String tenantCode);
	}

	@Inject
	private IdpUserAssociationRepository idpUserAssociationRepo;

	@Inject
	private EmployeeDataManageInfoAdapter employeeDataManageInfoAdapter;

	@Inject
	private UserRepository userRepo;

	@Inject
	private SamlResponseValidationRepository validationRepo;

	@RequiredArgsConstructor
	public class RequireImpl extends LoginRequire.BaseImpl implements Require {

		private final String tenantCode;

		@Override
		public Optional<IdpUserAssociation> getIdpUserAssociation(String idpUserId) {
			return idpUserAssociationRepo.findByIdpUser(tenantCode, new SamlIdpUserName(idpUserId));
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
		public Optional<SamlResponseValidation> getSamlResponseValidation(String tenantCode) {
			return validationRepo.find(tenantCode);
		}
	}
}