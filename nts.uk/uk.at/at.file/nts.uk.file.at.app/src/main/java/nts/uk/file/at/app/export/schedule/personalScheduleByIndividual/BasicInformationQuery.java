package nts.uk.file.at.app.export.schedule.personalScheduleByIndividual;

import lombok.experimental.var;
import lombok.val;
import nts.arc.layer.app.cache.DateHistoryCache;
import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScWorkplaceAdapter;
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
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.GetLegalWorkTimeOfEmployeeService;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.LegalWorkTimeOfEmployee;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonAndWeekStatutoryTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyFlexStatutoryLaborTime;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentHisScheduleAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.file.at.app.export.schedule.personalScheduleByIndividual.dto.BasicInformationDto;
import nts.uk.file.at.app.export.schedule.personalScheduleByIndividual.dto.WorkPlaceHistoryDto;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 基本情報取得
 */
@Stateless
public class BasicInformationQuery {
    @Inject
    private CompanyAdapter company;
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
    private ScWorkplaceAdapter scWorkplaceAdapter;

    @Inject
    private WorkplaceAdapter workplaceAdapter;
    @Inject
    private AffWorkplaceHistoryRepository workplaceHistoryRepository;

    @Inject
    private AffWorkplaceHistoryItemRepository affWorkplaceHistoryItemRepository;

    @Inject
    private WorkingConditionItemRepository workingConditionItemRepository;

    @Inject
    private EmploymentHisScheduleAdapter employmentHisScheduleAdapter;


    /**
     * 取得する
     *
     * @param employeeId
     * @param period
     * @return BasicInformationDto
     */
    public <T> BasicInformationDto<T> get(String employeeId, DatePeriod period) {
        String companyId = AppContexts.user().companyId();
        // 基本情報取得
        // [RQ622]会社IDから会社情報を取得する
        String companyName = company.getCurrentCompany().orElseGet(() -> {
            throw new RuntimeException("System Error: Company Info");
        }).getCompanyName();

        //[No.650]社員が所属している職場を取得する
        val workplaceId = scWorkplaceAdapter.getAffWkpHistItemByEmpDate(companyId, period.end()).getWorkplaceId();
        List<String> workplaceIds = new ArrayList<>();
        if (!workplaceId.isEmpty()) {
            workplaceIds.add(workplaceId);
        }
        //[No.560]職場IDから職場の情報をすべて取得する
        val workPlaceInforExports = workplaceAdapter.findWkpByWkpIdList(companyId, period.end(), workplaceIds);

        //社員（List）と期間から職場履歴を取得する
        var workPlaceHitory = getWorkPlaceHitory(employeeId, period);
        final DateHistoryCache<WorkPlaceHistoryDto> historyCache;
        historyCache = new DateHistoryCache<>(workPlaceHitory.stream()
                .map(h -> DateHistoryCache.Entry.of(h.getDatePeriod(), h))
                .collect(Collectors.toList()));
        List<DateInformation> dateInformationList = new ArrayList<>();
        // Loop：年月日 in Input.対象期間
        period.datesBetween().forEach(date -> {
            var HistoryData = historyCache.get(date);
            var workPplaceId = HistoryData.get().getHistoryItem().getWorkplaceId();
            if (HistoryData.isPresent() && !workPplaceId.isEmpty()) {
                TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.creatIdentifiWorkplace(workPplaceId);
                // 作成する(Require, 年月日, 対象組織識別情報)
                DateInformation information = DateInformation.create(new DateInformation.Require() {
                    @Override
                    public List<WorkplaceSpecificDateItem> getWorkplaceSpecByDate(String workplaceId, GeneralDate specificDate) {
                        return workplaceSpecificDateRepo.getWorkplaceSpecByDate(workplaceId, specificDate);
                    }

                    @Override
                    public List<CompanySpecificDateItem> getComSpecByDate(GeneralDate specificDate) {
                        return companySpecificDateRepo.getComSpecByDate(companyId, specificDate);
                    }

                    @Override
                    public Optional<WorkplaceEvent> findByPK(String workplaceId, GeneralDate date) {
                        return workplaceEventRepo.findByPK(workplaceId, date);
                    }

                    @Override
                    public Optional<CompanyEvent> findCompanyEventByPK(GeneralDate date) {
                        return companyEventRepo.findByPK(companyId, date);
                    }

                    @Override
                    public Optional<PublicHoliday> getHolidaysByDate(GeneralDate date) {
                        return publicHolidayRepo.getHolidaysByDate(companyId, date);
                    }

                    @Override
                    public List<SpecificDateItem> getSpecifiDateByListCode(List<SpecificDateItemNo> lstSpecificDateItemNo) {
                        if (lstSpecificDateItemNo.isEmpty()) return new ArrayList<>();
                        List<Integer> _lstSpecificDateItemNo = lstSpecificDateItemNo.stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
                        return specificDateItemRepo.getSpecifiDateByListCode(companyId, _lstSpecificDateItemNo);
                    }
                }, date, targetOrgIdenInfor);
                dateInformationList.add(information);
            }
        });
        //Optional<社員の法定労働時間>
        Optional<LegalWorkTimeOfEmployee> legalWorkTimeOfEmployee = GetLegalWorkTimeOfEmployeeService.get(new GetLegalWorkTimeOfEmployeeService.Require() {
            @Override
            public Optional<WorkingConditionItem> getHistoryItemBySidAndBaseDate(String sid, GeneralDate baseDate) {
                return workingConditionItemRepository.getBySidAndStandardDate(sid, baseDate);
            }

            @Override
            public List<EmploymentPeriodImported> getEmploymentHistories(String sid, DatePeriod datePeriod) {
                return employmentHisScheduleAdapter.getEmploymentPeriod(Arrays.asList(sid), period);
            }

            @Override
            public MonthlyFlexStatutoryLaborTime flexMonAndWeekStatutoryTime(YearMonth ym, String employmentCd, String employeeId, GeneralDate baseDate) {
                return RequiredDependency.flexMonAndWeekStatutoryTime(companyId, employmentCd, employeeId, baseDate, ym);
            }

            @Override
            public Optional<MonAndWeekStatutoryTime> monAndWeekStatutoryTime(YearMonth ym, String employmentCd, String employeeId, GeneralDate baseDate, WorkingSystem workingSystem) {
                return RequiredDependency.monAndWeekStatutoryTime(companyId, employmentCd, employeeId, baseDate, ym, workingSystem);
            }
        }, employeeId, period);

        return new BasicInformationDto(
                companyName,
                workPlaceInforExports.get(0),
                dateInformationList,
                legalWorkTimeOfEmployee
        );
    }

