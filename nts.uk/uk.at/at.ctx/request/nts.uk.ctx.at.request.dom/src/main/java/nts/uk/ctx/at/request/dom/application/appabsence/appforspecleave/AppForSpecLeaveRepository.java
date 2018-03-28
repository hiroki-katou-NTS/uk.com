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
}
