package nts.uk.ctx.bs.employee.dom.jobtitle.main;

import java.util.Optional;

public interface JobTitleMainRepository {
	public Optional<JobTitleMain> getJobTitleMainById(String jobTitleMainId);
}
