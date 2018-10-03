package nts.uk.ctx.pr.core.wageprovision.companyuniformamount;

import java.util.List;
import java.util.Optional;
/**
 * 給与会社単価履歴
 */
public interface PayrollUnitPriceHistoryRepository {
    List<PayrollUnitPriceHistory> getAllPayrollUnitPriceHistory();

    Optional<PayrollUnitPriceHistory> getPayrollUnitPriceHistoryById(String cid, String code, String hisId);

    void add(PayrollUnitPriceHistory domain);

    void update(PayrollUnitPriceHistory domain);

    void remove(String cid, String code, String hisId);
}
