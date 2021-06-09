package nts.uk.ctx.at.schedule.dom.appremove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.change.state.SCReflectStatusResult;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.change.state.SCReflectedState;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.cancelreflectapp.CancellationOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         [RQ670]勤務予定を申請反映前に戻す
 */
public class RecoverWorkScheduleBeforeAppReflect {

	public static SCRecoverAppReflectOutput process(Require require, ApplicationShare application, GeneralDate date,
			SCReflectStatusResult reflectStatus, NotUseAtr dbRegisterClassfi) {

		// 勤務予定から日別実績(work）を取得する
		WorkSchedule workSchedule = require.get(application.getEmployeeID(), date).orElse(null);
		if (workSchedule == null)
			return new SCRecoverAppReflectOutput(reflectStatus, Optional.empty(), AtomTask.none());
		IntegrationOfDaily domainDaily = new IntegrationOfDaily(workSchedule.getEmployeeID(), workSchedule.getYmd(),
				workSchedule.getWorkInfo(), CalAttrOfDailyAttd.createAllCalculate(), workSchedule.getAffInfo(),
				Optional.empty(), new ArrayList<>(), Optional.empty(), workSchedule.getLstBreakTime(),
				workSchedule.getOptAttendanceTime(), workSchedule.getOptTimeLeaving(),
				workSchedule.getOptSortTimeWork(), Optional.empty(), Optional.empty(), Optional.empty(),
				workSchedule.getLstEditState(), Optional.empty(), new ArrayList<>(), Optional.empty());

		// 申請取消の処理
		DailyAfterAppReflectResult cancellationResult = CancellationOfApplication.process(require, application, date,
				ScheduleRecordClassifi.SCHEDULE, domainDaily);
		domainDaily = cancellationResult.getDomainDaily().getDomain();

		// 労働条件項目を取得
		Optional<WorkingConditionItem> workCondOpt = WorkingConditionService.findWorkConditionByEmployee(require,
				domainDaily.getEmployeeId(), domainDaily.getYmd());

		// 変更された項目を確認
		ChangeDailyAttendance changeAtt = createChangeDailyAtt(cancellationResult.getLstItemId());

		// 勤怠変更後の補正（日別実績の補正処理）
		domainDaily = require.corectionAfterTimeChange(domainDaily, changeAtt, workCondOpt).getRight();

		// 日別実績の修正からの計算
		List<IntegrationOfDaily> lstAfterCalc = require.calculateForSchedule(ExecutionType.NORMAL_EXECUTION,
				Arrays.asList(domainDaily));
		if (!lstAfterCalc.isEmpty()) {
			domainDaily = lstAfterCalc.get(0);
		}

		// 勤務予定(work）に変換する
		WorkSchedule workScheduleReflect = new WorkSchedule(domainDaily.getEmployeeId(), domainDaily.getYmd(),
				workSchedule.getConfirmedATR(), domainDaily.getWorkInformation(), domainDaily.getAffiliationInfor(),
				domainDaily.getBreakTime(), domainDaily.getEditState(), 
				workSchedule.getTaskSchedule(),
				domainDaily.getAttendanceLeave(),
				domainDaily.getAttendanceTimeOfDailyPerformance(), domainDaily.getShortTime(),
				domainDaily.getOutingTime());

		AtomTask atomTask = AtomTask.none();
		// [input.DB登録するか区分]をチェック
		if (dbRegisterClassfi == NotUseAtr.USE) {
			atomTask = AtomTask.of(() -> {
				// 勤務予定のDB更新
				require.insertSchedule(workScheduleReflect);

				// 申請反映履歴の取消区分を更新する
				require.updateAppReflectHist(application.getEmployeeID(), application.getAppID(), date,
						ScheduleRecordClassifi.SCHEDULE, true);
			});
		}
		// 反映状態を「取消済み」に更新する
		reflectStatus.setReflectStatus(SCReflectedState.CANCELED);
		return new SCRecoverAppReflectOutput(reflectStatus, Optional.of(domainDaily), atomTask);
	}

	private static ChangeDailyAttendance createChangeDailyAtt(List<Integer> lstItemId) {

		boolean workInfo = lstItemId.stream().filter(x -> x.intValue() == 28 || x.intValue() == 29).findFirst()
				.isPresent();
		boolean attendance = lstItemId.stream()
				.filter(x -> x.intValue() == 31 || x.intValue() == 34 || x.intValue() == 41 || x.intValue() == 44)
				.findFirst().isPresent();
		boolean directBounceClassifi = lstItemId.stream()
				.filter(x -> x.intValue() == 859 || x.intValue() == 860)
				.findFirst().isPresent();
		return new ChangeDailyAttendance(workInfo, attendance, false, workInfo, ScheduleRecordClassifi.SCHEDULE, directBounceClassifi);
	}

	public static interface Require extends WorkingConditionService.RequireM1, CancellationOfApplication.Require {

		// WorkScheduleRepository
		public Optional<WorkSchedule> get(String employeeID, GeneralDate ymd);

		// CalculateDailyRecordServiceCenterNew
		public List<IntegrationOfDaily> calculateForSchedule(ExecutionType type,
				List<IntegrationOfDaily> integrationOfDaily);

		// WorkScheduleRepository
		public void insertSchedule(WorkSchedule workSchedule);

		public void updateAppReflectHist(String sid, String appId, GeneralDate baseDate,
				ScheduleRecordClassifi classification, boolean flagRemove);

		// CorrectionAfterTimeChange
		public Pair<ChangeDailyAttendance, IntegrationOfDaily> corectionAfterTimeChange(IntegrationOfDaily domainDaily,
				ChangeDailyAttendance changeAtt, Optional<WorkingConditionItem> workCondOpt);
	}
}
