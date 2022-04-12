package nts.uk.ctx.alarm.byemployee.execute;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.AlarmListPatternCode;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.execute.ExecutePersistAlarmListByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.pattern.AlarmListPatternByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.at.aggregation.dom.adapter.dailyrecord.DailyRecordAdapter;
import nts.uk.ctx.at.aggregation.dom.adapter.workschedule.WorkScheduleAdapter;
import nts.uk.ctx.at.aggregation.dom.common.DailyAttendanceGettingService;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.extractresult.AlarmListExtractResult;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.extractresult.ExtractEmployeeErAlData;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.TypeOfItem;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemNameService;
import nts.uk.ctx.at.record.app.find.anyperiod.AnyPeriodRecordToAttendanceItemConverterImpl;
import nts.uk.ctx.at.record.app.find.dailyperform.ConvertFactory;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.ApprovalStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriodRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.anyperiod.ErrorAlarmAnyPeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeekly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeeklyRepository;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.ConfirmationMonth;
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
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.*;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.IntegrationOfDailyGetter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriodRepository;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod.AnyAggrFrameCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeeklyRepository;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthlyGetter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeekly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeeklyKey;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.converter.WeeklyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignemployee.TaskAssignEmployee;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
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
import nts.uk.ctx.workflow.dom.service.output.ApprovalRootStateStatus;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.closure.ClosureMonth;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
        public void save(ExtractEmployeeErAlData alarm) {

        }


        //--- 個人情報系 ---//

        @Override
        public List<WorkingConditionItemWithPeriod> getWorkingConditions(String employeeId, DatePeriod period) {
            return workingConditionRepo.getWorkingConditionItemWithPeriod(this.companyId, Arrays.asList(employeeId), period);
        }


        //--- 勤務種類 ---//

        @Override
        public Optional<WorkType> getWorkType(String workTypeCode) {
            return workTypeRepo.findByPK(this.companyId, workTypeCode);
        }

        @Override
        public boolean existsWorkType(String workTypeCode) {
            return getWorkType(workTypeCode).isPresent();
        }

        @Override
        public Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCode) {
            return getWorkType(workTypeCode.v());
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
            return integrationOfDailyGetter.getIntegrationOfDaily(employeeId, DatePeriod.oneDay(date))
                    .stream().findFirst();
        }

        @Override
        public Optional<Identification> getIdentification(String employeeId, GeneralDate date) {
            // TODO
            return Optional.empty();
        }


        //--- 日別見込み ---//

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


        //--- 月別実績 ---//

        @Override
        public IntegrationOfMonthly getIntegrationOfMonthly(String employeeId, ClosureMonth closureMonth) {
            return integrationOfMonthlyGetter.get(employeeId, closureMonth.yearMonth(), ClosureId.valueOf(closureMonth.closureId()), closureMonth.closureDate());
        }

        @Override
        public Optional<ConfirmationMonth> getConfirmationMonth(String employeeId, ClosureMonth closureMonth) {
            // TODO
            return Optional.empty();
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
        public Iterable<AttendanceTimeOfWeekly> getAttendanceTimeOfWeekly(List<AttendanceTimeOfWeeklyKey> keys) {
            return IteratorUtil.iterable(keys, key -> {
                Optional<AttendanceTimeOfWeekly> ret = attendanceTimeOfWeeklyRepo.find(
                        key.getEmployeeId(),
                        key.getYearMonth(),
                        key.getClosureId(),
                        key.getClosureDate(),
                        key.getWeekNo());
                return ret.get();
            });
        }

        @Override
        public WeeklyRecordToAttendanceItemConverter createWeeklyConverter() {
            return convertFactory.createWeeklyConverter();
        }

        //--- 申請承認 ---//

        @Override
        public List<ApprovalRootStateStatus> getApprovalRootStateByPeriod(String employeeId, DatePeriod period) {
            // TODO
            return null;
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


        //--- 任意期間集計 ---//

		@Override
		public AttendanceTimeOfAnyPeriod getAttendanceTimeOfAnyPeriod(String employeeId, String anyPeriodFrameCode) {
			return attendanceTimeOfAnyPeriodRepo.find(employeeId, anyPeriodFrameCode).get();
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
    }
}
