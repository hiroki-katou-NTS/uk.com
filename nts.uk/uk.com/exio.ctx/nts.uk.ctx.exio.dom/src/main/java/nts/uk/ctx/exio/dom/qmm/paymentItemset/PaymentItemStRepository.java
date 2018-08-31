package nts.uk.ctx.exio.dom.qmm.paymentItemset;

import java.util.Optional;
import java.util.List;

/**
 * 
 * @author thanh.tq 支給項目設定
 *
 */
public interface PaymentItemStRepository {

	List<PaymentItemSt> getAllPaymentItemSt();

	Optional<PaymentItemSt> getPaymentItemStById(String cid, String salaryItemId);

	void add(PaymentItemSt domain);

	void update(PaymentItemSt domain);

	void remove(String cid, String salaryItemId);

}
