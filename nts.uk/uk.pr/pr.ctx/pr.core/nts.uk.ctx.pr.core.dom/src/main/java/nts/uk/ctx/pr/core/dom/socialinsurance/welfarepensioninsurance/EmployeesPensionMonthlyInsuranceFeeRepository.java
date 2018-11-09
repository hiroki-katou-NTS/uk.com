package nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance;

import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.List;
import java.util.Optional;

/**
 * 厚生年金月額保険料額
 */
public interface EmployeesPensionMonthlyInsuranceFeeRepository {
    Optional<EmployeesPensionMonthlyInsuranceFee> getEmployeesPensionMonthlyInsuranceFeeByHistoryId(String historyId);
    void deleteByHistoryIds(List<String> historyIds);
    void add (EmployeesPensionMonthlyInsuranceFee domain, String officeCode, YearMonthHistoryItem yearMonth);
    void update (EmployeesPensionMonthlyInsuranceFee domain, String officeCode, YearMonthHistoryItem yearMonth);
    void remove (EmployeesPensionMonthlyInsuranceFee domain, String officeCode, YearMonthHistoryItem yearMonth);
    void updateWelfarePension(EmployeesPensionMonthlyInsuranceFee data);
    void insertWelfarePension(EmployeesPensionMonthlyInsuranceFee data);
    void updateHistory(String officeCode, YearMonthHistoryItem yearMonth);
}
