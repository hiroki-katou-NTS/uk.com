package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.JobtitleSearchSet;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.JobtitleSearchSetRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author yennth
 *
 */
@Stateless
public class JobtitleSearchSetFinder {
	@Inject
	private JobtitleSearchSetRepository jobRep;
	/**
	 * convert from domain to dto
	 * @param jobSet
	 * @return
	 */
	private JobtitleSearchSetDto convertToDto(JobtitleSearchSet jobSet){
		JobtitleSearchSetDto jobTitle = new JobtitleSearchSetDto();
		jobTitle.setCompanyId(jobSet.getCompanyId());
		jobTitle.setJobId(jobSet.getJobId());
		jobTitle.setSearchSetFlg(jobSet.getSearchSetFlg().value);
		return jobTitle;
	}
	
	public JobtitleSearchSetDto getById(String jobtitleId){
		String companyId = AppContexts.user().companyId();
		Optional<JobtitleSearchSet> jobtitle = jobRep.finById(companyId, jobtitleId);
		if(jobtitle.isPresent()){
			return convertToDto(jobtitle.get());
		}
		return null;
	}
}
