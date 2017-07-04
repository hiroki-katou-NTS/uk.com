package nts.uk.ctx.sys.portal.dom.jobtitletying;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenu;

/**
 * @author yennth
 * The interface JobTitleTyingRepository
 */
public interface JobTitleTyingRepository {
	/**
	 * @author yennth
	 * @param JobTitleTying
	 * @return
	 */
	boolean isExistWebMenuCode(List<JobTitleTying> JobTitleTying);
	/**
	 * @author yennth
	 * @param JobTitleTying
	 */
	void changeMenuCode(List<JobTitleTying> JobTitleTying);
	/**
	 * 
	 * @param companyId
	 * @param jobId
	 * @return
	 */
	List<JobTitleTying> findWebMenuCode(String companyId, List<String> jobId);
}
