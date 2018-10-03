package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 休日打刻
 * 
 * @author nampt
 *
 */
@Stateless
public class HolidayStampCheck {

	@Inject
	private BasicScheduleService basicScheduleService;

	public EmployeeDailyPerError holidayStamp(String companyID, String employeeID, GeneralDate processingDate,
			WorkType workType, TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {

		EmployeeDailyPerError employeeDailyPerError = null;

		// 1日半日出勤・1日休日系の判定
		WorkStyle workStyle = basicScheduleService.checkWorkDay(workType.getWorkTypeCode().v());

		if (workStyle == WorkStyle.ONE_DAY_REST) {
			if (timeLeavingOfDailyPerformance != null
					&& !timeLeavingOfDailyPerformance.getTimeLeavingWorks().isEmpty()) {

				if ((timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0) != null
						&& ((timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getAttendanceStamp().isPresent()
								&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getAttendanceStamp().get().getStamp().isPresent())
								|| (timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getLeaveStamp().isPresent()
								&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getLeaveStamp().get().getStamp().isPresent())))
						|| (timeLeavingOfDailyPerformance.getTimeLeavingWorks().size() > 1 
								&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1) != null
								&& ((timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getAttendanceStamp().isPresent()
								&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getAttendanceStamp().get().getStamp().isPresent())
								|| (timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getLeaveStamp().isPresent()
								&& timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getLeaveStamp().get().getStamp().isPresent())))) {
					List<Integer> attendanceItemIds = new ArrayList<>();
					attendanceItemIds.add(28);
					employeeDailyPerError = new EmployeeDailyPerError(companyID, employeeID, processingDate,
							new ErrorAlarmWorkRecordCode("S005"), attendanceItemIds);
				}
			}
		}

		return employeeDailyPerError;
	}

}
