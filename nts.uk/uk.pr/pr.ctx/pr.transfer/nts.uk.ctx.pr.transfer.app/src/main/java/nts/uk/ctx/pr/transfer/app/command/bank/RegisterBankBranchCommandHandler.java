package nts.uk.ctx.pr.transfer.app.command.bank;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.pr.transfer.dom.bank.BankBranch;
import nts.uk.ctx.pr.transfer.dom.bank.BankBranchRepository;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class RegisterBankBranchCommandHandler extends CommandHandlerWithResult<BankBranchCommand, String> {
	
	@Inject
	private BankBranchRepository branchRepo;

	@Override
	protected String handle(CommandHandlerContext<BankBranchCommand> context) {
		BankBranch branch = context.getCommand().toDomain();
		if (context.getCommand().getId() == null || context.getCommand().getId().isEmpty()) {
			branchRepo.addBranch(branch);
		} else {
			branchRepo.updateBranch(branch);
		}
		return branch.getBranchId();
	}

	

}
