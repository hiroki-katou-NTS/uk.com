package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.BreakFrameNo;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.CreateEmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;

/*
 * 休憩系打刻順序不正をチェックする
 */
@Stateless
public class BreakTimeStampIncorrectOrderChecking {

	@Inject
	private BreakTimeOfDailyPerformanceRepository breakTimeOfDailyPerformanceRepository;

	@Inject
	private CreateEmployeeDailyPerError createEmployeeDailyPerError;

	public void breakTimeStampIncorrectOrderChecking(String companyId, String employeeId, GeneralDate processingDate) {
		List<BreakTimeOfDailyPerformance> breakTimeOfDailyPerformances = breakTimeOfDailyPerformanceRepository
				.findByKey(employeeId, processingDate);
		if (!breakTimeOfDailyPerformances.isEmpty()) {
			BreakTimeOfDailyPerformance breakTimeOfDailyPerformance = breakTimeOfDailyPerformances.get(0);

			List<BreakTimeSheet> breakTimeSheets = breakTimeOfDailyPerformance.getBreakTimeSheets();

			breakTimeSheets.sort((e1, e2) -> e1.getStartTime().getTimeWithDay().v()
					.compareTo(e2.getStartTime().getTimeWithDay().v()));

			int breakFrameNo = 1;
			for (BreakTimeSheet item : breakTimeSheets) {
				WorkStamp startTime = item.getStartTime();
				WorkStamp endTime = item.getEndTime();
				item = new BreakTimeSheet(new BreakFrameNo(new BigDecimal(breakFrameNo)), startTime, endTime);
				breakFrameNo++;
			}

			for (BreakTimeSheet breakTimeSheet : breakTimeSheets) {
				
				List<Integer> attendanceItemIDList = new ArrayList<>();

				if (breakTimeSheet.getBreakFrameNo().equals(new WorkNo(new BigDecimal(1)))) {
					attendanceItemIDList.add(157);
					attendanceItemIDList.add(159);
				} else if (breakTimeSheet.getBreakFrameNo().equals(new WorkNo(new BigDecimal(2)))) {
					attendanceItemIDList.add(163);
					attendanceItemIDList.add(165);
				} else if (breakTimeSheet.getBreakFrameNo().equals(new WorkNo(new BigDecimal(3)))) {
					attendanceItemIDList.add(169);
					attendanceItemIDList.add(171);
				} else if (breakTimeSheet.getBreakFrameNo().equals(new WorkNo(new BigDecimal(4)))) {
					attendanceItemIDList.add(175);
					attendanceItemIDList.add(177);
				} else if (breakTimeSheet.getBreakFrameNo().equals(new WorkNo(new BigDecimal(5)))) {
					attendanceItemIDList.add(181);
					attendanceItemIDList.add(183);
				} else if (breakTimeSheet.getBreakFrameNo().equals(new WorkNo(new BigDecimal(6)))) {
					attendanceItemIDList.add(187);
					attendanceItemIDList.add(189);
				} else if (breakTimeSheet.getBreakFrameNo().equals(new WorkNo(new BigDecimal(7)))) {
					attendanceItemIDList.add(193);
					attendanceItemIDList.add(195);
				} else if (breakTimeSheet.getBreakFrameNo().equals(new WorkNo(new BigDecimal(8)))) {
					attendanceItemIDList.add(199);
					attendanceItemIDList.add(201);
				} else if (breakTimeSheet.getBreakFrameNo().equals(new WorkNo(new BigDecimal(9)))) {
					attendanceItemIDList.add(205);
					attendanceItemIDList.add(207);
				} else if (breakTimeSheet.getBreakFrameNo().equals(new WorkNo(new BigDecimal(10)))) {
					attendanceItemIDList.add(211);
					attendanceItemIDList.add(213);
				}
				
				if(breakTimeSheet.getStartTime().getTimeWithDay().greaterThan(breakTimeSheet.getEndTime().getTimeWithDay())){
					createEmployeeDailyPerError.createEmployeeDailyPerError(companyId, employeeId, processingDate,
							new ErrorAlarmWorkRecordCode("S004"), attendanceItemIDList);
				}
			}
		}
	}
}
