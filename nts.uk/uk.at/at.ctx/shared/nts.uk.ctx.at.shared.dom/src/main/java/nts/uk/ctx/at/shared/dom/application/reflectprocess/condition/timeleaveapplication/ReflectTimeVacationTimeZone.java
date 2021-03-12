package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.timeleaveapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.CancelAppStamp;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

/**
 * @author thanh_nx
 *
 *         時間休暇時間帯の反映
 */
public class ReflectTimeVacationTimeZone {

	public static List<Integer> process(AppTimeType appTimeType, List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst,
			DailyRecordOfApplication dailyApp) {
		List<Integer> lstItemId = new ArrayList<Integer>();

		// [input.時間休暇時間帯（List）]でループ
		for (TimeZoneWithWorkNo workNo : timeZoneWithWorkNoLst) {

			if (appTimeType == AppTimeType.PRIVATE || appTimeType == AppTimeType.UNION) {
				// 日別勤怠(work）の外出時間帯をチェック
				if (!dailyApp.getOutingTime().isPresent()) {
					dailyApp.setOutingTime(Optional.of(new OutingTimeOfDailyAttd(new ArrayList<>())));
				}

				if (!dailyApp.getOutingTime().get().getOutingTimeSheets().stream()
						.filter(x -> x.getOutingFrameNo().v() == workNo.getWorkNo().v()).findFirst().isPresent()) {
					dailyApp.getOutingTime().get().getOutingTimeSheets().add(OutingTimeSheet.createDefaultWithNo(
							workNo.getWorkNo().v(),
							appTimeType == AppTimeType.PRIVATE ? GoingOutReason.PRIVATE : GoingOutReason.UNION));
				}

				// 時間帯を日別勤怠(work）の外出時間帯にセットする
				dailyApp.getOutingTime().get().getOutingTimeSheets().stream()
						.filter(x -> x.getOutingFrameNo().v() == workNo.getWorkNo().v()).map(x -> {
							x.getGoOut().ifPresent(y -> y.getStamp().map(st -> {
								st.getTimeDay().setTimeWithDay(Optional.of(workNo.getTimeZone().getStartTime()));
								st.getTimeDay().getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.APPLICATION);
								lstItemId.add(CancelAppStamp.createItemId(88, workNo.getWorkNo().v(), 7));
								return st;
							}));

							x.getComeBack().ifPresent(y -> y.getStamp().map(st -> {
								st.getTimeDay().setTimeWithDay(Optional.of(workNo.getTimeZone().getEndTime()));
								st.getTimeDay().getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.APPLICATION);
								lstItemId.add(CancelAppStamp.createItemId(91, workNo.getWorkNo().v(), 7));
								return st;
							}));
							x.setReasonForGoOut(appTimeType == AppTimeType.PRIVATE ? GoingOutReason.PRIVATE : GoingOutReason.UNION);
							lstItemId.add(CancelAppStamp.createItemId(86, workNo.getWorkNo().v(), 7));
							return x;
						}).collect(Collectors.toList());

			} else {

				// 日別勤怠(work）の出退勤をチェック
				if (!dailyApp.getAttendanceLeave().isPresent()) {
					dailyApp.setAttendanceLeave(
							Optional.of(new TimeLeavingOfDailyAttd(new ArrayList<>(), new WorkTimes(0))));
				}

				// 処理中の時間帯.勤務NOをもとに[出退勤]を作成する
				if (!dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(workNo.getWorkNo().v()).isPresent()
						&& (checkRespectiveAttNo(workNo.getWorkNo().v(), appTimeType)
								|| checkRespectiveOffNo(workNo.getWorkNo().v(), appTimeType))) {
					dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().add(
							TimeLeavingWork.createDefaultWithNo(workNo.getWorkNo().v(), TimeChangeMeans.APPLICATION));
				}

				if (dailyApp.getClassification() == ScheduleRecordClassifi.SCHEDULE) {
					// 時間帯を日別勤怠(work）の時間休暇時間帯にセットする
					dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().stream()
							.filter(x -> x.getWorkNo().v() == workNo.getWorkNo().v()).map(x -> {
								if (checkRespectiveAttNo(workNo.getWorkNo().v(), appTimeType)) {
									x.getAttendanceStamp().ifPresent(y -> {
										y.setTimeVacation(
												Optional.of(new TimeSpanForCalc(workNo.getTimeZone().getStartTime(),
														workNo.getTimeZone().getEndTime())));
										lstItemId.add(appTimeType == AppTimeType.ATWORK ? 1288 : 1290);
									});
								} else if (checkRespectiveOffNo(workNo.getWorkNo().v(), appTimeType)) {
									x.getLeaveStamp().ifPresent(y -> {
										y.setTimeVacation(
												Optional.of(new TimeSpanForCalc(workNo.getTimeZone().getStartTime(),
														workNo.getTimeZone().getEndTime())));
										lstItemId.add(appTimeType == AppTimeType.OFFWORK ? 1289 : 1291);
									});
								}
								return x;
							}).collect(Collectors.toList());
				} else {
					// 時間帯を日別勤怠(work）の出退勤にセットする
					dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().stream()
							.filter(x -> x.getWorkNo().v() == workNo.getWorkNo().v()).map(x -> {
								if (checkRespectiveAttNo(workNo.getWorkNo().v(), appTimeType)) {
									x.getAttendanceStamp().ifPresent(y -> y.getStamp().map(st -> {

										st.getTimeDay().setTimeWithDay(Optional.ofNullable(workNo.getTimeZone().getEndTime()));
										st.getTimeDay().getReasonTimeChange()
												.setTimeChangeMeans(TimeChangeMeans.APPLICATION);
										lstItemId.add(appTimeType == AppTimeType.ATWORK ? 31 : 41);
										return st;
									}));
								} else if (checkRespectiveOffNo(workNo.getWorkNo().v(), appTimeType)) {
									x.getLeaveStamp().ifPresent(y -> y.getStamp().map(st -> {
										st.getTimeDay()
												.setTimeWithDay(Optional.ofNullable(workNo.getTimeZone().getStartTime()));
										st.getTimeDay().getReasonTimeChange()
												.setTimeChangeMeans(TimeChangeMeans.APPLICATION);
										lstItemId.add(appTimeType == AppTimeType.OFFWORK ? 34 : 44);
										return st;
									}));
								}
								return x;
							}).collect(Collectors.toList());
				}

			}
		}
		
		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);
		return lstItemId;
	}
	
	private static boolean checkRespectiveAttNo(int no, AppTimeType appType) {
		if (no == 1 && appType == AppTimeType.ATWORK) {
			return true;
		}
		if (no == 2 && appType == AppTimeType.ATWORK2) {
			return true;
		}
		return false;
	}

	private static boolean checkRespectiveOffNo(int no, AppTimeType appType) {
		if (no == 1 && appType == AppTimeType.OFFWORK) {
			return true;
		}
		if (no == 2 && appType == AppTimeType.OFFWORK2) {
			return true;
		}
		return false;
	}
}
