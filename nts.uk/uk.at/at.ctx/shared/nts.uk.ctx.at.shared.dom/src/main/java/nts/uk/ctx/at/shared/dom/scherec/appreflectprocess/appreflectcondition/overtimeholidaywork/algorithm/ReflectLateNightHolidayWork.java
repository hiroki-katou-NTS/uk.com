package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.ApplicationTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayMidnightWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkMidNightTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;

/**
 * @author thanh_nx
 *
 *         時間外深夜時間の反映（休日出勤）
 */
public class ReflectLateNightHolidayWork {

	public static void process(DailyRecordOfApplication dailyApp, ApplicationTimeShare applicationTime,
			PrePostAtrShare prePostAtr) {

		// [input. 申請時間. 就業時間外深夜時間]をチェック
		if (!applicationTime.getOverTimeShiftNight().isPresent())
			return;
		// [input. 事前事後区分]をチェック
		if (prePostAtr == PrePostAtrShare.PREDICT) {
			// [input. 申請時間. 就業時間外深夜時間]を日別勤怠(work）にセットする
			dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
					.getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime()
					.setBeforeApplicationTime(applicationTime.getOverTimeShiftNight().map(x -> x.getMidNightOutSide())
							.orElse(new AttendanceTime(0)));
			// 申請反映状態にする
			// 申請反映状態にする
			UpdateEditSttCreateBeforeAppReflect.update(dailyApp, Arrays.asList(565));
		} else {
			// [input. 申請時間. 就業時間外深夜時間]を[所定外深夜時間]にセットする
			dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
					.getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime().getTime()
					.setTime(applicationTime.getOverTimeShiftNight().map(x -> x.getMidNightOutSide())
							.orElse(new AttendanceTime(0)));

			// [input.申請時間.就業外深夜時間]休出深夜時間（List）をループする
			applicationTime.getOverTimeShiftNight().get().getMidNightHolidayTimes().forEach(overMn -> {
				// 日別勤怠(work）の該当する[休出深夜時間]をチェック
				dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().ifPresent(wh -> {
							val valueDefault = HolidayWorkMidNightTime.createDefaultWithAtr(overMn.getLegalClf());

							// 休出深夜 = empty
							if (!wh.getHolidayMidNightWork().isPresent()) {
								List<HolidayWorkMidNightTime> lstHolidayWorkMid = new ArrayList<>();
								lstHolidayWorkMid.add(valueDefault);
								wh.getHolidayMidNightWork().set(new HolidayMidnightWork(lstHolidayWorkMid));
							}

							val dataSet = wh.getHolidayMidNightWork().get().getHolidayWorkMidNightTime().stream()
									.filter(y -> y.getStatutoryAtr() == overMn.getLegalClf()).findFirst();

							if (dataSet.isPresent()) {
								// [input. 申請時間. 就業時間外深夜時間]を[休出深夜時間]にセットする
								dataSet.get().getTime().setTime(overMn.getAttendanceTime());
							} else {
								// 該当の法定区分をキーにした[休出深夜時間]を作成する
								valueDefault.getTime().setTime(overMn.getAttendanceTime());
								// [input. 申請時間. 就業時間外深夜時間]を[休出深夜時間]にセットする
								wh.getHolidayMidNightWork().get().getHolidayWorkMidNightTime().add(valueDefault);
							}
							// 申請反映状態にする
							UpdateEditSttCreateBeforeAppReflect.update(dailyApp,
									Arrays.asList(563, findId(overMn.getLegalClf())));
						});

			});

		}
		return;
	}

	private static Integer findId(StaturoryAtrOfHolidayWork statutoryAtr) {
		switch (statutoryAtr) {
		case WithinPrescribedHolidayWork:
			// 法内休出外深夜
			return 568;
		case ExcessOfStatutoryHolidayWork:
			// 法外休出外深夜
			return 570;
		case PublicHolidayWork:
			// 祝日休出深夜
			return 572;
		default:
			return null;
		}
	}
}
