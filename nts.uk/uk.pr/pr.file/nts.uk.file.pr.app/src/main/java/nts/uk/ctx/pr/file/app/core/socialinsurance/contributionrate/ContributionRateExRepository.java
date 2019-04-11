package nts.uk.ctx.pr.file.app.core.socialinsurance.contributionrate;

import java.util.List;

public interface ContributionRateExRepository {

    List<Object[]> getContributionRate(String cid);

}
