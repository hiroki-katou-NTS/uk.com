package nts.uk.ctx.sys.gateway.app.command.tenantlogin;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.gateway.app.find.login.dto.CheckContractDto;
import nts.uk.ctx.sys.gateway.dom.login.LoginClient;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.*;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 
 * @author hiroki_katou
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TenantCheckCommandHandler extends CommandHandlerWithResult<TenantAuthenticateCommand, CheckContractDto> {

	@Inject
    private TenantAuthenticationRepository tenantAuthenticationRepo;
	
	@Inject
	private TenantAuthenticationFailureLogRepository tenantAuthenticationFailureLogRepo;

	@Override
	protected CheckContractDto handle(CommandHandlerContext<TenantAuthenticateCommand> context) {
		RequireImpl require = new RequireImpl();
		val command = context.getCommand();
		val request = command.getRequest();
		if(command.getTenantCode().isEmpty()) {
			// テナント認証情報がないので、再認証必要
			return new CheckContractDto(true);
		}
		
		// ログインクライアントの生成
		val loginClient = LoginClient.create(request);

		val tenantAuthResult = ConnectDataSourceOfTenant.connect(
				require, loginClient, command.getTenantCode(), command.getPassword());
		
		if(tenantAuthResult.isFailure()) {
			transaction.execute(() -> {
				tenantAuthResult.getAtomTask().run();
			});
			// テナント認証失敗なので、再認証必要
			return new CheckContractDto(true);
		}
		// テナント認証成功なので、再認証不要
		return new CheckContractDto(false);
	}
	
	@RequiredArgsConstructor
	private class RequireImpl implements AuthenticateTenant.Require{
		@Override
		public Optional<TenantAuthentication> getTenantAuthentication(String tenantCode) {
			return tenantAuthenticationRepo.find(tenantCode);
		}
		
		@Override
		public void insert(TenantAuthenticationFailureLog failureLog) {
			tenantAuthenticationFailureLogRepo.insert(failureLog);
		}
	}
}
