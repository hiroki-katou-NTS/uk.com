package nts.uk.ctx.at.schedule.dom.appreflectprocess.change;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.appreflect.SCAppReflectionSetting;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ConfirmedATR;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.snapshot.DailySnapshotWork;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.application.common.ReflectedStateShare;
import nts.uk.ctx.at.shared.dom.application.reflect.ReflectStatusResultShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.cancellation.CreateApplicationReflectionHist;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.CorrectDailyAttendanceService;
import nts.uk.ctx.at.shared.dom.dailyprocess.calc.CalculateOption;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

/**
 * @author thanh_nx
 *
 *         [RQ666]申請を勤務予定へ反映する
 */
public class ReflectApplicationWorkSchedule {

	public static Pair<ReflectStatusResultShare, AtomTask> process(Require require, String companyId, ApplicationShare application,
			GeneralDate date, ReflectStatusResultShare reflectStatus, int preAppWorkScheReflectAttr) {
		// 勤務予定から日別実績(work）を取得する
		WorkSchedule workSchedule = require.get(application.getEmployeeID(), date).orElse(null);
		if (workSchedule == null)
			return Pair.of(reflectStatus, AtomTask.of(() -> {}));

		/** 事前申請を勤務予定に反映する */
		if(preAppWorkScheReflectAttr == 0) { /** [反映しない] */
			
			return Pair.of(reflectStatus, AtomTask.of(() -> {}));
		}
		Optional<SnapShot> snapshot = Optional.empty();
		
		if (preAppWorkScheReflectAttr == 2) { /** [反映しないがスナップショット作成後は反映する] */
			
			/** スナップショットを取得する */
			val dailySnapshot = require.snapshot(application.getEmployeeID(), date);
			if (!dailySnapshot.isPresent()) {
				
				return Pair.of(reflectStatus, AtomTask.of(() -> {}));
			}
			
			snapshot = dailySnapshot.map(c -> c.getSnapshot());
		}

		// input.日別勤怠(work）を[反映前の日別勤怠(work)]へコピーして保持する
		IntegrationOfDaily domainDaily = new IntegrationOfDaily(workSchedule.getEmployeeID(), workSchedule.getYmd(),
				workSchedule.getWorkInfo(), null, workSchedule.getAffInfo(), Optional.empty(), new ArrayList<>(),
				Optional.empty(), workSchedule.getLstBreakTime(), workSchedule.getOptAttendanceTime(),
				workSchedule.getOptTimeLeaving(), workSchedule.getOptSortTimeWork(), Optional.empty(), Optional.empty(),
				Optional.empty(), workSchedule.getLstEditState(), Optional.empty(), new ArrayList<>(), snapshot);

		IntegrationOfDaily domainBeforeReflect = createDailyDomain(require, domainDaily);

		// 日別勤怠(申請取消用Work）を作成して、日別勤怠(work）をコピーする
		DailyRecordOfApplication dailyRecordApp = new DailyRecordOfApplication(new ArrayList<>(),
				ScheduleRecordClassifi.SCHEDULE, createDailyDomain(require, domainDaily));

		// TODO: 申請の反映（勤務予定）in processing reflect all application
		DailyAfterAppReflectResult affterReflect = SCCreateDailyAfterApplicationeReflect.process(require, companyId, application,
				dailyRecordApp, date);
		dailyRecordApp.setDomain(affterReflect.getDomainDaily());

		// 日別実績の補正処理
		ChangeDailyAttendance changeAtt = createChangeDailyAtt(affterReflect.getLstItemId());
		IntegrationOfDaily domainCorrect = CorrectDailyAttendanceService.processAttendanceRule(require,
				dailyRecordApp.getDomain(), changeAtt);

		// 振休振出として扱う日数を補正する
		WorkInfoOfDailyAttendance workInfoAfter = CorrectDailyAttendanceService.correctFurikyu(require,
				domainBeforeReflect.getWorkInformation(), domainCorrect.getWorkInformation());
		domainCorrect.setWorkInformation(workInfoAfter);
		dailyRecordApp.setDomain(domainCorrect);

		// 日別実績の修正からの計算
		List<IntegrationOfDaily> lstAfterCalc = require.calculateForSchedule(CalculateOption.asDefault(),
				Arrays.asList(domainCorrect));
		if (!lstAfterCalc.isEmpty()) {
			dailyRecordApp.setDomain(lstAfterCalc.get(0));
		}
		
		AtomTask atomTask = AtomTask.of(() -> {
			// 勤務予定の更新 --- co update , thuoc tinh ConfirmedATR
			WorkSchedule workScheduleReflect = new WorkSchedule(dailyRecordApp.getEmployeeId(), dailyRecordApp.getYmd(),
					ConfirmedATR.UNSETTLED, dailyRecordApp.getWorkInformation(), dailyRecordApp.getAffiliationInfor(),
					dailyRecordApp.getBreakTime(), dailyRecordApp.getEditState(), dailyRecordApp.getAttendanceLeave(),
					dailyRecordApp.getAttendanceTimeOfDailyPerformance(), dailyRecordApp.getShortTime());
			require.insertSchedule(workScheduleReflect);

			// 申請反映履歴を作成する
			CreateApplicationReflectionHist.create(require, application.getAppID(), ScheduleRecordClassifi.SCHEDULE,
					dailyRecordApp, domainBeforeReflect);
		});

		reflectStatus.setReflectStatus(ReflectedStateShare.REFLECTED);

		return Pair.of(reflectStatus, atomTask);

	}

