package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance;

import java.util.Optional;

/**
 * 厚生年金保険料率履歴
 */
public interface WelfarePensionInsuranceRateHistoryRepository {
	void add (WelfarePensionInsuranceRateHistory domain);
	Optional<WelfarePensionInsuranceRateHistory> getWelfarePensionInsuranceRateHistoryByOfficeCode(String officeCode);
	void deleteByCidAndCode(String cid, String officeCode);
	void update (WelfarePensionInsuranceRateHistory domain);
	void remove (WelfarePensionInsuranceRateHistory domain);
}
