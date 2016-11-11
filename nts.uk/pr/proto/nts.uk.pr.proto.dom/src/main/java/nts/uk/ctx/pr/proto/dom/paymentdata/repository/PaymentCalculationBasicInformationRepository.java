package nts.uk.ctx.pr.proto.dom.paymentdata.repository;

import java.util.Optional;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.paymentdata.PaymentCalculationBasicInformation;

public interface PaymentCalculationBasicInformationRepository {
	/**
	 * Find a basic information of payment calculation by company code.
	 * 
	 * @param companyCode code
	 * @return PaymentCalculationBasicInformation found
	 */
	Optional<PaymentCalculationBasicInformation> find(CompanyCode companyCode);
}
