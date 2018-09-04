package nts.uk.ctx.at.function.ac.jobtitle;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.jobtitle.JobTitleAdapter;
import nts.uk.ctx.at.function.dom.adapter.jobtitle.JobTitleImport;
import nts.uk.ctx.bs.employee.pub.jobtitle.JobTitleExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;

@Stateless
public class JobTitleAcFinder implements JobTitleAdapter {

	@Inject
	private SyJobTitlePub syJobTitlePub;

	@Override
	public Optional<JobTitleImport> findByJobId(String companyId, String jobId, GeneralDate baseDate) {
		Optional<JobTitleExport> jobTitleOtp = syJobTitlePub.findByJobId(companyId, jobId, baseDate);
		if (!jobTitleOtp.isPresent()) {
			return Optional.empty();
		}
		JobTitleExport jobTitle = jobTitleOtp.get();
		JobTitleImport jobTitleImport = new JobTitleImport(jobTitle.getCompanyId(), jobTitle.getJobTitleId(),
				jobTitle.getJobTitleCode(), jobTitle.getJobTitleName(), jobTitle.getSequenceCode(),
				jobTitle.getStartDate(), jobTitle.getEndDate(), jobTitle.isManager());
		return Optional.ofNullable(jobTitleImport);
	}

}
