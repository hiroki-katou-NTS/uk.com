package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.lateearlycancellation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.application.lateleaveearly.ArrivedLateLeaveEarlyShare;
import nts.uk.ctx.at.shared.dom.scherec.application.lateleaveearly.LateOrEarlyAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.algorithm.CancelAppStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;

/**
 * refactor4 refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.遅刻早退取消申請
 * 遅刻早退取消申請の反映
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LateEarlyCancelReflect extends AggregateRoot {
	private String companyId;

	/**
	 * 遅刻早退報告を行った場合はアラームとしない
	 */
	private boolean clearLateReportWarning;

	/**
	 * @author thanh_nx
	 * 
	 *         遅刻早退取消申請の反映
	 */
	public DailyAfterAppReflectResult reflect(ArrivedLateLeaveEarlyShare appWorkChange,
			DailyRecordOfApplication dailyApp) {
		List<Integer> lstId = new ArrayList<>();
		// 取消の反映
		lstId.addAll(this.reflectCancel(appWorkChange, dailyApp).getLstItemId());
		// [遅刻早退報告を行った場合はアラームとしない]をチェック
		if (this.isClearLateReportWarning()) {
			// 時刻報告の反映
			lstId.addAll(this.reflectTimeReport(appWorkChange, dailyApp).getLstItemId());
		}
		return new DailyAfterAppReflectResult(dailyApp, lstId);
	}
		
	/**
	 * @author thanh_nx
	 *
	 *         取消の反映
	 */
	public DailyAfterAppReflectResult reflectCancel(ArrivedLateLeaveEarlyShare appWorkChange, DailyRecordOfApplication dailyApp) {
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
		return new DailyAfterAppReflectResult(dailyApp, lstItemId);
	}

	/**
	 * @author thanh_nx
	 *
	 *         時刻報告の反映
	 */
	public DailyAfterAppReflectResult reflectTimeReport(ArrivedLateLeaveEarlyShare appWorkChange,
			DailyRecordOfApplication dailyApp) {
		List<Integer> lstItemId = new ArrayList<>();
		// input.遅刻早退取消申請.時刻報告（List）でループ
		appWorkChange.getLateOrLeaveEarlies().forEach(data -> {
			// 処理中の[時刻報告.区分]をチェック
			if (data.getLateOrEarlyClassification() == LateOrEarlyAtrShare.LATE) {
				// 処理中の時刻報告.勤務NOをもとに日別勤怠（work）の遅刻時間をチェック
				dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
					Optional<LateTimeOfDaily> early = x.getActualWorkingTimeOfDaily().getTotalWorkingTime()
							.getLateTimeOfDaily().stream().filter(er -> er.getWorkNo().v() == data.getWorkNo())
							.findFirst();
					// 処理中の[時刻報告.時刻]を日別勤怠(work）にセットする
					early.ifPresent(value -> {
						value.setDoNotSetAlarm(true);
						lstItemId.add(CancelAppStamp.createItemId(865, data.getWorkNo(), 1));
					});
				});

			} else {
				// 処理中の時刻報告.勤務NOをもとに日別勤怠（work）の早退時間をチェック
				dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
					Optional<LeaveEarlyTimeOfDaily> early = x.getActualWorkingTimeOfDaily().getTotalWorkingTime()
							.getLeaveEarlyTimeOfDaily().stream().filter(er -> er.getWorkNo().v() == data.getWorkNo())
							.findFirst();
					// 処理中の[時刻報告.時刻]を日別勤怠(work）にセットする
					early.ifPresent(value -> {
						value.setDoNotSetAlarm(true);
						lstItemId.add(CancelAppStamp.createItemId(867, data.getWorkNo(), 1));
					});
				});
			}
		});

		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);
		return new DailyAfterAppReflectResult(dailyApp, lstItemId);
	}
		
}
