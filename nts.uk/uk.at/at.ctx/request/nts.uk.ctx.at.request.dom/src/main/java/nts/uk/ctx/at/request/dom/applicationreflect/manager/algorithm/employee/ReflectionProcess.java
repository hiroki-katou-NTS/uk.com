package nts.uk.ctx.at.request.dom.applicationreflect.manager.algorithm.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.applicationreflect.algorithm.reflectprocess.ProcessReflectWorkRecord;
import nts.uk.ctx.at.request.dom.applicationreflect.algorithm.reflectprocess.ProcessReflectWorkSchedule;
import nts.uk.ctx.at.request.dom.applicationreflect.object.AppReflectExecCond;
import nts.uk.ctx.at.request.dom.applicationreflect.object.OneDayReflectStatusOutput;
import nts.uk.ctx.at.request.dom.applicationreflect.object.ReflectStatusResult;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule.ExecutionType;
import nts.uk.ctx.at.request.dom.setting.company.request.appreflect.AppReflectionSetting;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.worktime.empwork.EmployeeWorkDataSetting;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;

/**
 * @author thanh_nx
 *
 *         反映処理
 */
public class ReflectionProcess {

	// 反映処理
	public static Pair<Optional<OneDayReflectStatusOutput>, Optional<AtomTask>> process(Require require,
			String companyId, Application application, ExecutionType execType, boolean isCalWhenLock,
			GeneralDate targetDate, SEmpHistImport empHist) {

		// 対象日の反映状態を<output>1日の反映状態にセット
		ReflectionStatusOfDay reflectionStatusOfDay = application.getReflectionStatus().getListReflectionStatusOfDay()
				.stream().filter(x -> x.getTargetDate().equals(targetDate)).findFirst().orElse(null);
		if (reflectionStatusOfDay == null)
			return Pair.of(Optional.empty(), Optional.empty());

		OneDayReflectStatusOutput result = new OneDayReflectStatusOutput();
		result.setReflectStatusAll(reflectionStatusOfDay.getActualReflectStatus(),
				reflectionStatusOfDay.getScheReflectStatus());

		// 締めIDを取得する
		Optional<ClosureEmployment> closureEmpOpt = require.findByEmploymentCD(companyId, empHist.getEmploymentCode());

		if (!closureEmpOpt.isPresent())
			return Pair.of(Optional.empty(), Optional.empty());
		List<AtomTask> tasks = new ArrayList<>();
		// 勤務予定への反映処理-- in processing
		Pair<ReflectStatusResult, Optional<AtomTask>> reflectSchedule = ProcessReflectWorkSchedule.processReflect(
				require, companyId, closureEmpOpt.get().getClosureId(), application, execType, isCalWhenLock,
				targetDate, result.getStatusWorkSchedule());
		result.setStatusWorkSchedule(reflectSchedule.getLeft());
		reflectSchedule.getRight().ifPresent(x -> tasks.add(x));

		// 勤務実績への反映処理-- in processing
		Pair<ReflectStatusResult, Optional<AtomTask>> resultRecord = ProcessReflectWorkRecord.processReflect(require,
				companyId, closureEmpOpt.get().getClosureId(), application, execType, isCalWhenLock, targetDate,
				result.getStatusWorkRecord());
		result.setStatusWorkRecord(resultRecord.getLeft());
		resultRecord.getRight().ifPresent(x -> tasks.add(x));

		// 1日の反映状態を返す
		return Pair.of(Optional.of(result), Optional.of(AtomTask.bundle(tasks)));

	}

	public static interface Require extends ProcessReflectWorkSchedule.Require, ProcessReflectWorkRecord.Require {

		/**
		 * require{ 申請反映実行条件を取得する(会社ID) ｝
		 */
		public Optional<AppReflectExecCond> findAppReflectExecCond(String companyId);

		/**
		 * 
		 * require{ 申請反映設定を取得する(会社ID、申請種類） }
		 * RequestSettingRepository.getAppReflectionSetting
		 */
		public Optional<AppReflectionSetting> getAppReflectionSetting(String companyId, ApplicationType appType);

		/**
		 * 
		 * require{ 社員の作業データ設定を取得する(社員ID） }
		 * 
		 */
		public Optional<EmployeeWorkDataSetting> getEmpWorkDataSetting(String employeeId);

		// ClosureEmploymentRepository
		public Optional<ClosureEmployment> findByEmploymentCD(String companyID, String employmentCD);
	}
}
