package nts.uk.ctx.pr.core.dom.wageprovision.speclayout;

import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.Optional;
import java.util.List;

/**
* 控除項目明細設定
*/
public interface DeductionItemDetailSetRepository
{

    List<DeductionItemDetailSet> getAllDeductionItemDetailSet();

    Optional<DeductionItemDetailSet> getDeductionItemDetailSetById(String histId);

    void add(DeductionItemDetailSet domain, YearMonthPeriod ymPeriod, String cid, String specCode);

    void update(DeductionItemDetailSet domain, YearMonthPeriod ymPeriod, String cid, String specCode);

    void remove(String histId, String cid, String specCode);

}
