package nts.uk.ctx.pr.core.app.command.payment.banktransfer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.bank.BankAdapter;
import nts.uk.ctx.pr.core.dom.bank.BasicBankDto;
import nts.uk.ctx.pr.core.dom.bank.branch.adapter.BankBranchAdapter;
import nts.uk.ctx.pr.core.dom.bank.branch.adapter.BasicBankBranchDto;
import nts.uk.ctx.pr.core.dom.bank.linebank.adapter.BasicLineBankDto;
import nts.uk.ctx.pr.core.dom.bank.linebank.adapter.LineBankAdapter;
import nts.uk.ctx.pr.core.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.core.dom.enums.PayBonusAtr;
import nts.uk.ctx.pr.core.dom.enums.SparePayAtr;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.BankTransfer;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.BankTransferRepository;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.adapter.BasicPersonBankAccountDto;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.adapter.BasicPersonUseSettingDto;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.adapter.PersonBankAccountAdapter;
import nts.uk.ctx.pr.core.dom.paymentdata.Payment;
import nts.uk.ctx.pr.core.dom.paymentdata.PaymentDetail;
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

	@Inject
	private LineBankAdapter lineBankAdapter;

	@Inject
	private BankBranchAdapter bankBranchAdapter;

	@Inject
	private BankAdapter bankAdapter;

	@Inject
	private PaymentDataRepository paymentDataRepo;

	@Override
	protected void handle(CommandHandlerContext<AddBankTransferCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		AddBankTransferCommand addBankTransferCommand = context.getCommand();
		// BANK_TRANSFER DEL_1 with SPARE_PAY_ATR = 0
		if (addBankTransferCommand.getSparePayAtrOfScreenE() == 1) {
			process1(companyCode, addBankTransferCommand, SparePayAtr.NORMAL.value);
		}
		// BANK_TRANSFER DEL_1 with SPARE_PAY_ATR = 1
		else if (addBankTransferCommand.getSparePayAtrOfScreenE() == 2) {
			process1(companyCode, addBankTransferCommand, SparePayAtr.PRELIMINARY.value);
		} else {
			// BANK_TRANSFER DEL_1 with SPARE_PAY_ATR = 0 & 1
			process1_1(companyCode, addBankTransferCommand);
		}
	}

	private void process1(String companyCode, AddBankTransferCommand addBankTransferCommand, int sparePayAtr) {
		// PAYMENT_HEADER SEL_3 with PAYBONUS_ATR = 0 and SPARE_PAY_ATR = 0
		Optional<Payment> paymentHeaderObj = paymentDataRepository.find(companyCode,
				addBankTransferCommand.getPersonId(), addBankTransferCommand.getProcessingNoOfScreenE(),
				PayBonusAtr.SALARY.value, addBankTransferCommand.getProcessingYMOfScreenE(), sparePayAtr);
		// After perform SEL_3 of PAYMENT_HEADER, if exist, next process
		if (paymentHeaderObj.isPresent()) {
			// PERSON_BANK_ACCOUNT SEL_7
			Optional<BasicPersonBankAccountDto> basicPersonBankAccountDtoObj = personBankAccountAdapter
					.findBasePIdAndBaseYM(companyCode, addBankTransferCommand.getPersonId(),
							addBankTransferCommand.getProcessingYMOfScreenE());
			if (basicPersonBankAccountDtoObj.isPresent()) {
				if (basicPersonBankAccountDtoObj.get().getUseSet1().getUseSet() == 1
						&& basicPersonBankAccountDtoObj.get().getUseSet1().getPaymentMethod() == 0) {
					process2(companyCode, addBankTransferCommand, basicPersonBankAccountDtoObj.get().getUseSet1(),
							basicPersonBankAccountDtoObj, paymentHeaderObj, sparePayAtr, "F304");
				}
				if (basicPersonBankAccountDtoObj.get().getUseSet2().getUseSet() == 1
						&& basicPersonBankAccountDtoObj.get().getUseSet2().getPaymentMethod() == 0) {
					process2(companyCode, addBankTransferCommand, basicPersonBankAccountDtoObj.get().getUseSet2(),
							basicPersonBankAccountDtoObj, paymentHeaderObj, sparePayAtr, "F305");
				}
				if (basicPersonBankAccountDtoObj.get().getUseSet3().getUseSet() == 1
						&& basicPersonBankAccountDtoObj.get().getUseSet3().getPaymentMethod() == 0) {
					process2(companyCode, addBankTransferCommand, basicPersonBankAccountDtoObj.get().getUseSet3(),
							basicPersonBankAccountDtoObj, paymentHeaderObj, sparePayAtr, "F306");
				}
				if (basicPersonBankAccountDtoObj.get().getUseSet4().getUseSet() == 1
						&& basicPersonBankAccountDtoObj.get().getUseSet4().getPaymentMethod() == 0) {
					process2(companyCode, addBankTransferCommand, basicPersonBankAccountDtoObj.get().getUseSet4(),
							basicPersonBankAccountDtoObj, paymentHeaderObj, sparePayAtr, "F307");
				}
				if (basicPersonBankAccountDtoObj.get().getUseSet5().getUseSet() == 1
						&& basicPersonBankAccountDtoObj.get().getUseSet5().getPaymentMethod() == 0) {
					process2(companyCode, addBankTransferCommand, basicPersonBankAccountDtoObj.get().getUseSet5(),
							basicPersonBankAccountDtoObj, paymentHeaderObj, sparePayAtr, "F308");
				}
			} else {
				// Save error to list
			}
		} else {
			// Save error to list
		}
	}

	private void process1_1(String companyCode, AddBankTransferCommand addBankTransferCommand) {
		// PAYMENT_HEADER SEL_3 with PAYBONUS_ATR = 0 and SPARE_PAY_ATR = 0
		// and 1
		// ERRORRRR
		List<Payment> paymentHeaderObj = paymentDataRepository.findItemWith5Property(companyCode,
				addBankTransferCommand.getPersonId(), addBankTransferCommand.getProcessingNoOfScreenE(),
				PayBonusAtr.SALARY.value, addBankTransferCommand.getProcessingYMOfScreenE());
		if (paymentHeaderObj.size() > 0) {
			// PERSON_BANK_ACCOUNT SEL_7
			Optional<BasicPersonBankAccountDto> basicPersonBankAccountDtoObj = personBankAccountAdapter
					.findBasePIdAndBaseYM(companyCode, addBankTransferCommand.getPersonId(),
							addBankTransferCommand.getProcessingYMOfScreenE());
			if (basicPersonBankAccountDtoObj.isPresent()) {
				for (Payment x : paymentHeaderObj) {
					if (basicPersonBankAccountDtoObj.get().getUseSet1().getUseSet() == 1
							&& basicPersonBankAccountDtoObj.get().getUseSet1().getPaymentMethod() == 0) {
						process2_1(companyCode, addBankTransferCommand, basicPersonBankAccountDtoObj.get().getUseSet1(),
								basicPersonBankAccountDtoObj, x, "F304");
					}
					if (basicPersonBankAccountDtoObj.get().getUseSet2().getUseSet() == 1
							&& basicPersonBankAccountDtoObj.get().getUseSet2().getPaymentMethod() == 0) {
						process2_1(companyCode, addBankTransferCommand, basicPersonBankAccountDtoObj.get().getUseSet2(),
								basicPersonBankAccountDtoObj, x, "F305");
					}
					if (basicPersonBankAccountDtoObj.get().getUseSet3().getUseSet() == 1
							&& basicPersonBankAccountDtoObj.get().getUseSet3().getPaymentMethod() == 0) {
						process2_1(companyCode, addBankTransferCommand, basicPersonBankAccountDtoObj.get().getUseSet3(),
								basicPersonBankAccountDtoObj, x, "F306");
					}
					if (basicPersonBankAccountDtoObj.get().getUseSet4().getUseSet() == 1
							&& basicPersonBankAccountDtoObj.get().getUseSet4().getPaymentMethod() == 0) {
						process2_1(companyCode, addBankTransferCommand, basicPersonBankAccountDtoObj.get().getUseSet4(),
								basicPersonBankAccountDtoObj, x, "F307");
					}
					if (basicPersonBankAccountDtoObj.get().getUseSet5().getUseSet() == 1
							&& basicPersonBankAccountDtoObj.get().getUseSet5().getPaymentMethod() == 0) {
						process2_1(companyCode, addBankTransferCommand, basicPersonBankAccountDtoObj.get().getUseSet5(),
								basicPersonBankAccountDtoObj, x, "F308");
					}
				}
			} else {
				// Save error to list
			}
		} else {
			// Save error to list
		}
	}

	private void process2(String companyCode, AddBankTransferCommand addBankTransferCommand,
			BasicPersonUseSettingDto basicPersonUseSettingDtoObj,
			Optional<BasicPersonBankAccountDto> basicPersonBankAccountDtoObj, Optional<Payment> paymentHeaderObj,
			int sparePayAtr, String itemCode) {
		Optional<BasicLineBankDto> basicLineBankDtoObj = lineBankAdapter.find(companyCode,
				basicPersonUseSettingDtoObj.getFromLineBankCd());
		Optional<BasicBankBranchDto> basicBankBranchDtoObj = bankBranchAdapter.find(companyCode,
				basicLineBankDtoObj.get().getBranchId());
		Optional<BasicBankDto> basicBankDtoObj = bankAdapter.find(companyCode,
				basicBankBranchDtoObj.get().getBankCode());
		// PAYBONUS_ATR = 0 and SPARE_PAY_ATR = 0
		Optional<PaymentDetail> paymentDetailObj = paymentDataRepo.findItemWith9Property(companyCode,
				addBankTransferCommand.getPersonId(), addBankTransferCommand.getProcessingNoOfScreenE(),
				addBankTransferCommand.getProcessingYMOfScreenE(), PayBonusAtr.SALARY.value, sparePayAtr,
				CategoryAtr.ARTICLES.value, itemCode, BigDecimal.ZERO);
		if (paymentDetailObj.isPresent()) {
			// NOTE: dang bi loi
			BankTransfer bankTransfer = BankTransfer.createFromJavaType(companyCode, "companyNameKana",
					"99900000-0000-0000-0000-000000000001", paymentHeaderObj.get().getDepartmentCode(),
					addBankTransferCommand.getPayDateOfScreenE(), PayBonusAtr.SALARY.value,
					paymentDetailObj.get().getValue(), addBankTransferCommand.getProcessingNoOfScreenE(),
					addBankTransferCommand.getProcessingYMOfScreenE(), sparePayAtr);
			bankTransfer.fromBank(basicLineBankDtoObj.get().getBranchId(), basicBankDtoObj.get().getBankNameKana(),
					basicBankBranchDtoObj.get().getBankBranchNameKana(), basicLineBankDtoObj.get().getAccountAtr(),
					basicLineBankDtoObj.get().getAccountNo());
			bankTransfer.toBank(basicPersonBankAccountDtoObj.get().getUseSet1().getToBranchId(),
					basicBankDtoObj.get().getBankNameKana(), basicBankBranchDtoObj.get().getBankBranchNameKana(),
					basicPersonUseSettingDtoObj.getAccountAtr(), basicPersonUseSettingDtoObj.getAccountNo(),
					basicPersonUseSettingDtoObj.getAccountHolderKnName());
			bankTransferRepository.add(bankTransfer);
		} else {
			throw new BusinessException(new RawErrorMessage("PaymentDetail Object has no exist, ERROR!"));
		}
	}

	private void process2_1(String companyCode, AddBankTransferCommand addBankTransferCommand,
			BasicPersonUseSettingDto basicPersonUseSettingDtoObj,
			Optional<BasicPersonBankAccountDto> basicPersonBankAccountDtoObj, Payment paymentHeaderObj,
			String itemCode) {
		Optional<BasicLineBankDto> basicLineBankDtoObj = lineBankAdapter.find(companyCode,
				basicPersonUseSettingDtoObj.getFromLineBankCd());
		Optional<BasicBankBranchDto> basicBankBranchDtoObj = bankBranchAdapter.find(companyCode,
				basicLineBankDtoObj.get().getBranchId());
		Optional<BasicBankDto> basicBankDtoObj = bankAdapter.find(companyCode,
				basicBankBranchDtoObj.get().getBankCode());
		// PAYBONUS_ATR = 0 and SPARE_PAY_ATR = 0
		List<PaymentDetail> paymentDetailObj = paymentDataRepo.findItemWith8Property(companyCode,
				addBankTransferCommand.getPersonId(), addBankTransferCommand.getProcessingNoOfScreenE(),
				addBankTransferCommand.getProcessingYMOfScreenE(), PayBonusAtr.SALARY.value, CategoryAtr.ARTICLES.value,
				itemCode, BigDecimal.ZERO);
		if (paymentDetailObj.size() > 0) {
			// NOTE: dang bi loi
			for (PaymentDetail x : paymentDetailObj) {
				BankTransfer bankTransfer = BankTransfer.createFromJavaType(companyCode, "companyNameKana2",
						"11100000-0000-0000-0000-000000000001", paymentHeaderObj.getDepartmentCode(),
						addBankTransferCommand.getPayDateOfScreenE(), PayBonusAtr.SALARY.value, x.getValue(),
						addBankTransferCommand.getProcessingNoOfScreenE(),
						addBankTransferCommand.getProcessingYMOfScreenE(), x.getSparePayAtr().value);
				bankTransfer.fromBank(basicLineBankDtoObj.get().getBranchId(), basicBankDtoObj.get().getBankNameKana(),
						basicBankBranchDtoObj.get().getBankBranchNameKana(), basicLineBankDtoObj.get().getAccountAtr(),
						basicLineBankDtoObj.get().getAccountNo());
				bankTransfer.toBank(basicPersonBankAccountDtoObj.get().getUseSet1().getToBranchId(),
						basicBankDtoObj.get().getBankNameKana(), basicBankBranchDtoObj.get().getBankBranchNameKana(),
						basicPersonUseSettingDtoObj.getAccountAtr(), basicPersonUseSettingDtoObj.getAccountNo(),
						basicPersonUseSettingDtoObj.getAccountHolderKnName());
				bankTransferRepository.add(bankTransfer);
			}
		} else {
			throw new BusinessException(new RawErrorMessage("PaymentDetail Object has no exist, ERROR!"));
		}
	}
}
