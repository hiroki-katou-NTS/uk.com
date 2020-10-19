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
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.system.config.InstallationType;

/**
 * The Class LocalContractFormCommandHandler.
 */
@Stateless
public class LocalContractFormCommandHandler
		extends CommandHandlerWithResult<LocalContractFormCommand, CheckContractDto> {

	/** The contract repository. */
	@Inject
	private TenantAuthenticationRepository tenantAuthenticationRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandlerWithResult#handle(nts.arc.layer.
	 * app.command.CommandHandlerContext)
	 */
	@Override
	protected CheckContractDto handle(CommandHandlerContext<LocalContractFormCommand> context) {
		LocalContractFormCommand command = context.getCommand();
		try {
//			SystemConfig systemConfig = this.getSystemConfig();
			InstallationType systemConfig = AppContexts.system().getInstallationType();

			// case Cloud
			systemConfig.value = InstallationType.CLOUD.value;
			if (systemConfig.value == InstallationType.CLOUD.value) {
				if (this.isShowContract(command)) {
					return CheckContractDto.failed();
				}
				return CheckContractDto.success();
			}
			// case OnPre
			return CheckContractDto.onpre();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Checks if is show contract.
	 *
	 * @param command
	 *            the command
	 * @return true, if is show contract
	 */
	private boolean isShowContract(LocalContractFormCommand command) {
		// get contract info
		String tenantCode = command.getContractCode();
		String tenantPassword = command.getContractPassword();

		if (tenantCode == null || tenantCode.isEmpty()) {
			return true;
		}
		// get domain contract
		FindTenant.Require require = EmbedStopwatch.embed(new RequireImpl());
		return FindTenant.byTenantCode(require, tenantCode, GeneralDate.today())
				.map(t -> t.verify(tenantPassword))
				.orElse(false);
	}
	
	public class RequireImpl implements FindTenant.Require{

		@Override
		public Optional<TenantAuthentication> getTenantAuthentication(String tenantCode, GeneralDate date) {
			return tenantAuthenticationRepository.find(tenantCode, date);
		}

		@Override
		public Optional<TenantAuthentication> getTenantAuthentication(String tenantCode) {
			return tenantAuthenticationRepository.find(tenantCode);
		}
	}
}