 package nts.uk.ctx.basic.app.command.system.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.system.bank.Bank;
import nts.uk.ctx.basic.dom.system.bank.BankCode;
import nts.uk.ctx.basic.dom.system.bank.BankRepository;
import nts.uk.ctx.basic.dom.system.bank.branch.BankBranch;
import nts.uk.ctx.basic.dom.system.bank.branch.BankBranchRepository;
import nts.uk.ctx.basic.dom.system.bank.personaccount.PersonBankAccountRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class RemoveBankCommandHandler extends CommandHandler<RemoveBankCommand> {

	@Inject
    private BankRepository bankRepository;
	
	@Inject
	private PersonBankAccountRepository personBankAccountRepository;
	
	@Inject
	private BankBranchRepository bankBranchRepo;
	
	@Override
	protected void handle(CommandHandlerContext<RemoveBankCommand> context) {
		
       RemoveBankCommand command = context.getCommand();
       String companyCode = AppContexts.user().companyCode();
       List<String>  bankCodeList = new ArrayList<String>();
       bankCodeList.add(command.getBankCode());
       
       Optional<Bank> domain = bankRepository.find(companyCode, command.getBankCode());
       if (!domain.isPresent()) {
    	   throw new RuntimeException("Bank not found");
       }
       
       // check exists person bank account
       if(personBankAccountRepository.checkExistsBankAccount(companyCode, bankCodeList)){
    	   throw new BusinessException("選択された＊は使用されているため削除できません。"); // ER008
       }
       
       // delete all branch by bank code
       List<BankBranch> branchAll = bankBranchRepo.findAll(companyCode, new BankCode(command.getBankCode()));
       if (!branchAll.isEmpty()) {
    	   branchAll.forEach((item) -> {
    		   bankBranchRepo.remove(companyCode, command.getBankCode(), item.getBankBranchCode().v());
    	   }); 
       }
       
       // delete bank
  	   bankRepository.remove(domain.get());
	}
}
