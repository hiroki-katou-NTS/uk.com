package nts.uk.screen.at.ws.dailyperformance.correction;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.dailyperformance.correction.mobile.ErrorAlarmDetailMob;
import nts.uk.screen.at.app.dailyperformance.correction.mobile.ErrorAlarmDtoMob;
import nts.uk.screen.at.app.dailyperformance.correction.mobile.ErrorAlarmParamMob;

@Path("screen/at/dailyperformance")
@Produces("application/json")
public class DailyPerformanceKdwS03WebService {
	@Inject
	private ErrorAlarmDetailMob errAlarmMob;

	@POST
	@Path("error-detail")
	public ErrorAlarmDtoMob getError(ErrorAlarmParamMob param) {
		return errAlarmMob.getErrorAlarm(param);
	}
}
