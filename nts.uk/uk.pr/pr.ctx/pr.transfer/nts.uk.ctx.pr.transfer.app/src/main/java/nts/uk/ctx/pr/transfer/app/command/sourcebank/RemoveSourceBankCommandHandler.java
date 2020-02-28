package nts.uk.ctx.pr.transfer.app.command.sourcebank;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.transfer.dom.sourcebank.TransferSourceBankRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class RemoveSourceBankCommandHandler extends CommandHandler<String> {

	@Inject
	private TransferSourceBankRepository srcBankRepo;

	@Override
	protected void handle(CommandHandlerContext<String> context) {
		srcBankRepo.removeSourceBank(AppContexts.user().companyId(), context.getCommand());
	}

}
