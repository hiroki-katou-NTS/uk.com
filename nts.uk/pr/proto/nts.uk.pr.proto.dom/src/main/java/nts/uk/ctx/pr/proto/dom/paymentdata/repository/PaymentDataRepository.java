package nts.uk.ctx.pr.proto.dom.paymentdata.repository;

import java.util.Optional;

import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;

public interface PaymentDataRepository {
	Optional<Payment> find(String companyCode, String personId, int processingNo, int payBonusAttribute,
			int processingYM, int sparePayAttribute);

	/**
	 * Add new data payment
	 * 
	 * @param payment
	 */
	void add(Payment payment);
}
