package nts.uk.ctx.sys.gateway.app.command.login.saml.start;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.gateway.app.command.tenantlogin.ConnectDataSourceOfTenant;
import nts.uk.ctx.sys.gateway.dom.login.LoginClient;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.operate.SamlOperation;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.operate.SamlOperationRepository;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.UkRelayState;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.*;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Optional;

/**
 * SAML認証によるログインを開始する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class StartSamlLoginCommandHandler extends CommandHandlerWithResult<StartSamlLoginCommand, StartSamlLoginResult> {

	@SneakyThrows
	protected StartSamlLoginResult handle(CommandHandlerContext<StartSamlLoginCommand> context) {

		StartSamlLoginCommand command = context.getCommand();
		command.checkInput();

		Require require = EmbedStopwatch.embed(new RequireImpl());

		authTenant(require, command);

		return checkSamlOperation(require, command);
	}

	/**
	 * テナント認証
	 * @param require
	 * @param command
	 */
	private void authTenant(Require require, StartSamlLoginCommand command) {

		val result = ConnectDataSourceOfTenant.connect(
				require, LoginClient.create(command.getRequest()), command.getTenantCode(), command.getTenantPassword());

		transaction.execute(result.getAtomTask());

		if (result.isFailure()) {
			throw new BusinessException(result.getErrorMessageID());
		}
	}

	/**
	 * SAML運用のチェック
	 * @param require
	 * @param command
	 * @return
	 */
	private StartSamlLoginResult checkSamlOperation(Require require, StartSamlLoginCommand command) {

		return require.getSamlOperation(command.getTenantCode())
				.map(operation -> {
					val relayState = new UkRelayState(command.getTenantCode(), command.getTenantPassword());
					return operation.tryCreateIdpEntryUrl(relayState)
							.map(url -> StartSamlLoginResult.isUsed(url))
							.orElseGet(() -> StartSamlLoginResult.isNotUsed());
				})
				.orElseGet(() -> StartSamlLoginResult.operationSettingNotExist());
	}

	public interface Require extends AuthenticateTenant.Require {

		Optional<SamlOperation> getSamlOperation(String tenantCode);
	}

	@Inject
	private TenantAuthenticationRepository tenantAuthenticationRepo;

	@Inject
	private TenantAuthenticationFailureLogRepository failureLogRepo;

	@Inject
	private SamlOperationRepository samlOperationRepo;

	private class RequireImpl implements Require {

		@Override
		public Optional<TenantAuthentication> getTenantAuthentication(String tenantCode) {
			return tenantAuthenticationRepo.find(tenantCode);
		}

		@Override
		public void insert(TenantAuthenticationFailureLog failureLog) {
			failureLogRepo.insert(failureLog);
		}

		@Override
		public Optional<SamlOperation> getSamlOperation(String tenantCode) {
			return samlOperationRepo.find(tenantCode);
		}
	}
}
