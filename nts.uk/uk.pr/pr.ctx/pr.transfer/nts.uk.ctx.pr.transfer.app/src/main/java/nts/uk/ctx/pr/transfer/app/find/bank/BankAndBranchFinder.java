package nts.uk.ctx.pr.transfer.app.find.bank;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls.CurrProcessYmAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls.CurrProcessYmImport;
import nts.uk.ctx.pr.transfer.dom.bank.BankBranch;
import nts.uk.ctx.pr.transfer.dom.bank.BankBranchRepository;
import nts.uk.ctx.pr.transfer.dom.bank.BankRepository;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeBonusPaymentInfor;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeBonusPaymentMethod;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeePaymentInforRepository;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeePaymentMethodRepository;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeSalaryPaymentInfor;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeSalaryPaymentMethod;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.PaymentMethodDetail;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class BankAndBranchFinder {

	@Inject
	private BankRepository bankRepo;

	@Inject
	private BankBranchRepository branchRepo;

	@Inject
	private CurrProcessYmAdapter currProcYmAdapter;

	@Inject
	private EmployeePaymentInforRepository empPayInfoRepo;

	@Inject
	private EmployeePaymentMethodRepository empPayMethodRepo;

	public List<BankDto> getAllBank() {
		String companyId = AppContexts.user().companyId();
		return bankRepo.findAllBank(companyId).stream().map(b -> new BankDto(b)).collect(Collectors.toList());
	}

	public List<BankBranchDto> getAllBankBranch(List<String> bankCodes) {
		String companyId = AppContexts.user().companyId();
		return branchRepo.findAllBranch(companyId, bankCodes).stream().map(br -> new BankBranchDto(br))
				.collect(Collectors.toList());
	}

	public BankBranchDto getBankBranchById(String branchId) {
		String companyId = AppContexts.user().companyId();
		Optional<BankBranch> optBr = branchRepo.findBranch(companyId, branchId);
		if (optBr.isPresent()) return new BankBranchDto(optBr.get());
		else return null;
	}

	public void checkBeforDeleteBranch(String branchId) {
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		if (branchId.length() > 4) {
			Optional<CurrProcessYmImport> optCurProcYm = currProcYmAdapter.getCurrentSalaryProcessYm(companyId, 1);
			if (optCurProcYm.isPresent()) {
				Optional<EmployeeSalaryPaymentInfor> optEmpSalInfo = empPayInfoRepo.getEmpSalPaymentInfo(employeeId);
				if (optEmpSalInfo.isPresent()) {
					List<EmployeeSalaryPaymentMethod> lstSalPayMethod = empPayMethodRepo
							.getSalPayMethodByHistoryId(optEmpSalInfo.get().items().stream()
									.filter(h -> h.contains(optCurProcYm.get().getCurrentYm())).map(h -> h.identifier())
									.collect(Collectors.toList()))
							.stream().filter(m -> checkPaymentMethodDetailOfSelectedBrach(m, branchId))
							.collect(Collectors.toList());
					if (!lstSalPayMethod.isEmpty()) {
						throw new BusinessException("Msg_17", "QMM002_35");
					}
				}
				Optional<EmployeeBonusPaymentInfor> optEmpBonusInfo = empPayInfoRepo.getEmpBonusPaymentInfo(employeeId);
				if (optEmpBonusInfo.isPresent()) {
					List<EmployeeBonusPaymentMethod> lstBonusPayMethod = empPayMethodRepo
							.getBonusPayMethodByHistoryId(optEmpBonusInfo.get().items().stream()
									.filter(h -> h.contains(optCurProcYm.get().getCurrentYm())).map(h -> h.identifier())
									.collect(Collectors.toList()))
							.stream().filter(m -> checkPaymentMethodDetailOfSelectedBrach(m, branchId))
							.collect(Collectors.toList());
					if (!lstBonusPayMethod.isEmpty()) {
						throw new BusinessException("Msg_17", "QMM002_35");
					}
				}
			}
		} else {
			String bankCode = branchId;
			if (!branchRepo.findAllBranchByBank(companyId, bankCode).isEmpty())
				throw new BusinessException("Msg_17", "QMM002_36");
		}
	}

	private boolean checkPaymentMethodDetailOfSelectedBrach(EmployeeSalaryPaymentMethod empSalPayMethod,
			String branchId) {
		List<PaymentMethodDetail> listPaymentMethod = empSalPayMethod.getListPaymentMethod();
		if (!listPaymentMethod.isEmpty()) {
			for (PaymentMethodDetail detail : listPaymentMethod) {
				if (detail.getTransferInfor().isPresent()
						&& detail.getTransferInfor().get().getDesBankBranchInfor().equals(branchId))
					return true;
			}
		}
		return false;
	}

	private boolean checkPaymentMethodDetailOfSelectedBrach(EmployeeBonusPaymentMethod empSalPayMethod,
			String branchId) {
		if (empSalPayMethod.getListPaymentMethod().isPresent()) {
			List<PaymentMethodDetail> listPaymentMethod = empSalPayMethod.getListPaymentMethod().get();
			if (!listPaymentMethod.isEmpty()) {
				for (PaymentMethodDetail detail : listPaymentMethod) {
					if (detail.getTransferInfor().isPresent()
							&& detail.getTransferInfor().get().getDesBankBranchInfor().equals(branchId))
						return true;
				}
			}
		}
		return false;
	}

}
