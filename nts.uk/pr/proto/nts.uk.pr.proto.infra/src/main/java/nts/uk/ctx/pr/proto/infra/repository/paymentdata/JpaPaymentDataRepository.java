package nts.uk.ctx.pr.proto.infra.repository.paymentdata;

import javax.enterprise.context.RequestScoped;

import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDataRepository;

@RequestScoped
public class JpaPaymentDataRepository implements PaymentDataRepository {

	@Override
	public void add(Payment payment) {
		// TODO Auto-generated method stub
		
	}

}
