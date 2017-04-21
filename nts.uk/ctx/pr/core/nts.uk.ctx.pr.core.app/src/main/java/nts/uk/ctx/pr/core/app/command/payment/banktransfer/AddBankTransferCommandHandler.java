package nts.uk.ctx.pr.core.app.command.payment.banktransfer;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.enums.PayBonusAtr;
import nts.uk.ctx.pr.core.dom.enums.SparePayAtr;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.BankTransfer;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.BankTransferRepository;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.adapter.BasicPersonBankAccountDto;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.adapter.PersonBankAccountAdapter;
import nts.uk.ctx.pr.core.dom.paymentdata.Payment;
import nts.uk.ctx.pr.core.dom.paymentdata.repository.PaymentDataRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddBankTransferCommandHandler extends CommandHandler<AddBankTransferCommand> {
	@Inject
	private BankTransferRepository bankTransferRepository;

	@Inject
	private PaymentDataRepository paymentDataRepository;

	@Inject
	private PersonBankAccountAdapter personBankAccountAdapter;

	@Override
	protected void handle(CommandHandlerContext<AddBankTransferCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		AddBankTransferCommand addBankTransferCommand = context.getCommand();
		BankTransfer bankTransfer = addBankTransferCommand.toDomain(companyCode);
		// BANK_TRANSFER DEL_1 with SPARE_PAY_ATR = 0
		if (addBankTransferCommand.getSparePayAtrOfScreenE() == 1) {
			bankTransferRepository.remove(companyCode, addBankTransferCommand.getProcessingNoOfScreenE(),
					addBankTransferCommand.getPayDateOfScreenE(), SparePayAtr.NORMAL.value);
			// PAYMENT_HEADER SEL_3 with PAYBONUS_ATR = 0 and SPARE_PAY_ATR = 0
			List<Payment> paymentList = paymentDataRepository.findWith6Property(companyCode,
					addBankTransferCommand.getPersonId(), addBankTransferCommand.getProcessingNoOfScreenE(),
					PayBonusAtr.SALARY.value, addBankTransferCommand.getProcessingYMOfScreenE(),
					SparePayAtr.NORMAL.value);
			// after perform SEL_3 of PAYMENT_HEADER, if return list has 1
			// object or more
			if (paymentList.size() > 0) {
				// PERSON_BANK_ACCOUNT SEL_7
				List<BasicPersonBankAccountDto> personBankAccountAdapterList = personBankAccountAdapter
						.findBasePIdAndBaseYM(companyCode, addBankTransferCommand.getPersonId(),
								addBankTransferCommand.getProcessingYMOfScreenE());
				// bankTransferRepository.add(bankTransfer);
			} else {
				// Save error to list
			}
		}
		// BANK_TRANSFER DEL_1 with SPARE_PAY_ATR = 1
		else if (addBankTransferCommand.getSparePayAtrOfScreenE() == 2) {
			bankTransferRepository.remove(companyCode, addBankTransferCommand.getProcessingNoOfScreenE(),
					addBankTransferCommand.getPayDateOfScreenE(), SparePayAtr.PRELIMINARY.value);
			// PAYMENT_HEADER SEL_3 with PAYBONUS_ATR = 0 and SPARE_PAY_ATR = 1
			List<Payment> paymentList = paymentDataRepository.findWith6Property(companyCode,
					addBankTransferCommand.getPersonId(), addBankTransferCommand.getProcessingNoOfScreenE(),
					PayBonusAtr.SALARY.value, addBankTransferCommand.getProcessingYMOfScreenE(),
					SparePayAtr.NORMAL.value);
			// after perform SEL_3 of PAYMENT_HEADER, if return list has 1
			// object or more
			if (paymentList.size() > 0) {
				// PERSON_BANK_ACCOUNT SEL_7
				personBankAccountAdapter.findBasePIdAndBaseYM(companyCode, addBankTransferCommand.getPersonId(),
						addBankTransferCommand.getProcessingYMOfScreenE());
				// bankTransferRepository.add(bankTransfer);
			}
		} else {
			// BANK_TRANSFER DEL_1 with SPARE_PAY_ATR = 0 & 1
			bankTransferRepository.removeAll(companyCode, addBankTransferCommand.getProcessingNoOfScreenE(),
					addBankTransferCommand.getPayDateOfScreenE());
			// PAYMENT_HEADER SEL_3 with PAYBONUS_ATR = 0 and SPARE_PAY_ATR = 1
			// & 0
			List<Payment> paymentList = paymentDataRepository.findWith5Property(companyCode,
					addBankTransferCommand.getPersonId(), addBankTransferCommand.getProcessingNoOfScreenE(),
					PayBonusAtr.SALARY.value, addBankTransferCommand.getProcessingYMOfScreenE());
			// after perform SEL_3 of PAYMENT_HEADER, if return list has 1
			// object or
			// more
			if (paymentList.size() > 0) {
				// PERSON_BANK_ACCOUNT SEL_7
				personBankAccountAdapter.findBasePIdAndBaseYM(companyCode, addBankTransferCommand.getPersonId(),
						addBankTransferCommand.getProcessingYM());
				// bankTransferRepository.add(bankTransfer);
			}
		}
	}
}
