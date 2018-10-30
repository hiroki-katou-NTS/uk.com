package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import java.util.Optional;
import java.util.List;

/**
* 支給項目明細設定
*/
public interface PaymentItemDetailSetRepository
{

    List<PaymentItemDetailSet> getAllPaymentItemDetailSet();

    Optional<PaymentItemDetailSet> getPaymentItemDetailSetById(String histId);

    void add(PaymentItemDetailSet domain);

    void update(PaymentItemDetailSet domain);

    void remove(String histId);

}
