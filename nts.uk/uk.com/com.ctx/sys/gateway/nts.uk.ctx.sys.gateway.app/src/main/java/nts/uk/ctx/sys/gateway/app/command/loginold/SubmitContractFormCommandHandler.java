/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.loginold;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.FindTenant;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthentication;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticationRepository;

/**
 * The Class SubmitContractFormCommandHandler.
 */
@Stateless
public class SubmitContractFormCommandHandler extends CommandHandler<SubmitContractFormCommand> {

	/** The contract repository. */
	@Inject
	private TenantAuthenticationRepository tenantAuthenticationRepository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SubmitContractFormCommand> context) {
		SubmitContractFormCommand command = context.getCommand();
		String contractCode = command.getContractCode();
		String password = command.getPassword();
		// pre check
		this.checkInput(command);
		// contract auth
		this.contractAccAuth(contractCode, password);
	}

	/**
	 * Contract acc auth.
	 *
	 * @param contractCode the contract code
	 * @param password the password
	 */
	private void contractAccAuth(String tenantCode, String password) {
		FindTenant.Require require = EmbedStopwatch.embed(new RequireImpl());
		val optTenant = FindTenant.byTenantCode(require, tenantCode);
		if (!optTenant.isPresent()) {
			// テナントが取得できない
			throw new BusinessException("Msg_314");
		} 
		val tenant = optTenant.get();
		if(!tenant.verify(password)) {
			// テナントパスワードが間違っている
			throw new BusinessException("Msg_302");
		}
		if(!tenant.isAvailableAt(GeneralDate.today())) {
			// テナントの有効期限が来てレイル
			throw new BusinessException("Msg_315");
		}
	}
	
	/**
	 * Check input.
	 *
	 * @param command the command
	 */
	private void checkInput(SubmitContractFormCommand command) {
		if (command.getContractCode() == null || command.getContractCode().isEmpty()) {
			throw new BusinessException("Msg_313");
		}
		if (command.getPassword() == null || command.getPassword().isEmpty()) {
			throw new BusinessException("Msg_310");
		}
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
