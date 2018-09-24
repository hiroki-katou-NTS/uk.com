package nts.uk.ctx.at.function.dom.adapter.jobtitle;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface JobTitleAdapter {
	Optional<JobTitleImport> findByJobId(String companyId, String jobId, GeneralDate baseDate);
}
