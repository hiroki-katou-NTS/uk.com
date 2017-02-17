package nts.uk.ctx.pr.core.dom.rule.law.tax.residential.output;

import java.util.Optional;

public interface ResidentTaxPaymentDataRepository {
	/**
	 * 
	 * @param companyCode
	 * @param residentTaxCode
	 * @param yearMonth
	 * @return
	 */
	Optional<ResidentTaxPaymentData> find(String companyCode, String residentTaxCode, int yearMonth);
	
	/**
	 * 
	 * @param companyCode
	 * @param domain
	 */
	void add(String companyCode, ResidentTaxPaymentData domain);
	
	/**
	 * 
	 * @param companyCode
	 * @param domain
	 */
	void update(String companyCode, ResidentTaxPaymentData domain);
}
