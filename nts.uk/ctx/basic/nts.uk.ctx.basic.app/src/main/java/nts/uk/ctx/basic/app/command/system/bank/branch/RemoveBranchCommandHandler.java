package nts.uk.ctx.basic.app.command.system.bank.branch;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.system.bank.branch.BankBranchRepository;
import nts.uk.ctx.basic.dom.system.bank.personaccount.PersonBankAccountRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class RemoveBranchCommandHandler extends CommandHandler<RemoveBranchCommand> {
	@Inject
	private BankBranchRepository banhBranchRepo;
	
	@Inject
	private PersonBankAccountRepository personBankAccountRepository;
	
	@Override
	protected void handle(CommandHandlerContext<RemoveBranchCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		RemoveBranchCommand command = context.getCommand();
		
		banhBranchRepo.remove(companyCode, command.getBankCode(), command.getBranchCode());	
	}

}
