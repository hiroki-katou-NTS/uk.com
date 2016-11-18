package nts.uk.ctx.pr.proto.dom.paymentdata.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;

public interface PaymentDataRepository {
	/**
	 * 
	 * @param companyCode
	 * @param personId
	 * @param processingNo
	 * @param payBonusAttribute
	 * @param processingYM
	 * @param sparePayAttribute
	 * @return Payment
	 */
	Optional<Payment> find(String companyCode, String personId, int processingNo, int payBonusAttribute,
			int processingYM, int sparePayAttribute);

	List<Payment> findPaymentHeader(String companyCode, String personId, int payBonusAttribute, int processingYM);
	/**
	 * Add new data payment
	 * 
	 * @param payment
	 */
	void add(Payment payment);

	/**
	 * Update
	 * 
	 * @param payment
	 */
	void update(Payment payment);

	/**
	 * Insert
	 * 
	 * @param payment
	 */
	void insert(Payment payment);
}
