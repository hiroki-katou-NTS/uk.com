package nts.uk.ctx.pr.proto.dom.paymentdata.repository;

import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;

public interface PaymentDataRepository {
	
	/**
	 * Add new data payment
	 * @param payment
	 */
	void add(Payment payment);
}
