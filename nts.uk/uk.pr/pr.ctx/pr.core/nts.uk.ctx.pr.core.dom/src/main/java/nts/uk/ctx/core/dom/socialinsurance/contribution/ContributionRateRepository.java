package nts.uk.ctx.core.dom.socialinsurance.contribution;

import java.util.Optional;

public interface ContributionRateRepository {
    /**
     * Get 拠出金率
     *
     * @param historyId 履歴ID
     * @return 拠出金率
     */
    Optional<ContributionRate> getContributionRateByHistoryId(String historyId);
}
