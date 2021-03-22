package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.lateleaveearly.record;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.application.lateleaveearly.ArrivedLateLeaveEarlyShare;
import nts.uk.ctx.at.shared.dom.scherec.application.lateleaveearly.LateOrEarlyAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.lateearlycancellation.LateEarlyCancelReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.stamp.CancelAppStamp;

/**
 * @author thanh_nx
 *
 *         取消の反映
 */
public class RCReflectArrivedLateLeaveEarlyApp {

	public static List<Integer> reflect(ArrivedLateLeaveEarlyShare appWorkChange, DailyRecordOfApplication dailyApp,
			LateEarlyCancelReflect reflectApp) {

		List<Integer> lstItemId = new ArrayList<>();
		// [input.遅刻早退取消申請.取消（List）]でループ
		appWorkChange.getLateCancelation().stream().forEach(data -> {

			// 処理中の[取消.区分]をチェック
			if (data.getLateOrEarlyClassification() == LateOrEarlyAtrShare.EARLY) {
				// 早退
				// 勤務NOで早退時間をチェック
				dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
					Optional<LeaveEarlyTimeOfDaily> early = x.getActualWorkingTimeOfDaily().getTotalWorkingTime()
							.getLeaveEarlyTimeOfDaily().stream().filter(er -> er.getWorkNo().v() == data.getWorkNo())
							.findFirst();
					// 早退時間をクリア
					early.ifPresent(value -> {
						lstItemId.add(CancelAppStamp.createItemId(604, data.getWorkNo(), 6));
						value.resetData();
					});
				});

				// 出退勤の早退取消
				dailyApp.getAttendanceLeave().ifPresent(att -> {
					Optional<TimeLeavingWork> timeOpt = att.getTimeLeavingWorks().stream()
							.filter(x -> x.getWorkNo().v() == data.getWorkNo()).findFirst();
					timeOpt.ifPresent(time -> {
						time.setCanceledEarlyLeave(true);
						lstItemId.add(CancelAppStamp.createItemId(863, data.getWorkNo(), 1));
					});
				});
			} else {

				// 遅刻
				// 勤務NOで遅刻時間をチェック
				dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
					Optional<LateTimeOfDaily> early = x.getActualWorkingTimeOfDaily().getTotalWorkingTime()
							.getLateTimeOfDaily().stream().filter(er -> er.getWorkNo().v() == data.getWorkNo())
							.findFirst();
					// 遅刻時間をクリア
					early.ifPresent(value -> {
						lstItemId.add(CancelAppStamp.createItemId(592, data.getWorkNo(), 6));
						value.resetData();
					});
				});

				// 出退勤の遅刻取消
				dailyApp.getAttendanceLeave().ifPresent(att -> {
					Optional<TimeLeavingWork> timeOpt = att.getTimeLeavingWorks().stream()
							.filter(x -> x.getWorkNo().v() == data.getWorkNo()).findFirst();
					timeOpt.ifPresent(time -> {
						time.setCanceledLate(true);
						lstItemId.add(CancelAppStamp.createItemId(861, data.getWorkNo(), 1));
					});
				});
			}

		});

		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);
		return lstItemId;
	}

}
