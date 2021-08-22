package nts.uk.file.at.app.export.schedule.personalschedulebyworkplace;

import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.common.DailyAttendanceGettingService;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.*;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.*;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.PersonalCounterCategory;
import nts.uk.ctx.at.aggregation.dom.scheduletable.*;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.item.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.item.SpecificDateItemRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateRepository;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.IntegrationOfDailyGetter;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AttendanceTimesForAggregation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentHisAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.DisplayInfoOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.WorkplaceInfo;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupImport;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.AffWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.WorkplaceExportServiceAdapter;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class PersonalScheduleByWorkplaceExportQuery {
    @Inject
    private CompanyAdapter company;

    @Inject
    private ScheduleTableOutputSettingRepository outputSettingRepo;

    @Inject
    private GetPersonalInfoScheduleTableDomainService personalInfoScheduleDomainService;

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
    private IntegrationOfDailyGetter integrationOfDailyGetter;

    @Inject
    private AggregatePersonalTotalQuery aggregatePersonalTotalQuery;

    public PersonalScheduleByWkpDataSource get(int orgUnit, String orgId, DatePeriod period, String outputSettingCode, List<String> employeeIds, GeneralDate closureDate) {
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
            public List<WorkplaceGroupImport> getSpecifyingWorkplaceGroupId(List<String> workplacegroupId) {
                return groupAdapter.getbySpecWorkplaceGroupID(workplacegroupId);
            }

            @Override
            public List<WorkplaceInfo> getWorkplaceInforFromWkpIds(List<String> listWorkplaceId, GeneralDate baseDate) {
                List<WorkplaceInfo> workplaceInfos = serviceAdapter.getWorkplaceInforByWkpIds(companyId, listWorkplaceId, baseDate).stream()
                        .map(mapper-> new WorkplaceInfo(mapper.getWorkplaceId(), Optional.ofNullable(mapper.getWorkplaceCode()), Optional.ofNullable(mapper.getWorkplaceName()), Optional.ofNullable(mapper.getWorkplaceExternalCode()),
                                Optional.ofNullable(mapper.getWorkplaceGenericName()), Optional.ofNullable(mapper.getWorkplaceDisplayName()), Optional.ofNullable(mapper.getHierarchyCode()))).collect(Collectors.toList());
                return workplaceInfos;
            }

            @Override
            public List<String> getWKPID(String WKPGRPID) {
                return wplAdapter.getWKPID(companyId, WKPGRPID);
            }
        }, period.end());

        List<DateInformation> dateInformationList = new ArrayList<>();
        // Loop：年月日 in Input.対象期間
        period.datesBetween().forEach(date -> {
            // 作成する(Require, 年月日, 対象組織識別情報)
            DateInformation information = DateInformation.create(new DateInformation.Require() {
                @Override
                public List<WorkplaceSpecificDateItem> getWorkplaceSpecByDate(String workplaceId, GeneralDate specificDate) {
                    List<WorkplaceSpecificDateItem> data = workplaceSpecificDateRepo.getWorkplaceSpecByDate(workplaceId,
                            specificDate);
                    return data;
                }

                @Override
                public List<CompanySpecificDateItem> getComSpecByDate(GeneralDate specificDate) {
                    List<CompanySpecificDateItem> data = companySpecificDateRepo
                            .getComSpecByDate(companyId, specificDate);
                    return data;
                }

                @Override
                public Optional<WorkplaceEvent> findByPK(String workplaceId, GeneralDate date) {
                    Optional<WorkplaceEvent> data = workplaceEventRepo.findByPK(workplaceId, date);
                    return data;
                }

                @Override
                public Optional<CompanyEvent> findCompanyEventByPK(GeneralDate date) {
                    Optional<CompanyEvent> data = companyEventRepo.findByPK(companyId, date);
                    return data;
                }

                @Override
                public Optional<PublicHoliday> getHolidaysByDate(GeneralDate date) {
                    return publicHolidayRepo.getHolidaysByDate(companyId, date);
                }

                @Override
                public List<SpecificDateItem> getSpecifiDateByListCode(List<SpecificDateItemNo> lstSpecificDateItemNo) {
                    if (lstSpecificDateItemNo.isEmpty()) return new ArrayList<>();
                    List<Integer> _lstSpecificDateItemNo = lstSpecificDateItemNo.stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
                    List<SpecificDateItem> data = specificDateItemRepo.getSpecifiDateByListCode(companyId, _lstSpecificDateItemNo);
                    return data;
                }
            }, date, targetOrgIdenInfor);
            dateInformationList.add(information);
        });

        // 出力項目設定情報を取得する
        Optional<ScheduleTableOutputSetting> outputSetting = outputSettingRepo.get(companyId, new OutputSettingCode(outputSettingCode));

        if (!outputSetting.isPresent()) throw new RuntimeException("出力設定 not found!");

        // 表示対象の勤怠項目を取得する
        // 表示対象の個人情報項目を取得する
        List<ScheduleTableAttendanceItem> attendanceItems = new ArrayList<>();
        List<ScheduleTablePersonalInfoItem> personalInfoItems = new ArrayList<>();
        outputSetting.get().getOutputItem().getDetails().forEach(d -> {
            d.getPersonalInfo().ifPresent(item -> personalInfoItems.add(item));
            d.getAdditionalInfo().ifPresent(item -> personalInfoItems.add(item));
            d.getAttendanceItem().ifPresent(item -> attendanceItems.add(item));
        });

        // 5.
        List<PersonalInfoScheduleTable> personalInfoScheduleTableList = personalInfoScheduleDomainService.create(employeeIds, period.end(), personalInfoItems);

        // 6.
        Map<ScheRecGettingAtr, List<IntegrationOfDaily>> integrationOfDailyMap = DailyAttendanceGettingService.get(
            new DailyAttendanceGettingService.Require() {
                @Override
                public List<IntegrationOfDaily> getSchduleList(List<EmployeeId> empIds, DatePeriod period) {
                    List<IntegrationOfDaily> result = new ArrayList<>();
                    empIds.forEach(sid -> {
                        List<IntegrationOfDaily> tmp = integrationOfDailyGetter.getIntegrationOfDaily(sid.v(), period);
                        result.addAll(tmp);
                    });
                    return result;
                }

                @Override
                public List<IntegrationOfDaily> getRecordList(List<EmployeeId> empIds, DatePeriod period) {
                    List<IntegrationOfDaily> result = new ArrayList<>();
                    empIds.forEach(sid -> {
                        List<IntegrationOfDaily> tmp = integrationOfDailyGetter.getIntegrationOfDaily(sid.v(), period);
                        result.addAll(tmp);
                    });
                    return result;
                }
            },
            employeeIds.stream().map(EmployeeId::new).collect(Collectors.toList()),
            period,
            outputSetting.get().getOutputItem().getDailyDataDisplayAtr() == NotUseAtr.USE ? ScheRecGettingAtr.SCHEDULE_WITH_RECORD : ScheRecGettingAtr.ONLY_SCHEDULE
        );
        List<IntegrationOfDaily> integrationOfDailyList = new ArrayList<>();
        integrationOfDailyMap.forEach((scheRecAtr, dailyIntegrations) -> {
            integrationOfDailyList.addAll(dailyIntegrations);
        });

        // 7. 一日分の社員の表示情報を取得す
        List<EmployeeOneDayAttendanceInfo> listEmpOneDayAttendanceInfo = employeeOneDayAttendanceInfoQuery.get(integrationOfDailyList, attendanceItems);

        // 8. 個人計を集計する
        Map<PersonalCounterCategory, Object> personalTotalResult = new HashMap<>();
        if (!outputSetting.get().getPersonalCounterCategories().isEmpty()) {
            personalTotalResult = aggregatePersonalTotalQuery.get(employeeIds, period, closureDate, outputSetting.get().getPersonalCounterCategories(), integrationOfDailyList);
        }

        // 9.
        if (!outputSetting.get().getWorkplaceCounterCategories().isEmpty()) {

        }

        return new PersonalScheduleByWkpDataSource(
                orgUnit,
                period,
                null,
                companyName,
                outputSetting.get(),
                displayInfoOrganization,
                dateInformationList,
                personalInfoScheduleTableList,
                listEmpOneDayAttendanceInfo,
                personalTotalResult
        );
    }
}
