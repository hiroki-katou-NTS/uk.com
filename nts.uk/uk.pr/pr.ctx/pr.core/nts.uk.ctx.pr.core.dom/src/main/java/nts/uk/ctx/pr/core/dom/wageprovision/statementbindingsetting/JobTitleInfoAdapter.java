package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import nts.arc.time.GeneralDate;

import java.util.Optional;

public interface JobTitleInfoAdapter {
    Optional<JobTitleInfo> getJobTitleInfoByBaseDate(String cid, GeneralDate baseDate);
}
