package nts.uk.ctx.core.dom.socialinsurance.contribution;

import java.util.List;
import java.util.Optional;

public interface ContributionRateRepository {
	/**
	 * Get 拠出金率
	 *
	 * @param historyId
	 *            履歴ID
	 * @return 拠出金率
	 */
	Optional<ContributionRate> getContributionRateByHistoryId(String historyId);

	void deleteByHistoryIds(List<String> historyIds);

	void add(ContributionRate domain);

	void update(ContributionRate domain);

	void addContributionByGrade(ContributionRate domain);

	void updateContributionByGrade(ContributionRate domain);
	
	void insertContributionByGrade(ContributionRate domain);

	void deleteContributionByGradeByHistoryId(List<String> historyIds);
}
