package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance;

import java.util.List;
import java.util.Optional;

/**
 * 賞与厚生年金保険料率
 */
public interface BonusEmployeePensionInsuranceRateRepository {
    Optional<BonusEmployeePensionInsuranceRate> getBonusEmployeePensionInsuranceRateById(String historyId);
    void deleteByHistoryIds(List<String> historyIds);
    void add (BonusEmployeePensionInsuranceRate domain);
    void update (BonusEmployeePensionInsuranceRate domain);
    void remove (BonusEmployeePensionInsuranceRate domain);
}
