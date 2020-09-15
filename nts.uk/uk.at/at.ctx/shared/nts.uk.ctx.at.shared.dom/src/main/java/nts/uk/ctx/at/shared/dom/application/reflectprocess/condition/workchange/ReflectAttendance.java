package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;

/**
 * @author thanh_nx
 *
 *         出退勤の反映
 */
public class ReflectAttendance {

	public static List<Integer> reflect(List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst,
			ScheduleRecordClassifi classification, DailyRecordOfApplication dailyApp, Optional<Boolean> reflectAtt,
			Optional<Boolean> reflectLeav) {

		// [input. 勤務時間帯(List）]をループ
		for (TimeZoneWithWorkNo timeZone : timeZoneWithWorkNoLst) {

			if (classification == ScheduleRecordClassifi.SCHEDULE) {
				// [日別勤怠(work)の勤務予定時間帯]をチェック
				Optional<ScheduleTimeSheet> scheduleTimeSheet = dailyApp.getWorkInformation()
						.getScheduleTimeSheet(timeZone.getWorkNo());
				if (!scheduleTimeSheet.isPresent()) {
					// 勤務時間帯を日別勤怠(work）にセットする
					dailyApp.getWorkInformation().getScheduleTimeSheets()
							.add(new ScheduleTimeSheet(timeZone.getWorkNo().v(),
									timeZone.getTimeZone().getStartTime().v(),
									timeZone.getTimeZone().getEndTime().v()));
				} else {
					// 勤務時間帯を日別勤怠(work）にセットする
					scheduleTimeSheet.get().setAttendance(timeZone.getTimeZone().getStartTime());
					scheduleTimeSheet.get().setLeaveWork(timeZone.getTimeZone().getEndTime());
				}
			} else {
				// [日別勤怠(work）の出退勤]をチェック
				// 日別勤怠の出退勤
				Optional<TimeLeavingWork> attendanceLeave = !dailyApp.getAttendanceLeave().isPresent()
						? Optional.empty()
						: dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(timeZone.getWorkNo());
				if (attendanceLeave.isPresent()) {
					if (reflectAtt.isPresent() && reflectAtt.get()
							&& attendanceLeave.get().getAttendanceStamp().get().getStamp().isPresent()) {
						attendanceLeave.get().getAttendanceStamp().get().getStamp().get().getTimeDay()
								.setTimeWithDay(Optional.of(timeZone.getTimeZone().getStartTime()));
						if (dailyApp.getClassification() == ScheduleRecordClassifi.RECORD) {
							attendanceLeave.get().getAttendanceStamp().get().getStamp().get().getTimeDay()
									.getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.APPLICATION);
						}
					}

					if (reflectLeav.isPresent() && reflectLeav.get()
							&& attendanceLeave.get().getLeaveStamp().get().getStamp().isPresent()) {
						attendanceLeave.get().getLeaveStamp().get().getStamp().get().getTimeDay()
								.setTimeWithDay(Optional.of(timeZone.getTimeZone().getEndTime()));
						if (dailyApp.getClassification() == ScheduleRecordClassifi.RECORD) {
							attendanceLeave.get().getLeaveStamp().get().getStamp().get().getTimeDay()
									.getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.APPLICATION);
						}
					}
				} else {
					TimeLeavingWork work = new TimeLeavingWork(timeZone.getWorkNo(), null, null);
					if (reflectAtt.isPresent() && reflectAtt.get()) {
						work.setAttendanceStamp(Optional.of(new TimeActualStamp(null,
								new WorkStamp(timeZone.getTimeZone().getStartTime(),
										new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.APPLICATION, null),
												timeZone.getTimeZone().getStartTime()),
										Optional.empty()),
								0)));
					}
					if (reflectLeav.isPresent() && reflectLeav.get()) {
						work.setLeaveStamp(Optional.of(new TimeActualStamp(null,
								new WorkStamp(timeZone.getTimeZone().getStartTime(),
										new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.APPLICATION, null),
												timeZone.getTimeZone().getStartTime()),
										Optional.empty()),
								0)));
					}

					if (dailyApp.getAttendanceLeave().isPresent()) {
						dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().add(work);
					} else {
						List<TimeLeavingWork> lst = new ArrayList<TimeLeavingWork>();
						lst.add(work);
						dailyApp.setAttendanceLeave(Optional.of(new TimeLeavingOfDailyAttd(lst, new WorkTimes(0))));
					}

				}
			}
		}
		if (classification == ScheduleRecordClassifi.RECORD) {
			// 出退勤回数の計算
			dailyApp.getAttendanceLeave().ifPresent(x -> x.setCountWorkTime());
		}
		List<Integer> lstItemId = new ArrayList<Integer>();
		if (classification == ScheduleRecordClassifi.SCHEDULE) {
			lstItemId.addAll(Arrays.asList(3, 5, 4, 6));
		} else {
			lstItemId.addAll(Arrays.asList(31, 41, 34, 44));
		}
		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);
		return lstItemId;
	}

}
