package nts.uk.ctx.pr.transfer.app.command.bank;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.transfer.dom.bank.Bank;
import nts.uk.ctx.pr.transfer.dom.bank.BankRepository;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class RegisterBankCommandHandler extends CommandHandler<BankCommand> {

	@Inject
	private BankRepository bankRepo;

	@Override
	protected void handle(CommandHandlerContext<BankCommand> context) {
		Bank bank = context.getCommand().toDomain();
		if (context.getCommand().isUpdateMode()) {
			bankRepo.updateBank(bank);
		} else {
			if (bankRepo.checkExistBank(bank.getCompanyId(), bank.getBankCode().v()))
				throw new BusinessException("Msg_3");
			bankRepo.addBank(bank);
		}
	}

}
