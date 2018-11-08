package nts.uk.ctx.pr.core.ac.wageprovision.statementbindingsetting;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.JobTitleInfo;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.JobTitleInfoAdapter;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JobTitleInfoImplement implements JobTitleInfoAdapter {
    @Override
    public Optional<JobTitleInfo> getJobTitleInfoByBaseDate(String cid, GeneralDate baseDate) {
        return Optional.empty();
    }
}
