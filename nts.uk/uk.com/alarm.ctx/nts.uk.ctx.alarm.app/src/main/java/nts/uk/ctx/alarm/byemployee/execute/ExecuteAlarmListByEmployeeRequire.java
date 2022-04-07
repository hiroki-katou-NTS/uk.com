package nts.uk.ctx.alarm.byemployee.execute;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.AlarmListPatternCode;
import nts.uk.ctx.alarm.dom.byemployee.check.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.execute.ExecutePersistAlarmListByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.pattern.AlarmListPatternByEmployee;
import nts.uk.ctx.at.aggregation.dom.adapter.dailyrecord.DailyRecordAdapter;
import nts.uk.ctx.at.aggregation.dom.adapter.workschedule.WorkScheduleAdapter;
import nts.uk.ctx.at.aggregation.dom.common.DailyAttendanceGettingService;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.ApprovalStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.TypeOfItem;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemNameService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.ConfirmationMonth;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.extractresult.AlarmListExtractResult;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.extractresult.ExtractEmployeeErAlData;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRootStateStatus;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.closure.ClosureMonth;


@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExecuteAlarmListByEmployeeRequire {

	@Inject
	private WorkTypeRepository workTypeRepo;
	
	@Inject
	private WorkingConditionRepository workingConditionRepo;

    @Inject
    private WorkScheduleAdapter workScheduleAdapter;

    @Inject
    private DailyRecordAdapter dailyRecordAdapter;
	
	@Inject
	private ApplicationRepository applicatoinRepo;

    @Inject
    private ApprovalStatusAdapter approvalStatusAdapter;

    @Inject
    private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepo;

    @Inject
    private AttendanceItemNameService attendanceItemNameService;

    public Require create() {
        return EmbedStopwatch.embed(new RequireImpl(
                AppContexts.user().companyId(),
                AppContexts.user().employeeId()));
    }

    public interface Require extends ExecutePersistAlarmListByEmployee.Require {

        // テスト用
        List<AlarmRecordByEmployee> getAlarms();
    }

    @RequiredArgsConstructor
    public class RequireImpl implements Require {

    	private final String companyId;

        private final String loginEmployeeId;
    	
        @Getter
        private List<AlarmRecordByEmployee> alarms = new ArrayList<>();

        @Override
        public String getCompanyId() {
            return companyId;
        }

        @Override
        public String getLoginEmployeeId() {
            return loginEmployeeId;
        }

        @Override
        public Optional<AlarmListPatternByEmployee> getAlarmListPatternByEmployee(AlarmListPatternCode patternCode) {
            return Optional.empty();
        }

        @Override
        public void save(AlarmListExtractResult result) {

        }

        @Override
        public void save(ExtractEmployeeErAlData alarm) {

        }

        @Override
        public Optional<AlarmListCheckerByEmployee> getAlarmListChecker(AlarmListCategoryByEmployee category, AlarmListCheckerCode checkerCode) {
            return Optional.empty();
        }

		@Override
		public List<WorkingConditionItemWithPeriod> getWorkingConditions(String employeeId, DatePeriod period) {
			return workingConditionRepo.getWorkingConditionItemWithPeriod(this.companyId, Arrays.asList(employeeId), period);
		}

        @Override
        public Optional<WorkType> getWorkType(String workTypeCode) {
            return Optional.empty();
        }

        @Override
        public Optional<IntegrationOfDaily> getIntegrationOfDaily(String employeeId, GeneralDate date) {
            return Optional.empty();
        }

        @Override
        public List<ApprovalRootStateStatus> getApprovalRootStateByPeriod(String employeeId, DatePeriod period) {
            return null;
        }

        @Override
        public Optional<Identification> getIdentification(String employeeId, GeneralDate date) {
            return Optional.empty();
        }

        @Override
        public boolean existsWorkType(WorkTypeCode workTypeCode) {
            return false;
        }

        @Override
        public boolean existsWorkTime(WorkTimeCode workTimeCode) {
            return false;
        }

        @Override
        public int getBaseUnitPrice(GeneralDate date, String employeeId) {
            return 0;
        }

        @Override
        public List<IntegrationOfDaily> getIntegrationOfDaily(DatePeriod period, String employeeId) {
            return DailyAttendanceGettingService.get(
                    new DailyAttendanceGettingService.Require() {
                        @Override
                        public List<IntegrationOfDaily> getSchduleList(List<EmployeeId> list, DatePeriod datePeriod) {
                            return workScheduleAdapter.getList(Arrays.asList(employeeId), period);
                        }

                        @Override
                        public List<IntegrationOfDaily> getRecordList(List<EmployeeId> list, DatePeriod datePeriod) {
                            return dailyRecordAdapter.getDailyRecordByScheduleManagement(Arrays.asList(employeeId), period);
                        }
                    },
                    Arrays.asList(new EmployeeId(employeeId)),
                    period,
                    ScheRecGettingAtr.SCHEDULE_WITH_RECORD
            ).get(ScheRecGettingAtr.SCHEDULE_WITH_RECORD);
        }

        @Override
        public String getItemName(Integer attendanceItemId) {
            return null;
        }

        @Override
		public List<Application> getApplicationBy(String employeeId, GeneralDate targetDate, ReflectedState states) {
			return applicatoinRepo.getByListRefStatus(this.companyId, employeeId, targetDate, targetDate, Arrays.asList(states.value));
		}

        @Override
        public Optional<ConfirmationMonth> getConfirmationMonth(String employeeId, ClosureMonth closureMonth) {
            return Optional.empty();
        }

        @Override
        public List<ApproveRootStatusForEmpImport> getApprovalStateMonth(String employeeId, ClosureMonth closureMonth) {
            return approvalStatusAdapter.getApprovalByListEmplAndListApprovalRecordDateNew(
                    closureMonth.defaultPeriod().datesBetween(),
                    Arrays.asList(employeeId),
                    RecordRootType.CONFIRM_WORK_BY_MONTH.value);
        }

        @Override
        public Iterable<EmployeeDailyPerError> getEmployeeDailyPerErrors(String employeeId, DatePeriod period, List<ErrorAlarmWorkRecordCode> targetCodes) {
            return employeeDailyPerErrorRepo.findsByCodeLst(
                    Arrays.asList(employeeId),
                    period,
                    targetCodes.stream().map(t -> t.v()).collect(Collectors.toList()));
        }

        @Override
        public String getDailyAttendanceItemName(int attendanceItemId) {
            return attendanceItemNameService.getNameOfAttendanceItem(Arrays.asList(attendanceItemId), TypeOfItem.Daily)
                    .get(0).getAttendanceItemName();
        }
    }
}
