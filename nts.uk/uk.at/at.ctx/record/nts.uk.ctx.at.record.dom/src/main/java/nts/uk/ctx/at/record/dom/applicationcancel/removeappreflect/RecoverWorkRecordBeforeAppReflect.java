package nts.uk.ctx.at.record.dom.applicationcancel.removeappreflect;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.request.application.state.RCReflectStatusResult;
import nts.uk.ctx.at.record.dom.adapter.request.application.state.RCReflectedState;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.cancelreflectapp.CancellationOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         [RQ669]勤務実績を申請反映前に戻す
 */
public class RecoverWorkRecordBeforeAppReflect {

	public static RCRecoverAppReflectOutput process(Require require, ApplicationShare application, GeneralDate date,
			RCReflectStatusResult reflectStatus, NotUseAtr dbRegisterClassfi) {
		// 勤務実績から日別実績(work）を取得する
		IntegrationOfDaily domainDaily = require.findDaily(application.getEmployeeID(), date).orElse(null);
		if (domainDaily == null) {
			reflectStatus.setReflectStatus(RCReflectedState.CANCELED);
			return new RCRecoverAppReflectOutput(reflectStatus, Optional.empty(), AtomTask.none());
		}
		// 申請の取消
		DailyAfterAppReflectResult cancellationResult = CancellationOfApplication.process(require, application, date,
				ScheduleRecordClassifi.RECORD, domainDaily);
		domainDaily = cancellationResult.getDomainDaily().getDomain();

//		// 労働条件項目を取得
//		Optional<WorkingConditionItem> workCondOpt = WorkingConditionService.findWorkConditionByEmployee(require,
//				domainDaily.getEmployeeId(), domainDaily.getYmd());

		// 変更された項目を確認
		ChangeDailyAttendance changeAtt = ChangeDailyAttendance.createChangeDailyAtt(cancellationResult.getLstItemId(), ScheduleRecordClassifi.RECORD);

		// 勤怠変更後の補正（日別実績の補正処理）
		domainDaily = require.correct(domainDaily, changeAtt);

		// 日別実績の修正からの計算
		List<IntegrationOfDaily> lstAfterCalc = require.calculateForRecord(Arrays.asList(domainDaily),
				ExecutionType.NORMAL_EXECUTION);
		if (!lstAfterCalc.isEmpty()) {
			domainDaily = lstAfterCalc.get(0);
		}

		// 日別実績に変換する
		IntegrationOfDaily domainDailyUpdate = domainDaily;

		AtomTask atomTask = AtomTask.none();
		// [input.DB登録するか区分]をチェック
		if (dbRegisterClassfi == NotUseAtr.USE) {
			atomTask = AtomTask.of(() -> {
				// 勤務実績のDB更新
				require.addAllDomain(domainDailyUpdate);

				// 申請反映履歴の取消区分を更新する
				require.updateAppReflectHist(application.getEmployeeID(), application.getAppID(), date,
						ScheduleRecordClassifi.RECORD, true);
			});
		}
		// 反映状態を「取消済み」に更新する
		reflectStatus.setReflectStatus(RCReflectedState.CANCELED);
		return new RCRecoverAppReflectOutput(reflectStatus, Optional.of(domainDailyUpdate), atomTask);
	}

	public static interface Require extends CancellationOfApplication.Require{

		// DailyRecordShareFinder
		public Optional<IntegrationOfDaily> findDaily(String employeeId, GeneralDate date);

		// ICorrectionAttendanceRule
		public IntegrationOfDaily correct(IntegrationOfDaily domainDaily, ChangeDailyAttendance changeAtt);

		// CalculateDailyRecordServiceCenter
		public List<IntegrationOfDaily> calculateForRecord(List<IntegrationOfDaily> integrationOfDaily,
				ExecutionType reCalcAtr);

		// DailyRecordAdUpService
		public void addAllDomain(IntegrationOfDaily domain);

		public void updateAppReflectHist(String sid, String appId, GeneralDate baseDate,
				ScheduleRecordClassifi classification, boolean flagRemove);

	}
}
