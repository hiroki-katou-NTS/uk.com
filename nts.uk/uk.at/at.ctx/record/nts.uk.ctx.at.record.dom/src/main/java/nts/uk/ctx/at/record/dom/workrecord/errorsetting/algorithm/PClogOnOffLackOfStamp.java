package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.LogOnInfo;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;

@Stateless
public class PClogOnOffLackOfStamp {

	@Inject
	private BasicScheduleService basicScheduleService;

	public void pClogOnOffLackOfStamp(String companyId, String employeeId, GeneralDate processingDate,
			PCLogOnInfoOfDaily pCLogOnInfoOfDaily, WorkInfoOfDailyPerformance workInfoOfDailyPerformance) {
		// 1日半日出勤・1日休日系の判定
		WorkStyle workStyle = basicScheduleService
				.checkWorkDay(workInfoOfDailyPerformance.getRecordWorkInformation().getWorkTypeCode().v());
		if (workStyle != WorkStyle.ONE_DAY_REST) {
			if (pCLogOnInfoOfDaily != null && !pCLogOnInfoOfDaily.getLogOnInfo().isEmpty()) {
				List<Integer> attendanceItemIDList = new ArrayList<>();
				
				List<LogOnInfo> logOnInfos = pCLogOnInfoOfDaily.getLogOnInfo();
				for (LogOnInfo logOnInfo : logOnInfos) {
					// ログオフのみ存在している(only has Logoff time)
					if (logOnInfo.getLogOff().getTimeWithDay() != null && logOnInfo.getLogOn().getTimeWithDay() == null) {
						if (logOnInfo.getWorkNo().v() == 1) {
							attendanceItemIDList.add(794);
						} else if (logOnInfo.getWorkNo().v() == 2) {
							attendanceItemIDList.add(796);
						}
					}
					// ログオンのみ存在している(only has Logon time)
				}
			}
		}
	}

}
