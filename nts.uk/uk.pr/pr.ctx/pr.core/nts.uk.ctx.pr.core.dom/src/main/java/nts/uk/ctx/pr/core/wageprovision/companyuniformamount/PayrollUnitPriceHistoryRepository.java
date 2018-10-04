package nts.uk.ctx.pr.core.wageprovision.companyuniformamount;

import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.List;
import java.util.Optional;
/**
 * 給与会社単価履歴
 */
public interface PayrollUnitPriceHistoryRepository {

    Optional<PayrollUnitPriceHistory> getPayrollUnitPriceHistoryById(String cid, String code, String hisId);

    Optional<PayrollUnitPriceHistory> getPayrollUnitPriceHistoryByCidCode(String cid, String code);

    void add(YearMonthHistoryItem domain, String cId, String code);

    void update(YearMonthHistoryItem domain, String cId,String code);

    void remove(String cid, String code, String hisId);
}
