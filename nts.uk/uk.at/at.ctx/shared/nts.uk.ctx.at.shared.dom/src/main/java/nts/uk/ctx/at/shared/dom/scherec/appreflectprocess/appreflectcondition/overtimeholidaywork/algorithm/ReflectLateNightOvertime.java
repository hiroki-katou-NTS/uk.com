package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm;

import java.util.Arrays;

import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.ApplicationTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;

/**
 * @author thanh_nx
 *
 *         時間外深夜時間の反映（残業）
 */
public class ReflectLateNightOvertime {

	public static void process(DailyRecordOfApplication dailyApp, ApplicationTimeShare applicationTimeShare,
			PrePostAtrShare prePostAtr) {

		// [input. 申請時間. 就業時間外深夜時間]をチェック
		if (!applicationTimeShare.getOverTimeShiftNight().isPresent()
				|| !dailyApp.getAttendanceTimeOfDailyPerformance().isPresent())
			return;

		// [input. 事前事後区分]をチェック
		if (prePostAtr == PrePostAtrShare.PREDICT) {
			// [input.申請時間.就業時間外深夜時間]を日別勤怠(work）にセットする
			dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
					.getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime()
					.setBeforeApplicationTime(applicationTimeShare.getOverTimeShiftNight().get().getMidNightOutSide());

			// 申請反映状態にする
			UpdateEditSttCreateBeforeAppReflect.update(dailyApp, Arrays.asList(565));

		}

		if (prePostAtr == PrePostAtrShare.POSTERIOR) {
			// [input.申請時間.就業時間外深夜時間]を日別勤怠(work）にセットする
			dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
					.getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime().getTime()
					.setTime(applicationTimeShare.getOverTimeShiftNight().get().getMidNightOutSide());

			// [input.申請時間.就業時間外深夜時間.残業深夜時間]を日別勤怠(work）にセットする
			dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
					.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().ifPresent(x -> {
						if (x.getExcessOverTimeWorkMidNightTime().isPresent()) {
							x.getExcessOverTimeWorkMidNightTime().get().getTime()
									.setTime(applicationTimeShare.getOverTimeShiftNight().get().getOverTimeMidNight());
						}
					});

			// 申請反映状態にする
			UpdateEditSttCreateBeforeAppReflect.update(dailyApp, Arrays.asList(563, 566));
		}
	}

}
