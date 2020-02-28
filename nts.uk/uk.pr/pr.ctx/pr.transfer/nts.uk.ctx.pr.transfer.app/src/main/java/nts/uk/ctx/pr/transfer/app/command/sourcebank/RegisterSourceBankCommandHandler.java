package nts.uk.ctx.pr.transfer.app.command.sourcebank;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
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
public class RegisterSourceBankCommandHandler extends CommandHandler<TransferSourceBankCommand> {

	@Inject
	private TransferSourceBankRepository srcBankRepo;

	@Override
	protected void handle(CommandHandlerContext<TransferSourceBankCommand> context) {
		TransferSourceBankCommand command = context.getCommand();
		if (command.isUpdateMode()) {
			srcBankRepo.updateSourceBank(command.toDomain());
		} else {
			if (srcBankRepo.getSourceBank(AppContexts.user().companyId(), command.getCode()).isPresent())
				throw new BusinessException("Msg_3");
			srcBankRepo.addSourceBank(command.toDomain());
		}
	}

}
