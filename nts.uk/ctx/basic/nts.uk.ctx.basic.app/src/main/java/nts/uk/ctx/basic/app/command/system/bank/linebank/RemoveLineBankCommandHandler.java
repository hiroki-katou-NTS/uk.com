package nts.uk.ctx.basic.app.command.system.bank.linebank;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.system.bank.linebank.LineBankRepository;
import nts.uk.shr.com.context.AppContexts;
@RequestScoped
public class RemoveLineBankCommandHandler extends CommandHandler<RemoveLineBankCommand> {
	@Inject
	private LineBankRepository lineBankRepository;
	
	@Override
	protected void handle(CommandHandlerContext<RemoveLineBankCommand> context) {
		// TODO Auto-generated method stub
		String companyCode = AppContexts.user().companyCode();
		String lineBankCode = context.getCommand().toDomain(companyCode).getLineBankCode().v();
		
		lineBankRepository.remove(companyCode,lineBankCode);
	}
}
