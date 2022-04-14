package nts.uk.ctx.alarm.byemployee.execute;

import java.util.*;
import java.util.ArrayList;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.execute.ExecuteAlarmListByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.pattern.AlarmListPatternByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.pattern.AlarmListPatternCode;
import nts.uk.ctx.at.aggregation.dom.adapter.dailyrecord.DailyRecordAdapter;
import nts.uk.ctx.at.aggregation.dom.adapter.workschedule.WorkScheduleAdapter;
import nts.uk.ctx.at.aggregation.dom.common.DailyAttendanceGettingService;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.extractresult.AlarmListExtractResult;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.extractresult.ExtractEmployeeErAlData;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.extractresult.ExtractEmployeeInfo;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatusRepository;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.TypeOfItem;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemNameService;
import nts.uk.ctx.at.record.app.find.anyperiod.AnyPeriodRecordToAttendanceItemConverterImpl;
import nts.uk.ctx.at.record.app.find.dailyperform.ConvertFactory;
import nts.uk.ctx.at.record.app.find.monthly.MonthlyRecordToAttendanceItemConverterImpl;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.ApprovalStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;
import nts.uk.ctx.at.record.dom.daily.export.AggregateSpecifiedDailys;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriodRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErAlCategory;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.anyperiod.ErrorAlarmAnyPeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.ExtraResultMonthly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.ExtraResultMonthlyRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeekly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeeklyRepository;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.ConfirmationMonth;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentificationRepository;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateRepository;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSidPeriodDateEmploymentImport;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.IntegrationOfDailyGetter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagementRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareManagementRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.CareManagementDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.*;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriodRepository;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod.AnyAggrFrameCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkCode;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthlyGetter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeekly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeeklyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.converter.WeeklyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskframe.TaskFrameUsageSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignemployee.TaskAssignEmployee;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameUsageSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.FamilyInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.*;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpAffiliationInforAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpOrganizationImport;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;
import nts.uk.ctx.workflow.dom.service.ApprovalRootStateStatusService;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRootStateStatus;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.license.option.OptionLicense;
import nts.uk.shr.com.time.closure.ClosureMonth;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.AggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.WorkDaysNumberOnLeaveCount;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExecuteAlarmListByEmployeeRequire {

    @Inject
    private WorkScheduleAdapter workScheduleAdapter;

    @Inject
    private WorkingConditionRepository workingConditionRepo;

    @Inject
    private WorkTypeRepository workTypeRepo;

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepo;

    @Inject
    private WorkScheduleRepository workScheduleRepo;

    @Inject
    private DailyRecordAdapter dailyRecordAdapter;

    @Inject
    private IntegrationOfMonthlyGetter integrationOfMonthlyGetter;

    @Inject
    private ApplicationRepository applicatoinRepo;

    @Inject
    private ApprovalStatusAdapter approvalStatusAdapter;

    @Inject
    private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepo;

    @Inject
    private AttendanceItemNameService attendanceItemNameService;

    @Inject
    private AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo;

    @Inject
    private YearHolidayRepository yearHolidayRepo;

    @Inject
    private AttendanceTimeOfAnyPeriodRepository attendanceTimeOfAnyPeriodRepo;

    @Inject
    private AnyAggrPeriodRepository anyAggrPeriodRepo;

    @Inject
    private OptionalItemRepository optionalItemRepo;

    @Inject
    private ExtractionCondWeeklyRepository extractionCondWeeklyRepository;

    @Inject
    private AttendanceTimeOfWeeklyRepository attendanceTimeOfWeeklyRepo;

    @Inject
    private ConvertFactory convertFactory;

    @Inject
    private IntegrationOfDailyGetter integrationOfDailyGetter;

    @Inject
    private ErrorAlarmWorkRecordRepository errorAlarmWorkRecordRepo;

    @Inject
    private ErrorAlarmConditionRepository errorAlarmConditionRepo;

    @Inject
    private StampCardRepository stampCardRepo;

    @Inject
    private WorkplaceSpecificDateRepository workplaceSpecificDateRepo;

    @Inject
    private CompanySpecificDateRepository companySpecificDateRepo;

    @Inject
    private WorkplaceEventRepository workplaceEventRepo;

    @Inject
    private CompanyEventRepository companyEventRepo;

    @Inject
    private PublicHolidayRepository publicHolidayRepo;

    @Inject
    private SpecificDateItemRepository specificDateItemRepo;

    @Inject
    private EmpAffiliationInforAdapter empAffiliationInforAdapter;

    @Inject
    private BasicScheduleService basicScheduleService;

    @Inject
    private PredetemineTimeSettingRepository predetemineTimeSettingRepo;

    @Inject
    private FixedWorkSettingRepository fixedWorkSettingRepo;

    @Inject
    private FlexWorkSettingRepository flexWorkSettingRepo;

    @Inject
    private FlowWorkSettingRepository flowWorkSettingRepo;

    @Inject
    private AlarmListExtraProcessStatusRepository alarmListExtraProcessStatusRepo;

    @Inject
    private ExtraResultMonthlyRepository extraResultMonthlyRepo;

    @Inject
    private RecordDomRequireService requireService;

    @Inject
    private EmpEmployeeAdapter empEmployeeAdapter;

    @Inject
    private TempChildCareManagementRepository tempChildCareManagementRepo;

    @Inject
    private TempCareManagementRepository tempCareManagementRepo;

    @Inject
    private NursingLeaveSettingRepository nursingLeaveSettingRepo;

    @Inject
    private ChildCareLeaveRemInfoRepository childCareLeaveRemInfoRepo;

    @Inject
    private CareLeaveRemainingInfoRepository careLeaveRemainingInfoRepo;

    @Inject
    private ChildCareUsedNumberRepository childCareUsedNumberRepo;

    @Inject
    private CareUsedNumberRepository careUsedNumberRepo;

    @Inject
    private ClosureStatusManagementRepository closureStatusManagementRepo;

    @Inject
    private AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepo;

    @Inject
    private ClosureEmploymentRepository closureEmploymentRepo;

    @Inject
    private ShareEmploymentAdapter shareEmploymentAdapter;

    @Inject
    private ClosureRepository closureRepo;

    @Inject
    private BPSettingRepository bPSettingRepo;

    @Inject
    private IdentificationRepository identificationRepo;

    @Inject
    private ApprovalRootStateStatusService approvalRootStateStatusService;

    @Inject
    private WorkingConditionItemRepository workingConditionItemRepo;

    @Inject
    private WorkLocationRepository workLocationRepo;

    @Inject
    private TaskingRepository taskingRepo;

    @Inject
    private TaskFrameUsageSettingRepository taskFrameUsageSettingRepo;

    public Require create() {
        return EmbedStopwatch.embed(new RequireImpl(
                AppContexts.user().contractCode(),
                AppContexts.user().companyId(),
                AppContexts.user().employeeId()));
    }

    public interface Require extends ExecuteAlarmListByEmployee.Require {

        void save(AlarmListExtractResult result);

        void save(ExtractEmployeeInfo employeeInfo);
    }

    @RequiredArgsConstructor
    public class RequireImpl implements Require {

        private final String companyId;
        private final String contractCode;
        private final String loginEmployeeId;

        //--- ログイン情報 ---//
        @Override
        public String getCompanyId() {
            return companyId;
        }

        @Override
        public String getLoginEmployeeId() {
            return loginEmployeeId;
        }

        //--- アラームリストの設定 ---//
        @Override
        public Optional<AlarmListPatternByEmployee> getAlarmListPatternByEmployee(AlarmListPatternCode patternCode) {
            return Optional.empty();
        }

        @Override
        public Optional<AlarmListCheckerByEmployee> getAlarmListChecker(AlarmListCategoryByEmployee category, AlarmListCheckerCode checkerCode) {
            return Optional.empty();
        }

        //--- アラームリストの抽出結果 ---//
        @Override
        public void save(AlarmListExtractResult result) {

        }

        @Override
        public void save(ExtractEmployeeInfo employeeInfo) {

        }

        @Override
        public void save(ExtractEmployeeErAlData alarm) {

        }

        //--- 個人情報系 ---//
        @Override
        public Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate generalDate) {
            return workingConditionRepo.getWorkingConditionItemByEmpIDAndDate(this.companyId, generalDate, employeeId);
        }

        @Override
        public List<WorkingConditionItemWithPeriod> getWorkingConditions(String employeeId, GeneralDate baseDate) {
            return workingConditionRepo.getWorkingConditionItemWithPeriod(this.companyId, Arrays.asList(employeeId), new DatePeriod(baseDate, baseDate));
        }

        @Override
        public Optional<BsEmploymentHistoryImport> employmentHistory(CacheCarrier cacheCarrier, String companyID, String employeeId, GeneralDate baseDate) {
            return Optional.empty();
        }

        //--- 労働条件 ---//
        @Override
        public Optional<WorkingConditionItem> getWorkingConditionItem(String employeeId, GeneralDate date) {
            return workingConditionItemRepo.getBySidAndStandardDate(employeeId, date);
        }

        @Override
        public Optional<WorkingCondition> workingCondition(String companyId, String employeeId, GeneralDate baseDate) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Optional<WorkingConditionItem> workingConditionItem(String historyId) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        //--- 勤務種類 ---//
        @Override
        public List<WorkingConditionItemWithPeriod> getWorkingConditions(String s, DatePeriod datePeriod) {
            return null;
        }

        @Override
        public Optional<WorkType> getWorkType(String workTypeCode) {
            return this.workType(companyId, new WorkTypeCode(workTypeCode));
        }

        @Override
        public boolean existsWorkType(String workTypeCode) {
            return getWorkType(workTypeCode).isPresent();
        }

        @Override
        public Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCode) {
            return getWorkType(workTypeCode.v());
        }

        @Override
        public Optional<WorkType> workType(String cid, String workTypeCode) {
            return workTypeRepo.findByPK(cid, workTypeCode);
        }

        //--- 就業時間帯 ---//
        @Override
        public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
            return workTimeSettingRepo.findByCode(this.companyId, workTimeCode);
        }

        @Override
        public boolean existsWorkTime(String workTimeCode) {
            return getWorkTime(workTimeCode).isPresent();
        }

        @Override
        public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
            return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
        }

        @Override
        public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, WorkTimeCode workTimeCode) {
            return predetemineTimeSettingRepo.findByWorkTimeCode(companyId, workTimeCode.toString());
        }

        @Override
        public Optional<WorkTimeSetting> workTimeSetting(String companyId, WorkTimeCode workTimeCode) {
            return workTimeSettingRepo.findByCode(companyId, workTimeCode.toString());
        }

        @Override
        public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode) {
            return fixedWorkSettingRepo.findByKey(companyId, workTimeCode.toString());
        }

        @Override
        public Optional<FlexWorkSetting> flexWorkSetting(String companyId, WorkTimeCode workTimeCode) {
            return flexWorkSettingRepo.find(companyId, workTimeCode.toString());
        }

        @Override
        public Optional<FlowWorkSetting> flowWorkSetting(String companyId, WorkTimeCode workTimeCode) {
            return flowWorkSettingRepo.find(companyId, workTimeCode.toString());
        }

        //--- 締め ---//
        @Override
        public List<Closure> closure(String companyId) {
            return closureRepo.findAll(companyId);
        }

        @Override
        public List<Closure> closureActive(String companyId, UseClassification useAtr) {
            return closureRepo.findAllActive(companyId, useAtr);
        }

        @Override
        public Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD) {
            return closureEmploymentRepo.findByEmploymentCD(companyID, employmentCD);
        }

        //--- 勤怠項目 ---//
        @Override
        public AttendanceItemConvertFactory getAttendanceItemConvertFactory() {
            // TODO
            return null;
        }

        @Override
        public String getDailyAttendanceItemName(Integer attendanceItemId) {
            return getAttendanceItemName(TypeOfItem.Daily, attendanceItemId);
        }

        @Override
        public String getMonthlyAttendanceItemName(Integer attendanceItemId) {
            return getAttendanceItemName(TypeOfItem.Monthly, attendanceItemId);
        }

        @Override
        public String getAttendanceItemName(TypeOfItem typeOfItem, int itemId) {
            return attendanceItemNameService.getNameOfAttendanceItem(Arrays.asList(itemId), typeOfItem)
                    .get(0).getAttendanceItemName();
        }

        //--- 勤務予定 ---//
        @Override
        public Optional<WorkSchedule> getWorkSchedule(String employeeId, GeneralDate date) {
            return workScheduleRepo.get(employeeId, date);
        }

        @Override
        public boolean existsWorkSchedule(String employeeId, GeneralDate date) {
            return getWorkSchedule(employeeId, date).isPresent();
        }

        //--- 日別実績 ---//
        @Override
        public Optional<IntegrationOfDaily> getIntegrationOfDailyRecord(String employeeId, GeneralDate date) {
            return this.getIntegrationOfDailyRecords(employeeId, DatePeriod.oneDay(date))
                    .stream().findFirst();
        }

        @Override
        public Optional<Identification> getIdentification(String employeeId, GeneralDate date) {
            return identificationRepo.findByCode(employeeId, date);
        }

        //--- 見込み月次 ---//
        @Override
        public List<IntegrationOfDaily> getIntegrationOfDailyProspect(String employeeId, DatePeriod period) {
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

        //--- 見込み年次 ---//
        @Override
        public List<IntegrationOfMonthly> getIntegrationOfMonthlyProspect(String employeeId, List<ClosureMonth> closureMonths) {
            List<IntegrationOfMonthly> result = new ArrayList<>();
            closureMonths.sort(Comparator.comparing(cm -> cm.defaultPeriod().start()));
            for (ClosureMonth closureMonth : closureMonths) {
                // TODO: 過去月か？
                if (closureMonth.defaultPeriod().end().before(GeneralDate.today())) {
                    val im = integrationOfMonthlyGetter.get(
                            employeeId, closureMonth.yearMonth(), ClosureId.valueOf(closureMonth.closureId()), closureMonth.closureDate());
                    result.add(im);
                    continue;
                }

                val require = requireService.createRequire();
                val cacheCarrier = new CacheCarrier();

                List<IntegrationOfDaily> dailies = this.getIntegrationOfDailyProspect(employeeId, closureMonth.defaultPeriod());
                val im = AggregateSpecifiedDailys.algorithm(
                        require, cacheCarrier, companyId, employeeId,
                        closureMonth.getYearMonth(), EnumAdaptor.valueOf(closureMonth.getClosureId(), ClosureId.class),
                        closureMonth.getClosureDate(), closureMonth.defaultPeriod(), null, dailies, null
                );
                im.ifPresent(data -> result.add(data));
            }
            return result;
        }

        @Override
        public RecordDomRequireService requireService() {
            return requireService;
        }

        //--- 月別実績 ---//
        @Override
        public IntegrationOfMonthly getIntegrationOfMonthly(String employeeId, ClosureMonth closureMonth) {
            return integrationOfMonthlyGetter.get(employeeId, closureMonth.yearMonth(), ClosureId.valueOf(closureMonth.closureId()), closureMonth.closureDate());
        }

        @Override
        public List<IntegrationOfMonthly> getIntegrationOfMonthly(String employeeId, List<ClosureMonth> closureMonthes) {
            return closureMonthes.stream()
                    .map(closureMonth -> this.getIntegrationOfMonthly(employeeId, closureMonth))
                    .collect(Collectors.toList());
        }

        @Override
        public Optional<ConfirmationMonth> getConfirmationMonth(String employeeId, ClosureMonth closureMonth) {
            // TODO
            return Optional.empty();
        }

        @Override
        public ExtraResultMonthly getExtraResultMonthly(ErrorAlarmWorkRecordCode codes) {
            return null;
        }

        @Override
        public List<ExtraResultMonthly> getExtraResultMonthly(List<ErrorAlarmWorkRecordCode> codes) {
            return codes.stream()
                    .map(code -> this.getExtraResultMonthly(code))
                    .collect(Collectors.toList());
        }

        @Override
        public List<MonthlyRecordToAttendanceItemConverter> getMonthlyRecordToAttendanceItemConverter(List<IntegrationOfMonthly> monthlyRecords) {
            return monthlyRecords.stream()
                    .map(record -> MonthlyRecordToAttendanceItemConverterImpl.builder(optionalItemRepo).setData(record))
                    .collect(Collectors.toList());
        }

        @Override
        public List<ApproveRootStatusForEmpImport> getApprovalStateMonth(String employeeId, ClosureMonth closureMonth) {
            return approvalStatusAdapter.getApprovalByListEmplAndListApprovalRecordDateNew(
                    closureMonth.defaultPeriod().datesBetween(),
                    Arrays.asList(employeeId),
                    RecordRootType.CONFIRM_WORK_BY_MONTH.value);
        }

        //--- 週別実績 ---//
        @Override
        public List<ExtractionCondWeekly> getUsedExtractionCondWeekly(List<String> codes) {
            return extractionCondWeeklyRepository.getByCodes(companyId, ErAlCategory.WEEKLY.value, codes);
        }

        @Override
        public Iterable<AttendanceTimeOfWeekly> getAttendanceTimeOfWeekly(String employeeId, DatePeriod period) {
            return attendanceTimeOfWeeklyRepo.findMatchAnyOneDay(employeeId, period);
        }

        @Override
        public WeeklyRecordToAttendanceItemConverter createWeeklyConverter() {
            return convertFactory.createWeeklyConverter();
        }

        //--- 申請承認 ---//
        @Override
        public List<ApprovalRootStateStatus> getApprovalRootStateByPeriod(String employeeId, DatePeriod period) {
            return approvalRootStateStatusService.getStatusByEmpAndDate(employeeId, period.start(), period.end(), 1);
        }

        @Override
        public List<Application> getApplicationBy(String employeeId, GeneralDate targetDate, ReflectedState states) {
            return applicatoinRepo.getByListRefStatus(this.companyId, employeeId, targetDate, targetDate, Arrays.asList(states.value));
        }

        //--- エラーアラーム ---//
        @Override
        public Iterable<EmployeeDailyPerError> getEmployeeDailyPerErrors(String employeeId, DatePeriod period, List<ErrorAlarmWorkRecordCode> targetCodes) {
            return employeeDailyPerErrorRepo.findsByCodeLst(
                    Arrays.asList(employeeId),
                    period,
                    targetCodes.stream().map(t -> t.v()).collect(Collectors.toList()));
        }

        @Override
        public Optional<ErrorAlarmWorkRecord> getErrorAlarmWorkRecord(ErrorAlarmWorkRecordCode code) {
            // 会社ID渡してないからビビるけど、Repositoryの中でAppContextsから取得してる・・・
            return errorAlarmWorkRecordRepo.findByCode(code.v());
        }

        @Override
        public Optional<ErrorAlarmCondition> getErrorAlarmConditionById(String id) {
            return errorAlarmConditionRepo.findConditionByErrorAlamCheckId(id);
        }

        //--- 年休 ---//
        @Override
        public Optional<AnnualLeaveEmpBasicInfo> getBasicInfo(String employeeId) {
            return annLeaEmpBasicInfoRepo.get(employeeId);
        }

        @Override
        public Optional<GrantHdTblSet> getTable(String yearHolidayCode) {
            return yearHolidayRepo.findByCode(this.companyId, yearHolidayCode);
        }

        @Override
        public AnnualPaidLeaveSetting annualPaidLeaveSetting(String companyId) {
            return annualPaidLeaveSettingRepo.findByCompanyId(companyId);
        }

        //--- 子の看護介護休暇 ---//
        @Override
        public EmployeeImport findByEmpId(String employeeId) {
            return empEmployeeAdapter.findByEmpId(employeeId);
        }

        @Override
        public List<FamilyInfo> familyInfo(String employeeId) {
            //TODO 2021/03/22 時点では家族情報は取得できない
            return new ArrayList<>();
        }

        @Override
        public List<TempChildCareManagement> tempChildCareManagement(String employeeId, DatePeriod period) {
            return tempChildCareManagementRepo.findByPeriodOrderByYmd(employeeId, period);
        }

        @Override
        public List<TempCareManagement> tempCareManagement(String employeeId, DatePeriod period) {
            return tempCareManagementRepo.findByPeriodOrderByYmd(employeeId, period);
        }

        @Override
        public NursingLeaveSetting nursingLeaveSetting(String companyId, NursingCategory nursingCategory) {
            return nursingLeaveSettingRepo.findByCompanyIdAndNursingCategory(companyId, nursingCategory.value);
        }

        @Override
        public Optional<NursingCareLeaveRemainingInfo> employeeInfo(String employeeId, NursingCategory nursingCategory) {
            if (nursingCategory.equals(NursingCategory.Nursing)) {
                return careLeaveEmployeeInfo(employeeId).map(mapper -> (NursingCareLeaveRemainingInfo) mapper);
            }
            if (nursingCategory.equals(NursingCategory.ChildNursing)) {
                return childCareLeaveEmployeeInfo(employeeId).map(mapper -> (NursingCareLeaveRemainingInfo) mapper);
            }
            return Optional.empty();
        }

        @Override
        public Optional<ChildCareLeaveRemainingInfo> childCareLeaveEmployeeInfo(String employeeId) {
            return childCareLeaveRemInfoRepo.getChildCareByEmpId(employeeId);
        }

        @Override
        public Optional<CareLeaveRemainingInfo> careLeaveEmployeeInfo(String employeeId) {
            return careLeaveRemainingInfoRepo.getCareByEmpId(employeeId);
        }

        @Override
        public Optional<ChildCareUsedNumberData> childCareUsedNumber(String employeeId) {
            return childCareUsedNumberRepo.find(employeeId);
        }

        @Override
        public Optional<CareUsedNumberData> careUsedNumber(String employeeId) {
            return careUsedNumberRepo.find(employeeId);
        }

        @Override
        public Optional<CareManagementDate> careData(String familyID) {
            //TODO 2021/03/22 時点では家族情報は取得できない
            return Optional.empty();
        }

        @Override
        public Optional<ClosureStatusManagement> latestClosureStatusManagement(String employeeId) {
            return closureStatusManagementRepo.getLatestByEmpId(employeeId);
        }

        @Override
        public OptionLicense getOptionLicense() {
            return AppContexts.optionLicense();
        }

        @Override
        public List<SharedSidPeriodDateEmploymentImport> employmentHistory(CacheCarrier cacheCarrier, List<String> employeeIds, DatePeriod datePeriod) {
            return shareEmploymentAdapter.getEmpHistBySidAndPeriodRequire(cacheCarrier, employeeIds, datePeriod);
        }

        @Override
        public EmployeeImport employee(CacheCarrier cacheCarrier, String employeeId) {
            return empEmployeeAdapter.findByEmpIdRequire(cacheCarrier, employeeId);
        }

        //--- 任意期間集計 ---//
        @Override
        public AttendanceTimeOfAnyPeriod getAttendanceTimeOfAnyPeriod(String employeeId, String anyPeriodFrameCode) {
            return attendanceTimeOfAnyPeriodRepo.find(employeeId, anyPeriodFrameCode).get();
        }

        @Override
        public List<IntegrationOfDaily> getIntegrationOfDailyRecords(String employeeId, DatePeriod period) {
            return integrationOfDailyGetter.getIntegrationOfDaily(employeeId, period);
        }

        @Override
        public AnyAggrPeriod getAnyAggrPeriod(AnyAggrFrameCode code) {
            return anyAggrPeriodRepo.findOneByCompanyIdAndFrameCode(companyId, code.v()).get();
        }

        @Override
        public List<ErrorAlarmAnyPeriod> getErrorAlarmAnyPeriod(List<ErrorAlarmWorkRecordCode> code) {
            return null;
        }

        @Override
        public AnyPeriodRecordToAttendanceItemConverter getAnyPeriodRecordToAttendanceItemConverter(
                String employeeId, AttendanceTimeOfAnyPeriod record) {
            return AnyPeriodRecordToAttendanceItemConverterImpl
                    .builder(optionalItemRepo)
                    .withBase(employeeId)
                    .withAttendanceTime(record)
                    .completed();
        }

        //--- 作業 ---//
        @Override
        public Optional<TaskAssignEmployee> getTaskAssign(String employeeId, TaskFrameNo frameNo) {
            return Optional.empty();
        }

        @Override
        public boolean existsTask(TaskFrameNo taskFrameNo, TaskCode code) {
            return false;
        }

        @Override
        public Optional<Task> getTask(TaskFrameNo taskFrameNo, WorkCode code) {
            return taskingRepo.getOptionalTask(companyId, taskFrameNo, new TaskCode(code.v()));
        }

        @Override
        public TaskFrameUsageSetting getTaskFrameUsageSetting() {
            return taskFrameUsageSettingRepo.getWorkFrameUsageSetting(companyId);
        }

        //--- 加給 ---//
        @Override
        public boolean existsBonusPay(BonusPaySettingCode code) {
            return bPSettingRepo.isExisted(companyId, code);
        }

        //================= 未分類の壁 =================//
        @Override
        public List<StampCard> getStampCard(String employeeId) {
            return stampCardRepo.getListStampCard(employeeId);
        }

        @Override
        public List<WorkLocation> getWorkLocation(String workLocationCode) {
            return null;
        }

        @Override
        public Optional<WorkplaceSpecificDateItem> getWorkplaceSpecByDate(String workplaceId, GeneralDate generalDate) {
            return workplaceSpecificDateRepo.get(workplaceId, generalDate);
        }

        @Override
        public Optional<CompanySpecificDateItem> getComSpecByDate(GeneralDate generalDate) {
            return companySpecificDateRepo.get(this.companyId, generalDate);
        }

        @Override
        public Optional<WorkplaceEvent> findByPK(String workplaceId, GeneralDate generalDate) {
            return workplaceEventRepo.findByPK(workplaceId, generalDate);
        }

        @Override
        public Optional<CompanyEvent> findCompanyEventByPK(GeneralDate generalDate) {
            return companyEventRepo.findByPK(this.companyId, generalDate);
        }

        @Override
        public Optional<PublicHoliday> getHolidaysByDate(GeneralDate generalDate) {
            return publicHolidayRepo.getHolidaysByDate(this.companyId, generalDate);
        }

        @Override
        public List<SpecificDateItem> getSpecifiDateByListCode(List<SpecificDateItemNo> list) {
            return specificDateItemRepo.getSpecifiDateByListCode(this.companyId, list);
        }

        @Override
        public List<EmpOrganizationImport> getEmpOrganization(GeneralDate generalDate, List<String> lstEmpId) {
            return empAffiliationInforAdapter.getEmpOrganization(generalDate, lstEmpId);
        }

        @Override
        public boolean existWorkLocation(WorkLocationCD code) {
            return workLocationRepo.findByCode(contractCode, code.v()).isPresent();
        }

        @Override
        public Optional<AggregateMethodOfMonthly> getAggregateMethodOfMonthly(String cid) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Optional<RetentionYearlySetting> retentionYearlySetting(String companyId) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public WorkDaysNumberOnLeaveCount workDaysNumberOnLeaveCount(String cid) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Optional<HolidayAddtionSet> holidayAddtionSet(String cid) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
