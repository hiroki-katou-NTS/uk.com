/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.loginold;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.app.command.loginold.dto.CheckContractDto;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.FindTenant;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthentication;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticationRepository;
import nts.uk.shr.com.system.property.UKServerSystemProperties;

/**
 * The Class LocalContractFormCommandHandler.
 */
@Stateless
public class LocalContractFormCommandHandler
		extends CommandHandlerWithResult<LocalContractFormCommand, CheckContractDto> {

	@Inject
	private TenantAuthenticationRepository tenantAuthenticationRepository;

	@Override
	protected CheckContractDto handle(CommandHandlerContext<LocalContractFormCommand> context) {
		LocalContractFormCommand command = context.getCommand();
		try {
			// case Cloud
			if (UKServerSystemProperties.isCloud()) {
				if (this.isTenantAuth(command)) {
					return CheckContractDto.success();
				}
				return CheckContractDto.failed();
			}
			// case OnPre
			return CheckContractDto.onpre();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * テナント認証
	 *
	 * @param command
	 * 
	 * @return boolean
	 */
	private boolean isTenantAuth(LocalContractFormCommand command) {
		// get contract info
		String tenantCode = command.getContractCode();
		String tenantPassword = command.getContractPassword();

		if (tenantCode == null || tenantCode.isEmpty()) {
			return false;
		}
		// 認証処理
		FindTenant.Require require = EmbedStopwatch.embed(new RequireImpl());
		return FindTenant.byTenantCode(require, tenantCode, GeneralDate.today())
				.map(t -> t.verify(tenantPassword))
				.orElse(false);
	}
	
	public class RequireImpl implements FindTenant.Require{

		@Override
		public Optional<TenantAuthentication> getTenantAuthentication(String tenantCode) {
			return tenantAuthenticationRepository.find(tenantCode);
		}

		@Override
		public Optional<TenantAuthentication> getTenantAuthentication(String tenantCode, GeneralDate date) {
			return tenantAuthenticationRepository.find(tenantCode, date);
		}
	}
}