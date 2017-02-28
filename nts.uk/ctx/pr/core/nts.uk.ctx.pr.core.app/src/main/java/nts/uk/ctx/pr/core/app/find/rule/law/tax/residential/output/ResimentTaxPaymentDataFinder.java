package nts.uk.ctx.pr.core.app.find.rule.law.tax.residential.output;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.find.rule.law.tax.residential.output.dto.ResimentTaxPaymentDataDto;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.output.ResidentTaxPaymentData;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.output.ResidentTaxPaymentDataRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
public class ResimentTaxPaymentDataFinder {
	@Inject
	private ResidentTaxPaymentDataRepository repository;
	
	/**
	 * 
	 * @param resimentTaxCode
	 * @param yearMonth
	 * @return
	 */
	public ResimentTaxPaymentDataDto find(String resimentTaxCode, int yearMonth) {
		String companyCode = AppContexts.user().companyCode();
		
		Optional<ResidentTaxPaymentData> domainOp = this.repository.find(companyCode, resimentTaxCode, yearMonth);
		if (!domainOp.isPresent()) {
			return null;
		}
		
		ResidentTaxPaymentData domain = domainOp.get();
		
		return new ResimentTaxPaymentDataDto(
				domain.getCode().v(), 
				domain.getYearMonth().v(), 
				domain.getTaxPayrollMoney().v(), 
				domain.getTaxBonusMoney().v(), 
				domain.getTaxOverdueMoney().v(),
				domain.getTaxDemandChargeMoney().v(), 
				domain.getAddress().v(), 
				domain.getDueDate(),
				domain.getStaffNo(),
				domain.getRetirementAmount().v(),
				domain.getCityTaxMoney().v(), 
				domain.getPrefectureTaxMoney().v()
				);
	}
}
