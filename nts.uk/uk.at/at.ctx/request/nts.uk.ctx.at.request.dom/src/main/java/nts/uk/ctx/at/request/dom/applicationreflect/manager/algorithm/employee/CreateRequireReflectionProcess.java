package nts.uk.ctx.at.request.dom.applicationreflect.manager.algorithm.employee;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SWkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock.DetermineActualResultLockAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock.DetermineActualResultLockAdapter.LockStatus;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock.DetermineActualResultLockAdapter.PerformanceType;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.reflect.CreateEditStatusHistAppReasonAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.reflect.ReflectApplicationWorkRecordAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.workfixed.WorkFixedAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.workrecord.identificationstatus.IdentificationAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.reflect.ReflectApplicationWorkScheduleAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.BasicScheduleConfirmImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleAdapter;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.applicationreflect.object.AppReflectExecCond;
import nts.uk.ctx.at.request.dom.reasonappdaily.ReasonApplicationDailyResult;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.appreflect.AppReflectionSetting;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.application.reflect.ReflectStatusResultShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.worktime.empwork.EmployeeWorkDataSetting;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;

@Stateless
public class CreateRequireReflectionProcess {

	@Inject
	private ReflectApplicationWorkScheduleAdapter eflectApplicationWorkScheduleAdapter;

	@Inject
	private ScBasicScheduleAdapter scBasicScheduleAdapter;

	@Inject
	private IdentificationAdapter identificationAdapter;

	@Inject
	private DetermineActualResultLockAdapter determineActualResultLockAdapter;

	@Inject
	private EmployeeRequestAdapter employeeRequestAdapter;

	@Inject
	private WorkFixedAdapter workFixedAdapter;

	@Inject
	private ReflectApplicationWorkRecordAdapter reflectApplicationWorkRecordAdapter;

	@Inject
	private CreateEditStatusHistAppReasonAdapter createEditStatusHistAppReasonAdapter;

	@Inject
	private RequestSettingRepository requestSettingRepository;

	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;

	public RequireImpl createImpl() {
		return new RequireImpl(eflectApplicationWorkScheduleAdapter, scBasicScheduleAdapter, identificationAdapter,
				determineActualResultLockAdapter, employeeRequestAdapter, workFixedAdapter,
				reflectApplicationWorkRecordAdapter, createEditStatusHistAppReasonAdapter, requestSettingRepository,
				closureEmploymentRepository);
	}

	@AllArgsConstructor
	public class RequireImpl implements ReflectionProcess.Require {

		private final ReflectApplicationWorkScheduleAdapter eflectApplicationWorkScheduleAdapter;

		private final ScBasicScheduleAdapter scBasicScheduleAdapter;

		private final IdentificationAdapter identificationAdapter;

		private final DetermineActualResultLockAdapter determineActualResultLockAdapter;

		private final EmployeeRequestAdapter employeeRequestAdapter;

		private final WorkFixedAdapter workFixedAdapter;

		private final ReflectApplicationWorkRecordAdapter reflectApplicationWorkRecordAdapter;

		private final CreateEditStatusHistAppReasonAdapter createEditStatusHistAppReasonAdapter;

		private final RequestSettingRepository requestSettingRepository;

		private final ClosureEmploymentRepository closureEmploymentRepository;

		@Override
		public Pair<Object, AtomTask> process(ApplicationShare application, GeneralDate date,
				ReflectStatusResultShare reflectStatus, int preAppWorkScheReflectAttr) {
			return eflectApplicationWorkScheduleAdapter.process(application, date, reflectStatus, preAppWorkScheReflectAttr);
		}

		@Override
		public List<BasicScheduleConfirmImport> findConfirmById(List<String> employeeID, DatePeriod date) {
			return scBasicScheduleAdapter.findConfirmById(employeeID, date);
		}

		@Override
		public List<GeneralDate> getProcessingYMD(String companyID, String employeeID, DatePeriod period) {
			return identificationAdapter.getProcessingYMD(companyID, employeeID, period);
		}

		@Override
		public LockStatus lockStatus(String companyId, GeneralDate baseDate, Integer closureId, PerformanceType type) {
			return determineActualResultLockAdapter.lockStatus(companyId, baseDate, closureId, type);
		}

		@Override
		public SWkpHistImport getSWkpHistByEmployeeID(String employeeId, GeneralDate baseDate) {
			return employeeRequestAdapter.getSWkpHistByEmployeeID(employeeId, baseDate);
		}

		@Override
		public List<String> getWorkplaceIdAndUpper(String companyId, GeneralDate baseDate, String workplaceId) {
			return employeeRequestAdapter.getWorkplaceIdAndUpper(companyId, baseDate, workplaceId);
		}

		@Override
		public boolean getEmploymentFixedStatus(String companyID, GeneralDate date, String workPlaceID, int closureID) {
			return workFixedAdapter.getEmploymentFixedStatus(companyID, date, workPlaceID, closureID);
		}

		@Override
		public Pair<ReflectStatusResultShare, Optional<AtomTask>> process(Application application, GeneralDate date,
				ReflectStatusResultShare reflectStatus) {
			return reflectApplicationWorkRecordAdapter.process(application, date, reflectStatus);
		}

		@Override
		public List<ReasonApplicationDailyResult> findReasonAppDaily(String employeeId, GeneralDate date,
				PrePostAtr preAtr, ApplicationType apptype, Optional<OvertimeAppAtr> overAppAtr) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void addUpdateReason(List<ReasonApplicationDailyResult> reason) {
			// TODO Auto-generated method stub

		}

		@Override
		public void processCreateHist(String employeeId, GeneralDate date, String appId,
				ScheduleRecordClassifi classification, Map<Integer, String> mapValue) {
			createEditStatusHistAppReasonAdapter.process(employeeId, date, appId, classification, mapValue);
		}

		@Override
		public Optional<AppReflectExecCond> findAppReflectExecCond(String companyId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<AppReflectionSetting> getAppReflectionSetting(String companyId, ApplicationType appType) {
			// TODO: ????? apptype
			return requestSettingRepository.getAppReflectionSetting(companyId);
		}

		@Override
		public Optional<EmployeeWorkDataSetting> getEmpWorkDataSetting(String employeeId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<ClosureEmployment> findByEmploymentCD(String companyID, String employmentCD) {
			return closureEmploymentRepository.findByEmploymentCD(companyID, employmentCD);
		}

	}
}
