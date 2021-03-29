package nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave;

import java.util.Optional;
/**
 * 
 * @author hoatt
 *
 */

public interface AppForSpecLeaveRepository {
	/**
	 * get Application For Special Leave By Id
	 * @param companyId
	 * @param appId
	 * @return
	 */
	public Optional<AppForSpecLeave_Old> getAppForSpecLeaveById(String companyId, String appId);
	/**
	 * add special holiday
	 * @param specHd
	 */
	public void addSpecHd(AppForSpecLeave_Old specHd);
	/**
	 * update specical holiday
	 * @param specHd
	 */
	public void updateSpecHd(AppForSpecLeave_Old specHd);
	/**
	 * detele specical holiday
	 * @param specHd
	 */
	public void deleteSpecHd(AppForSpecLeave_Old specHd);
}
