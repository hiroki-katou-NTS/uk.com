package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.timeleaveapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.application.timeleaveapplication.TimeDigestApplicationShare;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.WithinOutingTotalTime;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHdFrameNo;

/**
 * @author thanh_nx
 *
 *         時間休暇の時間消化の反映
 */
public class ReflectTimeDigestTimeVacation {

	public static List<Integer> process(DailyRecordOfApplication dailyApp, AppTimeType appTimeType,
			TimeDigestApplicationShare timeDigest) {

		// [input. 時間休種類]をチェック
		List<Integer> lstItemId = new ArrayList<>();
		if (appTimeType == AppTimeType.ATWORK || appTimeType == AppTimeType.ATWORK2) {
			dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
				// [input. 時間消化申請(work)]を 日別勤怠(work）の遅刻時間へセット
				for (LateTimeOfDaily data : x.getLateTimeOfDaily()) {
					if (data.getWorkNo().v() == 1 && appTimeType == AppTimeType.ATWORK) {
						data.getTimePaidUseTime().setTimeAnnualLeaveUseTime(timeDigest.getTimeAnnualLeave());
						data.getTimePaidUseTime().setTimeCompensatoryLeaveUseTime(timeDigest.getTimeOff());
						data.getTimePaidUseTime().setTimeSpecialHolidayUseTime(timeDigest.getTimeSpecialVacation());
						data.getTimePaidUseTime().setSpecialHolidayFrameNo(
								timeDigest.getSpecialVacationFrameNO().map(y -> new SpecialHdFrameNo(y)));
						data.getTimePaidUseTime().setSixtyHourExcessHolidayUseTime(timeDigest.getOvertime60H());
						lstItemId.addAll(Arrays.asList(595, 596, 597, 1123, 1124));
					}

					if (data.getWorkNo().v() == 2 && appTimeType == AppTimeType.ATWORK2) {
						data.getTimePaidUseTime().setTimeAnnualLeaveUseTime(timeDigest.getTimeAnnualLeave());
						data.getTimePaidUseTime().setTimeCompensatoryLeaveUseTime(timeDigest.getTimeOff());
						data.getTimePaidUseTime().setTimeSpecialHolidayUseTime(timeDigest.getTimeSpecialVacation());
						data.getTimePaidUseTime().setSpecialHolidayFrameNo(
								timeDigest.getSpecialVacationFrameNO().map(y -> new SpecialHdFrameNo(y)));
						data.getTimePaidUseTime().setSixtyHourExcessHolidayUseTime(timeDigest.getOvertime60H());
						lstItemId.addAll(Arrays.asList(601, 602, 603, 1127, 1128));
					}
				}
			});
		} else if (appTimeType == AppTimeType.OFFWORK || appTimeType == AppTimeType.OFFWORK2) {
			// [input. 時間消化申請(work)]を 日別勤怠(work）の早退時間へセット
			dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
				for (LeaveEarlyTimeOfDaily data : x.getLeaveEarlyTimeOfDaily()) {
					if (data.getWorkNo().v() == 1 && appTimeType == AppTimeType.OFFWORK) {
						data.getTimePaidUseTime().setTimeAnnualLeaveUseTime(timeDigest.getTimeAnnualLeave());
						data.getTimePaidUseTime().setTimeCompensatoryLeaveUseTime(timeDigest.getTimeOff());
						data.getTimePaidUseTime().setTimeSpecialHolidayUseTime(timeDigest.getTimeSpecialVacation());
						data.getTimePaidUseTime().setSpecialHolidayFrameNo(
								timeDigest.getSpecialVacationFrameNO().map(y -> new SpecialHdFrameNo(y)));
						data.getTimePaidUseTime().setSixtyHourExcessHolidayUseTime(timeDigest.getOvertime60H());
						lstItemId.addAll(Arrays.asList(607, 608, 609, 1131, 1132));
					}

					if (data.getWorkNo().v() == 2 && appTimeType == AppTimeType.OFFWORK2) {
						data.getTimePaidUseTime().setTimeAnnualLeaveUseTime(timeDigest.getTimeAnnualLeave());
						data.getTimePaidUseTime().setTimeCompensatoryLeaveUseTime(timeDigest.getTimeOff());
						data.getTimePaidUseTime().setTimeSpecialHolidayUseTime(timeDigest.getTimeSpecialVacation());
						data.getTimePaidUseTime().setSpecialHolidayFrameNo(
								timeDigest.getSpecialVacationFrameNO().map(y -> new SpecialHdFrameNo(y)));
						data.getTimePaidUseTime().setSixtyHourExcessHolidayUseTime(timeDigest.getOvertime60H());
						lstItemId.addAll(Arrays.asList(613, 614, 615, 1135, 1136));
					}
				}
			});
		} else {
			dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
				// [input.時間消化申請(work）を日別勤怠(work）の外出時間]へセット
				if (x.getOutingTimeOfDaily().isEmpty()) {
					x.getOutingTimeOfDaily().add(new OutingTimeOfDaily(new BreakTimeGoOutTimes(0),
							appTimeType == AppTimeType.PRIVATE ? GoingOutReason.PRIVATE : GoingOutReason.PUBLIC,
							TimevacationUseTimeOfDaily.defaultValue(),
							OutingTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)),
									WithinOutingTotalTime.sameTime(TimeWithCalculation.sameTime(new AttendanceTime(0))),
									TimeWithCalculation.sameTime(new AttendanceTime(0))),
							OutingTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)),
									WithinOutingTotalTime.sameTime(TimeWithCalculation.sameTime(new AttendanceTime(0))),
									TimeWithCalculation.sameTime(new AttendanceTime(0))),
							new ArrayList<>()));
				}
				
				for (OutingTimeOfDaily data : x.getOutingTimeOfDaily()) {
					if (appTimeType == AppTimeType.PRIVATE) {
						data.setReason(GoingOutReason.PRIVATE);
						data.getTimeVacationUseOfDaily().setTimeAnnualLeaveUseTime(timeDigest.getTimeAnnualLeave());
						data.getTimeVacationUseOfDaily().setTimeCompensatoryLeaveUseTime(timeDigest.getTimeOff());
						data.getTimeVacationUseOfDaily()
								.setTimeSpecialHolidayUseTime(timeDigest.getTimeSpecialVacation());
						data.getTimeVacationUseOfDaily().setSpecialHolidayFrameNo(
								timeDigest.getSpecialVacationFrameNO().map(y -> new SpecialHdFrameNo(y)));
						data.getTimeVacationUseOfDaily().setSixtyHourExcessHolidayUseTime(timeDigest.getOvertime60H());
						lstItemId.addAll(Arrays.asList(502, 503, 504, 1145, 505));
					}

					if (appTimeType == AppTimeType.UNION) {
						data.setReason(GoingOutReason.UNION);
						data.getTimeVacationUseOfDaily().setTimeAnnualLeaveUseTime(timeDigest.getTimeAnnualLeave());
						data.getTimeVacationUseOfDaily().setTimeCompensatoryLeaveUseTime(timeDigest.getTimeOff());
						data.getTimeVacationUseOfDaily()
								.setTimeSpecialHolidayUseTime(timeDigest.getTimeSpecialVacation());
						data.getTimeVacationUseOfDaily().setSpecialHolidayFrameNo(
								timeDigest.getSpecialVacationFrameNO().map(y -> new SpecialHdFrameNo(y)));
						data.getTimeVacationUseOfDaily().setSixtyHourExcessHolidayUseTime(timeDigest.getOvertime60H());
						lstItemId.addAll(Arrays.asList(514, 515, 516, 1146, 517));
					}
				}
			});
		}

		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);

		return lstItemId;
	}

}
