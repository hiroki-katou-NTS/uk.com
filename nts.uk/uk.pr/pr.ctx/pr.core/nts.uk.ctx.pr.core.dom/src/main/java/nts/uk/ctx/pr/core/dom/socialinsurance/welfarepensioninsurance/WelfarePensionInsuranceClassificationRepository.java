package nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance;

import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.List;
import java.util.Optional;

/**
 * 厚生年金保険区分
 */
public interface WelfarePensionInsuranceClassificationRepository {
	Optional<WelfarePensionInsuranceRateHistory> getWelfarePensionHistoryByOfficeCode(String officeCode);
	Optional<WelfarePensionInsuranceClassification> getWelfarePensionInsuranceClassificationById(String historyId);
	void deleteByHistoryIds (List<String> historyIds);
	void add (WelfarePensionInsuranceClassification domain, String officeCode, YearMonthHistoryItem yearMonth);
	void update (WelfarePensionInsuranceClassification domain, String officeCode, YearMonthHistoryItem yearMonth);
	void remove (WelfarePensionInsuranceClassification domain, String officeCode, YearMonthHistoryItem yearMonth);
	void updateHistory(String officeCode, YearMonthHistoryItem yearMonth);
}
