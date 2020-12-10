package nts.uk.ctx.at.request.dom.applicationreflect.algorithm.reflectprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.applicationreflect.algorithm.checkprocess.PreCheckProcessWorkRecord;
import nts.uk.ctx.at.request.dom.applicationreflect.algorithm.checkprocess.PreCheckProcessWorkSchedule.PreCheckProcessResult;
import nts.uk.ctx.at.request.dom.applicationreflect.object.AppReflectExecCond;
import nts.uk.ctx.at.request.dom.applicationreflect.object.ReflectStatusResult;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule.ExecutionType;
import nts.uk.ctx.at.shared.dom.application.common.ReasonNotReflectDailyShare;
import nts.uk.ctx.at.shared.dom.application.common.ReasonNotReflectShare;
import nts.uk.ctx.at.shared.dom.application.common.ReflectedStateShare;
import nts.uk.ctx.at.shared.dom.application.reflect.ReflectStatusResultShare;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.worktime.empwork.EmployeeWorkDataSetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         勤務実績への反映処理
 */
public class ProcessReflectWorkRecord {

	public static Pair<ReflectStatusResult, Optional<AtomTask>> processReflect(Require require, String companyId,
			int closureId, Application application, ExecutionType execType, boolean isCalWhenLock,
			GeneralDate targetDate, ReflectStatusResult statusWorkRecord) {

		// [申請反映実行条件]を取得する
		Optional<AppReflectExecCond> appReFlectExec = require.findAppReflectExecCond(companyId);

		// [勤務実績が確定状態でも反映する]をチェック
		if (!appReFlectExec.isPresent() || appReFlectExec.get().getWorkRecordConfirm() == NotUseAtr.NOT_USE) {
			// 事前チェック処理
			PreCheckProcessResult preCheckResult = PreCheckProcessWorkRecord.preCheck(require, companyId, application,
					closureId, isCalWhenLock, statusWorkRecord, targetDate);
			if (preCheckResult.getProcessFlag() == NotUseAtr.NOT_USE) {
				return Pair.of(preCheckResult.getReflectStatus(), Optional.empty());
			}
		}

		List<AtomTask> tasks = new ArrayList<>();
		// 勤務実績に反映 -- in process
		ReflectStatusResultShare reflectShare = new ReflectStatusResultShare(ReflectedStateShare.valueOf(statusWorkRecord.getReflectStatus().value),
				statusWorkRecord.getReasonNotReflectWorkRecord() == null ? null
						: ReasonNotReflectDailyShare
								.valueOf(statusWorkRecord.getReasonNotReflectWorkRecord().value),
				statusWorkRecord.getReasonNotReflectWorkSchedule() == null ? null
						: ReasonNotReflectShare
								.valueOf(statusWorkRecord.getReasonNotReflectWorkSchedule().value));
		Pair<ReflectStatusResultShare, Optional<AtomTask>> result = require.process(application, targetDate, reflectShare);
		result.getRight().ifPresent(x -> tasks.add(x));
		// 申請理由の反映-- in process chua co don xin lam them
		Optional<AtomTask> task = ReflectApplicationReason.reflectReason(require, application, targetDate);
		task.ifPresent(x -> tasks.add(x));

		return Pair.of(ProcessReflectWorkSchedule.statusResult(result.getLeft()), Optional.of(AtomTask.bundle(tasks)));
	}

	public static interface Require extends PreCheckProcessWorkRecord.Require, ReflectApplicationReason.Require {

		/**
		 * require{ 申請反映実行条件を取得する(会社ID) ｝
		 */
		public Optional<AppReflectExecCond> findAppReflectExecCond(String companyId);

		/**
		 * 
		 * require{ 社員の作業データ設定を取得する(社員ID） }
		 * 
		 */
		public Optional<EmployeeWorkDataSetting> getEmpWorkDataSetting(String employeeId);

		// ReflectApplicationWorkRecordAdapter
		public Pair<ReflectStatusResultShare, Optional<AtomTask>> process(Application application, GeneralDate date,
				ReflectStatusResultShare reflectStatus);
	}
}
