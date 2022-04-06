package nts.uk.file.at.app.export.schedule.personalschedulebyworkplace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.adapter.dailyrecord.DailyRecordAdapter;
import nts.uk.ctx.at.aggregation.dom.adapter.rank.EmployeeRankInfoAdapter;
import nts.uk.ctx.at.aggregation.dom.adapter.rank.EmployeeRankInfoImported;
import nts.uk.ctx.at.aggregation.dom.adapter.team.EmployeeTeamInfoAdapter;
import nts.uk.ctx.at.aggregation.dom.adapter.team.EmployeeTeamInfoImported;
import nts.uk.ctx.at.aggregation.dom.adapter.workschedule.WorkScheduleAdapter;
import nts.uk.ctx.at.aggregation.dom.common.DailyAttendanceGettingService;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.PersonalCounterCategory;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.WorkplaceCounterCategory;
import nts.uk.ctx.at.aggregation.dom.scheduletable.GetPersonalInfoForScheduleTableService;
import nts.uk.ctx.at.aggregation.dom.scheduletable.OneDayEmployeeAttendanceInfo;
import nts.uk.ctx.at.aggregation.dom.scheduletable.OutputSettingCode;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableAttendanceItem;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableOutputSetting;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableOutputSettingRepository;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTablePersonalInfo;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTablePersonalInfoItem;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateRepository;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureDateDto;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassificationRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.EmployeeAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.EmployeeInfoWantToBeGet;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.EmployeeInfoImported;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.DisplayInfoOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.WorkplaceInfo;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupImport;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.AffWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.WorkplaceExportServiceAdapter;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 個人スケジュール表(職場別)を作成する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class PersonalScheduleByWorkplaceExportQuery {
    @Inject
    private CompanyAdapter company;

    @Inject
    private ScheduleTableOutputSettingRepository outputSettingRepo;

    @Inject
    private EmployeeOneDayAttendanceInfoQuery employeeOneDayAttendanceInfoQuery;

    @Inject
    private WorkplaceGroupAdapter groupAdapter;
    @Inject
    private WorkplaceExportServiceAdapter serviceAdapter;
    @Inject
    private AffWorkplaceAdapter wplAdapter;

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
    private EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHisRepo;
    @Inject
    private NurseClassificationRepository nurseClassificationRepo;
    @Inject
    private EmployeeAdapter employeeAdapter;
    @Inject
    private EmployeeTeamInfoAdapter employeeTeamInfoAdapter;
    @Inject
    private EmployeeRankInfoAdapter employeeRankInfoAdapter;

    @Inject
    private WorkScheduleAdapter workScheduleAdapter;
    @Inject
    private DailyRecordAdapter dailyRecordAdapter;

    @Inject
    private WorkplaceAggregatePersonalTotalQuery aggregatePersonalTotalQuery;

    @Inject
    private WorkplaceTotalAggregatedInfoQuery workplaceTotalAggregatedInfoQuery;

    /**
     * 取得する
     * @param orgUnit
     * @param orgId
     * @param period
     * @param outputSettingCode
     * @param employeeIds
     * @param closureDate
     * @return
     */
    public <T> PersonalScheduleByWkpDataSource<T> get(int orgUnit, String orgId, DatePeriod period, String outputSettingCode, List<String> employeeIds, ClosureDateDto closureDate) {
        String companyId = AppContexts.user().companyId();
        // 共通情報を取得する
        // [RQ622]会社IDから会社情報を取得する
        String companyName = company.getCurrentCompany().orElseGet(() -> {
            throw new RuntimeException("System Error: Company Info");
        }).getCompanyName();

        TargetOrgIdenInfor targetOrgIdenInfor = orgUnit == 0
                ? TargetOrgIdenInfor.creatIdentifiWorkplace(orgId)
                : TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(orgId);
        // 組織の表示情報を取得する(Require, 年月日)
        DisplayInfoOrganization displayInfoOrganization = targetOrgIdenInfor.getDisplayInfor(new TargetOrgIdenInfor.Require() {
            @Override
            @TransactionAttribute(TransactionAttributeType.SUPPORTS)
            public List<WorkplaceGroupImport> getSpecifyingWorkplaceGroupId(List<String> workplacegroupId) {
                return groupAdapter.getbySpecWorkplaceGroupID(workplacegroupId);
            }
            @Override
            @TransactionAttribute(TransactionAttributeType.SUPPORTS)
            public List<WorkplaceInfo> getWorkplaceInforFromWkpIds(List<String> listWorkplaceId, GeneralDate baseDate) {
                List<WorkplaceInfo> workplaceInfos = serviceAdapter.getWorkplaceInforByWkpIds(companyId, listWorkplaceId, baseDate).stream()
                        .map(mapper-> new WorkplaceInfo(mapper.getWorkplaceId(), Optional.ofNullable(mapper.getWorkplaceCode()), Optional.ofNullable(mapper.getWorkplaceName()), Optional.ofNullable(mapper.getWorkplaceExternalCode()),
                                Optional.ofNullable(mapper.getWorkplaceGenericName()), Optional.ofNullable(mapper.getWorkplaceDisplayName()), Optional.ofNullable(mapper.getHierarchyCode()))).collect(Collectors.toList());
                return workplaceInfos;
            }
            @Override
            @TransactionAttribute(TransactionAttributeType.SUPPORTS)
            public List<String> getWKPID(String WKPGRPID) {
                return wplAdapter.getWKPID(companyId, WKPGRPID);
            }
        }, period.end());

        List<GeneralDate> targetDates = period.datesBetween();
        List<WorkplaceEvent> workplaceEvents = targetOrgIdenInfor.getWorkplaceId().isPresent()
                ? workplaceEventRepo.getWorkplaceEventsByListDate(targetOrgIdenInfor.getWorkplaceId().get(), targetDates)
                : new ArrayList<>();
        List<CompanyEvent> companyEvents = companyEventRepo.getCompanyEventsByListDate(companyId, targetDates);
        List<PublicHoliday> publicHolidays = publicHolidayRepo.getHolidaysByListDate(companyId, targetDates);
        List<DateInformation> dateInformationList = targetDates.stream().map(date -> {
            return DateInformation.create(new DateInformation.Require() {
                
				@Override
				@TransactionAttribute(TransactionAttributeType.SUPPORTS)
				public Optional<WorkplaceSpecificDateItem> getWorkplaceSpecByDate(String workplaceId, GeneralDate specificDate) {
					return workplaceSpecificDateRepo.get(workplaceId, specificDate);
				}
				
				@Override
				@TransactionAttribute(TransactionAttributeType.SUPPORTS)
				public Optional<CompanySpecificDateItem> getComSpecByDate(GeneralDate specificDate) {
					return companySpecificDateRepo.get(AppContexts.user().companyId(), specificDate);
				}
                
                
                @Override
                @TransactionAttribute(TransactionAttributeType.SUPPORTS)
                public Optional<WorkplaceEvent> findByPK(String workplaceId, GeneralDate date) {
                    return workplaceEvents.stream().filter(e -> e.getDate().equals(date)).findFirst();
                }
                @Override
                @TransactionAttribute(TransactionAttributeType.SUPPORTS)
                public Optional<CompanyEvent> findCompanyEventByPK(GeneralDate date) {
                    return companyEvents.stream().filter(e -> e.getDate().equals(date)).findFirst();
                }
                @Override
                @TransactionAttribute(TransactionAttributeType.SUPPORTS)
                public Optional<PublicHoliday> getHolidaysByDate(GeneralDate date) {
                    return publicHolidays.stream().filter(h -> h.getDate().equals(date)).findFirst();
                }
                @Override
                @TransactionAttribute(TransactionAttributeType.SUPPORTS)
                public List<SpecificDateItem> getSpecifiDateByListCode(List<SpecificDateItemNo> lstSpecificDateItemNo) {
					if (lstSpecificDateItemNo.isEmpty()) {
						return new ArrayList<>();
					}
					return specificDateItemRepo.getSpecifiDateByListCode(AppContexts.user().companyId(), lstSpecificDateItemNo);
                }
            }, date, targetOrgIdenInfor);
        }).collect(Collectors.toList());

        // 出力項目設定情報を取得する
        Optional<ScheduleTableOutputSetting> outputSetting = outputSettingRepo.get(companyId, new OutputSettingCode(outputSettingCode));

        if (!outputSetting.isPresent()) throw new RuntimeException("出力設定 not found!");

        // 表示対象の勤怠項目を取得する
        // 表示対象の個人情報項目を取得する
        List<ScheduleTableAttendanceItem> attendanceItems = outputSetting.get().getOutputItem().getDisplayAttendanceItems();
        List<ScheduleTablePersonalInfoItem> personalInfoItems = outputSetting.get().getOutputItem().getDisplayPersonalInfoItems();

        // 5.
        List<ScheduleTablePersonalInfo> personalInfoScheduleTableList = GetPersonalInfoForScheduleTableService.get(
                new GetPersonalInfoForScheduleTableService.Require() {
                    @Override
                    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
                    public List<EmpMedicalWorkStyleHistoryItem> getEmpMedicalWorkStyleHistoryItem(List<String> listEmp, GeneralDate referenceDate) {
                        return empMedicalWorkStyleHisRepo.get(listEmp, referenceDate);
                    }
                    @Override
                    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
                    public List<NurseClassification> getListCompanyNurseCategory() {
                        return nurseClassificationRepo.getListCompanyNurseCategory(companyId);
                    }
                    @Override
                    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
                    public List<EmployeeInfoImported> getEmployeeInfo(List<String> employeeIds, GeneralDate baseDate, EmployeeInfoWantToBeGet param) {
                        return employeeAdapter.getEmployeeInfo(employeeIds, baseDate, param);
                    }
                    @Override
                    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
                    public List<EmployeeTeamInfoImported> getEmployeeTeamInfo(List<String> employeeIds) {
                        return employeeTeamInfoAdapter.get(employeeIds);
                    }
                    @Override
                    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
                    public List<EmployeeRankInfoImported> getEmployeeRankInfo(List<String> employeeIds) {
                        return employeeRankInfoAdapter.get(employeeIds);
                    }
                },
                employeeIds,
                period.end(),
                personalInfoItems
        );

        // 6.
        ScheRecGettingAtr scheRecGettingAtr = outputSetting.get().getOutputItem().getDailyDataDisplayAtr() == NotUseAtr.USE ? ScheRecGettingAtr.SCHEDULE_WITH_RECORD : ScheRecGettingAtr.ONLY_SCHEDULE;
        Map<ScheRecGettingAtr, List<IntegrationOfDaily>> integrationOfDailyMap = DailyAttendanceGettingService.get(
            new DailyAttendanceGettingService.Require() {
                @Override
                @TransactionAttribute(TransactionAttributeType.SUPPORTS)
                public List<IntegrationOfDaily> getSchduleList(List<EmployeeId> empIds, DatePeriod period) {
                    return workScheduleAdapter.getList(empIds.stream().map(PrimitiveValueBase::v).collect(Collectors.toList()), period);
                }
                @Override
                @TransactionAttribute(TransactionAttributeType.SUPPORTS)
                public List<IntegrationOfDaily> getRecordList(List<EmployeeId> empIds, DatePeriod period) {
                    return dailyRecordAdapter.getDailyRecordByScheduleManagement(empIds.stream().map(PrimitiveValueBase::v).collect(Collectors.toList()), period);
                }
            },
            employeeIds.stream().map(EmployeeId::new).collect(Collectors.toList()),
            period,
            scheRecGettingAtr
        );

        // 7. 一日分の社員の表示情報を取得す
        List<OneDayEmployeeAttendanceInfo> listEmpOneDayAttendanceInfo = employeeOneDayAttendanceInfoQuery.get(integrationOfDailyMap.get(scheRecGettingAtr));

        // 8. 個人計を集計する
        Map<PersonalCounterCategory, Map<String, T>> personalTotalResult = new HashMap<>();
        if (!outputSetting.get().getPersonalCounterCategories().isEmpty()) {
            personalTotalResult = aggregatePersonalTotalQuery.get(
                    employeeIds,
                    period,
                    closureDate,
                    outputSetting.get().getPersonalCounterCategories(),
                    integrationOfDailyMap.get(scheRecGettingAtr)
            );
        }

        // 9.
        Map<WorkplaceCounterCategory, Map<GeneralDate, T>> workplaceTotalResult = new HashMap<>();
        if (!outputSetting.get().getWorkplaceCounterCategories().isEmpty()) {
            workplaceTotalResult = workplaceTotalAggregatedInfoQuery.get(
                    attendanceItems,
                    outputSetting.get().getWorkplaceCounterCategories(),
                    integrationOfDailyMap,
                    period,
                    targetOrgIdenInfor,
                    scheRecGettingAtr
            );
        }

        return new PersonalScheduleByWkpDataSource(
                orgUnit,
                period,
                companyName,
                outputSetting.get(),
                displayInfoOrganization,
                dateInformationList,
                personalInfoScheduleTableList,
                listEmpOneDayAttendanceInfo,
                personalTotalResult,
                workplaceTotalResult
        );
    }
}
