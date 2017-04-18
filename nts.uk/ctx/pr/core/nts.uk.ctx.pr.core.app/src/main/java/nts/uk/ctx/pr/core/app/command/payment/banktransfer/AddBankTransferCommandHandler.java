package nts.uk.ctx.pr.core.app.command.payment.banktransfer;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.BankTransfer;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.BankTransferRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddBankTransferCommandHandler extends CommandHandler<AddBankTransferCommand> {
	@Inject
	private BankTransferRepository bankTransferRepository;

	@Override
	protected void handle(CommandHandlerContext<AddBankTransferCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		BankTransfer bankTransfer = context.getCommand().toDomain(companyCode);
		bankTransferRepository.add(bankTransfer);
	}

}
