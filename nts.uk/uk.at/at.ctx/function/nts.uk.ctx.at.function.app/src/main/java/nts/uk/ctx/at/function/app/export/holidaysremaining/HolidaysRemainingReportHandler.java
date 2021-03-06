package nts.uk.ctx.at.function.app.export.holidaysremaining;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.cache.CacheCarrier;
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
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmploymentImport;
import nts.uk.ctx.at.function.dom.adapter.child.ChildNursingLeaveThisMonthFutureSituation;
import nts.uk.ctx.at.function.dom.adapter.child.GetRemainingNumberCareNurseAdapter;
import nts.uk.ctx.at.function.dom.adapter.child.NursingCareLeaveThisMonthFutureSituation;
import nts.uk.ctx.at.function.dom.adapter.holidayover60h.AggrResultOfHolidayOver60hImport;
import nts.uk.ctx.at.function.dom.adapter.holidayover60h.GetHolidayOver60hPeriodAdapter;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.*;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.ComplileInPeriodOfSpecialLeaveAdapter;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.SpecialVacationImportedKdr;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.ReserveHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.ReservedYearHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.RsvLeaUsedCurrentMonImported;
import nts.uk.ctx.at.function.dom.adapter.vacation.StatusHolidayImported;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;
import nts.uk.ctx.at.function.dom.holidaysremaining.VariousVacationControl;
import nts.uk.ctx.at.function.dom.holidaysremaining.VariousVacationControlService;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.*;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.EmployeeBasicInfoExport;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoAdapter;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.export.SpecialHolidayRemainDataSevice;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.GetRemainingNumberPublicHolidayService;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param.AggrResultOfPublicHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.interimdata.TempPublicHolidayManagement;
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
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remainmerge.RemainMerge;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remainmerge.RemainMergeRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.care.CareRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcare.ChildNursingLeaveStatus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcare.IGetChildcareRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcare.NursingCareLeaveMonthlyRemaining;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.publicholiday.PublicHolidayRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.workrule.closure.*;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
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
    @Inject
    private ClosureEmploymentRepository closureEmploymentRepository;

    @Inject
    private ClosureRepository closureRepo;

    @Inject
    private PersonEmpBasicInfoAdapter  personEmpBasicInfoAdapter;
    @Inject
    private ShareEmploymentAdapter shareEmploymentAdapter;
    @Inject
    private RecordDomRequireService requireServiceM6;



    @Override
    protected void handle(ExportServiceContext<HolidaysRemainingReportQuery> context) {
        val query = context.getQuery();
        val hdRemainCond = query.getHolidayRemainingOutputCondition();
        String cId = AppContexts.user().companyId();

        // ????????????????????????????????????
        //---------------------------------------------------
        // 2021.12.01 inaguma ??????
        //val baseDate = GeneralDate.fromString(hdRemainCond.getBaseDate(), "yyyy/MM/dd");
        //---------------------------------------------------

        val startDate = GeneralDate.fromString(hdRemainCond.getStartMonth(), "yyyy/MM/dd");		// ??????????????????From(??????)/01
        val endDate   = GeneralDate.fromString(hdRemainCond.getEndMonth(),   "yyyy/MM/dd");		// ??????????????????To  (??????)/?????????

        // ????????????????????????????????????????????????????????????????????????????????????
        val _hdManagement = hdFinder.findByLayOutId(hdRemainCond.getLayOutId());
        _hdManagement.ifPresent((HolidaysRemainingManagement hdManagement) -> {

            // ?????????????????????????????????????????????ID(0???5)??????????????????
            // ??????ID???(1???5)?????????
            int closureId = hdRemainCond.getClosureId();
            // ??????????????????ID??????0?????????????????????????????????1???????????????ID???1???????????????
            if (closureId == 0) {
                closureId = 1;
            }

            //(???) ??????????????????????????????????????????????????????????????????????????????????????????
            //(???) ??????????????????ID???????????????????????????????????????????????????
            Optional<Closure> closureOpt = closureRepository.findById(cId, closureId);
            if (!closureOpt.isPresent()) {
                return;
            }
            // ??????????????????????????????????????????????????????????????????????????????????????????
            List<DatePeriod> periodByYearMonths = closureOpt.get().getPeriodByYearMonth(endDate.yearMonth());
            // ??????????????????????????????
            Optional<DatePeriod> criteriaDatePeriodOpt = periodByYearMonths.stream()
                    .max(Comparator.comparing(DatePeriod::end));
            if (!criteriaDatePeriodOpt.isPresent()) {
                return;
            }
            GeneralDate criteriaDate = criteriaDatePeriodOpt.get().end();	// ???????????????
            //-----------------------------------------------------------------------------------
            // 2021.12.01 ?????? ?????? START
            GeneralDate baseDate = criteriaDate;	// ???????????????
            // 2021.12.01 ?????? ?????? END
            //-----------------------------------------------------------------------------------

            // ???????????????A2_2????????????????????????????????????????????????????????????ID
            List<String> employeeIds = query.getLstEmpIds().stream().map(EmployeeQuery::getEmployeeId)
                    .collect(Collectors.toList());
            // <<Public>> ????????????????????????
            employeeIds = this.regulationInfoEmployeeAdapter.sortEmployee(
                    cId,
                    employeeIds,
                    AppContexts.system().getInstallationType().value, null, null,
                    GeneralDateTime.legacyDateTime(criteriaDate.date()));

            Map<String, EmployeeQuery> empMap = query.getLstEmpIds().stream()
                    .collect(Collectors.toMap(EmployeeQuery::getEmployeeId, Function.identity()));

            // <<Public>> ??????????????????????????????
            List<EmployeeInformationImport> listEmployeeInformationImport = employeeInformationAdapter
                    .getEmployeeInfo(new EmployeeInformationQueryDtoImport(employeeIds, criteriaDate, true, false, true,
                            true, false, false));
            // ????????????????????????????????????????????????
            if (listEmployeeInformationImport.isEmpty()) {
                throw new BusinessException("Msg_885");
            }

            // ??????????????????????????????????????????????????????????????????????????????
            val varVacaCtr = varVacaCtrSv.getVariousVacationControl();

            // ?????????????????????????????????(??????????????????)???????????????????????????????????????(??????)??????????????????????????????
            //---------------------------------------------------
            // 2021.11.30 inaguma ??????
            //val closureInforOpt = this.getClosureInfo(closureId);
            //---------------------------------------------------

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

            // ??????????????????????????????

            // 2021.12.29 - 3S - chinh.hm  - #121957 ?????? START
            YearMonthPeriod yearMonthPeriod = new YearMonthPeriod(startDate.yearMonth(),endDate.yearMonth());
            String companyId = AppContexts.user().companyId();
            UseClassification useClassification = UseClassification.UseClass_Use;

            // ????????????????????????????????????????????????(get domain[closure])
            List<Closure> closureList = closureRepository.findAllActive(companyId, useClassification);
            Map<Integer,OutputPeriodForClosingDto> periodInformationMap = createOutputPeriodForClosing(yearMonthPeriod,closureList);
            //2021.12.29 - 3S - chinh.hm  - # 121957 ??????  END

            ////////////////////////////////////////////////////////////////////////////////
            // ??????????????????START
            ////////////////////////////////////////////////////////////////////////////////
            parallel.forEach(listEmployeeInformationImport, emp -> {
                String wpCode = emp.getWorkplace() != null ? emp.getWorkplace().getWorkplaceCode() : "";
                String wpName = emp.getWorkplace() != null ? emp.getWorkplace().getWorkplaceName() : TextResource.localize("KDR001_55");
                String empmentName = emp.getEmployment() != null ? emp.getEmployment().getEmploymentName() : "";
                String positionName = emp.getPosition() != null ? emp.getPosition().getPositionName() : "";
                String employmentCode = emp.getEmployment() != null ? emp.getEmployment().getEmploymentCode() : "";
                String positionCode = emp.getPosition() != null ? emp.getPosition().getPositionCode() : "";

                //-----------------------------------------------------------------------------------
                // 2021.12.06 - 3S - chinh.hm  - issues #121626- ?????? START
                // ?????????????????????(emp.getEmployeeId())??????????????????????????????????????????????????????????????????????????????
                // ????????????????????????ID?????????????????????????????????????????? ????????????????????????????????????????????????????????????
                EmploymentImport employment = emp.getEmployment();
                if(employment == null){
                    return;
                }
                Optional<ClosureEmployment> closureEmployment = closureEmploymentRepository.findByEmploymentCD(cId,employment.getEmploymentCode());
                if(!closureEmployment.isPresent()){
                    return;
                }
                int empClosureId = closureEmployment.get().getClosureId() ;
                val closureInforOpt = this.getClosureInfo(empClosureId);
                // 2021.12.06 - 3S - chinh.hm  - issues #121626- ?????? END
                //-----------------------------------------------------------------------------------

                // ??????
                Optional<YearMonth> currentMonth = hdRemainManageFinder.getCurrentMonth(
                        cId,
                        emp.getEmployeeId(),
                        baseDate);

                //?????????????????????????????????????????????????????????????????????????????????
                //(Th???c hi???n thu???t to??n ???T???o export period c???a t???ng employee])
                OutputPeriodForClosingDto outputPeriodInformation = periodInformationMap.getOrDefault(empClosureId,null);
                PeriodCorrespondingYm currentPeriod = outputPeriodInformation == null ? null : outputPeriodInformation.getCurrentPeriod();
                OutputPeriodInformation information = outputPeriodInformation == null ? null : outputPeriodInformation.getPeriodInformation();
                CurrentStatusAndPeriodInformation currentStatusAndPeriod =
                        createExportPeriodForEmployee(emp.getEmployeeId(),information,currentPeriod);
                boolean currentStatus = currentStatusAndPeriod != null && currentStatusAndPeriod.isCurrentStatus();
                OutputPeriodInformation periodInformation = currentStatusAndPeriod == null ? null : currentStatusAndPeriod.getOutputPeriodInformation();

                // ?????????
                if (isFirstEmployee.get()) {
                    isFirstEmployee.set(false);
                    currentMonthOfFirstEmp.set(currentMonth.orElse(null));
                } else {
                    if (isSameCurrentMonth.get() && currentMonth.isPresent() && !currentMonth.get().equals(currentMonthOfFirstEmp.get())) {
                        isSameCurrentMonth.set(false);
                    }
                }

                ////////////////////////////////////////////////////////////////////////////////
                // ????????????????????????????????????
                ////////////////////////////////////////////////////////////////////////////////
                HolidayRemainingInfor holidayRemainingInfor = this.getHolidayRemainingInfor(
                        varVacaCtr,				// ????????????????????????
                        closureInforOpt,		// (2021.12.06)????????????????????????????????????????????????
                        emp.getEmployeeId(),	// ??????ID
                        baseDate,				// ???????????????
                        startDate,				// ??????????????????From(??????)/01
                        endDate,				// ??????????????????To  (??????)/?????????
                        // 2022.01.04 - 3S - chinh.hm - issues #122017 - ?????? START
                        periodInformation,		// ??????????????????
                        currentStatus			// ??????????????????
                        // 2022.01.04 - 3S - chinh.hm - issues #122017 - ?????? END
                );

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
            ////////////////////////////////////////////////////////////////////////////////
            // ??????????????????END
            ////////////////////////////////////////////////////////////////////////////////

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
                    hdManagement.getName().v());

            this.reportGenerator.generate(context.getGeneratorContext(), dataSource);
        });
    }

    ////////////////////////////////////////////////////////////////////////////////
    // ????????????????????????????????????
    ////////////////////////////////////////////////////////////////////////////////
    private HolidayRemainingInfor getHolidayRemainingInfor(VariousVacationControl	variousVacationControl,		// ????????????????????????
                                                           Optional<ClosureInfo>	closureInforOpt,			// (2021.12.06)????????????????????????????????????????????????
                                                           String					employeeId,					// ??????ID
                                                           GeneralDate				baseDate,					// ???????????????
                                                           GeneralDate				startDate,					// ??????????????????From(??????)/01
                                                           GeneralDate				endDate,					// ??????????????????To  (??????)/?????????
                                                           // 2022.01.04 - 3S - chinh.hm - issues #122017 - ?????? START
                                                           OutputPeriodInformation	outputPeriodInformation,	// ??????????????????
                                                           boolean 					currentStatus				// ??????????????????
                                                           // 2022.01.04 - 3S - chinh.hm - issues #122017 - ?????? end)
    )
    {
        // 263New
        List<SpecialVacationPastSituation> getSpeHdOfConfMonVer2 = new ArrayList<>();
        // RequestList369
        Optional<GeneralDate> grantDate = Optional.empty();
        // RequestList281
        List<AnnLeaGrantNumberImported> listAnnLeaGrantNumber = null;
        // RequestList265
        //AnnLeaveOfThisMonthImported annLeaveOfThisMonth = null;
        // RequestList255
        List<AnnualLeaveUsageImported> listAnnualLeaveUsage = new ArrayList<>();
        // RequestList363
        //List<AnnLeaveUsageStatusOfThisMonthImported> listAnnLeaveUsageStatusOfThisMonth = null;
        // RequestList268
        ReserveHolidayImported reserveHoliday = null;
        // RequestList258
        List<ReservedYearHolidayImported> listReservedYearHoliday = new ArrayList<>();
        // RequestList364
        List<RsvLeaUsedCurrentMonImported> listRsvLeaUsedCurrentMon = new ArrayList<>();
        // RequestList269
        //List<CurrentHolidayImported> listCurrentHoliday = new ArrayList<>();
        // RequestList259
        List<StatusHolidayImported> listStatusHoliday = new ArrayList<>();
        // RequestList204
        List<CurrentHolidayRemainImported> listCurrentHolidayRemain = new ArrayList<>();
        // RequestList260
        List<StatusOfHolidayImported> listStatusOfHoliday = new ArrayList<>();
        // RequestList206
        ChildNursingLeaveCurrentSituationImported childNursingLeave = null;
        // RequestList207
        NursingLeaveCurrentSituationImported nursingLeave = null;
        //add by HieuLT
        //CurrentHolidayImported currentHolidayLeft = null;
        CurrentHolidayRemainImported currentHolidayRemainLeft = null;
        List<AggrResultOfAnnualLeaveEachMonthKdr> getRs363 = new ArrayList<>();
        //  RQ 203 right
        Map<YearMonth, SubstituteHolidayAggrResult> substituteHolidayAggrResultsRight = new HashMap<>();
        //  RQ 203 left
        SubstituteHolidayAggrResult substituteHolidayAggrResult = null;
        // RQ 342
        List<ChildNursingLeaveStatus> monthlyConfirmedCareForEmployees = new ArrayList<>();
        // RQ 344
        List<NursingCareLeaveMonthlyRemaining> obtainMonthlyConfirmedCareForEmployees = new ArrayList<>();
        List<ChildNursingLeaveThisMonthFutureSituation> childCareRemNumWithinPeriodRight = new ArrayList<>();
        ChildNursingLeaveThisMonthFutureSituation childCareRemNumWithinPeriodLeft = null;

        List<NursingCareLeaveThisMonthFutureSituation> nursingCareLeaveThisMonthFutureSituationRight = new ArrayList<>();
        NursingCareLeaveThisMonthFutureSituation nursingCareLeaveThisMonthFutureSituationLeft = null;
        HolidayRemainMerEx hdRemainMer = null;
        List<YearMonth> lstYrMon = new ArrayList<>();
        YearMonthPeriod period = null;
        // 262 - D??? li???u qu?? kh??? cho ng??y ngh??? ph???n L
        List<PublicHolidayPastSituation> publicHolidayPastSituations = new ArrayList<>();
        // 718 - Left : D??? li???u th??ng hi???n t???i c???a ph???n L
        FutureSituationOfTheMonthPublicHoliday leftPublicHolidays = null ;
        // 718 - Right: D??? li???u th??ng hi???n t???i v?? t????ng lai
        List<FutureSituationOfTheMonthPublicHoliday> rightPublicHolidays = new ArrayList<>() ;
        if (!closureInforOpt.isPresent()) {
            return null;
        }

        // ????????????
        YearMonth currentMonth = closureInforOpt.get().getCurrentMonth();
        val cId = AppContexts.user().companyId();

        // ??????????????????From(??????)/01???????????????????????????To  (??????)/???????????????????????????
        val datePeriod = new DatePeriod(startDate, endDate);

        // 2021.12.29 - 3S - chinh.hm - issues #122017 - ?????? START
        Optional<List<PeriodCorrespondingYm>> currentMonthAndFuture = outputPeriodInformation == null ?Optional.empty(): outputPeriodInformation.getCurrentMonthAndFuture();
        List<PeriodCorrespondingYm>           correspondingYmList   = currentMonthAndFuture.orElseGet(ArrayList::new);
        // 2021.12.29 - 3S - chinh.hm - issues #122017 - ?????? END



        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////
        // ?????????
        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////



        // ???????????????????????????????????????
        //YearMonthPeriod period = new YearMonthPeriod(startDate.yearMonth(), currentMonth.previousMonth());
        Optional<YearMonthPeriod> pastPeriod = outputPeriodInformation == null ? Optional.empty() : outputPeriodInformation.getPastPeriod();

        //-----------------------------------------------------------------------------------
        // 2021.12.29 - 3S - chinh.hm - issues #122017 - ?????? START
        if (pastPeriod.isPresent() ) {
            // 2021.12.29 - 3S - chinh.hm - issues #122017 - ?????? END
            //-----------------------------------------------------------------------------------

            period = pastPeriod.get();

            ////////////////////////////////////////////////////////////////////////////////
            // ????????????????????????????????????????????????
            ////////////////////////////////////////////////////////////////////////////////
            // Mer RQ255,258,259,260,263
            hdRemainMer = hdRemainAdapter.getRemainMer(employeeId, period);

            // ????????????????????????????????????
            lstYrMon = ConvertHelper.yearMonthsBetween(period);

            // 2022.01.04 - 3S - chinh.hm - issues #122037 - ?????? START
            //Map<YearMonth, List<RemainMerge>> mapRemainMer = repoRemainMer.findBySidsAndYrMons(employeeId, lstYrMon);
            //  2022.01.04 - - 3S - chinh.hm - issues #122037 - ?????? END
        }



        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////
        // ???????????????????????????
        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////



        ////////////////////////////////////////////////////////////////////////////////
        // ?????????????????????????????????????????????
        ////////////////////////////////////////////////////////////////////////////////
        DatePeriod currentPeriodDate = null;
        for (int i = 0; i < correspondingYmList.size(); i++){						// <List>???????????????
            PeriodCorrespondingYm   correspondingYm = correspondingYmList.get(i);	// ???????????????
            YearMonth ym          = correspondingYm.getYm();						    // ?????????

            if(currentMonth.compareTo(ym) == 0){									// ???????????? == ????????????????????????
                currentPeriodDate = correspondingYm.getDatePeriod();	            // ?????????
                if(currentPeriodDate == null) continue;
                break;
            }
        }
        ////////////////////////////////////////////////////////////////////////////////

        // Mer RQ265,268,269,363,364,369
        boolean call265 = variousVacationControl.isAnnualHolidaySetting();		//[?????????]	????????????(??????)
        boolean call268 = variousVacationControl.isYearlyReservedSetting();		//			????????????(??????)
        boolean call269 = variousVacationControl.isSubstituteHolidaySetting();	//[?????????]	????????????(???????????????????????????)
        boolean call363 = variousVacationControl.isAnnualHolidaySetting()		//			????????????(???????????????????????????)
                && currentMonth.compareTo(endDate.yearMonth()) <= 0;
        boolean call364 = variousVacationControl.isYearlyReservedSetting()		//			????????????(??????????????????)
                && currentMonth.compareTo(endDate.yearMonth()) <= 0;
        boolean call369 = variousVacationControl.isAnnualHolidaySetting();		//			????????????(?????????????????????)

        ////////////////////////////////////////////////////////////////////////////////
        // ??????????????????????????????????????????????????????(363??????????????????)
        ////////////////////////////////////////////////////////////////////////////////
        CheckCallRequest check = new CheckCallRequest(call265, call268, call269, call363, call364, call369);
        HdRemainDetailMerEx remainDel = hdRemainAdapter.getRemainDetailMer(
                employeeId,
                currentMonth,
                baseDate,
                new DatePeriod(startDate, endDate),
                check);

        ////////////////////////////////////////////////////////////////////////////////
        // ??????
        ////////////////////////////////////////////////////////////////////////////////
        if (variousVacationControl.isAnnualHolidaySetting()) {

            // Call RequestList369					// ?????????????????????
            grantDate = remainDel.getResult369();

            // Call RequestList281					// ??????????????????(??????????????????)
            listAnnLeaGrantNumber = annLeaveAdapter.getAnnLeaGrantNumberImporteds(employeeId);
            listAnnLeaGrantNumber = listAnnLeaGrantNumber.stream()
                    .sorted(Comparator.comparing(AnnLeaGrantNumberImported::getGrantDate)).collect(Collectors.toList());

            // Call RequestList265					// ???????????????(?????????)
            //annLeaveOfThisMonth = remainDel.getResult265();

            // Call RequestList255 ver2 - hoatt		// ??????????????????(?????????)
            //-----------------------------------------------------------------------------------
            // 2021.12.29 - 3S - chinh.hm) - issues #122017 - ?????? START
            if ( hdRemainMer!=null) {
                listAnnualLeaveUsage = hdRemainMer.getResult255();
            }
            // 2021.12.29 - 3S - chinh.hm - issues #122017 - ?????? END

            // Call RequestList363					// ??????????????????(??????????????????),???????????????
            if (currentMonth.compareTo(endDate.yearMonth()) <= 0) {
                ////////////////////////////////////////////////////////////////////////////////
                // ??????????????????????????????????????????????????????(363??????)
                ////////////////////////////////////////////////////////////////////////////////
                //listAnnLeaveUsageStatusOfThisMonth = remainDel.getResult363();
                getRs363 = hdRemainAdapter.getRs363(employeeId, 						// ??????ID
                        currentMonth, 						// ??????
                        baseDate,							// ???????????????
                        new DatePeriod(startDate, endDate), // ??????????????????From(??????)/01???????????????????????????To  (??????)/?????????
                        true);								//
            }
        }

        ////////////////////////////////////////////////////////////////////////////////
        // ????????????
        ////////////////////////////////////////////////////////////////////////////////
        if (variousVacationControl.isYearlyReservedSetting()) {
            // Call RequestList268						// ???????????????
            reserveHoliday = remainDel.getResult268();
            // Call RequestList258 ver2 - hoatt			// ??????????????????(?????????)
            // 2021.12.29 - 3S - chinh.hm) - issues #122017 - ?????? START
            if ( hdRemainMer!=null) {
                listReservedYearHoliday = hdRemainMer.getResult258();
            }
            // 2021.12.29 - 3S - chinh.hm - issues #122017 - ?????? END
            // Call RequestList364						// ??????????????????(??????????????????)
            if (currentMonth.compareTo(endDate.yearMonth()) <= 0) {
                listRsvLeaUsedCurrentMon = remainDel.getResult364();
            }
        }
        ////////////////////////////////////////////////////////////////////////////////
        // ??????
        ////////////////////////////////////////////////////////////////////////////////
        if (variousVacationControl.isSubstituteHolidaySetting()) {
            //========================================
            // ??????????????????
            //========================================
            // Call RequestList269
            //for (YearMonth s = currentMonth; s.lessThanOrEqualTo(endDate.yearMonth()); s = s.addMonths(1)) {
            //GeneralDate end = GeneralDate.ymd(s.year(), s.month(), 1).addMonths(1).addDays(-1);
            //DatePeriod periodDate = new DatePeriod(GeneralDate.ymd(s.year(), s.month(), 1),
            //        endDate.before(end) ? endDate : end);
            //BreakDayOffRemainMngRefactParam inputRefactor = new BreakDayOffRemainMngRefactParam(
            //        cId, employeeId,
            //       periodDate,
            //        false,
            //        closureInforOpt.get().getPeriod().end(),
            //        false,
            //        new ArrayList<>(),
            //        Optional.empty(),
            //        Optional.empty(),
            //        new ArrayList<>(),
            //        new ArrayList<>(),
            //        Optional.empty(), new FixedManagementDataMonth());
            //SubstituteHolidayAggrResult currentHoliday = NumberRemainVacationLeaveRangeQuery
            //        .getBreakDayOffMngInPeriod(breakDayOffMngInPeriodQueryRequire, inputRefactor);

            //listCurrentHoliday.add(new CurrentHolidayImported(s, currentHoliday.getCarryoverDay().v(),
            //        currentHoliday.getOccurrenceDay().v(), currentHoliday.getDayUse().v(),
            //        currentHoliday.getUnusedDay().v(), currentHoliday.getRemainDay().v()));
            //}

            //========================================
            // ?????????
            //========================================
            // Call RequestList259 ver2 - hoatt
            // 2021.12.29 - 3S - chinh.hm) - issues #122017 - ?????? START
            if ( hdRemainMer!=null) {
                listStatusHoliday = hdRemainMer.getResult259();
            }
            // 2021.12.29 - 3S - chinh.hm - issues #122017 - ?????? END

            //========================================
            // ??????
            //========================================
            // Call RequestList269
            //DatePeriod periodDate = new DatePeriod(GeneralDate.ymd(currentMonth.year(), currentMonth.month(), 1), GeneralDate.ymd(currentMonth.year(), currentMonth.month(), 1).addMonths(1).addDays(-1));
            //BreakDayOffRemainMngRefactParam inputRefactor = new BreakDayOffRemainMngRefactParam(
            //        cId, employeeId,
            //        periodDate,
            //        false,
            //        closureInforOpt.get().getPeriod().end(),
            //        false,
            //        new ArrayList<>(),
            //        Optional.empty(),
            //        Optional.empty(),
            //        new ArrayList<>(),
            //        new ArrayList<>(),
            //        Optional.empty(), new FixedManagementDataMonth());
            //SubstituteHolidayAggrResult currentHoliday = NumberRemainVacationLeaveRangeQuery
            //       .getBreakDayOffMngInPeriod(breakDayOffMngInPeriodQueryRequire, inputRefactor);
            //currentHolidayLeft = new CurrentHolidayImported(
            //        currentMonth,
            //        currentHoliday.getCarryoverDay().v(),
            //        currentHoliday.getOccurrenceDay().v(),
            //        currentHoliday.getDayUse().v(),
            //        currentHoliday.getUnusedDay().v(),
            //        currentHoliday.getRemainDay().v());
        }



		////////////////////////////////////////////////////////////////////////////////
		// ??????
		////////////////////////////////////////////////////////////////////////////////
        if (variousVacationControl.isPauseItemHolidaySettingCompany()) {
            //========================================
            // ??????????????????
            //========================================
            // Call RequestList204
            for (int i = 0; i < correspondingYmList.size(); i++){
                // 2021.12.29 - 3S - chinh.hm - issues #121957 - ?????? START
                PeriodCorrespondingYm  correspondingYm =    correspondingYmList.get(i);
                DatePeriod periodDate = correspondingYm.getDatePeriod();
                YearMonth ym = correspondingYm.getYm();
                // 2021.12.29 - 3S - chinh.hm - issues #121957 - ?????? END
                if(periodDate == null) continue;
                val mngParam = new AbsRecMngInPeriodRefactParamInput(
                        cId,
                        employeeId,
                        periodDate,
                        closureInforOpt.get().getPeriod().end(),
                        false,
                        false,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        new FixedManagementDataMonth());
                CompenLeaveAggrResult remainMng = NumberCompensatoryLeavePeriodQuery
                        .process(numberCompensatoryLeavePeriodProcess.createRequire(), mngParam);
                listCurrentHolidayRemain.add(
                        new CurrentHolidayRemainImported(
                                ym,
                                remainMng.getCarryoverDay().v(),
                                remainMng.getOccurrenceDay().v(),
                                remainMng.getDayUse().v(),
                                remainMng.getUnusedDay().v(),
                                remainMng.getRemainDay().v()));
            }

            //========================================
            // ?????????
            //========================================
            // Call RequestList260 ver2 - hoatt
            // 2021.12.29 - 3S - chinh.hm) - issues #122017  - ?????? START
            if ( hdRemainMer!=null) {
                listStatusOfHoliday = hdRemainMer.getResult260();
            }

            //========================================
            // ??????
            //========================================
            // Call RequestList204

            // 2021.12.29 - 3S - chinh.hm - issues #122017  - ?????? END
            //????????????Tr?????ng h???p ????????????????????????== true???
            // 2021.12.29 - 3S - chinh.hm - issues #122017  - ?????? START
            //????????????if????????? {

            // 2022.01.17 - inaguma #122461 CHANGE START
            //if(currentStatus){
            if(currentStatus && currentPeriodDate!=null){
                // 2022.01.17 - inaguma #122461 CHANGE END

                // 2021.12.29 - 3S - chinh.hm - issues #122017  - ?????? END
                val param = new AbsRecMngInPeriodRefactParamInput(
                        cId,
                        employeeId,
                        //-----------------------------------------------------------------------------------
                        // 2021.12.06 - 3S - chinh.hm  - issues #120916- ?????? START
                        //periodDate,

                        // 2022.01.17 - inaguma #122461 CHANGE START
                        //closureInforOpt.get().getPeriod(),
                        currentPeriodDate,
                        // 2022.01.17 - inaguma #122461 CHANGE END

                        // 2021.12.06 - 3S - chinh.hm  - issues #120916- ?????? END
                        //-----------------------------------------------------------------------------------
                        closureInforOpt.get().getPeriod().end(),
                        false,
                        false,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
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
            }else {
                //????????????Tr?????ng h???p????????????????????????== false???
                // 2021.12.29 - 3S - chinh.hm - issues #122017  - ?????? START
                //????????????}
                //????????????else {
                //??????????????????Set r???ng (null? empty?) v??o currentHolidayRemainLeft????????????}
                currentHolidayRemainLeft = null;
                // 2021.12.29 - 3S - chinh.hm - issues #122017  - ?????? END
            }
        }

        ////////////////////////////////////////////////////////////////////////////////
        // ????????????
        ////////////////////////////////////////////////////////////////////////////////
        // hoatt
        //Map<Integer, SpecialVacationImported> mapSpecVaca = new HashMap<>();
        //Map<YearMonth, Map<Integer, SpecialVacationImported>> lstMapSPVaCurrMon = new HashMap<>();// key
        //Map<Integer, List<SpecialHolidayImported>> mapSpeHd = new HashMap<>();
        Map<Integer, SpecialVacationImportedKdr> map273New = new HashMap<>();
        Map<YearMonth, Map<Integer, SpecialVacationImportedKdr>> lstMap273CurrMon = new HashMap<>();// key
        //========================================
        // ??????????????????
        //========================================
        // Call RequestList273
        for (int i = 0; i < correspondingYmList.size(); i++){
            //Map<Integer, SpecialVacationImported> mapSPVaCurrMon = new HashMap<>();
            Map<Integer, SpecialVacationImportedKdr> mapSP273CurrMon = new HashMap<>();
            // 2021.12.29 - 3S - chinh.hm - issues #121957 - ?????? START
            PeriodCorrespondingYm  correspondingYm =    correspondingYmList.get(i);
            DatePeriod periodDate = correspondingYm.getDatePeriod();
            YearMonth ym = correspondingYm.getYm();
            // 2021.12.29 - 3S - chinh.hm - issues #121957 - ?????? END
            for (SpecialHoliday specialHolidayDto : variousVacationControl.getListSpecialHoliday()) {// sphdCd
                int sphdCode = specialHolidayDto.getSpecialHolidayCode().v();
                //YearMonth ymEnd = ym.addMonths(1);
                //SpecialVacationImported spVaImported = specialLeaveAdapter.complileInPeriodOfSpecialLeave(cId,
                //        employeeId,
                //        new DatePeriod(GeneralDate.ymd(ym.year(), ym.month(), 1),
                //                GeneralDate.ymd(ymEnd.year(), ymEnd.month(), 1).addDays(-1)),
                //        false, baseDate, sphdCode, false);

                // ??????New RQ273
                SpecialVacationImportedKdr spVaImportedNew = specialLeaveAdapter.get273New(
                        cId,
                        employeeId,
                        // 2021.12.29 - 3S - chinh.hm - issues #121957 - ?????? START
                        periodDate,
                        // 2021.12.29 - 3S - chinh.hm - issues #121957 - ?????? END
                        false,
                        //-----------------------------------------------------------------------------------
                        // 2021.12.06 - 3S - chinh.hm  - issues #120916- ?????? START
                        //baseDate,
                        closureInforOpt.get().getPeriod().end(),
                        // 2021.12.06 - 3S - chinh.hm  - issues #120916- ?????? END
                        //-----------------------------------------------------------------------------------
                        sphdCode,
                        false);

                //mapSPVaCurrMon.put(sphdCode, spVaImported);
                mapSP273CurrMon.put(sphdCode, spVaImportedNew);
            }
            //lstMapSPVaCurrMon.put(ym, mapSPVaCurrMon);
            lstMap273CurrMon.put(ym, mapSP273CurrMon);
        }

        //========================================
        // ??????
        //========================================
        // 2021.12.29 - 3S - chinh.hm - issues #122017  - ?????? START

        // 2022.01.17 - inaguma #122461 CHANGE START
        //if(currentStatus){
        if(currentStatus && currentPeriodDate!=null){
            // 2022.01.17 - inaguma #122461 CHANGE END

            // 2021.12.29 - 3S - chinh.hm - issues #122017  - ?????? END
            for (SpecialHoliday specialHolidayDto : variousVacationControl.getListSpecialHoliday()) {
                int sphdCode = specialHolidayDto.getSpecialHolidayCode().v();
                // Call RequestList273
                //SpecialVacationImported specialVacationImported = specialLeaveAdapter.complileInPeriodOfSpecialLeave(
                //        cId,
                //        employeeId,
                //        closureInforOpt.get().getPeriod(),
                //        false,
                //        baseDate,
                //       sphdCode,
                //        false);

                // ??????New RQ273
                SpecialVacationImportedKdr specialVacationImportedNew = specialLeaveAdapter.get273New(
                        cId,
                        employeeId,

                        // 2022.01.17 - inaguma #122461 CHANGE START
                        //closureInforOpt.get().getPeriod(),
                        currentPeriodDate,
                        // 2022.01.17 - inaguma #122461 CHANGE END

                        false,
                        //-----------------------------------------------------------------------------------
                        // 2021.12.06 - 3S - chinh.hm  - issues #120916- ?????? START
                        //baseDate,
                        closureInforOpt.get().getPeriod().end(),
                        // 2021.12.06 - 3S - chinh.hm  - issues #120916- ?????? END
                        //-----------------------------------------------------------------------------------
                        sphdCode,
                        false);
                //mapSpecVaca.put(sphdCode, specialVacationImported);
                map273New.put(sphdCode, specialVacationImportedNew);

            }
        }else {
            // 2022.01.04 - 3S - chinh.hm - issues #122017 - ?????? START
            map273New = new HashMap<>();
            // 2022.12.04 - 3S - chinh.hm - issues #122017 - ?????? END
        }

        ////////////////////////////////////////////////////////////////////////////////
        // ????????????
        ////////////////////////////////////////////////////////////////////////////////
        //if (variousVacationControl.isChildNursingSetting()) {
        //    // Call RequestList206
        //    childNursingLeave = childNursingAdapter.getChildNursingLeaveCurrentSituation(
        //            cId,
        //            employeeId,
        //            datePeriod);
        //}

        ////////////////////////////////////////////////////////////////////////////////
        // ??????
        ////////////////////////////////////////////////////////////////////////////////
        //if (variousVacationControl.isNursingCareSetting()) {
        //    // Call RequestList207
        //    nursingLeave = nursingLeaveAdapter.getNursingLeaveCurrentSituation(
        //            cId,
        //            employeeId,
        //            datePeriod);
        //}

        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////
        // ???????????????????????????????????????????????????????????????return??????????????????????????????????????????
        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////
        // ??????
        ////////////////////////////////////////////////////////////////////////////////

        val rq = requireService.createRequire();
        // ?????????????????????????????????????????????

        //========================================
        // ??????
        //========================================
        // RequestList203
        // 2021.12.29 - 3S - chinh.hm - issues #122017  - ?????? START

        // 2022.01.17 - inaguma #122461 CHANGE START
        //if(currentStatus){
        if(currentStatus && currentPeriodDate!=null){
            // 2022.01.17 - inaguma #122461 CHANGE END

            // 2021.12.29 - 3S - chinh.hm - issues #122017  - ?????? END
            //-----------------------------------------------------------------------------------
            // 2021.12.06 - 3S - chinh.hm  - issues #120916- ?????? START
            //DatePeriod periodDate = new DatePeriod(
            //        GeneralDate.ymd(currentMonth.year(), currentMonth.month(), 1),
            //        GeneralDate.ymd(currentMonth.year(), currentMonth.month(), 1).addMonths(1).addDays(-1));
            //DatePeriod periodDate = closureInforOpt.get().getPeriod();
            // 2021.12.06 - 3S - chinh.hm  - issues #120916- ?????? END
            //-----------------------------------------------------------------------------------

            BreakDayOffRemainMngRefactParam inputRefactor = new BreakDayOffRemainMngRefactParam(
                    cId,
                    employeeId,

                    // 2022.01.17 - inaguma #122461 CHANGE START
                    //periodDate,
                    currentPeriodDate,
                    // 2022.01.17 - inaguma #122461 CHANGE END

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
        }else {
            // 2022.01.04 - 3S - chinh.hm - issues #122017 - ?????? START
            substituteHolidayAggrResult = null;
            // 2022.12.04 - 3S - chinh.hm - issues #122017 - ?????? END
        }

        //========================================
        // ??????????????????
        //========================================
        // RequestList203
        for (int i = 0; i < correspondingYmList.size(); i++){
            // 2021.12.29 - 3S - chinh.hm - issues #121957 - ?????? START
            PeriodCorrespondingYm  correspondingYm =    correspondingYmList.get(i);
            DatePeriod periods = correspondingYm.getDatePeriod();
            YearMonth ym = correspondingYm.getYm();
            // 2021.12.29 - 3S - chinh.hm - issues #121957 - ?????? END
            BreakDayOffRemainMngRefactParam input = new BreakDayOffRemainMngRefactParam(
                    cId,
                    employeeId,
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
            substituteHolidayAggrResultsRight.put(ym, item);

        }

        ////////////////////////////////////////////////////////////////////////////////
        // ??????
        ////////////////////////////////////////////////////////////////////////////////
//        CompenLeaveAggrResult subVaca = null;
//        if (closureInforOpt.isPresent()) {
//            val param = new AbsRecMngInPeriodRefactParamInput(
//                    cId,
//                    employeeId,
//                    periodDate,
//                    closureInforOpt.get().getPeriod().end(),
//                    false,
//                    false,
//                    new ArrayList<>(),
//                    new ArrayList<>(),
//                    new ArrayList<>(),
//                    Optional.empty()
//                    , Optional.empty()
//                    , Optional.empty(),
//                    new FixedManagementDataMonth());
//            subVaca = NumberCompensatoryLeavePeriodQuery
//                    .process(rq, param);
//        }



        ////////////////////////////////////////////////////////////////////////////////
        // 60H??????
        ////////////////////////////////////////////////////////////////////////////////

        //========================================
        // 60H??????????????????????????????
        //========================================

        // [RQ677]????????????60H???????????????????????????
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

        ////////////////////////////////////////////////////////////////////////////////
        // ????????????
        ////////////////////////////////////////////////////////////////////////////////

        //========================================
        // ?????????
        //========================================

        // Call RequestList263
        //-----------------------------------------------------------------------------------
        // 2021.12.24 - 3S - chinh.hm  - issues #122037 - ?????? START
        //getSpeHdOfConfMonVer2 =
        //        rq263.getSpeHdOfConfMonVer2(employeeId,
        //        							period,
        //        							mapRemainMer).stream().map(e -> new SpecialVacationPastSituation(
        //                e.getSid(),
        //                e.getYm(),
        //                e.getSpecialHolidayCd(),
        //                e.getUseDays(),
        //                e.getUseTimes(),
        //                e.getAfterRemainDays() == 0 ?e.getBeforeRemainDays() :e.getAfterRemainDays(),
        //                e.getAfterRemainTimes() == 0 ?e.getBeforeRemainTimes():e.getAfterRemainTimes()
        //        )).collect(Collectors.toList());
        // 2021.12.24 - 3S - chinh.hm  - issues #122037 - ?????? END

        // 2021.12.29 - 3S - chinh.hm) - issues #122037 - ?????? START
        if ( hdRemainMer!=null) {
            getSpeHdOfConfMonVer2 = hdRemainMer.getResult263();
        }
        // 2021.12.29 - 3S - chinh.hm - issues #122037 - ?????? END


        ////////////////////////////////////////////////////////////////////////////////
        // ????????????
        // ??????
        ////////////////////////////////////////////////////////////////////////////////

        //========================================
        // ?????????
        //========================================
        // 2021.12.29 - 3S - chinh.hm) - issues #121957 - ?????? START
        if(!lstYrMon.isEmpty()){
            // RQ 342
            monthlyConfirmedCareForEmployees = getChildcareRemNumEachMonth.getMonthlyConfirmedCareForEmployees(employeeId, lstYrMon);
            // RQ 344
            obtainMonthlyConfirmedCareForEmployees = getChildcareRemNumEachMonth.getObtainMonthlyConfirmedCareForEmployees(employeeId, lstYrMon);
        }
        // 2021.12.29 - 3S - chinh.hm) - issues #121957 - ?????? END

        //========================================
        // ??????????????????
        //========================================

        for (int i = 0; i < correspondingYmList.size(); i++){

            // 2021.12.29 - 3S - chinh.hm - issues #121957 - ?????? START
            PeriodCorrespondingYm  correspondingYm =    correspondingYmList.get(i);
            DatePeriod periods = correspondingYm.getDatePeriod();
            YearMonth ym = correspondingYm.getYm();
            // 2021.12.29 - 3S - chinh.hm - issues #121957 - ?????? END
            // RQ206(???)
            childCareRemNumWithinPeriodRight.add(
                    getRemainingNumberChildCareNurseAdapter.getChildCareRemNumWithinPeriod(
                            cId,
                            employeeId,
                            periods,
                            ym,
                            //-----------------------------------------------------------------------------------
                            // 2021.12.06 - 3S - chinh.hm  - issues #120916- ?????? START
                            //end)
                            closureInforOpt.get().getPeriod().end())
                    // 2021.12.06 - 3S - chinh.hm  - issues #120916- ?????? END
                    //-----------------------------------------------------------------------------------
            );
            // RQ207(???)
            nursingCareLeaveThisMonthFutureSituationRight.add(
                    getRemainingNumberChildCareNurseAdapter.getNursingCareLeaveThisMonthFutureSituation(
                            cId,
                            employeeId,
                            periods,
                            ym,
                            //-----------------------------------------------------------------------------------
                            // 2021.12.06 - 3S - chinh.hm  - issues #120916- ?????? START
                            //end)
                            closureInforOpt.get().getPeriod().end())
                    // 2021.12.06 - 3S - chinh.hm  - issues #120916- ?????? END
                    //-----------------------------------------------------------------------------------
            );

        }
        //========================================
        // ??????
        //========================================
        // 2021.12.29 - 3S - chinh.hm - issues #122017  - ?????? START

        // 2022.01.17 - inaguma #122461 CHANGE START
        //if(currentStatus){
        if(currentStatus && currentPeriodDate!=null){
            // 2022.01.17 - inaguma #122461 CHANGE END

            // 2021.12.29 - 3S - chinh.hm - issues #122017  - ?????? END
            // RQ206(???)
            childCareRemNumWithinPeriodLeft = getRemainingNumberChildCareNurseAdapter.getChildCareRemNumWithinPeriod(
                    cId,
                    employeeId,
                    //-----------------------------------------------------------------------------------
                    // 2021.12.06 - 3S - chinh.hm  - issues #120916- ?????? START
                    //periods,
                    //end

                    // 2022.01.17 - inaguma #122461 CHANGE START
                    //closureInforOpt.get().getPeriod(),
                    currentPeriodDate,
                    // 2022.01.17 - inaguma #122461 CHANGE END

                    closureInforOpt.get().getCurrentMonth(),
                    closureInforOpt.get().getPeriod().end()
                    // 2021.12.06 - 3S - chinh.hm  - issues #120916- ?????? END
                    //-----------------------------------------------------------------------------------
            );
            // RQ207(???)
            nursingCareLeaveThisMonthFutureSituationLeft = getRemainingNumberChildCareNurseAdapter.getNursingCareLeaveThisMonthFutureSituation(
                    cId,
                    employeeId,
                    //-----------------------------------------------------------------------------------
                    // 2021.12.06 - 3S - chinh.hm  - issues #120916- ?????? START
                    //periods,
                    //end

                    // 2022.01.17 - inaguma #122461 CHANGE START
                    //closureInforOpt.get().getPeriod(),
                    currentPeriodDate,
                    // 2022.01.17 - inaguma #122461 CHANGE END

                    closureInforOpt.get().getCurrentMonth(),
                    closureInforOpt.get().getPeriod().end()
                    // 2021.12.06 - 3S - chinh.hm  - issues #120916- ?????? END
                    //-----------------------------------------------------------------------------------
            );

        }else {
            // 2022.01.04 - 3S - chinh.hm - issues #122017 - ?????? START
            childCareRemNumWithinPeriodLeft = null;
            nursingCareLeaveThisMonthFutureSituationLeft = null;
            // 2022.12.04 - 3S - chinh.hm - issues #122017 - ?????? END
        }
        // 2021.12.29 - 3S - chinh.hm - issues #119961  - ?????? END


        // 2021.12.30 - 3S - chinh.hm - issues #119961  - ?????? START
        ////////////////////////////////////////////////////////////////////////////////
        // ??????
        ////////////////////////////////////////////////////////////////////////////////

        //========================================
        // ?????????
        //========================================
        // 2022.01.24 - 3S - chinh.hm  - issues #122620  - ?????? START
        // T???n t???i qu?? kh???.
        if(hdRemainMer!=null){
            publicHolidayPastSituations = hdRemainMer.getResult262();
        }
        // 2022.01.24 - 3S - chinh.hm  - issues #122620  - ?????? END


        //========================================
        // ???????????????????????????
        //========================================
        for (int i = 0; i < correspondingYmList.size(); i++){
            PeriodCorrespondingYm  correspondingYm =    correspondingYmList.get(i);
            YearMonth ym = correspondingYm.getYm();
            List<TempPublicHolidayManagement> tempPublicHolidayManagements = new ArrayList<>();
            AggrResultOfPublicHoliday aggrResultOfPublicHoliday = GetRemainingNumberPublicHolidayService.getPublicHolidayRemNumWithinPeriod(
                    cId,
                    employeeId,
                    Collections.singletonList(ym),
                    correspondingYm.getDatePeriod().end(),
                    InterimRemainMngMode.OTHER,
                    Optional.of(false),
                    tempPublicHolidayManagements,
                    Optional.empty(),
                    Optional.empty(),
                    new CacheCarrier(),
                    requireServiceM6.createRequire()
            );
            PublicHolidayRemNumEachMonth publicLeaRemNum =
                    aggrResultOfPublicHoliday.createPublicHolidayRemainData(
                            employeeId, ym, closureInforOpt.get().getClosureId(), closureInforOpt.get().getClosureDate()
                    );

            if(publicLeaRemNum!=null){
                //========================================
                // ??????
                //========================================
                if(currentStatus) {
                    if(ym.equals(currentMonth)){
                        leftPublicHolidays = new FutureSituationOfTheMonthPublicHoliday(
                                ym,
                                publicLeaRemNum.getCarryForwardNumber().v(),
                                publicLeaRemNum.getPublicHolidayday().v(),
                                publicLeaRemNum.getNumberOfAcquisitions().v(),
                                publicLeaRemNum.getNumberCarriedOverToTheNextMonth().v()
                        );
                    }
                }
                //========================================
                // ??????????????????
                //========================================
                rightPublicHolidays.add(( new FutureSituationOfTheMonthPublicHoliday(
                        ym,
                        publicLeaRemNum.getCarryForwardNumber().v(),
                        publicLeaRemNum.getPublicHolidayday().v(),
                        publicLeaRemNum.getNumberOfAcquisitions().v(),
                        publicLeaRemNum.getNumberCarriedOverToTheNextMonth().v()
                )));
            }
        }
        // 2021.12.30 - 3S - chinh.hm - issues #119961  - ?????? END


        ////////////////////////////////////////////////////////////////////////////////
        // RETURN
        ////////////////////////////////////////////////////////////////////////////////
        return new HolidayRemainingInfor(
                grantDate,
                listAnnLeaGrantNumber,
                //annLeaveOfThisMonth,
                listAnnualLeaveUsage,
                reserveHoliday,
                listReservedYearHoliday,
                listRsvLeaUsedCurrentMon,
                //listCurrentHoliday,
                listStatusHoliday,
                listCurrentHolidayRemain,
                listStatusOfHoliday,
                //currentHolidayLeft,
                currentHolidayRemainLeft,
                substituteHolidayAggrResult,
                //subVaca,
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
                nursingCareLeaveThisMonthFutureSituationLeft,
                publicHolidayPastSituations,
                leftPublicHolidays,
                rightPublicHolidays);
    }

    private Optional<ClosureInfo> getClosureInfo(int closureId) {

        val listClosureInfo = ClosureService.getAllClosureInfo(ClosureService.createRequireM2(closureRepository));
        return listClosureInfo.stream().filter(i -> i.getClosureId().value == closureId).findFirst();
    }

    /**
     * ?????????????????????????????????????????????????????????????????????????????????
     */
    private Map<Integer,OutputPeriodForClosingDto> createOutputPeriodForClosing(YearMonthPeriod outPutPeriod,List<Closure> closureList) {
        Map<Integer,OutputPeriodForClosingDto> periodInformationMap = new HashMap<>();

        if(closureList.isEmpty()) return periodInformationMap;
        closureList.forEach(closure->{
            OutputPeriodForClosingDto result = new OutputPeriodForClosingDto();
            YearMonthPeriod pastPeriod = null;
            List<PeriodCorrespondingYm> correspondingYmList = new ArrayList<>();
            //Current month;
            YearMonth processingYm = closure.getClosureMonth().getProcessingYm();
            //<??????>????????????????????????????????????????????????????????????????????????
            DatePeriod datePeriod = ClosureService.getClosurePeriod(closure, processingYm);
            PeriodCorrespondingYm currentMonthPeriod = new PeriodCorrespondingYm(processingYm,datePeriod);
            result.setCurrentPeriod(currentMonthPeriod);
            // ?????????????????????????????????
            // ???????????????????????????
            // Input : ???????????????INPUT.????????????
            // ??????????????? - Past period
            // ????????????????????????????????????????????????????????????
            // INPUT.????????????.???????????????????????????.??????
            if (outPutPeriod.start().lessThan(currentMonthPeriod.getYm())) {
                YearMonth start = outPutPeriod.start();
                YearMonth end = null;
                if (outPutPeriod.end().lessThanOrEqualTo(currentMonthPeriod.getYm())) {
                    end = outPutPeriod.end();
                } else {
                    end = currentMonthPeriod.getYm();
                }
                pastPeriod = new YearMonthPeriod(start, end);
            }
            //INPUT.????????????.???????????????????????????.??????
            //?????????????????????????????? - The current and future period
            //??????????????????????????????????????????????????????????????????

            //??????????????????????????????????????????????????????????????????
            //????????????.?????????INPUT.????????????.????????????
            if (currentMonthPeriod.getYm().lessThanOrEqualTo(outPutPeriod.end())) {
                YearMonth end = outPutPeriod.end();
                YearMonth start = null;
                if (currentMonthPeriod.getYm().lessThanOrEqualTo(outPutPeriod.start())) {
                    start = outPutPeriod.start();
                } else {
                    start = currentMonthPeriod.getYm();
                }
                if (start != null) {
                    YearMonthPeriod ymCurrentAndFuture = new YearMonthPeriod(start, end);
                    List<YearMonth> yearMonthList = ymCurrentAndFuture.yearMonthsBetween();
                    for (val ym : yearMonthList) {
                        //<??????>?????????????????????????????????????????????????????????????????????????????????????????????
                        List<DatePeriod> lstDatePeriod = closure.getPeriodByYearMonth(ym);
                        correspondingYmList.add(new PeriodCorrespondingYm(
                                ym,
                                lstDatePeriod.isEmpty() ? null : lstDatePeriod.get(0)
                        ));
                    }
                }
            }

            OutputPeriodInformation information = new OutputPeriodInformation();
            information.setPastPeriod(pastPeriod == null ? Optional.empty() : Optional.of(pastPeriod));
            information.setCurrentMonthAndFuture(correspondingYmList.isEmpty() ? Optional.empty() : Optional.of(correspondingYmList));
            information.setPeriod(outPutPeriod);
            result.setPeriodInformation(information);
            periodInformationMap.put(closure.getClosureId().value,result);
        });

        return periodInformationMap;
    }
    /**
     * ?????????????????????????????????????????????????????????????????????????????????
     */
    private CurrentStatusAndPeriodInformation createExportPeriodForEmployee(
            String sid,
            OutputPeriodInformation periodInformation,
            PeriodCorrespondingYm currentPeriod){
        YearMonthPeriod pastPeriodByEmployee = null;
        YearMonth startYm = null;
        YearMonth endYm   = null;
        CurrentStatusAndPeriodInformation rs = new CurrentStatusAndPeriodInformation();
        //?????????????????????????????????????????????
        //(L???y th??ng tin v??o c??ng ty???ngh??? vi???c c???a employee)
        val listEmpInfo =  personEmpBasicInfoAdapter.getEmployeeBasicInfoExport(Collections.singletonList(sid));
        if(periodInformation != null){
            EmployeeBasicInfoExport employee = listEmpInfo.isEmpty()? null: listEmpInfo.get(0);
            //????????????????????????????????????
            // ??o???n th??ng tin th??ng hi???n t???i v?? t????ng lai c???a employee.
            List<PeriodCorrespondingYm> currentMonthAndFuture =
                    periodInformation.getCurrentMonthAndFuture().isPresent()?
                            periodInformation.getCurrentMonthAndFuture().get() : new ArrayList<>();
            if(employee!=null){
                Optional<DatePeriod> optionalPeriod = currentPeriod== null ? Optional.empty(): Optional.of(currentPeriod.getDatePeriod());
                DatePeriod thisMonthPeriod          = optionalPeriod.orElse(null);

                // ????????????????????????????????????
                boolean ischeck =  thisMonthPeriod != null && checkStatus(employee,thisMonthPeriod);
                rs.setCurrentStatus(ischeck);
                // ???????????????
                GeneralDate entryDate = employee.getEntryDate();
                // ???????????????????????? update ng??y b???t ?????u.
                // G???i thu???t to??n ?????????????????????????????????????????????????????????????????????
                // Input: ???  + ??????ID???INPUT.??????ID
                //          ???+ ??????????????????.???????????????
                // Output:?????????(?????????)???OUTPUT.????????????
                Optional<ClosurePeriod> optPeriodByEntryDate =  getClosingPeriodOfSpecifiedDate(sid,entryDate);
                if(optPeriodByEntryDate.isPresent()){
                    // ??????(?????????)
                    ClosurePeriod closurePeriod = optPeriodByEntryDate.get();
                    //  ?????????????????????????????????????????????
                    //  ????????????
                    // ????????????????????????.??????????????????????????????
                    // ????????????????????????.????????????????????????(?????????).?????????????????????
                    boolean condition_1 = checkExist(closurePeriod.getYearMonth(),currentMonthAndFuture);
                    if(!condition_1 ){
                        //  ????????????
                        //???????????????????????????.?????????????????????
                        //???????????????????????????.???????????????(?????????).??????????????????
                        //??????????????????????????????(?????????).?????????????????????
                        Optional<YearMonthPeriod> pastPeriod = periodInformation.getPastPeriod();
                        boolean condition_2 = pastPeriod.isPresent()
                                &&  pastPeriod.get().start().lessThanOrEqualTo(closurePeriod.getYearMonth())
                                &&  closurePeriod.getYearMonth().lessThanOrEqualTo(pastPeriod.get().end());
                        if(condition_2){
                            //????????????????????????????????????
                            //??????.???????????????????????????(?????????).??????
                            // ???????????????????????? update ng??y b???t ?????u.
                            startYm = closurePeriod.getYearMonth();
                            endYm   = pastPeriod.get().end();
                            pastPeriodByEmployee = new YearMonthPeriod(startYm, endYm);
                            periodInformation.setPastPeriod(Optional.of(pastPeriodByEmployee));
                        }
                    }else {
                        //???????????????????????????????????????
                        periodInformation.setPastPeriod(Optional.empty());
                        //??????????????????
                        //??????????????????[n].???????????????(?????????).??????
                        //?????????List??????????????????
                        //??????????????????[n].???????????????(?????????).??????
                        //???????????????????????????
                        //??????????????????.??????????????????????????????.???????????????
                        //??????????????????[n].???????????????(?????????).??????
                        //????????????????????????
                        Iterator<PeriodCorrespondingYm> itrList =   currentMonthAndFuture.iterator();
                        while (itrList.hasNext()){
                            PeriodCorrespondingYm correspondingYm = itrList.next();
                            YearMonth ym = correspondingYm.getYm();
                            if(ym.greaterThan(closurePeriod.getYearMonth())) continue;
                            if(ym.lessThan(closurePeriod.getYearMonth())){
                                itrList.remove();
                            }else if (ym.equals(closurePeriod.getYearMonth())){
                                correspondingYm.setDatePeriod(new DatePeriod(entryDate,correspondingYm.getDatePeriod().end()));
                            }
                        }
                    }
                }
                //???????????????????????? - Update ng??y k???t th??c
                //G???i thu???t to??n ?????????????????????????????????????????????????????????????????????
                //???INPUT???
                //?????????ID???INPUT.??????ID
                //?????????????????????.???????????????
                //???OUTPUT???
                //?????????(?????????)???OUTPUT.????????????
                // ???????????????
                GeneralDate retiredDate = employee.getRetiredDate();
                Optional<ClosurePeriod> optPeriodByRetiredDate =
                        getClosingPeriodOfSpecifiedDate(sid, retiredDate);
                if(optPeriodByRetiredDate.isPresent()){
                    val periodByRetiredDate = optPeriodByRetiredDate.get();
                    //????????????
                    //???????????????????????????.??????????????????????????????
                    //???????????????????????????.????????????????????????(?????????).?????????????????????
                    boolean condition_1 = checkExist(periodByRetiredDate.getYearMonth(),currentMonthAndFuture);
                    if(!condition_1){
                        //????????????
                        //???????????????????????????.?????????????????????
                        //???????????????????????????.???????????????(?????????).??????????????????
                        //??????????????????????????????(?????????).?????????????????????
                        Optional<YearMonthPeriod> pastPeriod = periodInformation.getPastPeriod();
                        boolean condition_2 = pastPeriod.isPresent()
                                &&  pastPeriod.get().start().lessThanOrEqualTo(periodByRetiredDate.getYearMonth())
                                &&  periodByRetiredDate.getYearMonth().lessThanOrEqualTo(pastPeriod.get().end());
                        if(condition_2){
                            //????????????????????????????????????
                            //??????.???????????????????????????(?????????).??????
                            startYm = pastPeriod.get().start();
                            endYm   = periodByRetiredDate.getYearMonth();
                            pastPeriodByEmployee = new YearMonthPeriod(startYm, endYm);
                            periodInformation.setPastPeriod(Optional.of(pastPeriodByEmployee));
                            //????????????????????????????????????????????????
                            currentMonthAndFuture = new ArrayList<>();
                        }
                    }else {
                        //??????????????????
                        //??????????????????[n].???????????????(?????????).??????
                        //????????????????????????
                        //??????????????????[n].???????????????(?????????).??????
                        //???????????????????????????
                        //??????????????????.??????????????????????????????.???????????????
                        //??????????????????[n].???????????????(?????????).??????
                        //?????????List??????????????????
                        Iterator<PeriodCorrespondingYm> itrList =   currentMonthAndFuture.iterator();
                        while (itrList.hasNext()){
                            PeriodCorrespondingYm correspondingYm = itrList.next();
                            YearMonth ym = correspondingYm.getYm();
                            if (ym.equals(periodByRetiredDate.getYearMonth())){
                                correspondingYm.setDatePeriod(new DatePeriod(correspondingYm.getDatePeriod().start(),retiredDate));
                            }else if(periodByRetiredDate.getYearMonth().lessThan(ym)){
                                itrList.remove();
                            }
                        }
                    }
                }
                periodInformation.setCurrentMonthAndFuture(currentMonthAndFuture.isEmpty() ? Optional.empty() :Optional.of(currentMonthAndFuture));
                rs.setOutputPeriodInformation(periodInformation);
            }
        }
        return rs;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    //(Ki???m tra  tr???ng th??i c??n ??????ng ch???c )
    private boolean checkStatus(EmployeeBasicInfoExport employeeBasicInfoExport,DatePeriod currentMonth){
        //??????????????????????????????????????????
        //????????????
        //?????????????????????????????????
        //?????????.??????????????????????????????.?????????
        //?????????.??????????????????????????????.?????????
        //?????????????????????INPUT.????????????.??????
        return employeeBasicInfoExport.getEntryDate().beforeOrEquals(currentMonth.end())
                && employeeBasicInfoExport.getRetiredDate().afterOrEquals(currentMonth.start());
    }
    /**
     * ?????????????????????????????????????????????????????????????????????
     */

    private Optional<ClosurePeriod> getClosingPeriodOfSpecifiedDate(String sid, GeneralDate baseDate){
        val require = ClosureService.createRequireM3(closureRepo, closureEmploymentRepository, shareEmploymentAdapter);
        // ????????????????????????????????????????????????
        Closure closure = ClosureService.getClosureDataByEmployee(
                require, new CacheCarrier(), sid, baseDate);
        if(closure == null) return Optional.empty();
        return closure.getClosurePeriodByYmd(baseDate);
    }
    private boolean checkExist(YearMonth yearMonth,List<PeriodCorrespondingYm> correspondingYms) {
        boolean exist = false;
        if (yearMonth == null || correspondingYms.isEmpty()) {
            return false;
        }else {
            for (val correspondingYm:correspondingYms) {
                exist  = yearMonth.equals(correspondingYm.getYm());
                if(exist){
                    break;
                }
            }
        }
        return exist;
    }
}
