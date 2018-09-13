package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance;

import java.util.List;
import java.util.Optional;

/**
 * 厚生年金保険料率履歴
 */
public interface WelfarePensionInsuranceRateHistoryRepository {
	Optional<WelfarePensionInsuranceRateHistory> getWelfarePensionInsuranceRateHistoryByOfficeCode(String officeCode);
	List<WelfarePensionInsuranceRateHistory> findAll();
	void deleteByCidAndCode(String cid, String officeCode);
}
