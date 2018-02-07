package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.List;
import java.util.Optional;

public interface JobtitleSearchSetRepository {

	Optional<JobtitleSearchSet> finById(String cid, String jobtitleId);
	/**
	 * find a job title search set list by companyId and job title id list
	 * @param cid
	 * @param jobtitleId
	 * @return
	 * @author yennth
	 */
	List<JobtitleSearchSet> findByListJob(String cid, List<String> jobtitleId);
	/**
	 * update jobtitle search set
	 * @param jobSearch
	 * @author yennth
	 */
	void update(JobtitleSearchSet jobSearch);
	/**
	 * insert jobtitle search set
	 * @param jobSearch
	 * @author yennth
	 */
	void insert(JobtitleSearchSet jobSearch);
}
