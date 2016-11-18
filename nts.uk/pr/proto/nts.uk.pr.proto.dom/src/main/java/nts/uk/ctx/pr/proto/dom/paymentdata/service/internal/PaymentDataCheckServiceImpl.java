package nts.uk.ctx.pr.proto.dom.paymentdata.service.internal;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.proto.dom.paymentdata.PayBonusAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDataRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.service.PaymentDataCheckService;

@RequestScoped
public class PaymentDataCheckServiceImpl implements PaymentDataCheckService {
	@Inject
	private PaymentDataRepository paymentDataRepo;
	
	@Override
	public boolean isExists(String companyCode, String personId, PayBonusAtr payBonusAtr,
			int processingYearMonth) {
		List<Payment> paymentHeaders = paymentDataRepo.findPaymentHeader(companyCode, personId, payBonusAtr.value, processingYearMonth);
		
		return paymentHeaders.size() > 0;
	}


}
