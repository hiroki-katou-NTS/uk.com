package nts.uk.ctx.basic.app.command.system.bank.branch;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.system.bank.branch.BankBranch;
import nts.uk.ctx.basic.dom.system.bank.branch.BankBranchRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddBranchCommandHandler extends CommandHandler<AddBranchCommand> {
	@Inject
	private BankBranchRepository bankBranchRepository; 
	
	@Override
	protected void handle(CommandHandlerContext<AddBranchCommand> context) {
		AddBranchCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		boolean existsBranch = bankBranchRepository.checkExists(companyCode, command.getBankCode(), command.getBranchCode());
		if (existsBranch) {
			throw new BusinessException("ER005");
		}
		
		BankBranch domain =  BankBranch.newBranch(companyCode, command.getBankCode(), command.getBranchCode(), command.getBranchName(), command.getBranchKnName(), command.getMemo());
		
		// validate
		domain.validate();
		
		bankBranchRepository.add(domain);
	}

}
