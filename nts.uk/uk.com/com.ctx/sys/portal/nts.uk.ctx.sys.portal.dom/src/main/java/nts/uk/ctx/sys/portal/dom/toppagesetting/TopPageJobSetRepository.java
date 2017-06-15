package nts.uk.ctx.sys.portal.dom.toppagesetting;

import java.util.Optional;

/**
 * 
 * @author sonnh1
 *
 */
public interface TopPageJobSetRepository {
	/**
	 * 
	 * @param companyId
	 * @param jobId
	 * @return
	 */
	Optional<TopPageJobSet> findByJobId(String companyId, String jobId);

	/**
	 * Insert into table TOPPAGE_JOB_SET
	 * 
	 * @param topPageJobSet
	 */
	void add(TopPageJobSet topPageJobSet);

	/**
	 * update data in table TOPPAGE_JOB_SET
	 * 
	 * @param topPageJobSet
	 */
	void update(TopPageJobSet topPageJobSet);
}
