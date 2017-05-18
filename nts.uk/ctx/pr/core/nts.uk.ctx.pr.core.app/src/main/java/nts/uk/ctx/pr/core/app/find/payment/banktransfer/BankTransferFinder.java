package nts.uk.ctx.pr.core.app.find.payment.banktransfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.pr.core.dom.enums.SparePayAtr;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.adapter.BasicPersonBankAccountDto;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.adapter.PersonBankAccountAdapter;
import nts.uk.ctx.pr.core.dom.enums.PayBonusAtr;
import nts.uk.ctx.pr.core.dom.paymentdata.Payment;
import nts.uk.ctx.pr.core.dom.paymentdata.repository.PaymentDataRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.PaydayProcessingRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.paydayprocessing.PaydayProcessing;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class BankTransferFinder {
	@Inject
	private PaydayProcessingRepository paydayProcessingRepo;

	@Inject
	private PaymentDataRepository paymentDataRepo;

	@Inject
	private PersonBankAccountAdapter personBankAccountAdapter;

	public List<BankTransferDto> findDataForScreenD(int processingNo) {
		String companyCode = AppContexts.user().companyCode();
		Optional<PaydayProcessing> paydayProcessing = paydayProcessingRepo.select2(companyCode, processingNo);
		List<Payment> paymentHeader = paymentDataRepo.findPaymentHeaderSelect5(companyCode, processingNo,
				paydayProcessing.get().getCurrentProcessingYm().v(), PayBonusAtr.SALARY.value,
				SparePayAtr.NORMAL.value);
		int i = 1;
		List<BankTransferDto> result = new ArrayList<>();
		for (Payment x : paymentHeader) {
			Optional<BasicPersonBankAccountDto> basicPersonBankAccDto = personBankAccountAdapter.findBasePIdAndBaseYM(
					companyCode, x.getPersonId().v(), paydayProcessing.get().getCurrentProcessingYm().v());
			// PERSON_BASE SEL_1
			String nameB = "高橋" + i;
			// PERSON_COM SEL_1
			String scd = "A000000000000" + i;
			i += 1;
			result.add(new BankTransferDto(scd, nameB, basicPersonBankAccDto.get().getUseSet1().getPaymentMethod(),
					basicPersonBankAccDto.get().getUseSet2().getPaymentMethod(),
					basicPersonBankAccDto.get().getUseSet3().getPaymentMethod(),
					basicPersonBankAccDto.get().getUseSet4().getPaymentMethod(),
					basicPersonBankAccDto.get().getUseSet5().getPaymentMethod()));
		}
		return result;
	}
}
