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
	
	/**
	 * 
	 * @param companyCode
	 * @param personId
	 * @param payBonusAttribute
	 * @param processingYM
	 * @return payment header
	 */
	List<Payment> findPaymentHeader(String companyCode, String personId, int payBonusAttribute, int processingYM);

	/**
	 * Import data for payment include payment head and payment detail
	 * @param payment
	 */
	void importPayment(Payment payment);

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