    /**
     * 社員（List）と期間から職場履歴を取得する
     *
     * @param employeeId
     * @param period
     */
    private List<WorkPlaceHistoryDto> getWorkPlaceHitory(String employeeId, DatePeriod period) {
        var affWorkplaceHistoryOpt = workplaceHistoryRepository.findByEmployeesWithPeriod(Arrays.asList(employeeId), period).stream().findFirst();
        AffWorkplaceHistory affWorkplaceHistory = null;
        List<WorkPlaceHistoryDto> historyDtoList = new ArrayList<>();
        if (affWorkplaceHistoryOpt.isPresent()) {
            affWorkplaceHistory = affWorkplaceHistoryOpt.get();
            val historyItems = affWorkplaceHistory.getHistoryItems().stream().filter(
                    item -> {
                        val itemPriod = item.span();
                        return (itemPriod.start().beforeOrEquals(period.end()) && period.start().beforeOrEquals(itemPriod.end()));
                    }
            ).collect(Collectors.toList());
            val affWorkplaceHistoryItems = affWorkplaceHistoryItemRepository.findByHistIds(historyItems.stream().map(x -> x.identifier()).collect(Collectors.toList()));
            historyItems.forEach(item -> {
                val history = affWorkplaceHistoryItems.stream().filter(x -> x.getHistoryId().equals(item.identifier())).findFirst();
                if (history.isPresent()) {
                    historyDtoList.add(new WorkPlaceHistoryDto(
                            item.span(),
                            history.get()
                    ));
                }
            });
        }
        return historyDtoList;
    }
}
