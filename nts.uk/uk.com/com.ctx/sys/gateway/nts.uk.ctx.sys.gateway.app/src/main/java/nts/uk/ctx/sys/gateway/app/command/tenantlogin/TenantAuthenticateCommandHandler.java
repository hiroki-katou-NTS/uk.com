package nts.uk.ctx.sys.gateway.app.command.tenantlogin;

import java.util.Optional;

import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.web.HttpClientIpAddress;
import nts.uk.ctx.sys.gateway.dom.login.LoginClient;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.AuthenticateOfTenant;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticate;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticateFailureLog;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticateFailureLogRepository;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticateRepository;
import nts.uk.shr.com.net.Ipv4Address;

/**
 * テナント認証する
 * @author hiroki_katou
 *
 */
public class TenantAuthenticateCommandHandler extends CommandHandler<TenantAuthenticateCommand> {
	
	@Inject
    private TenantAuthenticateRepository tenantAuthenticationRepo;
	
	@Inject
	private TenantAuthenticateFailureLogRepository tenantAuthenticationFailureLogRepo;

	@Override
	protected void handle(CommandHandlerContext<TenantAuthenticateCommand> context) {
		RequireImpl require = new RequireImpl();
		val command = context.getCommand();
		val request = command.getRequest();
		
		// ログインクライアントの生成
		val loginClient = new LoginClient(
				Ipv4Address.parse(HttpClientIpAddress.get(request)), 
				request.getHeader("user-agent"));
		
		val tenantAuthResult = ConnectDataSourceOfTenant.connect(
				require, loginClient, command.getTenantCode(), command.getPassword());
		
		if(tenantAuthResult.isFailure()) {
			transaction.execute(() -> {
				tenantAuthResult.getAtomTask().get().run();
			});
		}
	}
	
	@RequiredArgsConstructor
	private class RequireImpl implements AuthenticateOfTenant.Require{@Override
		public Optional<TenantAuthenticate> getTenantAuthentication(String tenantCode) {
			return tenantAuthenticationRepo.find(tenantCode);
		}

		@Override
		public void insert(TenantAuthenticateFailureLog failureLog) {
			tenantAuthenticationFailureLogRepo.insert(failureLog);
		}
	}
}
