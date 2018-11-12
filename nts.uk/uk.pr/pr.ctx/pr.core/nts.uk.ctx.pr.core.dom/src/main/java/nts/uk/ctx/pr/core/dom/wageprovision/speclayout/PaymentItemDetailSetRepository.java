package nts.uk.ctx.pr.core.dom.wageprovision.speclayout;

import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.Optional;
import java.util.List;

/**
* 支給項目明細設定
*/
public interface PaymentItemDetailSetRepository
{

    List<PaymentItemDetailSet> getAllPaymentItemDetailSet();

    Optional<PaymentItemDetailSet> getPaymentItemDetailSetById(String histId);

    void add(PaymentItemDetailSet domain, YearMonthPeriod ymPeriod, String cid, String specCode);

    void update(PaymentItemDetailSet domain, YearMonthPeriod ymPeriod, String cid, String specCode);

    void remove(String histId, String cid, String specCode);

}
