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
	public Optional<AppForSpecLeave> getAppForSpecLeaveById(String companyId, String appId);
	/**
	 * add special holiday
	 * @param specHd
	 */
	public void addSpecHd(AppForSpecLeave specHd);
	/**
	 * update specical holiday
	 * @param specHd
	 */
	public void updateSpecHd(AppForSpecLeave specHd);
	/**
	 * detele specical holiday
	 * @param specHd
	 */
	public void deleteSpecHd(AppForSpecLeave specHd);
}
