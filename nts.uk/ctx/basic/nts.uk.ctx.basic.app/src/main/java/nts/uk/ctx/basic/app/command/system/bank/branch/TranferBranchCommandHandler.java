package nts.uk.ctx.basic.app.command.system.bank.branch;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.company.CompanyCode;
import nts.uk.ctx.basic.dom.system.bank.branch.BankBranch;
import nts.uk.ctx.basic.dom.system.bank.branch.BankBranchRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class TranferBranchCommandHandler extends CommandHandler<TranferBranchCommand> {
	@Inject
	private BankBranchRepository banhBranchRepo;
	
	@Override
	protected void handle(CommandHandlerContext<TranferBranchCommand> context) {
		TranferBranchCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		command.getBranchCodes().forEach(item -> {
			Optional<BankBranch> branchOp = banhBranchRepo.find(companyCode, item.getBankCode(), item.getBranchCode());
			if (branchOp.isPresent()) {
				BankBranch branch = branchOp.get();
				BankBranch newBankBranch = new BankBranch(
						new CompanyCode(companyCode),
						command.getBankNewCode(), 
						branch.getBankBranchCode(), 
						branch.getBankBranchName(), 
						branch.getBankBranchNameKana(),  
						branch.getMemo());
				banhBranchRepo.remove(companyCode, item.getBankCode(), item.getBranchCode());
				banhBranchRepo.add(newBankBranch);
			}
		});
	}

}
