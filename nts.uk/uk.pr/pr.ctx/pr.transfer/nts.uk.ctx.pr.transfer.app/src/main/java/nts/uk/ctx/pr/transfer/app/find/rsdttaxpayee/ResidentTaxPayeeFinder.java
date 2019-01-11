package nts.uk.ctx.pr.transfer.app.find.rsdttaxpayee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.EmployeeResidentTaxPayeeInfoAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.EmployeeResidentTaxPayeeInfoImport;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.PayeeInfoAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls.CurrProcessYmAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls.CurrProcessYmImport;
import nts.uk.ctx.pr.transfer.dom.rsdttaxpayee.ResidentTaxPayeeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

/**
 * 
 * @author HungTT
 *
 */
@Stateless
public class ResidentTaxPayeeFinder {

	@Inject
	private ResidentTaxPayeeRepository rsdtTaxPayeeRepo;

	@Inject
	private CurrProcessYmAdapter currProcYmAdapter;

	@Inject
	private EmployeeResidentTaxPayeeInfoAdapter empRsdtTaxPayeeInfoAdapter;

	@Inject
	private PayeeInfoAdapter payeeInfoAdapter;

	public List<ResidentTaxPayeeDto> getAll() {
		String companyId = AppContexts.user().companyId();
		return rsdtTaxPayeeRepo.getAllResidentTaxPayee(companyId).stream().map(r -> new ResidentTaxPayeeDto(r, null))
				.collect(Collectors.toList());
	}
	
	public List<ResidentTaxPayeeDto> getAllCompanyZero() {
		String companyId = AppContexts.user().zeroCompanyIdInContract();
		return rsdtTaxPayeeRepo.getAllResidentTaxPayee(companyId).stream().map(r -> new ResidentTaxPayeeDto(r, null))
				.collect(Collectors.toList());
	}

	public ResidentTaxPayeeDto getResidentTaxPayee(String code) {
		String companyId = AppContexts.user().companyId();
		val domain = rsdtTaxPayeeRepo.getResidentTaxPayeeById(companyId, code);
		val reportDomain = rsdtTaxPayeeRepo.getResidentTaxPayeeById(companyId,
				domain.isPresent() && domain.get().getReportCd().isPresent() ? domain.get().getReportCd().get().v()
						: "");
		return domain.isPresent() ? new ResidentTaxPayeeDto(domain.get(),
				reportDomain.isPresent() ? reportDomain.get().getName().v() : null) : null;
	}
	
	public ResidentTaxPayeeDto getResidentTaxPayeeCompanyZero(String code) {
		String companyId = AppContexts.user().zeroCompanyIdInContract();
		val domain = rsdtTaxPayeeRepo.getResidentTaxPayeeById(companyId, code);
		val reportDomain = rsdtTaxPayeeRepo.getResidentTaxPayeeById(companyId,
				domain.isPresent() && domain.get().getReportCd().isPresent() ? domain.get().getReportCd().get().v()
						: "");
		return domain.isPresent() ? new ResidentTaxPayeeDto(domain.get(),
				reportDomain.isPresent() ? reportDomain.get().getName().v() : null) : null;
	}

	public void checkBeforeDelete(String companyId, List<String> reportCodes) {
		Set<String> setCodes = new HashSet<>(reportCodes);
		for (String reportCode : setCodes) {
			if (rsdtTaxPayeeRepo.getResidentTaxPayeeWithReportCd(companyId, reportCode).isEmpty()) {
				Optional<CurrProcessYmImport> optCurProcYm = currProcYmAdapter.getCurrentSalaryProcessYm(companyId, 1);
				if (optCurProcYm.isPresent()) {
					List<EmployeeResidentTaxPayeeInfoImport> listEmpRsdtTaxPayeeInfo = empRsdtTaxPayeeInfoAdapter
							.getEmpRsdtTaxPayeeInfo(Arrays.asList(AppContexts.user().employeeId()),
									optCurProcYm.get().getCurrentYm());
					List<String> listHistId = new ArrayList<>();
					for (EmployeeResidentTaxPayeeInfoImport taxPayee : listEmpRsdtTaxPayeeInfo) {
						for (YearMonthHistoryItem histItem : taxPayee.getHistoryItems()) {
							listHistId.add(histItem.identifier());
						}
					}
					List<String> listRsdtTaxPayeeCd = payeeInfoAdapter.getListPayeeInfo(listHistId).stream()
							.map(x -> x.getResidentTaxPayeeCd()).collect(Collectors.toList());
					if (listRsdtTaxPayeeCd.contains(reportCode))
						throw new BusinessException("Msg_17", "QMM003_18");
				}
			} else {
				throw new BusinessException("Msg_17", "QMM003_16");
			}
		}
	}

}
