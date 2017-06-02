package nts.uk.ctx.pr.core.app.command.payment.banktransfer;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.enums.PayBonusAtr;
import nts.uk.ctx.pr.core.dom.enums.SparePayAtr;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.BankTransferRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class RemoveBankTransferCommandHandler extends CommandHandler<AddBankTransferCommand> {

	@Inject
	private BankTransferRepository bankTransferRepository;

	@Override
	protected void handle(CommandHandlerContext<AddBankTransferCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		AddBankTransferCommand addBankTransferCommand = context.getCommand();
		if (addBankTransferCommand.getSparePayAtrOfScreenE() == 1) {
			bankTransferRepository.remove(companyCode, PayBonusAtr.SALARY.value,
					addBankTransferCommand.getProcessingNoOfScreenE(), addBankTransferCommand.getPayDateOfScreenE(),
					SparePayAtr.NORMAL.value);
		} else if (addBankTransferCommand.getSparePayAtrOfScreenE() == 2) {
			bankTransferRepository.remove(companyCode, PayBonusAtr.SALARY.value,
					addBankTransferCommand.getProcessingNoOfScreenE(), addBankTransferCommand.getPayDateOfScreenE(),
					SparePayAtr.PRELIMINARY.value);
		} else if (addBankTransferCommand.getSparePayAtrOfScreenE() == 3) {
			// BANK_TRANSFER DEL_1 with SPARE_PAY_ATR = 0 and 1
			bankTransferRepository.removeAll(companyCode, PayBonusAtr.SALARY.value,
					addBankTransferCommand.getProcessingNoOfScreenE(), addBankTransferCommand.getPayDateOfScreenE());
		}
	}
}
