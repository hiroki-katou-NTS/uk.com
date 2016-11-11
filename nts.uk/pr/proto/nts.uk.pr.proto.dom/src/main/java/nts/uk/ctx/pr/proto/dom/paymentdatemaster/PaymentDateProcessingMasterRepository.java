package nts.uk.ctx.pr.proto.dom.paymentdatemaster;

import java.util.Optional;

import nts.uk.ctx.core.dom.company.CompanyCode;

public interface PaymentDateProcessingMasterRepository {
	
	Optional<PaymentDateProcessingMaster> findWithProcessingNo(CompanyCode companyCode, int paymentBonusAtribute, int processingNo);
	Optional<PaymentDateProcessingMaster> find(CompanyCode companyCode, int paymentBonusAtribute);
}
