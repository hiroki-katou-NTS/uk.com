package nts.uk.ctx.bs.employee.dom.jobtitle.info;

import java.util.Optional;

/**
 * The Interface JobTitleInfoRepository.
 */
public interface JobTitleInfoRepository {

	/**
	 * Adds the.
	 *
	 * @param jobTitleInfo the job title info
	 */
	void add(JobTitleInfo jobTitleInfo);
	
	/**
	 * Update.
	 *
	 * @param jobTitleInfo the job title info
	 */
	void update(JobTitleInfo jobTitleInfo);
	
	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param jobTitleId the job title id
	 * @param historyId the history id
	 */
	void remove(String companyId, String jobTitleId, String historyId);

	
	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param jobTitleId the job title id
	 * @param historyId the history id
	 * @return the optional
	 */
	Optional<JobTitleInfo> find(String companyId, String jobTitleId, String historyId);
}
