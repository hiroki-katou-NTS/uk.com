package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.Optional;

public interface JobtitleSearchSetRepository {

	Optional<JobtitleSearchSet> finById(String cid, String jobtitleId);
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
