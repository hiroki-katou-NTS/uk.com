 package nts.uk.ctx.basic.app.command.system.bank;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.system.bank.Bank;
import nts.uk.ctx.basic.dom.system.bank.BankRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class RemoveBankCommandHandler extends CommandHandler<RemoveBankCommand> {

	@Inject
    private BankRepository bankRepository;
	
	@Override
	protected void handle(CommandHandlerContext<RemoveBankCommand> context) {
		
       RemoveBankCommand command = context.getCommand();
       String companyCode = AppContexts.user().companyCode();
       
       Bank domain = Bank.createFromJavaType(companyCode, command.getBankCode(), command.getBankName(), command.getBankNameKana(), command.getMemo());
       
       bankRepository.remove(domain);	   
	}
}
