package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance;

import java.util.List;
import java.util.Optional;

/**
 * 厚生年金保険区分
 */
public interface WelfarePensionInsuranceClassificationRepository {
	Optional<WelfarePensionInsuranceClassification> getWelfarePensionInsuranceClassificationById(String historyId);
	void deleteByHistoryIds (List<String> historyIds);
	void add (WelfarePensionInsuranceClassification domain);
	void update (WelfarePensionInsuranceClassification domain);
	void remove (WelfarePensionInsuranceClassification domain);
}
