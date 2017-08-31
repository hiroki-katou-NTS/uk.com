package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.Optional;

public interface JobtitleSearchSetRepository {

	Optional<JobtitleSearchSet> finById(String cid, String jobtitleId);

}
