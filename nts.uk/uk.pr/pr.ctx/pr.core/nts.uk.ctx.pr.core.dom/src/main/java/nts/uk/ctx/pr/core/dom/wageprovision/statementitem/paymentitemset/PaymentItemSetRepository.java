package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset;

import java.util.Optional;
import java.util.List;

/**
 * 
 * @author thanh.tq 支給項目設定
 *
 */
public interface PaymentItemSetRepository {

	List<PaymentItemSet> getAllPaymentItemSt();

	Optional<PaymentItemSet> getPaymentItemStById(String cid, String salaryItemId);

	void add(PaymentItemSet domain);

	void update(PaymentItemSet domain);

	void updateAll(List<String> lstSalaryId);

	void remove(String cid, String salaryItemId);

}
