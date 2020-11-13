package nts.uk.ctx.sys.gateway.app.command.securitypolicy;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicyRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicyRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateAccountPolicyCommandHandler extends CommandHandler<UpdateAccountPolicyCommand> {
	@Inject
	private AccountLockPolicyRepository accountLockPolicyRepository;
	@Inject
	private PasswordPolicyRepository passwordPolicyRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateAccountPolicyCommand> context) {
		UpdateAccountPolicyCommand command = context.getCommand();
		String contractCode = AppContexts.user().contractCode();
		PasswordPolicy passwordPolicy = PasswordPolicy.createFromJavaType(contractCode,
				command.notificationPasswordChange, command.loginCheck, command.initialPasswordChange,
				command.isPasswordUse, command.historyCount, command.lowestDigits, command.validityPeriod,
				command.numberOfDigits, command.symbolCharacters, command.alphabetDigit);
		this.passwordPolicyRepository.updatePasswordPolicy(passwordPolicy);
		AccountLockPolicy accountLockPolicy = AccountLockPolicy.createFromJavaType(contractCode, command.errorCount,
				command.lockInterval, command.lockOutMessage, command.isAccLockUse);
		this.accountLockPolicyRepository.updateAccountLockPolicy(accountLockPolicy);
	}

}
