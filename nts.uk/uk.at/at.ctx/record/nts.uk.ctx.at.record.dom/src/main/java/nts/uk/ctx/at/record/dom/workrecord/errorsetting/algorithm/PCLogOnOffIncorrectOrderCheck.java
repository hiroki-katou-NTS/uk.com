package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.LogOnInfo;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicateStateAtr;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicationStatusOfTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.RangeOfDayTimeZoneService;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 打刻順序不正(PCログオンログオフ)
 * 
 * @author nampt
 *
 */
@Stateless
public class PCLogOnOffIncorrectOrderCheck {

	@Inject
	private RangeOfDayTimeZoneService rangeOfDayTimeZoneService;

	public EmployeeDailyPerError pCLogOnOffIncorrectOrderCheck(String companyId, String employeeId,
			GeneralDate processingDate, PCLogOnInfoOfDaily pCLogOnInfoOfDaily,
			TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {

		EmployeeDailyPerError employeeDailyPerError = null;

		if (pCLogOnInfoOfDaily != null && !pCLogOnInfoOfDaily.getLogOnInfo().isEmpty()) {

			List<LogOnInfo> logOnInfos = pCLogOnInfoOfDaily.getLogOnInfo();

			// ペアの逆転がないか確認する(PCログオンログオフ)
			List<Integer> attendanceItemIds = this.checkPairReversed(pCLogOnInfoOfDaily);

			if (!attendanceItemIds.isEmpty()) {
				employeeDailyPerError = new EmployeeDailyPerError(companyId, employeeId, processingDate,
						new ErrorAlarmWorkRecordCode("S004"), attendanceItemIds);
			} else {
				if (logOnInfos.size() == 2) {
					if (logOnInfos.get(0).getLogOn().isPresent() && logOnInfos.get(0).getLogOn().get() != null
							&& logOnInfos.get(1).getLogOn().isPresent() && logOnInfos.get(1).getLogOn().get() != null) {

						logOnInfos.sort((e1, e2) -> e1.getLogOn().get().v().compareTo(e2.getLogOn().get().v()));
						if (logOnInfos.get(0).getWorkNo().greaterThan(logOnInfos.get(1).getWorkNo())) {
							attendanceItemIds.add(794);
							attendanceItemIds.add(795);
							attendanceItemIds.add(796);
							attendanceItemIds.add(797);
							employeeDailyPerError = new EmployeeDailyPerError(companyId, employeeId, processingDate,
									new ErrorAlarmWorkRecordCode("S004"), attendanceItemIds);
						} else {
							if (logOnInfos.get(0).getLogOff().isPresent()
									&& logOnInfos.get(0).getLogOff().get() != null
									&& logOnInfos.get(1).getLogOff().isPresent()
									&& logOnInfos.get(1).getLogOff().get() != null) {

								TimeWithDayAttr startFirstTime = logOnInfos.get(0).getLogOn().get();
								TimeWithDayAttr endFirstTime = logOnInfos.get(0).getLogOff().get();
								TimeSpanForCalc timeSpanFirstTime = new TimeSpanForCalc(startFirstTime, endFirstTime);

								TimeWithDayAttr startSecondTime = logOnInfos.get(1).getLogOn().get();
								TimeWithDayAttr endSecondTime = logOnInfos.get(1).getLogOff().get();
								TimeSpanForCalc timeSpanSecondTime = new TimeSpanForCalc(startSecondTime,
										endSecondTime);

								DuplicateStateAtr duplicateStateAtr = this.rangeOfDayTimeZoneService
										.checkPeriodDuplication(timeSpanFirstTime, timeSpanSecondTime);
								DuplicationStatusOfTimeZone duplicationStatusOfTimeZone = this.rangeOfDayTimeZoneService
										.checkStateAtr(duplicateStateAtr);

								if (duplicationStatusOfTimeZone != DuplicationStatusOfTimeZone.NON_OVERLAPPING) {
									attendanceItemIds.add(794);
									attendanceItemIds.add(795);
									attendanceItemIds.add(796);
									attendanceItemIds.add(797);
									employeeDailyPerError = new EmployeeDailyPerError(companyId, employeeId,
											processingDate, new ErrorAlarmWorkRecordCode("S004"), attendanceItemIds);
								} else {
									// PCログオンログオフと出退勤の順序不正判断処理
									attendanceItemIds = this.checkOder(pCLogOnInfoOfDaily,
											timeLeavingOfDailyPerformance);
									if (!attendanceItemIds.isEmpty()) {
										employeeDailyPerError = new EmployeeDailyPerError(companyId, employeeId,
												processingDate, new ErrorAlarmWorkRecordCode("S004"),
												attendanceItemIds);
									}
								}

							}
						}
					}
				}
			}
		}
		return employeeDailyPerError;
	}

	/**
	 * ペアの逆転がないか確認する(PCログオンログオフ)
	 * 
	 * @param pCLogOnInfoOfDaily
	 * @return
	 */
	private List<Integer> checkPairReversed(PCLogOnInfoOfDaily pCLogOnInfoOfDaily) {
		List<Integer> attendanceItemIds = new ArrayList<>();

		List<LogOnInfo> logOnInfos = pCLogOnInfoOfDaily.getLogOnInfo();

		for (LogOnInfo logOnInfo : logOnInfos) {
			if (logOnInfo.getLogOn().isPresent() && logOnInfo.getLogOn() != null && logOnInfo.getLogOff().isPresent()
					&& logOnInfo.getLogOff() != null) {
				if (logOnInfo.getLogOn().get().greaterThan(logOnInfo.getLogOff().get())) {
					if (logOnInfo.getWorkNo().v() == 1) {
						attendanceItemIds.add(794);
						attendanceItemIds.add(795);
					} else {
						attendanceItemIds.add(796);
						attendanceItemIds.add(797);
					}
				}
			}
		}

		return attendanceItemIds;
	}

	/**
	 * PCログオンログオフと出退勤の順序不正判断処理
	 * 
	 * @param pCLogOnInfoOfDaily
	 * @param timeLeavingOfDailyPerformance
	 * @return
	 */
	private List<Integer> checkOder(PCLogOnInfoOfDaily pCLogOnInfoOfDaily,
			TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {
		List<Integer> attendanceItemIds = new ArrayList<>();

		List<LogOnInfo> logOnInfos = pCLogOnInfoOfDaily.getLogOnInfo();

		if (timeLeavingOfDailyPerformance != null && !timeLeavingOfDailyPerformance.getTimeLeavingWorks().isEmpty()) {
			List<TimeLeavingWork> timeLeavingWorks = timeLeavingOfDailyPerformance.getTimeLeavingWorks();

			// 1回目のログオンと出勤．打刻が存在するか確認する(check first LogOn and stamp of
			// attendance of first timeLeavingWork has data or not)
			if (logOnInfos.get(0).getLogOn() != null && timeLeavingWorks.get(0).getAttendanceStamp().isPresent()
					&& timeLeavingWorks.get(0).getAttendanceStamp().get().getStamp().isPresent()
					&& timeLeavingWorks.get(0).getAttendanceStamp().get().getStamp().get().getTimeWithDay() != null) {
				if (logOnInfos.get(0).getLogOn().get().greaterThan(
						timeLeavingWorks.get(0).getAttendanceStamp().get().getStamp().get().getTimeWithDay())) {
					attendanceItemIds.add(794);
					attendanceItemIds.add(31);
				}
			}

			// 1回目のログオフと退勤．打刻が存在するか確認する(check first LogOff and stamp of leave of
			// first timeLeavingWork has data or not)
			if (logOnInfos.get(0).getLogOff() != null && timeLeavingWorks.get(0).getLeaveStamp().isPresent()
					&& timeLeavingWorks.get(0).getLeaveStamp().get().getStamp().isPresent()
					&& timeLeavingWorks.get(0).getLeaveStamp().get().getStamp().get().getTimeWithDay() != null) {
				if (logOnInfos.get(0).getLogOff().get()
						.lessThan(timeLeavingWorks.get(0).getLeaveStamp().get().getStamp().get().getTimeWithDay())) {
					attendanceItemIds.add(795);
					attendanceItemIds.add(34);
				}
			}

			// 2回目のログオンと出勤．打刻が存在するか確認する(check second LogOn and stamp of
			// attendance of second timeLeavingWork has data or not)
			if (logOnInfos.get(1).getLogOn() != null && timeLeavingWorks.get(1).getAttendanceStamp().isPresent()
					&& timeLeavingWorks.get(1).getAttendanceStamp().get().getStamp().isPresent()
					&& timeLeavingWorks.get(1).getAttendanceStamp().get().getStamp().get().getTimeWithDay() != null) {
				if (logOnInfos.get(1).getLogOn().get().greaterThan(
						timeLeavingWorks.get(1).getAttendanceStamp().get().getStamp().get().getTimeWithDay())) {
					attendanceItemIds.add(796);
					attendanceItemIds.add(41);
				}
			}

			// 2回目のログオフと退勤．打刻が存在するか確認する(check second LogOff and stamp of leave
			// of second timeLeavingWork has data or not)
			if (logOnInfos.get(1).getLogOff() != null && timeLeavingWorks.get(1).getLeaveStamp().isPresent()
					&& timeLeavingWorks.get(1).getLeaveStamp().get().getStamp().isPresent()
					&& timeLeavingWorks.get(1).getLeaveStamp().get().getStamp().get().getTimeWithDay() != null) {
				if (logOnInfos.get(1).getLogOff().get()
						.lessThan(timeLeavingWorks.get(1).getLeaveStamp().get().getStamp().get().getTimeWithDay())) {
					attendanceItemIds.add(797);
					attendanceItemIds.add(44);
				}
			}
		}

		return attendanceItemIds;
	}
}
