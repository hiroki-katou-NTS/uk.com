package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.ArrayList;
import java.util.List;
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
	/**
	 * get jobtitle search set by companyId and a jobtitle id
	 * @param jobtitleId
	 * @return
	 * @author yennth
	 */
	public JobtitleSearchSetDto getById(String jobtitleId){
		String companyId = AppContexts.user().companyId();
		Optional<JobtitleSearchSet> jobtitle = jobRep.finById(companyId, jobtitleId);
		if(jobtitle.isPresent()){
			return convertToDto(jobtitle.get());
		}
		return null;
	}
	/**
	 * get job title search set by company id and jobtitle id list
	 * @param jobtitleLst
	 * @return
	 * @author yennth
	 */
	public List<JobtitleSearchSetDto> getByListId(List<String> jobtitleLst){
		String companyId = AppContexts.user().companyId();
		List<JobtitleSearchSetDto> listJobtitle = new ArrayList<>();
		List<JobtitleSearchSet> listJob = jobRep.findByListJob(companyId, jobtitleLst);
		if(!listJob.isEmpty()){
			for(JobtitleSearchSet item : listJob){
				listJobtitle.add(this.convertToDto(item));
			}
		}
		return listJobtitle;
	}
}
