package nts.uk.ctx.pr.transfer.app.command.bank;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.transfer.dom.bank.Bank;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class RegisterBankCommandHandler extends CommandHandler<BankCommand> {

	@Override
	protected void handle(CommandHandlerContext<BankCommand> context) {
		Bank bank = context.getCommand().toDomain();
	}

}
