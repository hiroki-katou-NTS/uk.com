package nts.uk.ctx.at.request.dom.applicationreflect.algorithm.checkprocess;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.BasicScheduleConfirmImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.BasicScheduleConfirmImport.ConfirmedAtrImport;
import nts.uk.ctx.at.request.dom.applicationreflect.algorithm.checkprocess.CheckAchievementConfirmation.ConfirmClsStatus;
import nts.uk.ctx.at.request.dom.applicationreflect.object.ReflectStatusResult;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         事前チェック処理（勤務予定）
 */
public class PreCheckProcessWorkSchedule {

	public static PreCheckProcessResult preCheck(Require require, String companyId, Application application,
			int closureId, boolean isCalWhenLock, ReflectStatusResult reflectStatus, GeneralDate targetDate) {

		// ロック中処理のチェック
		NotUseAtr checkProcess = CheckProcessDuringLock.checkProcess(require, companyId, closureId, isCalWhenLock,
				targetDate);

		if (checkProcess == NotUseAtr.NOT_USE) {
			// 予定反映不可理由に「2：実績がロックされている」をセットする
			reflectStatus.setReasonNotReflectWorkSchedule(ReasonNotReflect.ACHIEVEMENTS_LOCKED);
			return new PreCheckProcessResult(NotUseAtr.NOT_USE, reflectStatus);
		}

		// 事前の残業申請かどうかチェック
		if (application.getAppType() == ApplicationType.OVER_TIME_APPLICATION
				&& application.getPrePostAtr() == PrePostAtr.PREDICT) {
			return new PreCheckProcessResult(NotUseAtr.USE, reflectStatus);
		}

		// 勤務予定の確定状態を取得する
		List<BasicScheduleConfirmImport> basicSchedule = require
				.findConfirmById(Arrays.asList(application.getEmployeeID()), new DatePeriod(targetDate, targetDate));
		if (basicSchedule.stream().filter(x -> x.getConfirmedAtr() == ConfirmedAtrImport.CONFIRMED).findFirst()
				.isPresent()) {
			// 予定反映不可理由に「0：勤務スケジュール確定済」をセットする
			reflectStatus.setReasonNotReflectWorkSchedule(ReasonNotReflect.WORK_FIXED);
			return new PreCheckProcessResult(NotUseAtr.NOT_USE, reflectStatus);
		}

		// 対象期間内で本人確認をした日をチェックする
		if (!require.getProcessingYMD(companyId, application.getEmployeeID(), new DatePeriod(targetDate, targetDate))
				.isEmpty()) {
			// 予定反映不可理由に「3：本人確認、上司確認済」をセット
			reflectStatus.setReasonNotReflectWorkSchedule(ReasonNotReflect.SELF_CONFIRMED_BOSS_CONFIRMED);
			return new PreCheckProcessResult(NotUseAtr.NOT_USE, reflectStatus);
		}

		// 実績の締め確定をチェックする
		ConfirmClsStatus confirmClsStatus = CheckAchievementConfirmation.check(require, companyId,
				application.getEmployeeID(), targetDate, closureId);
		if (confirmClsStatus == ConfirmClsStatus.Confirm) {
			reflectStatus.setReasonNotReflectWorkSchedule(ReasonNotReflect.TIGHTENING_PROCESS_COMPLETED);
			return new PreCheckProcessResult(NotUseAtr.NOT_USE, reflectStatus);
		}

		return new PreCheckProcessResult(NotUseAtr.USE, reflectStatus);
	}

	public static interface Require extends CheckProcessDuringLock.Require, CheckAchievementConfirmation.Require {

		// ScBasicScheduleAdapter
		public List<BasicScheduleConfirmImport> findConfirmById(List<String> employeeID, DatePeriod date);

		// IdentificationAdapter
		public List<GeneralDate> getProcessingYMD(String companyID, String employeeID, DatePeriod period);

	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public static class PreCheckProcessResult {

		// 処理フラグ（する/しない）
		private NotUseAtr processFlag;

		// 反映状態
		private ReflectStatusResult reflectStatus;
	}
}
