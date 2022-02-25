package nts.uk.ctx.sys.gateway.app.command.login.saml;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.gateway.app.command.tenantlogin.ConnectDataSourceOfTenant;
import nts.uk.ctx.sys.gateway.dom.login.LoginClient;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.SamlOperation;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.SamlOperationRepository;
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
public class StartSamlLoginCommandHandler extends CommandHandlerWithResult<SamlAuthenticateCommand, SamlAuthenticateInfo> {

	
	@SneakyThrows
	protected SamlAuthenticateInfo handle(CommandHandlerContext<SamlAuthenticateCommand> context) {

		SamlAuthenticateCommand command = context.getCommand();
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
	private void authTenant(Require require, SamlAuthenticateCommand command) {

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
	private SamlAuthenticateInfo checkSamlOperation(Require require, SamlAuthenticateCommand command) {

		return require.getSamlOperation(command.getTenantCode())
				.map(operation -> {
					val relayState = new UkRelayState(command.getTenantCode(), command.getTenantPassword());
					return operation.tryCreateIdpEntryUrl(relayState)
							.map(url -> SamlAuthenticateInfo.isUsed(url))
							.orElseGet(() -> SamlAuthenticateInfo.isNotUsed());
				})
				.orElseGet(() -> SamlAuthenticateInfo.operationSettingNotExist());
	}

	private interface Require extends AuthenticateTenant.Require {

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
