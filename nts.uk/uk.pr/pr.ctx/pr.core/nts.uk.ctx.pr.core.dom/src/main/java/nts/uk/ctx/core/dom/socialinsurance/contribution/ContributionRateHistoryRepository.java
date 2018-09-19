package nts.uk.ctx.core.dom.socialinsurance.contribution;

import java.util.List;
import java.util.Optional;

/**
 * 拠出金率履歴
 */
public interface ContributionRateHistoryRepository {

	Optional<ContributionRateHistory> findByCodeAndCid(String cid, String officeCode);

	Optional<ContributionRateHistory> getContributionRateHistoryByOfficeCode(String officeCode);

	Optional<ContributionRate> getContributionRateByHistoryId(String historyId);

	List<ContributionByGrade> getContributionByGradeByHistoryId(String historyId);

	void deleteByCidAndCode(String cid, String officeCode);

}
