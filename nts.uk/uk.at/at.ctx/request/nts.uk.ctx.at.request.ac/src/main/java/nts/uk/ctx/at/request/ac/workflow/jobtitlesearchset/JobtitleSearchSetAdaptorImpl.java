package nts.uk.ctx.at.request.ac.workflow.jobtitlesearchset;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.JobtitleSearchSetAdaptor;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.JobtitleSearchSetAdaptorDto;
import nts.uk.ctx.workflow.pub.jobtitlesearchset.JobtitleSearchSetDto;
import nts.uk.ctx.workflow.pub.jobtitlesearchset.JobtitleSearchSetPub;

/**
 * 
 * @author vunv
 *
 */
@Stateless
public class JobtitleSearchSetAdaptorImpl implements JobtitleSearchSetAdaptor {

	@Inject
	JobtitleSearchSetPub jobtitleSearchSetPub;

	@Override
	public JobtitleSearchSetAdaptorDto finById(String cid, String jobtitleId) {
		JobtitleSearchSetDto job = this.jobtitleSearchSetPub.finById(cid, jobtitleId);
		if (Objects.isNull(job)) {
			return null;
		}

		return JobtitleSearchSetAdaptorDto.createSimpleFromJavaType(job.getCompanyId(), job.getJobId(),
				job.getSearchSetFlg());
	}

}
