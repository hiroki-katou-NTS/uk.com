package nts.uk.ctx.pr.transfer.app.command.bank;

import java.util.List;
import java.util.stream.Collectors;

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
public class DeleteListBankBranchCommandHandler extends CommandHandler<List<String>> {

	@Inject
	private BankRepository bankRepo;

	@Inject
	private BankBranchRepository branchRepo;

	@Override
	protected void handle(CommandHandlerContext<List<String>> context) {
		String companyId = AppContexts.user().companyId();
		List<String> targets = context.getCommand();
		List<String> banks = targets.stream().filter(t -> t.length() <= 4).collect(Collectors.toList());
		for (String bankCode : banks) {
			bankRepo.removeBank(companyId, bankCode);
			branchRepo.removeListBranchFromBank(companyId, bankCode);
		}
		List<String> branches = targets.stream().filter(t -> t.length() > 4).collect(Collectors.toList());
		branchRepo.removeListBranch(companyId, branches);
	}

}
