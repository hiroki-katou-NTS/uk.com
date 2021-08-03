package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.algorithm.CancelAppStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;

/**
 * @author thanh_nx
 *
 *         出退勤の反映
 */
public class ReflectAttendance {

	public static List<Integer> reflect(Require require, String cid, List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst,
			ScheduleRecordClassifi classification, DailyRecordOfApplication dailyApp, Optional<Boolean> reflectAtt,
			Optional<Boolean> reflectLeav, Optional<TimeChangeMeans> timeChangeMeanOpt) {

		List<Integer> lstItemId = new ArrayList<Integer>();
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
					lstItemId.addAll(Arrays.asList(CancelAppStamp.createItemId(3, timeZone.getWorkNo().v(), 2),
							CancelAppStamp.createItemId(4, timeZone.getWorkNo().v(), 2)));
				} else {
					//input.出勤を反映すると input.退勤を反映するをチェックする
					if(reflectLeav.orElse(false) && reflectLeav.orElse(false)) {
					// 勤務時間帯を日別勤怠(work）にセットする
					scheduleTimeSheet.get().setAttendance(timeZone.getTimeZone().getStartTime());
					scheduleTimeSheet.get().setLeaveWork(timeZone.getTimeZone().getEndTime());
					lstItemId.addAll(Arrays.asList(CancelAppStamp.createItemId(3, timeZone.getWorkNo().v(), 2),
							CancelAppStamp.createItemId(4, timeZone.getWorkNo().v(), 2)));
					}
				}

			} else {
				// [日別勤怠(work）の出退勤]をチェック
				// 日別勤怠の出退勤
				Optional<TimeLeavingWork> attendanceLeave = dailyApp.getAttendanceLeave()
						.flatMap(at -> at.getAttendanceLeavingWork(timeZone.getWorkNo()));
				
				if (attendanceLeave.isPresent()) {
					if (reflectAtt.orElse(false)) {
						//時刻を変更してもいいか判断する
						if (timeChangeMeanOpt.isPresent() && attendanceLeave.flatMap(c -> c.getStampOfAttendance())
								.map(x -> x.isCanChangeTime(require, cid, timeChangeMeanOpt.get())).orElse(true)) {
							if (attendanceLeave.get().getAttendanceStamp().isPresent()
									&& !attendanceLeave.get().getAttendanceStamp().get().getStamp().isPresent()) {
								attendanceLeave.get().getAttendanceStamp().get()
										.setStamp(Optional.of(WorkStamp.createDefault()));
							}
							attendanceLeave.flatMap(c -> c.getStampOfAttendance()).ifPresent(at -> {
								at.getTimeDay().setTimeWithDay(Optional.of(timeZone.getTimeZone().getStartTime()));
								at.getTimeDay().getReasonTimeChange().setTimeChangeMeans(timeChangeMeanOpt.get());
								
								lstItemId.addAll(Arrays.asList(CancelAppStamp.createItemId(31, timeZone.getWorkNo().v(), 10)));
							});
						}
					}

					if (reflectLeav.orElse(false)) {
						//時刻を変更してもいいか判断する
						if (timeChangeMeanOpt.isPresent() && attendanceLeave.flatMap(c -> c.getStampOfLeave())
								.map(x -> x.isCanChangeTime(require, cid, timeChangeMeanOpt.get())).orElse(true)) {
							if (attendanceLeave.get().getLeaveStamp().isPresent()
									&& !attendanceLeave.get().getLeaveStamp().get().getStamp().isPresent()) {
								attendanceLeave.get().getLeaveStamp().get().setStamp(Optional.of(WorkStamp.createDefault()));
							}
							attendanceLeave.flatMap(c -> c.getStampOfLeave()).ifPresent(at -> {
								at.getTimeDay().setTimeWithDay(Optional.of(timeZone.getTimeZone().getEndTime()));
								at.getTimeDay().getReasonTimeChange().setTimeChangeMeans(timeChangeMeanOpt.get());
								
								lstItemId.addAll(Arrays.asList(CancelAppStamp.createItemId(34, timeZone.getWorkNo().v(), 10)));
							});
						}
					}
				} else {
					TimeLeavingWork work = new TimeLeavingWork(timeZone.getWorkNo(), null, null);
					if (reflectAtt.orElse(false)) {
						work.setAttendanceStamp(Optional.of(new TimeActualStamp(null,
								new WorkStamp(
										new WorkTimeInformation(new ReasonTimeChange(timeChangeMeanOpt.get(), Optional.empty()),
												timeZone.getTimeZone().getStartTime()),
										Optional.empty()),
								0)));
						lstItemId.addAll(Arrays.asList(CancelAppStamp.createItemId(31, timeZone.getWorkNo().v(), 10)));
					}
					if (reflectLeav.orElse(false)) {
						work.setLeaveStamp(Optional.of(new TimeActualStamp(null,
								new WorkStamp(
										new WorkTimeInformation(new ReasonTimeChange(timeChangeMeanOpt.get(), Optional.empty()),
												timeZone.getTimeZone().getEndTime()),
										Optional.empty()),
								0)));
						lstItemId.addAll(Arrays.asList(CancelAppStamp.createItemId(34, timeZone.getWorkNo().v(), 10)));
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
		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);
		return lstItemId;
	}

	public static interface Require extends WorkStamp.Require{
		
	}
}
