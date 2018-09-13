package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance;

import java.util.Optional;
import java.util.List;

/**
 * 賞与厚生年金保険料率
 */
public interface BonusEmployeePensionInsuranceRateRepository {
    Optional<BonusEmployeePensionInsuranceRate> getBonusEmployeePensionInsuranceRateById(String historyId);
    void add (BonusEmployeePensionInsuranceRate domain);
}
