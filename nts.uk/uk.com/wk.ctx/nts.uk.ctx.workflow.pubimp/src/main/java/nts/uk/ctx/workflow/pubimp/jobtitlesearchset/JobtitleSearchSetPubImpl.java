package nts.uk.ctx.workflow.pubimp.jobtitlesearchset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.workflow.dom.approvermanagement.workroot.JobtitleSearchSet;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.JobtitleSearchSetRepository;
import nts.uk.ctx.workflow.pub.jobtitlesearchset.JobtitleSearchSetExport;
import nts.uk.ctx.workflow.pub.jobtitlesearchset.JobtitleSearchSetPub;

@Stateless
public class JobtitleSearchSetPubImpl implements JobtitleSearchSetPub {

	@Inject
	private JobtitleSearchSetRepository jobtitleSearchSetRepository;

	@Override
	public JobtitleSearchSetExport finById(String cid, String jobtitleId) {
		Optional<JobtitleSearchSet> job = jobtitleSearchSetRepository.finById(cid, jobtitleId);
		if (job.isPresent()) {
			return JobtitleSearchSetExport.createSimpleFromJavaType(job.get().getCompanyId(), job.get().getJobId(),
					job.get().getSearchSetFlg().value);
		}

		return null;
	}
}
