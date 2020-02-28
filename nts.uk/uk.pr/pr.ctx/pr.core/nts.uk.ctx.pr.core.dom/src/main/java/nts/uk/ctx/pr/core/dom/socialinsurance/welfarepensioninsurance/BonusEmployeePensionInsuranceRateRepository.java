package nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance;

import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.List;
import java.util.Optional;

/**
 * 賞与厚生年金保険料率
 */
public interface BonusEmployeePensionInsuranceRateRepository {
    Optional<BonusEmployeePensionInsuranceRate> getBonusEmployeePensionInsuranceRateById(String historyId);
    void deleteByHistoryIds(List<String> historyIds);
    void add (BonusEmployeePensionInsuranceRate domain, String officeCode, YearMonthHistoryItem yearMonth);
    void update (BonusEmployeePensionInsuranceRate domain, String officeCode, YearMonthHistoryItem yearMonth);
    void remove (BonusEmployeePensionInsuranceRate domain, String officeCode, YearMonthHistoryItem yearMonth);
    void updateHistory(String officeCode, YearMonthHistoryItem yearMonth);
}
