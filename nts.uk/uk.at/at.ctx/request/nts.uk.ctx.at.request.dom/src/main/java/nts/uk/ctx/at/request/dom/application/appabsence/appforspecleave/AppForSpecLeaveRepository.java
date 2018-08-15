package nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave;

import java.util.Optional;

public interface AppForSpecLeaveRepository {
	/**
	 * get Application For Special Leave By Id
	 * @author hoatt
	 * @param companyId
	 * @param appId
	 * @return
	 */
	public Optional<AppForSpecLeave> getAppForSpecLeaveById(String companyId, String appId);
	/**
	 * add special holiday
	 * @author hoatt
	 * @param specHd
	 */
	public void addSpecHd(AppForSpecLeave specHd);
	/**
	 * update specical holiday
	 * @author hoatt
	 * @param specHd
	 */
	public void updateSpecHd(AppForSpecLeave specHd);
}
