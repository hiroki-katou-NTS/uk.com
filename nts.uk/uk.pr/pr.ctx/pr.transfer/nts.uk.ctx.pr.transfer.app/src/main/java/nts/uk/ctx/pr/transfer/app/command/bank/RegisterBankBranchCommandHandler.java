package nts.uk.ctx.pr.transfer.app.command.bank;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.pr.transfer.dom.bank.BankBranch;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class RegisterBankBranchCommandHandler extends CommandHandlerWithResult<BankBranchCommand, String> {

	@Override
	protected String handle(CommandHandlerContext<BankBranchCommand> context) {
		BankBranch branch = context.getCommand().toDomain();
		return branch.getBranchId();
	}

	

}
