package nts.uk.ctx.pr.transfer.app.find.sourcebank;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.transfer.dom.adapter.employment.EmploymentHistImport;
import nts.uk.ctx.pr.transfer.dom.adapter.employment.TransEmploymentHistAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls.CurrProcessYmAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls.CurrProcessYmImport;
import nts.uk.ctx.pr.transfer.dom.bank.BankBranchRepository;
import nts.uk.ctx.pr.transfer.dom.bank.BankRepository;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeBonusPaymentInfor;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeBonusPaymentMethod;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeePaymentInforRepository;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeePaymentMethodRepository;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeSalaryPaymentInfor;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeSalaryPaymentMethod;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.PaymentMethodDetail;
import nts.uk.ctx.pr.transfer.dom.sourcebank.TransferSourceBankRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class TransferSourceBankFinder {

	@Inject
	private TransferSourceBankRepository srcBankRepo;

	@Inject
	private BankRepository bankRepo;

	@Inject
	private BankBranchRepository bankBranchRepo;

	@Inject
	private CurrProcessYmAdapter currProcYmAdapter;

	@Inject
	private EmployeePaymentInforRepository empPayInfoRepo;

	@Inject
	private EmployeePaymentMethodRepository empPayMethodRepo;
	
	@Inject
	private TransEmploymentHistAdapter employmentAdapter;

	public List<TransferSourceBankDto> getAll() {
		String companyId = AppContexts.user().companyId();
		return srcBankRepo.getAllSourceBank(companyId).stream().map(b -> new TransferSourceBankDto(b, null, null))
				.collect(Collectors.toList());
	}

	public TransferSourceBankDto getTransferSourceBank(String code) {
		String companyId = AppContexts.user().companyId();
		val transferSourceBank = srcBankRepo.getSourceBank(companyId, code).orElse(null);
		if (transferSourceBank != null) {
			val bankBranch = bankBranchRepo.findBranch(companyId, transferSourceBank.getBranchId()).orElse(null);
			val bank = bankRepo.findBank(companyId, bankBranch == null ? "" : bankBranch.getBankCode().v())
					.orElse(null);
			return new TransferSourceBankDto(transferSourceBank, bankBranch, bank);
		} else
			return null;
	}

	public void checkBeforeDelete(String code) {
		String companyId = AppContexts.user().companyId();
		Optional<CurrProcessYmImport> optCurProcYm = currProcYmAdapter.getCurrentSalaryProcessYm(companyId, 1);
		if (optCurProcYm.isPresent()) {
			List<EmploymentHistImport> lstEmpHist = employmentAdapter.findByCidAndDate(companyId, GeneralDate.today());
			for (EmploymentHistImport empHist : lstEmpHist) {
				String employeeId = empHist.getEmployeeId();
				Optional<EmployeeSalaryPaymentInfor> optEmpSalInfo = empPayInfoRepo.getEmpSalPaymentInfo(employeeId);
				if (optEmpSalInfo.isPresent()) {
					List<EmployeeSalaryPaymentMethod> lstSalPayMethod = empPayMethodRepo
							.getSalPayMethodByHistoryId(optEmpSalInfo.get().items().stream()
									.filter(h -> h.contains(optCurProcYm.get().getCurrentYm())).map(h -> h.identifier())
									.collect(Collectors.toList()))
							.stream().filter(m -> checkPaymentMethodDetailOfSelectedBrach(m, code))
							.collect(Collectors.toList());
					if (!lstSalPayMethod.isEmpty()) {
						throw new BusinessException("Msg_17", "QMM006_50");
					}
				}
				Optional<EmployeeBonusPaymentInfor> optEmpBonusInfo = empPayInfoRepo.getEmpBonusPaymentInfo(employeeId);
				if (optEmpBonusInfo.isPresent()) {
					List<EmployeeBonusPaymentMethod> lstBonusPayMethod = empPayMethodRepo
							.getBonusPayMethodByHistoryId(optEmpBonusInfo.get().items().stream()
									.filter(h -> h.contains(optCurProcYm.get().getCurrentYm())).map(h -> h.identifier())
									.collect(Collectors.toList()))
							.stream().filter(m -> checkPaymentMethodDetailOfSelectedBrach(m, code))
							.collect(Collectors.toList());
					if (!lstBonusPayMethod.isEmpty()) {
						throw new BusinessException("Msg_17", "QMM006_50");
					}
				}
			}
		}
	}

	private boolean checkPaymentMethodDetailOfSelectedBrach(EmployeeSalaryPaymentMethod empSalPayMethod, String code) {
		List<PaymentMethodDetail> listPaymentMethod = empSalPayMethod.getListPaymentMethod();
		if (!listPaymentMethod.isEmpty()) {
			for (PaymentMethodDetail detail : listPaymentMethod) {
				if (detail.getTransferInfor().isPresent()
						&& detail.getTransferInfor().get().getSourceBankBranchInfor().v().equals(code))
					return true;
			}
		}
		return false;
	}

	private boolean checkPaymentMethodDetailOfSelectedBrach(EmployeeBonusPaymentMethod empSalPayMethod, String code) {
		if (empSalPayMethod.getListPaymentMethod().isPresent()) {
			List<PaymentMethodDetail> listPaymentMethod = empSalPayMethod.getListPaymentMethod().get();
			if (!listPaymentMethod.isEmpty()) {
				for (PaymentMethodDetail detail : listPaymentMethod) {
					if (detail.getTransferInfor().isPresent()
							&& detail.getTransferInfor().get().getSourceBankBranchInfor().v().equals(code))
						return true;
				}
			}
		}
		return false;
	}

}
