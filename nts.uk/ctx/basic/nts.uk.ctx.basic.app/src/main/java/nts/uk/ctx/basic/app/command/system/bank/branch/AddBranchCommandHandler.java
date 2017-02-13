package nts.uk.ctx.basic.app.command.system.bank.branch;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.system.bank.branch.BankBranch;
import nts.uk.ctx.basic.dom.system.bank.branch.BankBranchRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class AddBranchCommandHandler extends CommandHandler<AddBranchCommand> {
	@Inject
	private BankBranchRepository bankBranchRepository; 
	
	@Override
	protected void handle(CommandHandlerContext<AddBranchCommand> context) {
		AddBranchCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		Optional<BankBranch> branch = bankBranchRepository.find(companyCode, command.getBankCode(), command.getBranchCode());
		if (branch.isPresent()) {
			throw new BusinessException("Bank branch exists!");
		}
		
		BankBranch domain =  BankBranch.createFromJavaType(companyCode, command.getBankCode(), command.getBranchCode(), command.getBranchName(), command.getBranchKnName(), command.getMemo());
		
		bankBranchRepository.add(domain);
	}

}
