package nts.uk.ctx.sys.portal.dom.webmenu.jobtitletying;

import java.util.List;

/**
 * @author yennth
 * The interface JobTitleTyingRepository
 */
public interface JobTitleTyingRepository {

	/**
	 * update menu code
	 * @author yennth
	 * @param JobTitleTying
	 */
	void changeMenuCode(List<JobTitleTying> JobTitleTying);
	/**
	 * find a web menu code list in the list
	 * @param companyId
	 * @param jobId
	 * @return
	 */
	List<JobTitleTying> findWebMenuCode(String companyId, List<String> jobId);
}
