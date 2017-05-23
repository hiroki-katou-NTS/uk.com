package nts.uk.ctx.pr.core.app.find.payment.banktransfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.pr.core.dom.enums.PayBonusAtr;
import nts.uk.ctx.pr.core.dom.enums.SparePayAtr;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.adapter.BasicPersonBankAccountDto;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.adapter.PersonBankAccountAdapter;
import nts.uk.ctx.pr.core.dom.paymentdata.Payment;
import nts.uk.ctx.pr.core.dom.paymentdata.repository.PaymentDataRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.PaydayProcessingRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.PaydayRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.payday.Payday;
import nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.paydayprocessing.PaydayProcessing;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class BankTransferFinder {
	@Inject
	private PaydayProcessingRepository paydayProcessingRepo;

	@Inject
	private PaydayRepository paydayRepo;

	@Inject
	private PaymentDataRepository paymentDataRepo;

	@Inject
	private PersonBankAccountAdapter personBankAccountAdapter;

	public BankTransferDto findDataForScreenD(int processingNo) {
		String companyCode = AppContexts.user().companyCode();
		Optional<PaydayProcessing> paydayProcessing = paydayProcessingRepo.select2(companyCode, processingNo);
		Optional<Payday> payday = paydayRepo.select13(companyCode, processingNo, PayBonusAtr.SALARY.value,
				paydayProcessing.get().getCurrentProcessingYm().v(), SparePayAtr.NORMAL.value);
		List<Payment> paymentHeader = paymentDataRepo.findPaymentHeaderSelect5(companyCode, processingNo,
				paydayProcessing.get().getCurrentProcessingYm().v(), PayBonusAtr.SALARY.value,
				SparePayAtr.NORMAL.value);
		int i = 1;
		List<ListOfScreenDDto> listOfScreenDDto = new ArrayList<>();
		List<ListOfScreenDDto> listOfScreenDDto0 = new ArrayList<>();
		List<ListOfScreenDDto> listOfScreenDDto1 = new ArrayList<>();
		for (Payment x : paymentHeader) {
			Optional<BasicPersonBankAccountDto> basicPersonBankAccDto = personBankAccountAdapter.findBasePIdAndBaseYM(
					companyCode, x.getPersonId().v(), paydayProcessing.get().getCurrentProcessingYm().v());
			// PERSON_BASE SEL_1
			String nameB = "高橋" + i;
			// PERSON_COM SEL_1
			String scd = "A000000000" + i;
			i += 1;
			if (x.getSparePayAtr().value == 0) {
				listOfScreenDDto0.add(
						new ListOfScreenDDto(scd, nameB, basicPersonBankAccDto.get().getUseSet1().getPaymentMethod(),
								basicPersonBankAccDto.get().getUseSet2().getPaymentMethod(),
								basicPersonBankAccDto.get().getUseSet3().getPaymentMethod(),
								basicPersonBankAccDto.get().getUseSet4().getPaymentMethod(),
								basicPersonBankAccDto.get().getUseSet5().getPaymentMethod(),
								basicPersonBankAccDto.get().getPersonID()));
			}
			if (x.getSparePayAtr().value == 1) {
				listOfScreenDDto0.add(
						new ListOfScreenDDto(scd, nameB, basicPersonBankAccDto.get().getUseSet1().getPaymentMethod(),
								basicPersonBankAccDto.get().getUseSet2().getPaymentMethod(),
								basicPersonBankAccDto.get().getUseSet3().getPaymentMethod(),
								basicPersonBankAccDto.get().getUseSet4().getPaymentMethod(),
								basicPersonBankAccDto.get().getUseSet5().getPaymentMethod(),
								basicPersonBankAccDto.get().getPersonID()));
			}
			listOfScreenDDto
					.add(new ListOfScreenDDto(scd, nameB, basicPersonBankAccDto.get().getUseSet1().getPaymentMethod(),
							basicPersonBankAccDto.get().getUseSet2().getPaymentMethod(),
							basicPersonBankAccDto.get().getUseSet3().getPaymentMethod(),
							basicPersonBankAccDto.get().getUseSet4().getPaymentMethod(),
							basicPersonBankAccDto.get().getUseSet5().getPaymentMethod(),
							basicPersonBankAccDto.get().getPersonID()));
		}
		BankTransferDto result = new BankTransferDto(listOfScreenDDto, listOfScreenDDto0, listOfScreenDDto1,
				payday.get().getPayDate());
		return result;
	}
}
