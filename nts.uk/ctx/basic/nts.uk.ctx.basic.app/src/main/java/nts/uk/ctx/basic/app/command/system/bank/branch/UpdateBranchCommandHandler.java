package nts.uk.ctx.basic.app.command.system.bank.branch;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.app.command.system.bank.UpdateBankCommand;
import nts.uk.ctx.basic.dom.system.bank.Bank;
import nts.uk.ctx.basic.dom.system.bank.branch.BankBranch;
import nts.uk.ctx.basic.dom.system.bank.branch.BankBranchRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class UpdateBranchCommandHandler extends CommandHandler<UpdateBranchCommand> {
	@Inject
	private BankBranchRepository bankBranchRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateBranchCommand> context) {
		UpdateBranchCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		BankBranch domain = BankBranch.createFromJavaType(companyCode, command.getBankCode(), command.getBranchCode(), command.getBranchName(), command.getBranchKnName(), command.getMemo());
		
		// validate
		domain.validate();
				
		bankBranchRepository.update(domain);	
	} 
	
	
}
