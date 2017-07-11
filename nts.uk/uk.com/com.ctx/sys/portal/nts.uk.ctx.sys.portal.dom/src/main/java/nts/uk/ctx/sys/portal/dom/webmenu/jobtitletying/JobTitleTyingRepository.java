package nts.uk.ctx.sys.portal.dom.webmenu.jobtitletying;

import java.util.List;

/**
 * @author yennth
 * The interface JobTitleTyingRepository
 */
public interface JobTitleTyingRepository {

	/**
	 * update menu code, if record doesn't exit in list, insert record to the list
	 * @author yennth
	 * @param JobTitleTying
	 */
	void updateAndInsertMenuCode(List<JobTitleTying> JobTitleTying);
	/**
	 * find a web menu code list in the list
	 * @param companyId
	 * @param jobId
	 * @return
	 */
	List<JobTitleTying> findWebMenuCode(String companyId, List<String> jobId);
	/**
	 * check web menu code
	 */
	void validate(JobTitleTying obj);
}
