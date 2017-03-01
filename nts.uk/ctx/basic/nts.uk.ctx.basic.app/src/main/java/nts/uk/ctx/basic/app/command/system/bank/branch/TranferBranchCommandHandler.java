package nts.uk.ctx.basic.app.command.system.bank.branch;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.system.bank.personaccount.PersonBankAccount;
import nts.uk.ctx.basic.dom.system.bank.personaccount.PersonBankAccountRepository;
import nts.uk.ctx.basic.dom.system.bank.personaccount.PersonUseSetting;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class TranferBranchCommandHandler  extends CommandHandler<TranferBranchCommand> {
    
	@Inject
	private PersonBankAccountRepository personBankAccountRepository;
	
	@Override
	protected void handle(CommandHandlerContext<TranferBranchCommand> context) {
		
		TranferBranchCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		command.getBranchCodes().forEach(item -> {
			List<PersonBankAccount> listPersonBankAcc = personBankAccountRepository.findAllBranchCode(companyCode, item.getBankCode(), item.getBranchCode());
			
			listPersonBankAcc.forEach(x -> {
				
				PersonBankAccount bankAccount = x;
				PersonUseSetting useSet1 = bankAccount.getUseSet1(); 
				PersonUseSetting useSet2 = bankAccount.getUseSet2();
				PersonUseSetting useSet3 = bankAccount.getUseSet3();
				PersonUseSetting useSet4 = bankAccount.getUseSet4();
				PersonUseSetting useSet5 = bankAccount.getUseSet5(); 
				
				useSet1 = useSet(item.getBankCode(), item.getBranchCode(), useSet1, command);
				useSet2 = useSet(item.getBankCode(), item.getBranchCode(), useSet2, command);
				useSet3 = useSet(item.getBankCode(), item.getBranchCode(), useSet3, command);
				useSet4 = useSet(item.getBankCode(), item.getBranchCode(), useSet4, command);
				useSet5 = useSet(item.getBankCode(), item.getBranchCode(), useSet5, command);
				
				PersonBankAccount domain = new PersonBankAccount(
						companyCode,
					    x.getPersonID(),
						x.getHistId(),
						x.getStartYearMonth(),
						x.getEndYearMonth(),
						useSet1,
						useSet2,
						useSet3,
						useSet4,
						useSet5);
				
				personBankAccountRepository.update(domain);
			});
		});
	}
	
	private PersonUseSetting useSet(String bankCode, String branchCode, PersonUseSetting bankAccount, TranferBranchCommand command) {
		if (bankAccount.getToBankCd().equals(bankCode) && bankAccount.getToBranchCd().equals(branchCode)) {
			bankAccount.setToBankCd(command.getBankNewCode());
			bankAccount.setToBranchCd(command.getBranchNewCode());
		}
		
		return bankAccount;
	}

}
