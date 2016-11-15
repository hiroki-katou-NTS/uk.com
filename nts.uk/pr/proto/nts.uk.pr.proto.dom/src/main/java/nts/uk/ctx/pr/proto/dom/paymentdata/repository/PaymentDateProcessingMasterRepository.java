package nts.uk.ctx.pr.proto.dom.paymentdata.repository;

import java.util.Optional;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster.PaymentDateProcessingMaster;

public interface PaymentDateProcessingMasterRepository {
	/**
	 * 
	 * @param companyCode
	 * @param paymentBonusAtribute
	 * @param processingNo
	 * @return PaymentDateProcessingMaster
	 */
	Optional<PaymentDateProcessingMaster> find(CompanyCode companyCode, int paymentBonusAtribute, int processingNo);
	
	/**
	 * 
	 * @param companyCode
	 * @param paymentBonusAtribute
	 * @return PaymentDateProcessingMaster
	 */
	Optional<PaymentDateProcessingMaster> find(CompanyCode companyCode, int paymentBonusAtribute);
}
