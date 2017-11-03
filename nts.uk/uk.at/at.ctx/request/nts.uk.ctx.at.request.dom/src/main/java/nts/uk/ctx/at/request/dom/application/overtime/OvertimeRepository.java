package nts.uk.ctx.at.request.dom.application.overtime;

import java.util.Optional;

public interface OvertimeRepository {
	/**
	 * @param companyID
	 * @param appID
	 * @return
	 */
	Optional<AppOverTime> getAppOvertime(String companyID, String appID);
}
