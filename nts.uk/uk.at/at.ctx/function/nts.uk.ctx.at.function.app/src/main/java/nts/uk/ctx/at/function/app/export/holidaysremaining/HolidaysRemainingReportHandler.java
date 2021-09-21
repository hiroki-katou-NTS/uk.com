package nts.uk.ctx.at.function.app.export.holidaysremaining;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.gul.util.value.MutableValue;
import nts.uk.ctx.at.function.app.find.holidaysremaining.HdRemainManageFinder;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.function.dom.adapter.child.ChildNursingLeaveThisMonthFutureSituation;
import nts.uk.ctx.at.function.dom.adapter.child.GetRemainingNumberCareNurseAdapter;
import nts.uk.ctx.at.function.dom.adapter.child.NursingCareLeaveThisMonthFutureSituation;
import nts.uk.ctx.at.function.dom.adapter.holidayover60h.AggrResultOfHolidayOver60hImport;
import nts.uk.ctx.at.function.dom.adapter.holidayover60h.GetHolidayOver60hPeriodAdapter;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.*;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.ComplileInPeriodOfSpecialLeaveAdapter;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.SpecialHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.SpecialVacationImported;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.SpecialVacationImportedKdr;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.ReserveHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.ReservedYearHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.RsvLeaUsedCurrentMonImported;
import nts.uk.ctx.at.function.dom.adapter.vacation.CurrentHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.vacation.StatusHolidayImported;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;
import nts.uk.ctx.at.function.dom.holidaysremaining.VariousVacationControl;
import nts.uk.ctx.at.function.dom.holidaysremaining.VariousVacationControlService;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.*;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.export.SpecialHolidayRemainDataSevice;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.NumberCompensatoryLeavePeriodProcess;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.NumberCompensatoryLeavePeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.CompenLeaveAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.NumberRemainVacationLeaveRangeProcess;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.NumberRemainVacationLeaveRangeQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remainmerge.RemainMerge;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remainmerge.RemainMergeRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcare.ChildNursingLeaveStatus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcare.IGetChildcareRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcare.NursingCareLeaveMonthlyRemaining;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureInfo;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class HolidaysRemainingReportHandler extends ExportService<HolidaysRemainingReportQuery> {

    @Inject
    private HolidaysRemainingReportGenerator reportGenerator;
    @Inject
    private HdRemainManageFinder hdFinder;
    @Inject
    private RegulationInfoEmployeeAdapter regulationInfoEmployeeAdapter;
    @Inject
    private EmployeeInformationAdapter employeeInformationAdapter;
    @Inject
    private HdRemainManageFinder hdRemainManageFinder;
    @Inject
    private ClosureRepository closureRepository;
    @Inject
    private AnnLeaveRemainingAdapter annLeaveAdapter;
    @Inject
    private ComplileInPeriodOfSpecialLeaveAdapter specialLeaveAdapter;
    @Inject
    private ChildNursingLeaveRemainingAdapter childNursingAdapter;
    @Inject
    private NursingLeaveRemainingAdapter nursingLeaveAdapter;
    @Inject
    private VariousVacationControlService varVacaCtrSv;
    @Inject
    private CompanyAdapter companyRepo;
    @Inject
    private ManagedParallelWithContext parallel;
    @Inject
    private HolidayRemainMergeAdapter hdRemainAdapter;
    @Inject
    private NumberRemainVacationLeaveRangeProcess numberRemainVacationLeaveRangeProcess;
    @Inject
    private NumberCompensatoryLeavePeriodProcess numberCompensatoryLeavePeriodProcess;
    @Inject
    private RemainNumberTempRequireService requireService;
    @Inject
    private GetHolidayOver60hPeriodAdapter getHolidayOver60hRemNumWithinPeriodAdapter;
    @Inject
    private RemainMergeRepository repoRemainMer;
    @Inject
    private SpecialHolidayRemainDataSevice rq263;
    @Inject
    private IGetChildcareRemNumEachMonth getChildcareRemNumEachMonth;
    @Inject
    private GetRemainingNumberCareNurseAdapter getRemainingNumberChildCareNurseAdapter;

    @Override
    protected void handle(ExportServiceContext<HolidaysRemainingReportQuery> context) {
        val query = context.getQuery();
        val hdRemainCond = query.getHolidayRemainingOutputCondition();
        String cId = AppContexts.user().companyId();
        val baseDate = GeneralDate.fromString(hdRemainCond.getBaseDate(), "yyyy/MM/dd");
        val startDate = GeneralDate.fromString(hdRemainCond.getStartMonth(), "yyyy/MM/dd");
        val endDate = GeneralDate.fromString(hdRemainCond.getEndMonth(), "yyyy/MM/dd");
        // ドメインモデル「休暇残数管理表の出力項目設定」を取得する
        val _hdManagement = hdFinder.findByLayOutId(hdRemainCond.getLayOutId());
        _hdManagement.ifPresent((HolidaysRemainingManagement hdManagement) -> {
            int closureId = hdRemainCond.getClosureId();
            // ※該当の締めIDが「0：全締め」のときは、「1締め（締めID＝1）」とする
            if (closureId == 0) {
                closureId = 1;
            }
            // アルゴリズム「指定した年月の期間をすべて取得する」を実行する
            Optional<Closure> closureOpt = closureRepository.findById(cId, closureId);
            if (!closureOpt.isPresent()) {
                return;
            }
            // アルゴリズム「指定した年月の期間をすべて取得する」を実行する
            List<DatePeriod> periodByYearMonths = closureOpt.get().getPeriodByYearMonth(endDate.yearMonth());
            // 処理基準日を決定する
            Optional<DatePeriod> criteriaDatePeriodOpt = periodByYearMonths.stream()
                    .max(Comparator.comparing(DatePeriod::end));
            if (!criteriaDatePeriodOpt.isPresent()) {
                return;
            }
            GeneralDate criteriaDate = criteriaDatePeriodOpt.get().end();
            // 画面項目「A2_2：社員リスト」で選択されている社員の社員ID
            List<String> employeeIds = query.getLstEmpIds().stream().map(EmployeeQuery::getEmployeeId)
                    .collect(Collectors.toList());
            // <<Public>> 社員を並べ替える
            employeeIds = this.regulationInfoEmployeeAdapter.sortEmployee(
                    cId,
                    employeeIds,
                    AppContexts.system().getInstallationType().value, null, null,
                    GeneralDateTime.legacyDateTime(criteriaDate.date()));

            Map<String, EmployeeQuery> empMap = query.getLstEmpIds().stream()
                    .collect(Collectors.toMap(EmployeeQuery::getEmployeeId, Function.identity()));

            // <<Public>> 社員の情報を取得する
            List<EmployeeInformationImport> listEmployeeInformationImport = employeeInformationAdapter
                    .getEmployeeInfo(new EmployeeInformationQueryDtoImport(employeeIds, criteriaDate, true, false, true,
                            true, false, false));
            // 出力するデータ件数をチェックする
            if (listEmployeeInformationImport.isEmpty()) {
                throw new BusinessException("Msg_885");
            }
            val varVacaCtr = varVacaCtrSv.getVariousVacationControl();
            val closureInforOpt = this.getClosureInfo(closureId);

            if (!varVacaCtr.isAnnualHolidaySetting()) {
                hdManagement.getListItemsOutput().getAnnualHoliday().setYearlyHoliday(false);
            }

            if (!varVacaCtr.isYearlyReservedSetting()) {
                hdManagement.getListItemsOutput().getYearlyReserved().setYearlyReserved(false);
            }

            if (!varVacaCtr.isSubstituteHolidaySetting()) {
                hdManagement.getListItemsOutput().getSubstituteHoliday().setOutputItemSubstitute(false);
            }
            if (!varVacaCtr.isPauseItemHolidaySettingCompany()) {
                hdManagement.getListItemsOutput().getPause().setPauseItem(false);
            }
            List<Integer> checkItem = hdManagement.getListItemsOutput().getSpecialHoliday();
            boolean listSpecialHoliday = true;
            for (Integer item : checkItem) {
                for (SpecialHoliday list : varVacaCtr.getListSpecialHoliday()) {
                    if (list.getSpecialHolidayCode().v().equals(item)) {
                        listSpecialHoliday = true;
                        break;
                    } else {
                        listSpecialHoliday = false;
                    }
                }

            }
            if (!listSpecialHoliday) {
                hdManagement.getListItemsOutput().setSpecialHoliday(new ArrayList<>());
            }

            if (!hdManagement.getListItemsOutput().getAnnualHoliday().isYearlyHoliday()
                    && !hdManagement.getListItemsOutput().getYearlyReserved().isYearlyReserved()
                    && !hdManagement.getListItemsOutput().getSubstituteHoliday().isOutputItemSubstitute()
                    && !hdManagement.getListItemsOutput().getPause().isPauseItem()
                    && (hdManagement.getListItemsOutput().getSpecialHoliday().size() == 0)) {

                throw new BusinessException("Msg_885");
            }
            MutableValue<Boolean> isSameCurrentMonth = new MutableValue<>();
            MutableValue<Boolean> isFirstEmployee = new MutableValue<>();
            isSameCurrentMonth.set(true);
            isFirstEmployee.set(true);
            MutableValue<YearMonth> currentMonthOfFirstEmp = new MutableValue<>();
            Map<String, HolidaysRemainingEmployee> mapTmp = Collections
                    .synchronizedMap(new HashMap<String, HolidaysRemainingEmployee>());
            parallel.forEach(listEmployeeInformationImport, emp -> {
                String wpCode = emp.getWorkplace() != null ? emp.getWorkplace().getWorkplaceCode() : "";
                String wpName = emp.getWorkplace() != null ? emp.getWorkplace().getWorkplaceName()
                        : TextResource.localize("KDR001_55");
                String empmentName = emp.getEmployment() != null ? emp.getEmployment().getEmploymentName() : "";
                String positionName = emp.getPosition() != null ? emp.getPosition().getPositionName() : "";
                String employmentCode = emp.getEmployment() != null ? emp.getEmployment().getEmploymentCode() : "";
                String positionCode = emp.getPosition() != null ? emp.getPosition().getPositionCode() : "";
                Optional<YearMonth> currentMonth = hdRemainManageFinder.getCurrentMonth(
                        cId,
                        emp.getEmployeeId(),
                        baseDate);

                if (isFirstEmployee.get()) {
                    isFirstEmployee.set(false);
                    currentMonthOfFirstEmp.set(currentMonth.orElse(null));
                } else {
                    if (isSameCurrentMonth.get() && currentMonth.isPresent() && !currentMonth.get().equals(currentMonthOfFirstEmp.get())) {
                        isSameCurrentMonth.set(false);
                    }
                }
                HolidayRemainingInfor holidayRemainingInfor = this.getHolidayRemainingInfor(
                        varVacaCtr,
                        closureInforOpt,
                        emp.getEmployeeId(),
                        baseDate,
                        startDate,
                        endDate,
                        currentMonth);
                mapTmp.put(emp.getEmployeeId(),
                        new HolidaysRemainingEmployee(
                                emp.getEmployeeId(),
                                emp.getEmployeeCode(),
                                empMap.get(emp.getEmployeeId()).getEmployeeName(),
                                empMap.get(emp.getEmployeeId()).getWorkplaceId(),
                                wpCode,
                                wpName,
                                empmentName,
                                employmentCode,
                                positionName,
                                positionCode,
                                currentMonth,
                                holidayRemainingInfor));
            });

            Map<String, HolidaysRemainingEmployee> mapEmp = new HashMap<>(mapTmp);
            Optional<CompanyInfor> companyCurrent = this.companyRepo.getCurrentCompany();

            HolidayRemainingDataSource dataSource = new HolidayRemainingDataSource(
                    hdRemainCond.getStartMonth(),
                    hdRemainCond.getEndMonth(),
                    varVacaCtr, hdRemainCond.getPageBreak(),
                    hdRemainCond.getBaseDate(),
                    hdManagement, isSameCurrentMonth.get(),
                    employeeIds, mapEmp,
                    companyCurrent.isPresent() ? companyCurrent.get().getCompanyName() : "",
                    hdRemainCond.getTitle());

            this.reportGenerator.generate(context.getGeneratorContext(), dataSource);
        });
    }

    private HolidayRemainingInfor getHolidayRemainingInfor(VariousVacationControl variousVacationControl,
                                                           Optional<ClosureInfo> closureInforOpt,
                                                           String employeeId,
                                                           GeneralDate baseDate,
                                                           GeneralDate startDate,
                                                           GeneralDate endDate,
                                                           Optional<YearMonth> currMonth) {
        // 263New
        List<SpecialHolidayRemainDataOutputKdr> getSpeHdOfConfMonVer2;
        // RequestList369
        Optional<GeneralDate> grantDate = Optional.empty();
        // RequestList281
        List<AnnLeaGrantNumberImported> listAnnLeaGrantNumber = null;
        // RequestList265
        AnnLeaveOfThisMonthImported annLeaveOfThisMonth = null;
        // RequestList255
        List<AnnualLeaveUsageImported> listAnnualLeaveUsage = new ArrayList<>();
        // RequestList363
        List<AnnLeaveUsageStatusOfThisMonthImported> listAnnLeaveUsageStatusOfThisMonth = null;
        // RequestList268
        ReserveHolidayImported reserveHoliday = null;
        // RequestList258
        List<ReservedYearHolidayImported> listReservedYearHoliday = null;
        // RequestList364
        List<RsvLeaUsedCurrentMonImported> listRsvLeaUsedCurrentMon = null;
        // RequestList269
        List<CurrentHolidayImported> listCurrentHoliday = new ArrayList<>();
        // RequestList259
        List<StatusHolidayImported> listStatusHoliday = null;
        // RequestList270
        List<CurrentHolidayRemainImported> listCurrentHolidayRemain = new ArrayList<>();
        // RequestList260
        List<StatusOfHolidayImported> listStatusOfHoliday = null;
        // RequestList206
        ChildNursingLeaveCurrentSituationImported childNursingLeave = null;
        // RequestList207
        NursingLeaveCurrentSituationImported nursingLeave = null;
        //add by HieuLT
        CurrentHolidayImported currentHolidayLeft = null;
        CurrentHolidayRemainImported currentHolidayRemainLeft = null;
        List<AggrResultOfAnnualLeaveEachMonthKdr> getRs363 = new ArrayList<>();
        //  RQ 203 right
        Map<YearMonth, SubstituteHolidayAggrResult> substituteHolidayAggrResultsRight = new HashMap<>();
        //  RQ 203 left
        SubstituteHolidayAggrResult substituteHolidayAggrResult;
        // RQ 342
        List<ChildNursingLeaveStatus> monthlyConfirmedCareForEmployees;
        // RQ 344
        List<NursingCareLeaveMonthlyRemaining> obtainMonthlyConfirmedCareForEmployees;
        List<ChildNursingLeaveThisMonthFutureSituation> childCareRemNumWithinPeriodRight = Collections.emptyList();
        ChildNursingLeaveThisMonthFutureSituation childCareRemNumWithinPeriodLeft;

        List<NursingCareLeaveThisMonthFutureSituation> nursingCareLeaveThisMonthFutureSituationRight = Collections.emptyList();
        NursingCareLeaveThisMonthFutureSituation nursingCareLeaveThisMonthFutureSituationLeft;

        if (!closureInforOpt.isPresent()) {
            return null;
        }
        YearMonth currentMonth = closureInforOpt.get().getCurrentMonth();
        val cId = AppContexts.user().companyId();
        val datePeriod = new DatePeriod(startDate, endDate);
        // hoatt
        YearMonthPeriod period = new YearMonthPeriod(startDate.yearMonth(), currentMonth.previousMonth());
        // Mer RQ255,258,259,260,263
        HolidayRemainMerEx hdRemainMer = hdRemainAdapter.getRemainMer(employeeId, period);
        val lstYrMon = ConvertHelper.yearMonthsBetween(period);
        Map<YearMonth, List<RemainMerge>> mapRemainMer = repoRemainMer.findBySidsAndYrMons(employeeId, lstYrMon);
        // Mer RQ265,268,269,363,364,369
        boolean call265 = variousVacationControl.isAnnualHolidaySetting();
        boolean call268 = variousVacationControl.isYearlyReservedSetting();
        boolean call269 = variousVacationControl.isSubstituteHolidaySetting();
        boolean call363 = variousVacationControl.isAnnualHolidaySetting()
                && currentMonth.compareTo(endDate.yearMonth()) <= 0;
        boolean call364 = variousVacationControl.isYearlyReservedSetting()
                && currentMonth.compareTo(endDate.yearMonth()) <= 0;
        boolean call369 = variousVacationControl.isAnnualHolidaySetting();
        CheckCallRequest check = new CheckCallRequest(call265, call268, call269, call363, call364, call369);
        HdRemainDetailMerEx remainDel = hdRemainAdapter.getRemainDetailMer(employeeId, currentMonth, baseDate,
                new DatePeriod(startDate, endDate), check);
        if (variousVacationControl.isAnnualHolidaySetting()) {
            // Call RequestList369
            grantDate = remainDel.getResult369();
            // Call RequestList281
            listAnnLeaGrantNumber = annLeaveAdapter.getAnnLeaGrantNumberImporteds(employeeId);
            listAnnLeaGrantNumber = listAnnLeaGrantNumber.stream()
                    .sorted(Comparator.comparing(AnnLeaGrantNumberImported::getGrantDate)).collect(Collectors.toList());
            // Call RequestList265
            annLeaveOfThisMonth = remainDel.getResult265();
            // Call RequestList255 ver2 - hoatt
            if (currentMonth.compareTo(startDate.yearMonth()) > 0) {
                listAnnualLeaveUsage = hdRemainMer.getResult255();
            }
            // Call RequestList363
            if (currentMonth.compareTo(endDate.yearMonth()) <= 0) {
                listAnnLeaveUsageStatusOfThisMonth = remainDel.getResult363();
                getRs363 = hdRemainAdapter.getRs363(employeeId, currentMonth, baseDate,
                        new DatePeriod(startDate, endDate), true);
            }
        }

        if (variousVacationControl.isYearlyReservedSetting()) {
            // Call RequestList268
            reserveHoliday = remainDel.getResult268();
            // Call RequestList258 ver2 - hoatt
            if (currentMonth.compareTo(startDate.yearMonth()) > 0) {
                listReservedYearHoliday = hdRemainMer.getResult258();
            }
            // Call RequestList364
            if (currentMonth.compareTo(endDate.yearMonth()) <= 0) {
                listRsvLeaUsedCurrentMon = remainDel.getResult364();
            }
        }
        if (variousVacationControl.isSubstituteHolidaySetting()) {

            val breakDayOffMngInPeriodQueryRequire = numberRemainVacationLeaveRangeProcess.createRequire();

            // Call RequestList269
            for (YearMonth s = currentMonth; s.lessThanOrEqualTo(endDate.yearMonth()); s = s.addMonths(1)) {
                GeneralDate end = GeneralDate.ymd(s.year(), s.month(), 1).addMonths(1).addDays(-1);
                DatePeriod periodDate = new DatePeriod(GeneralDate.ymd(s.year(), s.month(), 1),
                        endDate.before(end) ? endDate : end);
                BreakDayOffRemainMngRefactParam inputRefactor = new BreakDayOffRemainMngRefactParam(
                        cId, employeeId,
                        periodDate,
                        false,
                        closureInforOpt.get().getPeriod().end(),
                        false,
                        new ArrayList<>(),
                        Optional.empty(),
                        Optional.empty(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        Optional.empty(), new FixedManagementDataMonth());
                SubstituteHolidayAggrResult currentHoliday = NumberRemainVacationLeaveRangeQuery
                        .getBreakDayOffMngInPeriod(breakDayOffMngInPeriodQueryRequire, inputRefactor);

                listCurrentHoliday.add(new CurrentHolidayImported(s, currentHoliday.getCarryoverDay().v(),
                        currentHoliday.getOccurrenceDay().v(), currentHoliday.getDayUse().v(),
                        currentHoliday.getUnusedDay().v(), currentHoliday.getRemainDay().v()));
            }
            // Call RequestList259 ver2 - hoatt
            if (currentMonth.compareTo(startDate.yearMonth()) > 0) {
                listStatusHoliday = hdRemainMer.getResult259();
            }
            DatePeriod periodDate = new DatePeriod(GeneralDate.ymd(currentMonth.year(), currentMonth.month(), 1), GeneralDate.ymd(currentMonth.year(), currentMonth.month(), 1).addMonths(1).addDays(-1));
            BreakDayOffRemainMngRefactParam inputRefactor = new BreakDayOffRemainMngRefactParam(
                    cId, employeeId,
                    periodDate,
                    false,
                    closureInforOpt.get().getPeriod().end(),
                    false,
                    new ArrayList<>(),
                    Optional.empty(),
                    Optional.empty(),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    Optional.empty(), new FixedManagementDataMonth());
            SubstituteHolidayAggrResult currentHoliday = NumberRemainVacationLeaveRangeQuery
                    .getBreakDayOffMngInPeriod(breakDayOffMngInPeriodQueryRequire, inputRefactor);
            currentHolidayLeft = new CurrentHolidayImported(
                    currentMonth,
                    currentHoliday.getCarryoverDay().v(),
                    currentHoliday.getOccurrenceDay().v(),
                    currentHoliday.getDayUse().v(),
                    currentHoliday.getUnusedDay().v(),
                    currentHoliday.getRemainDay().v());
        }
        if (variousVacationControl.isPauseItemHolidaySetting()) {
            for (YearMonth s = currentMonth; s.lessThanOrEqualTo(endDate.yearMonth()); s = s.addMonths(1)) {
                GeneralDate end = GeneralDate.ymd(s.year(), s.month(), 1).addMonths(1).addDays(-1);
                DatePeriod periodDate = new DatePeriod(GeneralDate.ymd(s.year(), s.month(), 1),
                        endDate.before(end) ? endDate : end);
                val mngParam = new AbsRecMngInPeriodRefactParamInput(cId, employeeId, periodDate,
                        closureInforOpt.get().getPeriod().end(), false, false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), Optional.empty()
                        , Optional.empty()
                        , Optional.empty(),
                        new FixedManagementDataMonth());
                CompenLeaveAggrResult remainMng = NumberCompensatoryLeavePeriodQuery
                        .process(numberCompensatoryLeavePeriodProcess.createRequire(), mngParam);
                listCurrentHolidayRemain.add(
                        new CurrentHolidayRemainImported(
                                s,
                                remainMng.getCarryoverDay().v(),
                                remainMng.getOccurrenceDay().v(),
                                remainMng.getDayUse().v(),
                                remainMng.getUnusedDay().v(),
                                remainMng.getRemainDay().v()));
            }
            // Call RequestList260 ver2 - hoatt
            if (currentMonth.compareTo(startDate.yearMonth()) > 0) {
                listStatusOfHoliday = hdRemainMer.getResult260();
            }
            DatePeriod periodDate = new DatePeriod(GeneralDate.ymd(currentMonth.year(), currentMonth.month(), 1), GeneralDate.ymd(currentMonth.year(), currentMonth.month(), 1).addMonths(1).addDays(-1));
            val param = new AbsRecMngInPeriodRefactParamInput(
                    cId,
                    employeeId,
                    periodDate,
                    closureInforOpt.get().getPeriod().end(),
                    false,
                    false,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    Optional.empty()
                    , Optional.empty()
                    , Optional.empty(),
                    new FixedManagementDataMonth());
            CompenLeaveAggrResult remainMng = NumberCompensatoryLeavePeriodQuery.process(
                    numberCompensatoryLeavePeriodProcess.createRequire(), param);
            currentHolidayRemainLeft = new CurrentHolidayRemainImported(
                    currentMonth,
                    remainMng.getCarryoverDay().v(),
                    remainMng.getOccurrenceDay().v(),
                    remainMng.getDayUse().v(),
                    remainMng.getUnusedDay().v(),
                    remainMng.getRemainDay().v());
        }
        // hoatt
        Map<Integer, SpecialVacationImported> mapSpecVaca = new HashMap<>();
        Map<Integer, SpecialVacationImportedKdr> map273New = new HashMap<>();
        Map<YearMonth, Map<Integer, SpecialVacationImported>> lstMapSPVaCurrMon = new HashMap<>();// key
        Map<YearMonth, Map<Integer, SpecialVacationImportedKdr>> lstMap273CurrMon = new HashMap<>();// key
        Map<Integer, List<SpecialHolidayImported>> mapSpeHd = new HashMap<>();
        // Call RequestList273
        if (currMonth.isPresent() && currMonth.get().lessThanOrEqualTo(endDate.yearMonth())) {
            List<YearMonth> lstMon = new ArrayList<>();
            YearMonth monCheck = currMonth.get().greaterThanOrEqualTo(startDate.yearMonth()) ? currMonth.get()
                    : startDate.yearMonth();
            for (YearMonth i = monCheck; i.lessThanOrEqualTo(endDate.yearMonth()); i = i.addMonths(1)) {
                lstMon.add(i);
            }
            for (YearMonth ym : lstMon) {// year mon
                Map<Integer, SpecialVacationImported> mapSPVaCurrMon = new HashMap<>();
                Map<Integer, SpecialVacationImportedKdr> mapSP273CurrMon = new HashMap<>();
                for (SpecialHoliday specialHolidayDto : variousVacationControl.getListSpecialHoliday()) {// sphdCd
                    int sphdCode = specialHolidayDto.getSpecialHolidayCode().v();
                    YearMonth ymEnd = ym.addMonths(1);
                    SpecialVacationImported spVaImported = specialLeaveAdapter.complileInPeriodOfSpecialLeave(cId,
                            employeeId,
                            new DatePeriod(GeneralDate.ymd(ym.year(), ym.month(), 1),
                                    GeneralDate.ymd(ymEnd.year(), ymEnd.month(), 1).addDays(-1)),
                            false, baseDate, sphdCode, false);

                    SpecialVacationImportedKdr spVaImportedNew = specialLeaveAdapter.get273New(cId,
                            employeeId,
                            new DatePeriod(GeneralDate.ymd(ym.year(), ym.month(), 1),
                                    GeneralDate.ymd(ymEnd.year(), ymEnd.month(), 1).addDays(-1)),
                            false, baseDate, sphdCode, false);

                    mapSPVaCurrMon.put(sphdCode, spVaImported);
                    mapSP273CurrMon.put(sphdCode, spVaImportedNew);
                }
                lstMapSPVaCurrMon.put(ym, mapSPVaCurrMon);
                lstMap273CurrMon.put(ym, mapSP273CurrMon);
            }
        }
        for (SpecialHoliday specialHolidayDto : variousVacationControl.getListSpecialHoliday()) {
            int sphdCode = specialHolidayDto.getSpecialHolidayCode().v();
            // Call RequestList273
            SpecialVacationImported specialVacationImported = specialLeaveAdapter.complileInPeriodOfSpecialLeave(
                    cId,
                    employeeId,
                    closureInforOpt.get().getPeriod(),
                    false,
                    baseDate,
                    sphdCode,
                    false);
            SpecialVacationImportedKdr specialVacationImportedNew = specialLeaveAdapter.get273New(
                    cId,
                    employeeId,
                    closureInforOpt.get().getPeriod(),
                    false,
                    baseDate,
                    sphdCode,
                    false);
            mapSpecVaca.put(sphdCode, specialVacationImported);
            map273New.put(sphdCode, specialVacationImportedNew);
            // Call RequestList263 ver2 -
            if (currentMonth.compareTo(startDate.yearMonth()) > 0) {
                List<SpecialHolidayImported> specialHolidayList = specialLeaveAdapter.getSpeHoliOfConfirmedMonthly(
                        employeeId,
                        startDate.yearMonth(),
                        currentMonth.previousMonth(),
                        Collections.singletonList(sphdCode));
                mapSpeHd.put(sphdCode, specialHolidayList);

            } else {
                mapSpeHd.put(sphdCode, new ArrayList<>());
            }
        }

        if (variousVacationControl.isChildNursingSetting()) {
            // Call RequestList206
            childNursingLeave = childNursingAdapter.getChildNursingLeaveCurrentSituation(
                    cId,
                    employeeId,
                    datePeriod);
        }

        if (variousVacationControl.isNursingCareSetting()) {
            // Call RequestList207
            nursingLeave = nursingLeaveAdapter.getNursingLeaveCurrentSituation(
                    cId,
                    employeeId,
                    datePeriod);
        }
        val rq = requireService.createRequire();
        // 期間内の休出代休残数を取得する
        DatePeriod periodDate = new DatePeriod(
                GeneralDate.ymd(currentMonth.year(), currentMonth.month(), 1),
                GeneralDate.ymd(currentMonth.year(), currentMonth.month(), 1).addMonths(1).addDays(-1));
        BreakDayOffRemainMngRefactParam inputRefactor = new BreakDayOffRemainMngRefactParam(
                cId, employeeId,
                periodDate,
                false,
                closureInforOpt.get().getPeriod().end(),
                false,
                new ArrayList<>(),
                Optional.empty(),
                Optional.empty(),
                new ArrayList<>(),
                new ArrayList<>(),
                Optional.empty(), new FixedManagementDataMonth());
        substituteHolidayAggrResult = NumberRemainVacationLeaveRangeQuery
                .getBreakDayOffMngInPeriod(rq, inputRefactor);

        // RequestList203
        for (YearMonth s = currentMonth; s.lessThanOrEqualTo(endDate.yearMonth()); s = s.addMonths(1)) {
            GeneralDate end = GeneralDate.ymd(s.year(), s.month(), 1).addMonths(1).addDays(-1);

            DatePeriod periods = new DatePeriod(
                    GeneralDate.ymd(s.year(), s.month(), 1),
                    endDate.before(end) ? endDate : end);
            BreakDayOffRemainMngRefactParam input = new BreakDayOffRemainMngRefactParam(
                    cId, employeeId,
                    periods,
                    false,
                    closureInforOpt.get().getPeriod().end(),
                    false,
                    new ArrayList<>(),
                    Optional.empty(),
                    Optional.empty(),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    Optional.empty(), new FixedManagementDataMonth());
            val item = NumberRemainVacationLeaveRangeQuery
                    .getBreakDayOffMngInPeriod(rq, input);
            substituteHolidayAggrResultsRight.put(s, item);
        }
        CompenLeaveAggrResult subVaca = null;
        if (closureInforOpt.isPresent()) {
            val param = new AbsRecMngInPeriodRefactParamInput(
                    cId,
                    employeeId,
                    periodDate,
                    closureInforOpt.get().getPeriod().end(),
                    false,
                    false,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    Optional.empty()
                    , Optional.empty()
                    , Optional.empty(),
                    new FixedManagementDataMonth());
            subVaca = NumberCompensatoryLeavePeriodQuery
                    .process(rq, param);
        }
        // [RQ677]期間中の60H超休残数を取得する
        AggrResultOfHolidayOver60hImport aggrResultOfHolidayOver60h = this.getHolidayOver60hRemNumWithinPeriodAdapter.algorithm(
                cId,
                employeeId,
                datePeriod,
                InterimRemainMngMode.OTHER,
                baseDate,
                Optional.of(false),
                Optional.empty(),
                Optional.empty()
        );
        getSpeHdOfConfMonVer2 =
                rq263.getSpeHdOfConfMonVer2(employeeId, period, mapRemainMer)
                        .stream().map(e -> new SpecialHolidayRemainDataOutputKdr(
                        e.getSid(),
                        e.getYm(),
                        e.getSpecialHolidayCd(),
                        e.getUseDays(),
                        e.getBeforeUseDays(),
                        e.getAfterUseDays(),
                        e.getUseTimes(),
                        e.getBeforeUseTimes(),
                        e.getAfterUseTimes(),
                        e.getUseNumber(),
                        e.getRemainDays(),
                        e.getRemainTimes(),
                        e.getBeforeRemainDays(),
                        e.getBeforeRemainTimes(),
                        e.getAfterRemainDays(),
                        e.getAfterRemainTimes()
                )).collect(Collectors.toList());
        // RQ 342
        monthlyConfirmedCareForEmployees = getChildcareRemNumEachMonth.getMonthlyConfirmedCareForEmployees(employeeId, lstYrMon);
        // RQ 344
        obtainMonthlyConfirmedCareForEmployees = getChildcareRemNumEachMonth.getObtainMonthlyConfirmedCareForEmployees(employeeId, lstYrMon);


        for (YearMonth s = currentMonth; s.lessThanOrEqualTo(endDate.yearMonth()); s = s.addMonths(1)) {
            GeneralDate end = GeneralDate.ymd(s.year(), s.month(), 1).addMonths(1).addDays(-1);

            DatePeriod periods = new DatePeriod(
                    GeneralDate.ymd(s.year(), s.month(), 1),
                    endDate.before(end) ? endDate : end);
            childCareRemNumWithinPeriodRight.add(getRemainingNumberChildCareNurseAdapter
                    .getChildCareRemNumWithinPeriod(
                            cId,
                            employeeId,
                            periods,
                            end));

            nursingCareLeaveThisMonthFutureSituationRight.add(getRemainingNumberChildCareNurseAdapter
                    .getNursingCareLeaveThisMonthFutureSituation(
                            cId,
                            employeeId,
                            periods,
                            end));
        }

        GeneralDate end = GeneralDate.ymd(currentMonth.year(), currentMonth.month(), 1).addMonths(1).addDays(-1);
        DatePeriod periods = new DatePeriod(GeneralDate.ymd(currentMonth.year(), currentMonth.month(), 1),
                endDate.before(end) ? endDate : end);

        childCareRemNumWithinPeriodLeft = getRemainingNumberChildCareNurseAdapter
                .getChildCareRemNumWithinPeriod(cId, employeeId, periods, end);

        nursingCareLeaveThisMonthFutureSituationLeft = getRemainingNumberChildCareNurseAdapter
                .getNursingCareLeaveThisMonthFutureSituation(cId, employeeId, periods, end);

        return new HolidayRemainingInfor(
                grantDate,
                listAnnLeaGrantNumber,
                annLeaveOfThisMonth,
                listAnnualLeaveUsage,
                listAnnLeaveUsageStatusOfThisMonth,
                reserveHoliday,
                listReservedYearHoliday,
                listRsvLeaUsedCurrentMon,
                listCurrentHoliday,
                listStatusHoliday,
                listCurrentHolidayRemain,
                listStatusOfHoliday,
                mapSpecVaca,
                lstMapSPVaCurrMon,
                mapSpeHd,
                childNursingLeave,
                nursingLeave,
                currentHolidayLeft,
                currentHolidayRemainLeft,
                substituteHolidayAggrResult,
                subVaca,
                aggrResultOfHolidayOver60h,
                getRs363,
                getSpeHdOfConfMonVer2,
                substituteHolidayAggrResultsRight,
                closureInforOpt,
                lstMap273CurrMon,
                map273New,
                monthlyConfirmedCareForEmployees,
                obtainMonthlyConfirmedCareForEmployees,
                childCareRemNumWithinPeriodLeft,
                childCareRemNumWithinPeriodRight,
                nursingCareLeaveThisMonthFutureSituationRight,
                nursingCareLeaveThisMonthFutureSituationLeft);
    }

    private Optional<ClosureInfo> getClosureInfo(int closureId) {

        val listClosureInfo = ClosureService.getAllClosureInfo(ClosureService.createRequireM2(closureRepository));
        return listClosureInfo.stream().filter(i -> i.getClosureId().value == closureId).findFirst();
    }
}