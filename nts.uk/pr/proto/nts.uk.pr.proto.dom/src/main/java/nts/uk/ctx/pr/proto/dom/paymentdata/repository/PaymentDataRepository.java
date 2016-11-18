package nts.uk.ctx.pr.proto.dom.paymentdata.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailDeductionItem;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;

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
	 * check is exist payment header of personid in processing year month
	 * 
	 * @param personId
	 * @param processingYM
	 * @return
	 */
	boolean isExistHeader(String companyCode, String personId, int payBonusAttribute, int processingYM);

	/**
	 * import new data payment header
	 * 
	 * @param payment
	 *            (contains header)
	 */
	void importHeader(Payment payment);

	/**
	 * import new data payment detail
	 * 
	 * @param payment
	 *            (contains detail)
	 */
	void importDetails(Payment payment);

	/**
	 * Update
	 * 
	 * @param payment
	 */
	void update(Payment payment);

	/**
	 * Insert Header
	 * 
	 * @param payment
	 */
	void insertHeader(Payment payment);

	/**
	 * insert deduction detail items
	 * 
	 * @param categoryAtr
	 * @param items
	 */
	void insertDeductionDetails(int categoryAtr, List<DetailDeductionItem> items);

	/**
	 * insert detail item without deduction
	 * 
	 * @param categoryAtr
	 * @param items
	 */
	void insertDetails(int categoryAtr, List<DetailItem> items);

	void updateDetails(int categoryAtr, List<DetailItem> items);

	void updateDeductionDetails(int categoryAtr, List<DetailDeductionItem> items);

}
