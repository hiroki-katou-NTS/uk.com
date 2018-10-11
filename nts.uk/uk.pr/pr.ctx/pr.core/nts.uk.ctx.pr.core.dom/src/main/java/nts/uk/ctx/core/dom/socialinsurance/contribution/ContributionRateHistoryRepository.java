package nts.uk.ctx.core.dom.socialinsurance.contribution;

import java.util.Optional;

/**
 * 拠出金率履歴
 */
public interface ContributionRateHistoryRepository {

	Optional<ContributionRateHistory> findByCodeAndCid(String cid, String officeCode);

	Optional<ContributionRateHistory> getContributionRateHistoryByOfficeCode(String officeCode);

	void deleteByCidAndCode(String cid, String officeCode);

	void add(ContributionRateHistory domain);

	void update(ContributionRateHistory domain);
	
	void remove (ContributionRateHistory domain);
}
