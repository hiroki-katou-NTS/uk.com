package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance;

import java.util.Optional;

/**
 * 厚生年金保険区分
 */
public interface WelfarePensionInsuranceClassificationRepository {
	Optional<WelfarePensionInsuranceClassification> getWelfarePensionInsuranceClassificationById(String historyId);
	void add (WelfarePensionInsuranceClassification domain);
}
