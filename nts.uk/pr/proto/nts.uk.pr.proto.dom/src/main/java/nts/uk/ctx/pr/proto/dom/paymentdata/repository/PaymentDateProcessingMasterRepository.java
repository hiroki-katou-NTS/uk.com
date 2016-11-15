package nts.uk.ctx.pr.proto.dom.paymentdata.repository;

import java.util.Optional;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster.PaymentDateProcessingMaster;

public interface PaymentDateProcessingMasterRepository {
	
	Optional<PaymentDateProcessingMaster> findWithProcessingNo(CompanyCode companyCode, int paymentBonusAtribute, int processingNo);
	Optional<PaymentDateProcessingMaster> find(CompanyCode companyCode, int paymentBonusAtribute);
}
