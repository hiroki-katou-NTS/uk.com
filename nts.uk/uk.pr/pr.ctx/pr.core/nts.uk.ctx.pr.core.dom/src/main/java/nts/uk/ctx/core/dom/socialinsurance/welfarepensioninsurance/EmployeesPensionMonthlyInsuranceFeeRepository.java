package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance;

import java.util.Optional;

/**
 * 厚生年金月額保険料額
 */
public interface EmployeesPensionMonthlyInsuranceFeeRepository {
    Optional<EmployeesPensionMonthlyInsuranceFee> getEmployeesPensionMonthlyInsuranceFeeByHistoryId(String historyId);
}