	private static IntegrationOfDaily createDailyDomain(Require require, IntegrationOfDaily domainDaily) {
		DailyRecordToAttendanceItemConverter converter = require.createDailyConverter();
		converter.setData(domainDaily).employeeId(domainDaily.getEmployeeId()).workingDate(domainDaily.getYmd());
		return converter.toDomain();
	}

	private static ChangeDailyAttendance createChangeDailyAtt(List<Integer> lstItemId) {

		boolean workInfo = lstItemId.stream().filter(x -> x.intValue() == 28 || x.intValue() == 29).findFirst()
				.isPresent();
		boolean scheduleWorkInfo = lstItemId.stream().filter(x -> x.intValue() == 1 || x.intValue() == 2).findFirst()
				.isPresent();
		boolean attendance = lstItemId.stream()
				.filter(x -> x.intValue() == 31 || x.intValue() == 34 || x.intValue() == 41 || x.intValue() == 44)
				.findFirst().isPresent();
		return new ChangeDailyAttendance(workInfo, scheduleWorkInfo, attendance, false);
	}

	public static interface Require extends CorrectDailyAttendanceService.Require,
			SCCreateDailyAfterApplicationeReflect.Require, CreateApplicationReflectionHist.Require {

		Optional<DailySnapshotWork> snapshot(String sid, GeneralDate ymd);
		
		/**
		 * 
		 * require{ 申請反映設定を取得する(会社ID、申請種類） }
		 * RequestSettingRepository.getAppReflectionSetting
		 */
		public Optional<SCAppReflectionSetting> getAppReflectionSettingSc(String companyId, ApplicationTypeShare appType);

		// WorkScheduleRepository
		public Optional<WorkSchedule> get(String employeeID, GeneralDate ymd);

		// ConvertDailyRecordToAd
		public DailyRecordToAttendanceItemConverter createDailyConverter();

		// CorrectionAttendanceRule
		public IntegrationOfDaily process(IntegrationOfDaily domainDaily, ChangeDailyAttendance changeAtt);

		// WorkScheduleRepository
		public void insertSchedule(WorkSchedule workSchedule);

		// CalculateDailyRecordServiceCenterNew
		public List<IntegrationOfDaily> calculateForSchedule(CalculateOption calcOption,
				List<IntegrationOfDaily> integrationOfDaily);

	}
}
