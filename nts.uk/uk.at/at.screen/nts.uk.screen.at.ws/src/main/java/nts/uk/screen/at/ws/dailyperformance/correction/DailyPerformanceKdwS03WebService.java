package nts.uk.screen.at.ws.dailyperformance.correction;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.dailyperformance.correction.agreement.AgreementInfomationDto;
import nts.uk.screen.at.app.dailyperformance.correction.agreement.DisplayAgreementInfo;
import nts.uk.screen.at.app.dailyperformance.correction.mobile.AgreementInfoParamMob;
import nts.uk.screen.at.app.dailyperformance.correction.mobile.ErrorAlarmDetailMob;
import nts.uk.screen.at.app.dailyperformance.correction.mobile.ErrorAlarmDtoMob;
import nts.uk.screen.at.app.dailyperformance.correction.mobile.ErrorAlarmParamMob;
import nts.uk.screen.at.app.dailyperformance.correction.mobile.MonthlyPerData;
import nts.uk.screen.at.app.dailyperformance.correction.mobile.MonthlyPerParamMob;
import nts.uk.screen.at.app.dailyperformance.correction.mobile.MonthlyPerfomanceMob;
import nts.uk.shr.com.context.AppContexts;

@Path("screen/at/dailyperformance")
@Produces("application/json")
public class DailyPerformanceKdwS03WebService {
	@Inject
	private ErrorAlarmDetailMob errAlarmMob;
	@Inject
	private DisplayAgreementInfo agreementInfo;
	@Inject
	private MonthlyPerfomanceMob monthlyPerMob;
	
	@POST
	@Path("error-detail")
	public ErrorAlarmDtoMob getError(ErrorAlarmParamMob param) {
		return errAlarmMob.getErrorAlarm(param);
	}
	@POST
	@Path("36AgreementInfo")
	public AgreementInfomationDto get36AgreementInfo(AgreementInfoParamMob param) {
		return agreementInfo.displayAgreementInfo(AppContexts.user().companyId(),
				param.getEmployeeId(), param.getYear(), param.getMonth());
	}
	@POST
	@Path("monthly-perfomance")
	public List<MonthlyPerData> getMonthlyPer(MonthlyPerParamMob param) {
		return monthlyPerMob.getMonthlyPerData(param);
	}
}
