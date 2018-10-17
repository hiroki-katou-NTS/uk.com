package nts.uk.ctx.pr.transfer.app.command.bank;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.transfer.dom.bank.BankBranchRepository;
import nts.uk.ctx.pr.transfer.dom.bank.BankRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class DeleteBankBranchCommandHandler extends CommandHandler<String> {
	
	@Inject
	private BankRepository bankRepo;
	
	@Inject
	private BankBranchRepository branchRepo;

	@Override
	protected void handle(CommandHandlerContext<String> context) {
		String companyId = AppContexts.user().companyId();
		if (context.getCommand().length() > 4) {
			branchRepo.removeBranch(companyId, context.getCommand());
		} else {
			bankRepo.removeBank(companyId, context.getCommand());
		}
	}

}
