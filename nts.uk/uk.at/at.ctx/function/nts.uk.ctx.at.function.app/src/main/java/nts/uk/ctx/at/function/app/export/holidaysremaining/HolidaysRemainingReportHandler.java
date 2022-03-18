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

        // システム日付　★使用禁止
        //---------------------------------------------------
        // 2021.12.01 inaguma 削除
        //val baseDate = GeneralDate.fromString(hdRemainCond.getBaseDate(), "yyyy/MM/dd");
        //---------------------------------------------------

        val startDate = GeneralDate.fromString(hdRemainCond.getStartMonth(), "yyyy/MM/dd");		// 画面入力期間From(年月)/01
        val endDate   = GeneralDate.fromString(hdRemainCond.getEndMonth(),   "yyyy/MM/dd");		// 画面入力期間To  (年月)/月末日

        // ドメインモデル「休暇残数管理表の出力項目設定」を取得する
        val _hdManagement = hdFinder.findByLayOutId(hdRemainCond.getLayOutId());
        _hdManagement.ifPresent((HolidaysRemainingManagement hdManagement) -> {

            // 社員範囲選択画面で指定した締めID(0～5)　★使用禁止
            // 締めIDを(1～5)にする
            int closureId = hdRemainCond.getClosureId();
            // ※該当の締めIDが「0：全締め」のときは、「1締め（締めID＝1）」とする
            if (closureId == 0) {
                closureId = 1;
            }

            //(誤) アルゴリズム「指定した年月の期間をすべて取得する」を実行する
            //(正) 指定した締めIDのドメインモデル「締め」を取得する
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
            GeneralDate criteriaDate = criteriaDatePeriodOpt.get().end();	// 処理基準日
            //-----------------------------------------------------------------------------------
            // 2021.12.01 稲熊 追加 START
            GeneralDate baseDate = criteriaDate;	// 処理基準日
            // 2021.12.01 稲熊 追加 END
            //-----------------------------------------------------------------------------------

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

            // 休暇設定画面で設定した各休暇の「管理区分」を取得する
            val varVacaCtr = varVacaCtrSv.getVariousVacationControl();

            // ドメインモデル「締め」(社員範囲選択)より当月、締め期間、締め日(日付)を求める　★使用禁止
            //---------------------------------------------------
            // 2021.11.30 inaguma 削除
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

            // 休暇残数管理表の作成

            // 2021.12.29 - 3S - chinh.hm  - #121957 追加 START
            YearMonthPeriod yearMonthPeriod = new YearMonthPeriod(startDate.yearMonth(),endDate.yearMonth());
            String companyId = AppContexts.user().companyId();
            UseClassification useClassification = UseClassification.UseClass_Use;

            // ドメインモデル「締め」を取得する(get domain[closure])
            List<Closure> closureList = closureRepository.findAllActive(companyId, useClassification);
            Map<Integer,OutputPeriodForClosingDto> periodInformationMap = createOutputPeriodForClosing(yearMonthPeriod,closureList);
            //2021.12.29 - 3S - chinh.hm  - # 121957 追加  END

            ////////////////////////////////////////////////////////////////////////////////
            // 社員のループSTART
            ////////////////////////////////////////////////////////////////////////////////
            parallel.forEach(listEmployeeInformationImport, emp -> {
                String wpCode = emp.getWorkplace() != null ? emp.getWorkplace().getWorkplaceCode() : "";
                String wpName = emp.getWorkplace() != null ? emp.getWorkplace().getWorkplaceName() : TextResource.localize("KDR001_55");
                String empmentName = emp.getEmployment() != null ? emp.getEmployment().getEmploymentName() : "";
                String positionName = emp.getPosition() != null ? emp.getPosition().getPositionName() : "";
                String employmentCode = emp.getEmployment() != null ? emp.getEmployment().getEmploymentCode() : "";
                String positionCode = emp.getPosition() != null ? emp.getPosition().getPositionCode() : "";

                //-----------------------------------------------------------------------------------
                // 2021.12.06 - 3S - chinh.hm  - issues #121626- 追加 START
                // ループ中の社員(emp.getEmployeeId())の雇用を取得⇒取得できなかった場合は次の社員の処理へ
                // 雇用に紐付く締めIDを取得　　　　　　　　　　　 ⇒取得できなかった場合は次の社員の処理へ
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
                // 2021.12.06 - 3S - chinh.hm  - issues #121626- 追加 END
                //-----------------------------------------------------------------------------------

                // 当月
                Optional<YearMonth> currentMonth = hdRemainManageFinder.getCurrentMonth(
                        cId,
                        emp.getEmployeeId(),
                        baseDate);

                //アルゴリズム「社員ごとの出力期間を作成する」を実行する
                //(Thực hiện thuật toán 「Tạo export period của từng employee])
                OutputPeriodForClosingDto outputPeriodInformation = periodInformationMap.getOrDefault(empClosureId,null);
                PeriodCorrespondingYm currentPeriod = outputPeriodInformation == null ? null : outputPeriodInformation.getCurrentPeriod();
                OutputPeriodInformation information = outputPeriodInformation == null ? null : outputPeriodInformation.getPeriodInformation();
                CurrentStatusAndPeriodInformation currentStatusAndPeriod =
                        createExportPeriodForEmployee(emp.getEmployeeId(),information,currentPeriod);
                boolean currentStatus = currentStatusAndPeriod != null && currentStatusAndPeriod.isCurrentStatus();
                OutputPeriodInformation periodInformation = currentStatusAndPeriod == null ? null : currentStatusAndPeriod.getOutputPeriodInformation();

                // 赤丸？
                if (isFirstEmployee.get()) {
                    isFirstEmployee.set(false);
                    currentMonthOfFirstEmp.set(currentMonth.orElse(null));
                } else {
                    if (isSameCurrentMonth.get() && currentMonth.isPresent() && !currentMonth.get().equals(currentMonthOfFirstEmp.get())) {
                        isSameCurrentMonth.set(false);
                    }
                }

                ////////////////////////////////////////////////////////////////////////////////
                // 「休暇残数管理表の作成」
                ////////////////////////////////////////////////////////////////////////////////
                HolidayRemainingInfor holidayRemainingInfor = this.getHolidayRemainingInfor(
                        varVacaCtr,				// 各休暇の管理区分
                        closureInforOpt,		// (2021.12.06)着目社員の「締め期間」などの情報
                        emp.getEmployeeId(),	// 社員ID
                        baseDate,				// 処理基準日
                        startDate,				// 画面入力期間From(年月)/01
                        endDate,				// 画面入力期間To  (年月)/月末日
                        // 2022.01.04 - 3S - chinh.hm - issues #122017 - 追加 START
                        periodInformation,		// 出力期間情報
                        currentStatus			// 当月在職状況
                        // 2022.01.04 - 3S - chinh.hm - issues #122017 - 追加 END
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
            // 社員のループEND
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
    // 「休暇残数管理表の作成」
    ////////////////////////////////////////////////////////////////////////////////
    private HolidayRemainingInfor getHolidayRemainingInfor(VariousVacationControl	variousVacationControl,		// 各休暇の管理区分
                                                           Optional<ClosureInfo>	closureInforOpt,			// (2021.12.06)着目社員の「締め期間」などの情報
                                                           String					employeeId,					// 社員ID
                                                           GeneralDate				baseDate,					// 処理基準日
                                                           GeneralDate				startDate,					// 画面入力期間From(年月)/01
                                                           GeneralDate				endDate,					// 画面入力期間To  (年月)/月末日
                                                           // 2022.01.04 - 3S - chinh.hm - issues #122017 - 追加 START
                                                           OutputPeriodInformation	outputPeriodInformation,	// 出力期間情報
                                                           boolean 					currentStatus				// 当月在職状況
                                                           // 2022.01.04 - 3S - chinh.hm - issues #122017 - 追加 end)
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
        // 262 - Dữ liệu quá khứ cho ngày nghỉ phần L
        List<PublicHolidayPastSituation> publicHolidayPastSituations = new ArrayList<>();
        // 718 - Left : Dữ liệu tháng hiện tại của phần L
        FutureSituationOfTheMonthPublicHoliday leftPublicHolidays = null ;
        // 718 - Right: Dữ liệu tháng hiện tại và tương lai
        List<FutureSituationOfTheMonthPublicHoliday> rightPublicHolidays = new ArrayList<>() ;
        if (!closureInforOpt.isPresent()) {
            return null;
        }

        // 処理年月
        YearMonth currentMonth = closureInforOpt.get().getCurrentMonth();
        val cId = AppContexts.user().companyId();

        // 画面入力期間From(年月)/01　～　画面入力期間To  (年月)/月末日　★使用禁止
        val datePeriod = new DatePeriod(startDate, endDate);

        // 2021.12.29 - 3S - chinh.hm - issues #122017 - 追加 START
        Optional<List<PeriodCorrespondingYm>> currentMonthAndFuture = outputPeriodInformation == null ?Optional.empty(): outputPeriodInformation.getCurrentMonthAndFuture();
        List<PeriodCorrespondingYm>           correspondingYmList   = currentMonthAndFuture.orElseGet(ArrayList::new);
        // 2021.12.29 - 3S - chinh.hm - issues #122017 - 追加 END



        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////
        // 過去月
        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////



        // 過去月の開始年月と終了年月
        //YearMonthPeriod period = new YearMonthPeriod(startDate.yearMonth(), currentMonth.previousMonth());
        Optional<YearMonthPeriod> pastPeriod = outputPeriodInformation == null ? Optional.empty() : outputPeriodInformation.getPastPeriod();

        //-----------------------------------------------------------------------------------
        // 2021.12.29 - 3S - chinh.hm - issues #122017 - 追加 START
        if (pastPeriod.isPresent() ) {
            // 2021.12.29 - 3S - chinh.hm - issues #122017 - 追加 END
            //-----------------------------------------------------------------------------------

            period = pastPeriod.get();

            ////////////////////////////////////////////////////////////////////////////////
            // 過去月の月別残数データを取得する
            ////////////////////////////////////////////////////////////////////////////////
            // Mer RQ255,258,259,260,263
            hdRemainMer = hdRemainAdapter.getRemainMer(employeeId, period);

            // 過去月の年月のリスト作成
            lstYrMon = ConvertHelper.yearMonthsBetween(period);

            // 2022.01.04 - 3S - chinh.hm - issues #122037 - 追加 START
            //Map<YearMonth, List<RemainMerge>> mapRemainMer = repoRemainMer.findBySidsAndYrMons(employeeId, lstYrMon);
            //  2022.01.04 - - 3S - chinh.hm - issues #122037 - 追加 END
        }



        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////
        // 現在、当月・未来月
        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////



        ////////////////////////////////////////////////////////////////////////////////
        // 「現在の状況」の期間を設定する
        ////////////////////////////////////////////////////////////////////////////////
        DatePeriod currentPeriodDate = null;
        for (int i = 0; i < correspondingYmList.size(); i++){						// <List>当月・未来
            PeriodCorrespondingYm   correspondingYm = correspondingYmList.get(i);	// 当月・未来
            YearMonth ym          = correspondingYm.getYm();						    // 　年月

            if(currentMonth.compareTo(ym) == 0){									// 締め当月 == 当月・未来の年月
                currentPeriodDate = correspondingYm.getDatePeriod();	            // 　期間
                if(currentPeriodDate == null) continue;
                break;
            }
        }
        ////////////////////////////////////////////////////////////////////////////////

        // Mer RQ265,268,269,363,364,369
        boolean call265 = variousVacationControl.isAnnualHolidaySetting();		//[旧番号]	年休　　(現在)
        boolean call268 = variousVacationControl.isYearlyReservedSetting();		//			積立年休(現在)
        boolean call269 = variousVacationControl.isSubstituteHolidaySetting();	//[旧番号]	代休　　(現在、当月・未来月)
        boolean call363 = variousVacationControl.isAnnualHolidaySetting()		//			年休　　(現在、当月・未来月)
                && currentMonth.compareTo(endDate.yearMonth()) <= 0;
        boolean call364 = variousVacationControl.isYearlyReservedSetting()		//			積立年休(当月・未来月)
                && currentMonth.compareTo(endDate.yearMonth()) <= 0;
        boolean call369 = variousVacationControl.isAnnualHolidaySetting();		//			年休　　(次回年休付与日)

        ////////////////////////////////////////////////////////////////////////////////
        // 現在、当月・未来月のデータを取得する(363は不要かも…)
        ////////////////////////////////////////////////////////////////////////////////
        CheckCallRequest check = new CheckCallRequest(call265, call268, call269, call363, call364, call369);
        HdRemainDetailMerEx remainDel = hdRemainAdapter.getRemainDetailMer(
                employeeId,
                currentMonth,
                baseDate,
                new DatePeriod(startDate, endDate),
                check);

        ////////////////////////////////////////////////////////////////////////////////
        // 年休
        ////////////////////////////////////////////////////////////////////////////////
        if (variousVacationControl.isAnnualHolidaySetting()) {

            // Call RequestList369					// 次回年休付与日
            grantDate = remainDel.getResult369();

            // Call RequestList281					// 年休付与情報(有効期限あり)
            listAnnLeaGrantNumber = annLeaveAdapter.getAnnLeaGrantNumberImporteds(employeeId);
            listAnnLeaGrantNumber = listAnnLeaGrantNumber.stream()
                    .sorted(Comparator.comparing(AnnLeaGrantNumberImported::getGrantDate)).collect(Collectors.toList());

            // Call RequestList265					// 現在の状況(未使用)
            //annLeaveOfThisMonth = remainDel.getResult265();

            // Call RequestList255 ver2 - hoatt		// 月別利用状況(過去月)
            //-----------------------------------------------------------------------------------
            // 2021.12.29 - 3S - chinh.hm) - issues #122017 - 追加 START
            if ( hdRemainMer!=null) {
                listAnnualLeaveUsage = hdRemainMer.getResult255();
            }
            // 2021.12.29 - 3S - chinh.hm - issues #122017 - 追加 END

            // Call RequestList363					// 月別利用状況(当月・未来月),現在の状況
            if (currentMonth.compareTo(endDate.yearMonth()) <= 0) {
                ////////////////////////////////////////////////////////////////////////////////
                // 現在、当月・未来月のデータを取得する(363専用)
                ////////////////////////////////////////////////////////////////////////////////
                //listAnnLeaveUsageStatusOfThisMonth = remainDel.getResult363();
                getRs363 = hdRemainAdapter.getRs363(employeeId, 						// 社員ID
                        currentMonth, 						// 当月
                        baseDate,							// 処理基準日
                        new DatePeriod(startDate, endDate), // 画面入力期間From(年月)/01　～　画面入力期間To  (年月)/月末日
                        true);								//
            }
        }

        ////////////////////////////////////////////////////////////////////////////////
        // 積立年休
        ////////////////////////////////////////////////////////////////////////////////
        if (variousVacationControl.isYearlyReservedSetting()) {
            // Call RequestList268						// 現在の状況
            reserveHoliday = remainDel.getResult268();
            // Call RequestList258 ver2 - hoatt			// 月別利用状況(過去月)
            // 2021.12.29 - 3S - chinh.hm) - issues #122017 - 追加 START
            if ( hdRemainMer!=null) {
                listReservedYearHoliday = hdRemainMer.getResult258();
            }
            // 2021.12.29 - 3S - chinh.hm - issues #122017 - 追加 END
            // Call RequestList364						// 月別利用状況(当月・未来月)
            if (currentMonth.compareTo(endDate.yearMonth()) <= 0) {
                listRsvLeaUsedCurrentMon = remainDel.getResult364();
            }
        }
        ////////////////////////////////////////////////////////////////////////////////
        // 代休
        ////////////////////////////////////////////////////////////////////////////////
        if (variousVacationControl.isSubstituteHolidaySetting()) {
            //========================================
            // 当月・未来月
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
            // 過去月
            //========================================
            // Call RequestList259 ver2 - hoatt
            // 2021.12.29 - 3S - chinh.hm) - issues #122017 - 追加 START
            if ( hdRemainMer!=null) {
                listStatusHoliday = hdRemainMer.getResult259();
            }
            // 2021.12.29 - 3S - chinh.hm - issues #122017 - 追加 END

            //========================================
            // 現在
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
		// 振休
		////////////////////////////////////////////////////////////////////////////////
        if (variousVacationControl.isPauseItemHolidaySettingCompany()) {
            //========================================
            // 当月・未来月
            //========================================
            // Call RequestList204
            for (int i = 0; i < correspondingYmList.size(); i++){
                // 2021.12.29 - 3S - chinh.hm - issues #121957 - 追加 START
                PeriodCorrespondingYm  correspondingYm =    correspondingYmList.get(i);
                DatePeriod periodDate = correspondingYm.getDatePeriod();
                YearMonth ym = correspondingYm.getYm();
                // 2021.12.29 - 3S - chinh.hm - issues #121957 - 追加 END
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
            // 過去月
            //========================================
            // Call RequestList260 ver2 - hoatt
            // 2021.12.29 - 3S - chinh.hm) - issues #122017  - 追加 START
            if ( hdRemainMer!=null) {
                listStatusOfHoliday = hdRemainMer.getResult260();
            }

            //========================================
            // 現在
            //========================================
            // Call RequestList204

            // 2021.12.29 - 3S - chinh.hm - issues #122017  - 追加 END
            //③－２　Trường hợp 「当月在職状況」== true　
            // 2021.12.29 - 3S - chinh.hm - issues #122017  - 追加 START
            //　　　　if文追加 {

            // 2022.01.17 - inaguma #122461 CHANGE START
            //if(currentStatus){
            if(currentStatus && currentPeriodDate!=null){
                // 2022.01.17 - inaguma #122461 CHANGE END

                // 2021.12.29 - 3S - chinh.hm - issues #122017  - 追加 END
                val param = new AbsRecMngInPeriodRefactParamInput(
                        cId,
                        employeeId,
                        //-----------------------------------------------------------------------------------
                        // 2021.12.06 - 3S - chinh.hm  - issues #120916- 変更 START
                        //periodDate,

                        // 2022.01.17 - inaguma #122461 CHANGE START
                        //closureInforOpt.get().getPeriod(),
                        currentPeriodDate,
                        // 2022.01.17 - inaguma #122461 CHANGE END

                        // 2021.12.06 - 3S - chinh.hm  - issues #120916- 変更 END
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
                //③－３　Trường hợp「当月在職状況」== false　
                // 2021.12.29 - 3S - chinh.hm - issues #122017  - 追加 START
                //　　　　}
                //　　　　else {
                //　　　　　　Set rỗng (null? empty?) vào currentHolidayRemainLeft　　　　}
                currentHolidayRemainLeft = null;
                // 2021.12.29 - 3S - chinh.hm - issues #122017  - 追加 END
            }
        }

        ////////////////////////////////////////////////////////////////////////////////
        // 特別休暇
        ////////////////////////////////////////////////////////////////////////////////
        // hoatt
        //Map<Integer, SpecialVacationImported> mapSpecVaca = new HashMap<>();
        //Map<YearMonth, Map<Integer, SpecialVacationImported>> lstMapSPVaCurrMon = new HashMap<>();// key
        //Map<Integer, List<SpecialHolidayImported>> mapSpeHd = new HashMap<>();
        Map<Integer, SpecialVacationImportedKdr> map273New = new HashMap<>();
        Map<YearMonth, Map<Integer, SpecialVacationImportedKdr>> lstMap273CurrMon = new HashMap<>();// key
        //========================================
        // 当月・未来月
        //========================================
        // Call RequestList273
        for (int i = 0; i < correspondingYmList.size(); i++){
            //Map<Integer, SpecialVacationImported> mapSPVaCurrMon = new HashMap<>();
            Map<Integer, SpecialVacationImportedKdr> mapSP273CurrMon = new HashMap<>();
            // 2021.12.29 - 3S - chinh.hm - issues #121957 - 追加 START
            PeriodCorrespondingYm  correspondingYm =    correspondingYmList.get(i);
            DatePeriod periodDate = correspondingYm.getDatePeriod();
            YearMonth ym = correspondingYm.getYm();
            // 2021.12.29 - 3S - chinh.hm - issues #121957 - 追加 END
            for (SpecialHoliday specialHolidayDto : variousVacationControl.getListSpecialHoliday()) {// sphdCd
                int sphdCode = specialHolidayDto.getSpecialHolidayCode().v();
                //YearMonth ymEnd = ym.addMonths(1);
                //SpecialVacationImported spVaImported = specialLeaveAdapter.complileInPeriodOfSpecialLeave(cId,
                //        employeeId,
                //        new DatePeriod(GeneralDate.ymd(ym.year(), ym.month(), 1),
                //                GeneralDate.ymd(ymEnd.year(), ymEnd.month(), 1).addDays(-1)),
                //        false, baseDate, sphdCode, false);

                // ↓　New RQ273
                SpecialVacationImportedKdr spVaImportedNew = specialLeaveAdapter.get273New(
                        cId,
                        employeeId,
                        // 2021.12.29 - 3S - chinh.hm - issues #121957 - 変更 START
                        periodDate,
                        // 2021.12.29 - 3S - chinh.hm - issues #121957 - 変更 END
                        false,
                        //-----------------------------------------------------------------------------------
                        // 2021.12.06 - 3S - chinh.hm  - issues #120916- 変更 START
                        //baseDate,
                        closureInforOpt.get().getPeriod().end(),
                        // 2021.12.06 - 3S - chinh.hm  - issues #120916- 変更 END
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
        // 現在
        //========================================
        // 2021.12.29 - 3S - chinh.hm - issues #122017  - 追加 START

        // 2022.01.17 - inaguma #122461 CHANGE START
        //if(currentStatus){
        if(currentStatus && currentPeriodDate!=null){
            // 2022.01.17 - inaguma #122461 CHANGE END

            // 2021.12.29 - 3S - chinh.hm - issues #122017  - 追加 END
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

                // ↓　New RQ273
                SpecialVacationImportedKdr specialVacationImportedNew = specialLeaveAdapter.get273New(
                        cId,
                        employeeId,

                        // 2022.01.17 - inaguma #122461 CHANGE START
                        //closureInforOpt.get().getPeriod(),
                        currentPeriodDate,
                        // 2022.01.17 - inaguma #122461 CHANGE END

                        false,
                        //-----------------------------------------------------------------------------------
                        // 2021.12.06 - 3S - chinh.hm  - issues #120916- 変更 START
                        //baseDate,
                        closureInforOpt.get().getPeriod().end(),
                        // 2021.12.06 - 3S - chinh.hm  - issues #120916- 変更 END
                        //-----------------------------------------------------------------------------------
                        sphdCode,
                        false);
                //mapSpecVaca.put(sphdCode, specialVacationImported);
                map273New.put(sphdCode, specialVacationImportedNew);

            }
        }else {
            // 2022.01.04 - 3S - chinh.hm - issues #122017 - 追加 START
            map273New = new HashMap<>();
            // 2022.12.04 - 3S - chinh.hm - issues #122017 - 追加 END
        }

        ////////////////////////////////////////////////////////////////////////////////
        // 子の看護
        ////////////////////////////////////////////////////////////////////////////////
        //if (variousVacationControl.isChildNursingSetting()) {
        //    // Call RequestList206
        //    childNursingLeave = childNursingAdapter.getChildNursingLeaveCurrentSituation(
        //            cId,
        //            employeeId,
        //            datePeriod);
        //}

        ////////////////////////////////////////////////////////////////////////////////
        // 介護
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
        // ここからも引き続きデータ取得メソッド呼出、return値への項目移送を実施している
        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////
        // 代休
        ////////////////////////////////////////////////////////////////////////////////

        val rq = requireService.createRequire();
        // 期間内の休出代休残数を取得する

        //========================================
        // 現在
        //========================================
        // RequestList203
        // 2021.12.29 - 3S - chinh.hm - issues #122017  - 追加 START

        // 2022.01.17 - inaguma #122461 CHANGE START
        //if(currentStatus){
        if(currentStatus && currentPeriodDate!=null){
            // 2022.01.17 - inaguma #122461 CHANGE END

            // 2021.12.29 - 3S - chinh.hm - issues #122017  - 追加 END
            //-----------------------------------------------------------------------------------
            // 2021.12.06 - 3S - chinh.hm  - issues #120916- 変更 START
            //DatePeriod periodDate = new DatePeriod(
            //        GeneralDate.ymd(currentMonth.year(), currentMonth.month(), 1),
            //        GeneralDate.ymd(currentMonth.year(), currentMonth.month(), 1).addMonths(1).addDays(-1));
            //DatePeriod periodDate = closureInforOpt.get().getPeriod();
            // 2021.12.06 - 3S - chinh.hm  - issues #120916- 変更 END
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
            // 2022.01.04 - 3S - chinh.hm - issues #122017 - 追加 START
            substituteHolidayAggrResult = null;
            // 2022.12.04 - 3S - chinh.hm - issues #122017 - 追加 END
        }

        //========================================
        // 当月・未来月
        //========================================
        // RequestList203
        for (int i = 0; i < correspondingYmList.size(); i++){
            // 2021.12.29 - 3S - chinh.hm - issues #121957 - 追加 START
            PeriodCorrespondingYm  correspondingYm =    correspondingYmList.get(i);
            DatePeriod periods = correspondingYm.getDatePeriod();
            YearMonth ym = correspondingYm.getYm();
            // 2021.12.29 - 3S - chinh.hm - issues #121957 - 追加 END
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
        // 振休
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
        // 60H超休
        ////////////////////////////////////////////////////////////////////////////////

        //========================================
        // 60H超休の検証は後日実施
        //========================================

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

        ////////////////////////////////////////////////////////////////////////////////
        // 特別休暇
        ////////////////////////////////////////////////////////////////////////////////

        //========================================
        // 過去月
        //========================================

        // Call RequestList263
        //-----------------------------------------------------------------------------------
        // 2021.12.24 - 3S - chinh.hm  - issues #122037 - 変更 START
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
        // 2021.12.24 - 3S - chinh.hm  - issues #122037 - 変更 END

        // 2021.12.29 - 3S - chinh.hm) - issues #122037 - 追加 START
        if ( hdRemainMer!=null) {
            getSpeHdOfConfMonVer2 = hdRemainMer.getResult263();
        }
        // 2021.12.29 - 3S - chinh.hm - issues #122037 - 追加 END


        ////////////////////////////////////////////////////////////////////////////////
        // 子の看護
        // 介護
        ////////////////////////////////////////////////////////////////////////////////

        //========================================
        // 過去月
        //========================================
        // 2021.12.29 - 3S - chinh.hm) - issues #121957 - 追加 START
        if(!lstYrMon.isEmpty()){
            // RQ 342
            monthlyConfirmedCareForEmployees = getChildcareRemNumEachMonth.getMonthlyConfirmedCareForEmployees(employeeId, lstYrMon);
            // RQ 344
            obtainMonthlyConfirmedCareForEmployees = getChildcareRemNumEachMonth.getObtainMonthlyConfirmedCareForEmployees(employeeId, lstYrMon);
        }
        // 2021.12.29 - 3S - chinh.hm) - issues #121957 - 追加 END

        //========================================
        // 当月・未来月
        //========================================

        for (int i = 0; i < correspondingYmList.size(); i++){

            // 2021.12.29 - 3S - chinh.hm - issues #121957 - 追加 START
            PeriodCorrespondingYm  correspondingYm =    correspondingYmList.get(i);
            DatePeriod periods = correspondingYm.getDatePeriod();
            YearMonth ym = correspondingYm.getYm();
            // 2021.12.29 - 3S - chinh.hm - issues #121957 - 追加 END
            // RQ206(改)
            childCareRemNumWithinPeriodRight.add(
                    getRemainingNumberChildCareNurseAdapter.getChildCareRemNumWithinPeriod(
                            cId,
                            employeeId,
                            periods,
                            ym,
                            //-----------------------------------------------------------------------------------
                            // 2021.12.06 - 3S - chinh.hm  - issues #120916- 変更 START
                            //end)
                            closureInforOpt.get().getPeriod().end())
                    // 2021.12.06 - 3S - chinh.hm  - issues #120916- 変更 END
                    //-----------------------------------------------------------------------------------
            );
            // RQ207(改)
            nursingCareLeaveThisMonthFutureSituationRight.add(
                    getRemainingNumberChildCareNurseAdapter.getNursingCareLeaveThisMonthFutureSituation(
                            cId,
                            employeeId,
                            periods,
                            ym,
                            //-----------------------------------------------------------------------------------
                            // 2021.12.06 - 3S - chinh.hm  - issues #120916- 変更 START
                            //end)
                            closureInforOpt.get().getPeriod().end())
                    // 2021.12.06 - 3S - chinh.hm  - issues #120916- 変更 END
                    //-----------------------------------------------------------------------------------
            );

        }
        //========================================
        // 現在
        //========================================
        // 2021.12.29 - 3S - chinh.hm - issues #122017  - 追加 START

        // 2022.01.17 - inaguma #122461 CHANGE START
        //if(currentStatus){
        if(currentStatus && currentPeriodDate!=null){
            // 2022.01.17 - inaguma #122461 CHANGE END

            // 2021.12.29 - 3S - chinh.hm - issues #122017  - 追加 END
            // RQ206(改)
            childCareRemNumWithinPeriodLeft = getRemainingNumberChildCareNurseAdapter.getChildCareRemNumWithinPeriod(
                    cId,
                    employeeId,
                    //-----------------------------------------------------------------------------------
                    // 2021.12.06 - 3S - chinh.hm  - issues #120916- 変更 START
                    //periods,
                    //end

                    // 2022.01.17 - inaguma #122461 CHANGE START
                    //closureInforOpt.get().getPeriod(),
                    currentPeriodDate,
                    // 2022.01.17 - inaguma #122461 CHANGE END

                    closureInforOpt.get().getCurrentMonth(),
                    closureInforOpt.get().getPeriod().end()
                    // 2021.12.06 - 3S - chinh.hm  - issues #120916- 変更 END
                    //-----------------------------------------------------------------------------------
            );
            // RQ207(改)
            nursingCareLeaveThisMonthFutureSituationLeft = getRemainingNumberChildCareNurseAdapter.getNursingCareLeaveThisMonthFutureSituation(
                    cId,
                    employeeId,
                    //-----------------------------------------------------------------------------------
                    // 2021.12.06 - 3S - chinh.hm  - issues #120916- 変更 START
                    //periods,
                    //end

                    // 2022.01.17 - inaguma #122461 CHANGE START
                    //closureInforOpt.get().getPeriod(),
                    currentPeriodDate,
                    // 2022.01.17 - inaguma #122461 CHANGE END

                    closureInforOpt.get().getCurrentMonth(),
                    closureInforOpt.get().getPeriod().end()
                    // 2021.12.06 - 3S - chinh.hm  - issues #120916- 変更 END
                    //-----------------------------------------------------------------------------------
            );

        }else {
            // 2022.01.04 - 3S - chinh.hm - issues #122017 - 追加 START
            childCareRemNumWithinPeriodLeft = null;
            nursingCareLeaveThisMonthFutureSituationLeft = null;
            // 2022.12.04 - 3S - chinh.hm - issues #122017 - 追加 END
        }
        // 2021.12.29 - 3S - chinh.hm - issues #119961  - 追加 END


        // 2021.12.30 - 3S - chinh.hm - issues #119961  - 追加 START
        ////////////////////////////////////////////////////////////////////////////////
        // 公休
        ////////////////////////////////////////////////////////////////////////////////

        //========================================
        // 過去月
        //========================================
        // 2022.01.24 - 3S - chinh.hm  - issues #122620  - 変更 START
        // Tồn tại quá khứ.
        if(hdRemainMer!=null){
            publicHolidayPastSituations = hdRemainMer.getResult262();
        }
        // 2022.01.24 - 3S - chinh.hm  - issues #122620  - 変更 END


        //========================================
        // 現在、当月・未来月
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
                // 現在
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
                // 当月・未来月
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
        // 2021.12.30 - 3S - chinh.hm - issues #119961  - 追加 END


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
     * アルゴリズム「締めごとの出力期間を作成する」を実行する
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
            //<締め>のアルゴリズム「当月の期間を算出する」を実行する
            DatePeriod datePeriod = ClosureService.getClosurePeriod(closure, processingYm);
            PeriodCorrespondingYm currentMonthPeriod = new PeriodCorrespondingYm(processingYm,datePeriod);
            result.setCurrentPeriod(currentMonthPeriod);
            // 出力期間情報を作成する
            // 出力期間を設定する
            // Input : 出力期間：INPUT.出力期間
            // 過去の期間 - Past period
            // 出力期間に過去が含まれるかをチェックする
            // INPUT.出力期間.開始年月＜当月期間.年月
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
            //INPUT.出力期間.開始年月≧当月期間.年月
            //当月および未来の期間 - The current and future period
            //出力期間に当月以降が含まれるかをチェックする

            //出力期間に当月以降が含まれるかをチェックする
            //当月期間.年月≦INPUT.出力期間.終了年月
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
                        //<締め>のアルゴリズム「指定した年月の期間をすべて取得する」を実行する
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
     * アルゴリズム「社員ごとの出力期間を作成する」を実行する
     */
    private CurrentStatusAndPeriodInformation createExportPeriodForEmployee(
            String sid,
            OutputPeriodInformation periodInformation,
            PeriodCorrespondingYm currentPeriod){
        YearMonthPeriod pastPeriodByEmployee = null;
        YearMonth startYm = null;
        YearMonth endYm   = null;
        CurrentStatusAndPeriodInformation rs = new CurrentStatusAndPeriodInformation();
        //社員の入社・退職情報を取得する
        //(Lấy thông tin vào công ty・nghỉ việc của employee)
        val listEmpInfo =  personEmpBasicInfoAdapter.getEmployeeBasicInfoExport(Collections.singletonList(sid));
        if(periodInformation != null){
            EmployeeBasicInfoExport employee = listEmpInfo.isEmpty()? null: listEmpInfo.get(0);
            //社員の当月・未来月の期間
            // Đoạn thông tin tháng hiện tại và tương lai của employee.
            List<PeriodCorrespondingYm> currentMonthAndFuture =
                    periodInformation.getCurrentMonthAndFuture().isPresent()?
                            periodInformation.getCurrentMonthAndFuture().get() : new ArrayList<>();
            if(employee!=null){
                Optional<DatePeriod> optionalPeriod = currentPeriod== null ? Optional.empty(): Optional.of(currentPeriod.getDatePeriod());
                DatePeriod thisMonthPeriod          = optionalPeriod.orElse(null);

                // 当月の在職状況を設定する
                boolean ischeck =  thisMonthPeriod != null && checkStatus(employee,thisMonthPeriod);
                rs.setCurrentStatus(ischeck);
                // 入社年月日
                GeneralDate entryDate = employee.getEntryDate();
                // 開始年月日の補正 update ngày bắt đầu.
                // Gọi thuật toán アルゴリズム「指定日時点の締め期間を取得する」
                // Input: 　  + 社員ID：INPUT.社員ID
                //          　+ 基準日：社員.入社年月日
                // Output:　期間(入社時)：OUTPUT.締め期間
                Optional<ClosurePeriod> optPeriodByEntryDate =  getClosingPeriodOfSpecifiedDate(sid,entryDate);
                if(optPeriodByEntryDate.isPresent()){
                    // 期間(入社時)
                    ClosurePeriod closurePeriod = optPeriodByEntryDate.get();
                    //  各期間の開始と入社日を比較する
                    //  ＜条件＞
                    // ・社員別出力期間.当月・未来＝設定あり
                    // ・社員別出力期間.当月・未来に期間(入社時).年月が存在する
                    boolean condition_1 = checkExist(closurePeriod.getYearMonth(),currentMonthAndFuture);
                    if(!condition_1 ){
                        //  ＜条件＞
                        //　・社員別出力期間.過去＝設定あり
                        //　・社員別出力期間.過去が期間(入社時).年月を含む※
                        //　　※開始年月≦期間(入社時).年月≦終了年月
                        Optional<YearMonthPeriod> pastPeriod = periodInformation.getPastPeriod();
                        boolean condition_2 = pastPeriod.isPresent()
                                &&  pastPeriod.get().start().lessThanOrEqualTo(closurePeriod.getYearMonth())
                                &&  closurePeriod.getYearMonth().lessThanOrEqualTo(pastPeriod.get().end());
                        if(condition_2){
                            //社員別出力期間を更新する
                            //過去.開始年月　←　期間(入社時).年月
                            // 開始年月日の補正 update ngày bắt đầu.
                            startYm = closurePeriod.getYearMonth();
                            endYm   = pastPeriod.get().end();
                            pastPeriodByEmployee = new YearMonthPeriod(startYm, endYm);
                            periodInformation.setPastPeriod(Optional.of(pastPeriodByEmployee));
                        }
                    }else {
                        //過去：設定なし※クリアする
                        periodInformation.setPastPeriod(Optional.empty());
                        //＜未来入社＞
                        //◆当月・未来[n].年月＜期間(入社時).年月
                        //　　⇒Listから除去する
                        //◆当月・未来[n].年月＝期間(入社時).年月
                        //　　⇒値を更新する
                        //　　　・期間.開始年月日　←　社員.入社年月日
                        //◆当月・未来[n].年月＞期間(入社時).年月
                        //　　⇒何もしない
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
                //終了年月日の補正 - Update ngày kết thúc
                //Gọi thuật toán アルゴリズム「指定日時点の締め期間を取得する」
                //＜INPUT＞
                //　社員ID：INPUT.社員ID
                //　基準日：社員.退職年月日
                //＜OUTPUT＞
                //　期間(退職時)：OUTPUT.締め期間
                // 退職年月日
                GeneralDate retiredDate = employee.getRetiredDate();
                Optional<ClosurePeriod> optPeriodByRetiredDate =
                        getClosingPeriodOfSpecifiedDate(sid, retiredDate);
                if(optPeriodByRetiredDate.isPresent()){
                    val periodByRetiredDate = optPeriodByRetiredDate.get();
                    //＜条件＞
                    //　・社員別出力期間.当月・未来＝設定あり
                    //　・社員別出力期間.当月・未来に期間(退職時).年月が存在する
                    boolean condition_1 = checkExist(periodByRetiredDate.getYearMonth(),currentMonthAndFuture);
                    if(!condition_1){
                        //＜条件＞
                        //　・社員別出力期間.過去＝設定あり
                        //　・社員別出力期間.過去が期間(退職時).年月を含む※
                        //　　※開始年月≦期間(退職時).年月≦終了年月
                        Optional<YearMonthPeriod> pastPeriod = periodInformation.getPastPeriod();
                        boolean condition_2 = pastPeriod.isPresent()
                                &&  pastPeriod.get().start().lessThanOrEqualTo(periodByRetiredDate.getYearMonth())
                                &&  periodByRetiredDate.getYearMonth().lessThanOrEqualTo(pastPeriod.get().end());
                        if(condition_2){
                            //社員別出力期間を更新する
                            //過去.終了年月　←　期間(退職時).年月
                            startYm = pastPeriod.get().start();
                            endYm   = periodByRetiredDate.getYearMonth();
                            pastPeriodByEmployee = new YearMonthPeriod(startYm, endYm);
                            periodInformation.setPastPeriod(Optional.of(pastPeriodByEmployee));
                            //未来・過去：設定なし※クリアする
                            currentMonthAndFuture = new ArrayList<>();
                        }
                    }else {
                        //＜未来退職＞
                        //◆当月・未来[n].年月＜期間(退職時).年月
                        //　　⇒何もしない
                        //◆当月・未来[n].年月＝期間(退職時).年月
                        //　　⇒値を更新する
                        //　　　・期間.終了年月日　←　社員.退職年月日
                        //◆当月・未来[n].年月＞期間(入社時).年月
                        //　　⇒Listから除去する
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

    //(Kiểm tra  trạng thái còn đương chức )
    private boolean checkStatus(EmployeeBasicInfoExport employeeBasicInfoExport,DatePeriod currentMonth){
        //当月在職状況：＜条件＞の結果
        //＜条件＞
        //以下のいずれかを満たす
        //・社員.入社年月日≦締め期間.終了日
        //・社員.退職年月日≧締め期間.開始日
        //　※締め期間：INPUT.当月期間.期間
        return employeeBasicInfoExport.getEntryDate().beforeOrEquals(currentMonth.end())
                && employeeBasicInfoExport.getRetiredDate().afterOrEquals(currentMonth.start());
    }
    /**
     * アルゴリズム「指定日時点の締め期間を取得する」
     */

    private Optional<ClosurePeriod> getClosingPeriodOfSpecifiedDate(String sid, GeneralDate baseDate){
        val require = ClosureService.createRequireM3(closureRepo, closureEmploymentRepository, shareEmploymentAdapter);
        // 社員に対応する処理締めを取得する
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
