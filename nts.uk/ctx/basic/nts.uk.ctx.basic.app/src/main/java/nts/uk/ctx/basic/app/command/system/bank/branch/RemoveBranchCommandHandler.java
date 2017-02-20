package nts.uk.ctx.basic.app.command.system.bank.branch;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.system.bank.branch.BankBranchRepository;
import nts.uk.ctx.basic.dom.system.bank.personaccount.PersonBankAccountRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class RemoveBranchCommandHandler extends CommandHandler<RemoveBranchCommand> {
	@Inject
	private BankBranchRepository bankBranchRepo;
	
	@Inject
	private PersonBankAccountRepository personBankAccountRepository;
	
	@Override
	protected void handle(CommandHandlerContext<RemoveBranchCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		RemoveBranchCommand command = context.getCommand();
		List<String>  bankCodeList = new ArrayList<String>();
	    bankCodeList.add(command.getBankCode());
	    List<String> branchCodeList = new ArrayList<String>();
	    branchCodeList.add(command.getBranchCode());
	    if(personBankAccountRepository.checkExistsBranchAccount(companyCode, bankCodeList, branchCodeList)){
	    	throw new BusinessException("選択された＊は使用されているため削除できません。"); // ER008
	    }
		bankBranchRepo.remove(companyCode, command.getBankCode(), command.getBranchCode());	
	}

}
