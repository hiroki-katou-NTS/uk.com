package nts.uk.ctx.at.request.ac.workflow.jobtitlesearchset;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.JobtitleSearchSetAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.JobtitleSearchSetImport;
import nts.uk.ctx.workflow.pub.jobtitlesearchset.JobtitleSearchSetExport;
import nts.uk.ctx.workflow.pub.jobtitlesearchset.JobtitleSearchSetPub;

/**
 * 
 * @author vunv
 *
 */
@Stateless
public class JobtitleSearchSetAdapterImpl implements JobtitleSearchSetAdapter {

	@Inject
	JobtitleSearchSetPub jobtitleSearchSetPub;

	@Override
	public JobtitleSearchSetImport finById(String cid, String jobtitleId) {
		JobtitleSearchSetExport job = this.jobtitleSearchSetPub.finById(cid, jobtitleId);
		if (Objects.isNull(job)) {
			return null;
		}

		return this.toImport(job);
	}

	public JobtitleSearchSetImport toImport(JobtitleSearchSetExport job) {
		return new JobtitleSearchSetImport(job.getCompanyId(), job.getJobId(), job.getSearchSetFlg());
	}

}
