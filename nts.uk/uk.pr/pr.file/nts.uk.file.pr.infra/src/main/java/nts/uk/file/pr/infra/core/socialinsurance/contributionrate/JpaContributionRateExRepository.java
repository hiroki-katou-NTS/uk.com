package nts.uk.file.pr.infra.core.socialinsurance.contributionrate;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.socialinsurance.contributionrate.ContributionRateExRepository;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JpaContributionRateExRepository extends JpaRepository implements ContributionRateExRepository {

    @Override
    public List<Object[]> getContributionRate(String cid) {
        return null;
    }
}
