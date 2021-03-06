package nts.uk.ctx.at.schedule.dom.appreflectprocess.change;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.schedule.dom.adapter.appreflect.SCAppReflectionSetting;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.change.state.SCReflectStatusResult;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.change.state.SCReflectedState;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ConfirmedATR;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.snapshot.DailySnapshotWork;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ReflectedStateShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.cancellation.CreateApplicationReflectionHist;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.CorrectDailyAttendanceService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.CorrectionAttendanceRule;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyprocess.calc.CalculateOption;

/**
 * @author thanh_nx
 *
 *         [RQ666]????????????????????????????????????
 */
public class ReflectApplicationWorkSchedule {

	public static Pair<SCReflectStatusResult, AtomTask> process(Require require, String companyId,  ApplicationShare application,
			GeneralDate date, SCReflectStatusResult reflectStatus, int preAppWorkScheReflectAttr, String execId) {
		// ??????????????????????????????(work??????????????????
		WorkSchedule workSchedule = require.get(application.getEmployeeID(), date).orElse(null);
		if (workSchedule == null)
			return Pair.of(reflectStatus, AtomTask.of(() -> {}));

		/** ?????????????????????????????????????????? */
		if(preAppWorkScheReflectAttr == 0) { /** [???????????????] */
			reflectStatus.setReflectStatus(SCReflectedState.REFLECTED);
			return Pair.of(reflectStatus, AtomTask.of(() -> {}));
		}
		Optional<SnapShot> snapshot = Optional.empty();
		
		if (preAppWorkScheReflectAttr == 2) { /** [??????????????????????????????????????????????????????????????????] */
			
			/** ??????????????????????????????????????? */
			val dailySnapshot = require.snapshot(application.getEmployeeID(), date);
			if (!dailySnapshot.isPresent()) {
				
				return Pair.of(reflectStatus, AtomTask.of(() -> {}));
			}
			
			snapshot = dailySnapshot.map(c -> c.getSnapshot());
		}

		// input.????????????(work??????[????????????????????????(work)]??????????????????????????????
		IntegrationOfDaily domainDaily = new IntegrationOfDaily(workSchedule.getEmployeeID(), workSchedule.getYmd(),
				workSchedule.getWorkInfo(), CalAttrOfDailyAttd.createAllCalculate(), workSchedule.getAffInfo(), Optional.empty(), new ArrayList<>(),
				workSchedule.getOutingTime(), workSchedule.getLstBreakTime(), workSchedule.getOptAttendanceTime(),
				workSchedule.getOptTimeLeaving(), workSchedule.getOptSortTimeWork(), Optional.empty(), Optional.empty(),
				Optional.empty(), workSchedule.getLstEditState(), Optional.empty(), new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), snapshot);

		IntegrationOfDaily domainBeforeReflect = createDailyDomain(require, domainDaily);

		// ????????????(???????????????Work?????????????????????????????????(work?????????????????????
		DailyRecordOfApplication dailyRecordApp = new DailyRecordOfApplication(new ArrayList<>(),
				ScheduleRecordClassifi.SCHEDULE, createDailyDomain(require, domainDaily));

		// TODO: ?????????????????????????????????in processing reflect all application
		DailyAfterAppReflectResult affterReflect = SCCreateDailyAfterApplicationeReflect.process(require, companyId, application,
				dailyRecordApp, date);
		dailyRecordApp.setDomain(affterReflect.getDomainDaily());

		// ???????????????????????????
		ChangeDailyAttendance changeAtt = ChangeDailyAttendance.createChangeDailyAtt(affterReflect.getLstItemId(), ScheduleRecordClassifi.SCHEDULE);
		IntegrationOfDaily domainCorrect = CorrectionAttendanceRule.process(require, companyId,
				dailyRecordApp, changeAtt);
		if (domainCorrect instanceof DailyRecordOfApplication) {
			dailyRecordApp.setAttendanceBeforeReflect(
					((DailyRecordOfApplication) domainCorrect).getAttendanceBeforeReflect());
		}

		// ????????????????????????????????????????????????
		WorkInfoOfDailyAttendance workInfoAfter = CorrectDailyAttendanceService.correctFurikyu(require, companyId,
				domainBeforeReflect.getWorkInformation(), domainCorrect.getWorkInformation());
		domainCorrect.setWorkInformation(workInfoAfter);
		dailyRecordApp.setDomain(domainCorrect);

		// ????????????????????????????????????
		List<IntegrationOfDaily> lstAfterCalc = require.calculateForSchedule(CalculateOption.asDefault(),
				Arrays.asList(domainCorrect));
		if (!lstAfterCalc.isEmpty()) {
			dailyRecordApp.setDomain(lstAfterCalc.get(0));
		}
		
		ReflectedStateShare before = EnumAdaptor.valueOf(reflectStatus.getReflectStatus().value, ReflectedStateShare.class);
		AtomTask atomTask = AtomTask.of(() -> {
			// ????????????????????? --- co update , thuoc tinh ConfirmedATR
			WorkSchedule workScheduleReflect = new WorkSchedule(dailyRecordApp.getEmployeeId(), dailyRecordApp.getYmd(),
					ConfirmedATR.UNSETTLED, dailyRecordApp.getWorkInformation(), dailyRecordApp.getAffiliationInfor(),
					dailyRecordApp.getBreakTime(), dailyRecordApp.getEditState(), workSchedule.getTaskSchedule(), workSchedule.getSupportSchedule(),
					dailyRecordApp.getAttendanceLeave(), dailyRecordApp.getAttendanceTimeOfDailyPerformance(),
					dailyRecordApp.getShortTime(), dailyRecordApp.getOutingTime());
			require.insertSchedule(workScheduleReflect);

			// ?????????????????????????????????
			CreateApplicationReflectionHist.create(require, application.getAppID(), ScheduleRecordClassifi.SCHEDULE,
					dailyRecordApp, domainBeforeReflect, GeneralDateTime.now(), execId, before);
		});

		reflectStatus.setReflectStatus(SCReflectedState.REFLECTED);

		return Pair.of(reflectStatus, atomTask);

	}

	private static IntegrationOfDaily createDailyDomain(Require require, IntegrationOfDaily domainDaily) {
		DailyRecordToAttendanceItemConverter converter = require.createDailyConverter();
		converter.setData(domainDaily).employeeId(domainDaily.getEmployeeId()).workingDate(domainDaily.getYmd());
		return converter.toDomain();
	}

	public static interface Require extends CorrectDailyAttendanceService.Require,
			SCCreateDailyAfterApplicationeReflect.Require, CreateApplicationReflectionHist.Require, CorrectionAttendanceRule.Require {

		Optional<DailySnapshotWork> snapshot(String sid, GeneralDate ymd);
		
		/**
		 * 
		 * require{ ?????????????????????????????????(??????ID?????????????????? }
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
