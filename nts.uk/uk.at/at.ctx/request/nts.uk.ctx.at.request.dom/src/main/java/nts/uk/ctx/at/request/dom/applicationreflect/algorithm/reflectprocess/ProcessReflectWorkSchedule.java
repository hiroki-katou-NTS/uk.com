package nts.uk.ctx.at.request.dom.applicationreflect.algorithm.reflectprocess;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.enums.EnumAdaptor;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflectDaily;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.reflect.convert.ConvertApplicationToShare;
import nts.uk.ctx.at.request.dom.applicationreflect.algorithm.checkprocess.PreCheckProcessWorkSchedule;
import nts.uk.ctx.at.request.dom.applicationreflect.algorithm.checkprocess.PreCheckProcessWorkSchedule.PreCheckProcessResult;
import nts.uk.ctx.at.request.dom.applicationreflect.object.AppReflectExecCond;
import nts.uk.ctx.at.request.dom.applicationreflect.object.PreApplicationWorkScheReflectAttr;
import nts.uk.ctx.at.request.dom.applicationreflect.object.ReflectStatusResult;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule.ExecutionType;
import nts.uk.ctx.at.request.dom.setting.company.request.appreflect.AppReflectionSetting;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.application.common.ReasonNotReflectDailyShare;
import nts.uk.ctx.at.shared.dom.application.common.ReasonNotReflectShare;
import nts.uk.ctx.at.shared.dom.application.common.ReflectedStateShare;
import nts.uk.ctx.at.shared.dom.application.reflect.ReflectStatusResultShare;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         勤務予定への反映処理
 */
public class ProcessReflectWorkSchedule {

	public static Pair<ReflectStatusResult, Optional<AtomTask>> processReflect(Require require, String companyId,
			int closureId, Application application, ExecutionType execType, boolean isCalWhenLock,
			GeneralDate targetDate, ReflectStatusResult statusWorkSchedule) {

		// [input. 処理中の申請. 事前事後区分]をチェック
		if (application.getPrePostAtr() == PrePostAtr.POSTERIOR) {
			// 勤務予定の反映状態を「反映済み」にする
			statusWorkSchedule.setReflectStatus(ReflectedState.REFLECTED);
			return Pair.of(statusWorkSchedule, Optional.empty());
		}

		// [申請反映実行条件]を取得する
		Optional<AppReflectExecCond> appReFlectExec = require.findAppReflectExecCond(companyId);
		
		/** [事前申請を勤務予定に反映する]をチェック */
		if (!appReFlectExec.isPresent() || appReFlectExec.get().getPreAppSchedule() == PreApplicationWorkScheReflectAttr.NOT_REFLECT) {
			statusWorkSchedule.setReflectStatus(ReflectedState.REFLECTED);
			return Pair.of(statusWorkSchedule, Optional.empty());
		}

		// [勤務予定が確定状態でも反映する]をチェック
		if (appReFlectExec.get().getScheduleConfirm() == NotUseAtr.NOT_USE) {
			// 事前チェック処理
			PreCheckProcessResult preCheckProcessResult = PreCheckProcessWorkSchedule.preCheck(require, companyId,
					application, closureId, isCalWhenLock, statusWorkSchedule, targetDate);
			if (preCheckProcessResult.getProcessFlag() == NotUseAtr.NOT_USE)
				return Pair.of(statusWorkSchedule, Optional.empty());
		}

		// 勤務予定に反映
		Pair<Object, AtomTask> result = require.process(ConvertApplicationToShare.toAppliction(application), targetDate,
				new ReflectStatusResultShare(ReflectedStateShare.valueOf(statusWorkSchedule.getReflectStatus().value),
						ReasonNotReflectDailyShare.valueOf(statusWorkSchedule.getReasonNotReflectWorkRecord().value),
						ReasonNotReflectShare.valueOf(statusWorkSchedule.getReasonNotReflectWorkSchedule().value)),
				appReFlectExec.get().getPreAppSchedule().value);
		return Pair.of(statusResult((ReflectStatusResultShare) result.getLeft()), Optional.of(result.getRight()));

	}

	public static ReflectStatusResult statusResult(ReflectStatusResultShare share) {
		return new ReflectStatusResult(EnumAdaptor.valueOf(share.getReflectStatus().value, ReflectedState.class),
				share.getReasonNotReflectWorkRecord() == null ? null
						: EnumAdaptor.valueOf(share.getReasonNotReflectWorkRecord().value, ReasonNotReflectDaily.class),
				share.getReasonNotReflectWorkSchedule() == null ? null
						: EnumAdaptor.valueOf(share.getReasonNotReflectWorkSchedule().value, ReasonNotReflect.class));
	}

	public static interface Require extends PreCheckProcessWorkSchedule.Require {

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

		// ReflectApplicationWorkScheduleAdapter
		public Pair<Object, AtomTask> process(ApplicationShare application, GeneralDate date,
				ReflectStatusResultShare reflectStatus, int preAppWorkScheReflectAttr);

	}

}
