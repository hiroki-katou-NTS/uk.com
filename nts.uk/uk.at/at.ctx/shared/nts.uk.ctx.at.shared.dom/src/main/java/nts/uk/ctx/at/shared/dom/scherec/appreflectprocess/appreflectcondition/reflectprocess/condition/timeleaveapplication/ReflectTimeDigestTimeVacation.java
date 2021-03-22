package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.timeleaveapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeDigestApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
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
				for (int i = 1; i <= 2; i++) {
					int z = i;
					LateTimeOfDaily data = x.getLateTimeOfDaily().stream().filter(y -> y.getWorkNo().v() == z)
							.findFirst().orElse(null);
					if (data == null) {
						data = LateTimeOfDaily.createDefaultWithNo(i);
						x.getLateTimeOfDaily().add(data);
					}
					if (data.getWorkNo().v() == 1 && appTimeType == AppTimeType.ATWORK) {
						updateVacationTime(data.getTimePaidUseTime(), timeDigest);
						lstItemId.addAll(Arrays.asList(595, 596, 597, 1123, 1124, 1125, 1126));
					}

					if (data.getWorkNo().v() == 2 && appTimeType == AppTimeType.ATWORK2) {
						updateVacationTime(data.getTimePaidUseTime(), timeDigest);
						lstItemId.addAll(Arrays.asList(601, 602, 603, 1127, 1128, 1129, 1130));
					}
				}
			});
		} else if (appTimeType == AppTimeType.OFFWORK || appTimeType == AppTimeType.OFFWORK2) {
			// [input. 時間消化申請(work)]を 日別勤怠(work）の早退時間へセット
			dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
				for (int i = 1; i <= 2; i++) {
					int z = i;
					LeaveEarlyTimeOfDaily data = x.getLeaveEarlyTimeOfDaily().stream()
							.filter(y -> y.getWorkNo().v() == z).findFirst().orElse(null);
					if (data == null) {
						data = LeaveEarlyTimeOfDaily.createDefaultWithNo(i);
						x.getLeaveEarlyTimeOfDaily().add(data);
					}
					if (data.getWorkNo().v() == 1 && appTimeType == AppTimeType.OFFWORK) {
						updateVacationTime(data.getTimePaidUseTime(), timeDigest);
						lstItemId.addAll(Arrays.asList(607, 608, 609, 1131, 1132, 1133, 1134));
					}

					if (data.getWorkNo().v() == 2 && appTimeType == AppTimeType.OFFWORK2) {
						updateVacationTime(data.getTimePaidUseTime(), timeDigest);
						lstItemId.addAll(Arrays.asList(613, 614, 615, 1135, 1136, 1137, 1138));
					}
				}
			});
		} else {
			dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
				// [input.時間消化申請(work）を日別勤怠(work）の外出時間]へセット
				if (x.getOutingTimeOfDaily().isEmpty()) {
					x.getOutingTimeOfDaily().add(OutingTimeOfDaily.createDefaultWithReason(
							appTimeType == AppTimeType.PRIVATE ? GoingOutReason.PRIVATE : GoingOutReason.UNION));
				}

				for (OutingTimeOfDaily data : x.getOutingTimeOfDaily()) {
					if (appTimeType == AppTimeType.PRIVATE) {
						data.setReason(GoingOutReason.PRIVATE);
						updateVacationTime(data.getTimeVacationUseOfDaily(), timeDigest);
						lstItemId.addAll(Arrays.asList(502, 503, 504, 1145, 505, 1140, 1141));
					}

					if (appTimeType == AppTimeType.UNION) {
						data.setReason(GoingOutReason.UNION);
						updateVacationTime(data.getTimeVacationUseOfDaily(), timeDigest);
						lstItemId.addAll(Arrays.asList(514, 515, 516, 1146, 517, 1142, 1143));
					}
				}
			});
		}

		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);

		return lstItemId;
	}

	// 日別勤怠の時間休暇使用時間を更新する
	private static void updateVacationTime(TimevacationUseTimeOfDaily data, TimeDigestApplicationShare timeDigest) {
		data.setTimeAnnualLeaveUseTime(timeDigest.getTimeAnnualLeave());
		data.setTimeCompensatoryLeaveUseTime(timeDigest.getTimeOff());
		data.setTimeSpecialHolidayUseTime(timeDigest.getTimeSpecialVacation());
		data.setSpecialHolidayFrameNo(timeDigest.getSpecialVacationFrameNO().map(y -> new SpecialHdFrameNo(y)));
		data.setSixtyHourExcessHolidayUseTime(timeDigest.getOvertime60H());
		data.setTimeChildCareHolidayUseTime(timeDigest.getChildTime());
		data.setTimeCareHolidayUseTime(timeDigest.getNursingTime());
	}
}
