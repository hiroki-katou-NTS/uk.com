package nts.uk.ctx.basic.app.command.system.bank.linebank;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.system.bank.linebank.LineBank;
import nts.uk.ctx.basic.dom.system.bank.linebank.LineBankRepository;
import nts.uk.shr.com.context.AppContexts;
@RequestScoped
public class UpdateLineBankCommandHandler extends CommandHandler<UpdateLineBankCommand> {
	@Inject
	private LineBankRepository lineBankRepository; 
	@Override
	protected void handle(CommandHandlerContext<UpdateLineBankCommand> context) {
		// TODO Auto-generated method stub
		String companyCode = AppContexts.user().companyCode();
		LineBank lineBank = context.getCommand().toDomain(companyCode);
		
		lineBank.validate();
		
		this.lineBankRepository.update(lineBank);
	}
}
