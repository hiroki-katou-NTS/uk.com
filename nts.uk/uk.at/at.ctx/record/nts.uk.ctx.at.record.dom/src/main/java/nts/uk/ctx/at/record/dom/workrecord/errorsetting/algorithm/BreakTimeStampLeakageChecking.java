package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.BreakFrameNo;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;

/*
 * 休憩系打刻漏れをチェックする
 */
@Stateless
public class BreakTimeStampLeakageChecking {

	public EmployeeDailyPerError breakTimeStampLeakageChecking(String companyID, String employeeID,
			GeneralDate processingDate, BreakTimeOfDailyPerformance breakTimeOfDailyPerformance) {

		EmployeeDailyPerError employeeDailyPerError = null;

		// List<BreakTimeOfDailyPerformance> breakTimeOfDailyPerformances =
		// breakTimeOfDailyPerformanceRepository.findByKey(employeeID,
		// processingDate);

		if (breakTimeOfDailyPerformance != null && !breakTimeOfDailyPerformance.getBreakTimeSheets().isEmpty()) {
			List<BreakTimeSheet> breakTimeSheets = breakTimeOfDailyPerformance.getBreakTimeSheets();

			List<Integer> attendanceItemIDList = new ArrayList<>();

			for (BreakTimeSheet breakTimeSheet : breakTimeSheets) {

				if (breakTimeSheet.getStartTime() != null || breakTimeSheet.getEndTime() != null) {

					if (breakTimeSheet.getStartTime() == null) {

						if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(1))) {
							attendanceItemIDList.add(157);
						} else if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(2))) {
							attendanceItemIDList.add(163);
						} else if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(3))) {
							attendanceItemIDList.add(169);
						} else if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(4))) {
							attendanceItemIDList.add(175);
						} else if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(5))) {
							attendanceItemIDList.add(181);
						} else if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(6))) {
							attendanceItemIDList.add(187);
						} else if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(7))) {
							attendanceItemIDList.add(193);
						} else if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(8))) {
							attendanceItemIDList.add(199);
						} else if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(9))) {
							attendanceItemIDList.add(205);
						} else if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(10))) {
							attendanceItemIDList.add(211);
						}
					}

					if (breakTimeSheet.getEndTime() == null) {

						if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(1))) {
							attendanceItemIDList.add(159);
						} else if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(2))) {
							attendanceItemIDList.add(165);
						} else if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(3))) {
							attendanceItemIDList.add(171);
						} else if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(4))) {
							attendanceItemIDList.add(177);
						} else if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(5))) {
							attendanceItemIDList.add(183);
						} else if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(6))) {
							attendanceItemIDList.add(189);
						} else if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(7))) {
							attendanceItemIDList.add(195);
						} else if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(8))) {
							attendanceItemIDList.add(201);
						} else if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(9))) {
							attendanceItemIDList.add(207);
						} else if (breakTimeSheet.getBreakFrameNo().equals(new BreakFrameNo(10))) {
							attendanceItemIDList.add(213);
						}
						// if (!attendanceItemIDList.isEmpty()) {
						// createEmployeeDailyPerError.createEmployeeDailyPerError(companyID,
						// employeeID, processingDate,
						// new ErrorAlarmWorkRecordCode("S001"),
						// attendanceItemIDList);
						// }
					}
				}
			}

			if (!attendanceItemIDList.isEmpty()) {
				employeeDailyPerError = new EmployeeDailyPerError(companyID, employeeID, processingDate,
						new ErrorAlarmWorkRecordCode("S001"), attendanceItemIDList);
			}
		}
		return employeeDailyPerError;
	}

}
