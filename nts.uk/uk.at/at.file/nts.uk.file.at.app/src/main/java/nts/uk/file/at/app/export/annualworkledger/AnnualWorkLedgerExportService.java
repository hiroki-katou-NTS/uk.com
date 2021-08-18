package nts.uk.file.at.app.export.annualworkledger;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.app.find.annualworkledger.AnnualWorkLedgerOutputSettingFinder;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.ActualMultipleMonthAdapter;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.MonthlyRecordValueImport;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AffComHistAdapter;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceItemServiceAdapter;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceResultDto;
import nts.uk.ctx.at.function.dom.commonform.ClosureDateEmployment;
import nts.uk.ctx.at.function.dom.commonform.GetClosureDateEmploymentDomainService;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.*;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.EmpAffInfoExportDto;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffAtWorkplaceImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.algorithm.masterinfo.CodeNameInfo;
import nts.uk.ctx.at.record.dom.algorithm.masterinfo.GetMaterData;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.closure.GetSpecifyPeriod;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceConfigInfoAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsImport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 年間勤務台帳のExcelファイルを出力する
 */
@Stateless
public class AnnualWorkLedgerExportService extends ExportService<AnnualWorkLedgerFileQuery> {

    @Inject
    private EmpEmployeeAdapter empEmployeeAdapter;

    @Inject
    private CompanyBsAdapter companyBsAdapter;

    @Inject
    private AffWorkplaceAdapter affWorkplaceAdapter;

    @Inject
    private WorkplaceConfigInfoAdapter workplaceConfigInfoAdapter;

    @Inject
    private ShareEmploymentAdapter shareEmploymentAdapter;

    @Inject
    private ClosureRepository closureRepository;

    @Inject
    private ClosureEmploymentRepository closureEmploymentRepository;

    @Inject
    private AnnualWorkLedgerOutputSettingFinder annualWorkLedgerOutputSettingFinder;

    @Inject
    private AffComHistAdapter affComHistAdapter;

    @Inject
    private AttendanceItemServiceAdapter itemServiceAdapter;

    @Inject
    private ActualMultipleMonthAdapter actualMultipleMonthAdapter;

    @Inject
    private DisplayAnnualWorkLedgerReportGenerator displayGenerator;

    @Inject
    private GetSpecifyPeriod getSpecifyPeriod;

    @Inject
    private WorkTypeRepository workTypeRepository;

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepository;

    private static final int WORK_TYPE = 1;

    private static final int WORKING_HOURS = 2;

    /**
     * 勤務状況表の対象ファイルを出力する :: ファイルの出力
     *
     * @param context
     */
    @Override
    protected void handle(ExportServiceContext<AnnualWorkLedgerFileQuery> context) {
        AnnualWorkLedgerFileQuery query = context.getQuery();
        YearMonth yearMonthStart = new YearMonth(query.getStartMonth());
        YearMonth yearMonthEnd = new YearMonth(query.getEndMonth());
        YearMonthPeriod yearMonthPeriod = new YearMonthPeriod(yearMonthStart, yearMonthEnd);
        val closureId = query.getClosureId() == 0 ? 1 : query.getClosureId();
        String companyId = AppContexts.user().companyId();
        // 1 ⑨:find(会社ID、ClosureId)
        Optional<Closure> closureOptional = closureRepository.findById(AppContexts.user().companyId(), closureId);
        // 1.1
        val periodOptionalStart = this.getSpecifyPeriod.getSpecifyPeriod(yearMonthStart)
                .stream().filter(x -> x.getClosureId().value == closureId).findFirst();
        // 1.2
        val periodOptionalEnd = this.getSpecifyPeriod.getSpecifyPeriod(yearMonthEnd)
                .stream().filter(x -> x.getClosureId().value == closureId).findFirst();

        if (!periodOptionalStart.isPresent() || !periodOptionalEnd.isPresent()) {
            throw new RuntimeException(" CAN NOT FIND DATE PERIOD WITH CID = "
                    + companyId + "AND CLOSURE_ID = " + closureId);
        }

        List<String> lstEmpIds = query.getLstEmpIds();
        // ⑩
        DatePeriod datePeriodStart = periodOptionalStart.get().getPeriod();
        // ⑪
        DatePeriod datePeriodEnd = periodOptionalEnd.get().getPeriod();
        // 2 ⑫
        DatePeriod datePeriod = new DatePeriod(datePeriodStart.start(), datePeriodEnd.end());
        GeneralDate baseDate = datePeriod.end();
        ClosureDate closureDate = null;
        if (closureOptional.isPresent())
            closureDate = closureOptional.get().getHistoryByBaseDate(baseDate).getClosureDate();
        // 3 ① Call [No.600]社員ID（List）から社員コードと表示名を取得（削除社員考慮）
        List<EmployeeBasicInfoImport> lstEmployeeInfo = empEmployeeAdapter.getEmpInfoLstBySids(lstEmpIds, datePeriod, true, false);
        Map<String, EmployeeBasicInfoImport> mapEmployeeInfo = lstEmployeeInfo.stream().filter(distinctByKey(EmployeeBasicInfoImport::getSid))
                .collect(Collectors.toMap(EmployeeBasicInfoImport::getSid, i -> i));

        // 4 ② Call 会社を取得する
        CompanyBsImport companyInfo = companyBsAdapter.getCompanyByCid(companyId);

        // 5 ③ Call 社員ID（List）と基準日から所属職場IDを取得
        List<AffAtWorkplaceImport> lstAffAtWorkplaceImport = affWorkplaceAdapter.findBySIdAndBaseDate(lstEmpIds, baseDate);

        List<String> listWorkplaceId = lstAffAtWorkplaceImport.stream().map(AffAtWorkplaceImport::getWorkplaceId).distinct()
                .collect(Collectors.toList());
        // 5.1 ④ Call [No.560]職場IDから職場の情報をすべて取得する
        List<WorkplaceInfor> lstWorkplaceInfo = workplaceConfigInfoAdapter.getWorkplaceInforByWkpIds(companyId, listWorkplaceId, baseDate);

        Map<String, WorkplaceInfor> mapWorkplaceInfo = lstWorkplaceInfo.stream().filter(distinctByKey(WorkplaceInfor::getWorkplaceId))
                .collect(Collectors.toMap(WorkplaceInfor::getWorkplaceId, i -> i));

        Map<String, WorkplaceInfor> mapEmployeeWorkplace = new HashMap<>();
        lstAffAtWorkplaceImport.forEach(x -> {
            WorkplaceInfor workplaceInfor = mapWorkplaceInfo.get(x.getWorkplaceId());
            mapEmployeeWorkplace.put(x.getEmployeeId(), workplaceInfor);
        });

        // 4 Call 基準日で社員の雇用と締め日を取得する
        RequireClosureDateEmploymentService require1 = new RequireClosureDateEmploymentService(
                shareEmploymentAdapter, closureRepository, closureEmploymentRepository);


        // 5 Call 年間勤務台帳の出力設定の詳細を取得する
        Optional<AnnualWorkLedgerOutputSetting> outputSetting = annualWorkLedgerOutputSettingFinder.getById(query.getSettingId());
        if (!outputSetting.isPresent()) {
            throw new BusinessException("Msg_1898");
        }
        // 6 ⑤ call 基準日で社員の雇用と締め日を取得する
        List<ClosureDateEmployment> lstClosureDateEmployment = GetClosureDateEmploymentDomainService.get(require1, baseDate, lstEmpIds);
        Map<String, ClosureDateEmployment> mapClosureDateEmployment = lstClosureDateEmployment.stream().filter(distinctByKey(ClosureDateEmployment::getEmployeeId))
                .collect(Collectors.toMap(ClosureDateEmployment::getEmployeeId, i -> i));

        // 6 Call 年間勤務台帳の表示内容を作成する
        RequireCreateAnnualWorkLedgerContentService require2 = new RequireCreateAnnualWorkLedgerContentService(
                affComHistAdapter, itemServiceAdapter, actualMultipleMonthAdapter);
        List<AnnualWorkLedgerContent> lstContent = CreateAnnualWorkLedgerContentQuery.getData(
                require2,
                datePeriod,
                mapEmployeeInfo,
                outputSetting.get(),
                mapEmployeeWorkplace,
                mapClosureDateEmployment,
                yearMonthPeriod);
        Comparator<AnnualWorkLedgerContent> compare = Comparator
                .comparing(AnnualWorkLedgerContent::getWorkplaceCode)
                .thenComparing(AnnualWorkLedgerContent::getEmployeeCode);
        val lsSorted = lstContent.stream().sorted(compare).collect(Collectors.toList());
        // 7 年間勤務台帳を作成する
        AnnualWorkLedgerExportDataSource dataSource = new AnnualWorkLedgerExportDataSource(
                query.getMode(),
                companyInfo.getCompanyName(),
                outputSetting.get(),
                yearMonthPeriod,
                datePeriod,
                query.isZeroDisplay(),
                lsSorted
        );
        displayGenerator.generate(context.getGeneratorContext(), dataSource);
    }

    @AllArgsConstructor
    private class RequireClosureDateEmploymentService implements GetClosureDateEmploymentDomainService.Require {
        private ShareEmploymentAdapter shareEmploymentAdapter;
        private ClosureRepository closureRepository;
        private ClosureEmploymentRepository closureEmploymentRepository;

        @Override
        public Map<String, BsEmploymentHistoryImport> getEmploymentInfor(List<String> listSid, GeneralDate baseDate) {
            return shareEmploymentAdapter.findEmpHistoryVer2(AppContexts.user().companyId(), listSid, baseDate);
        }

        @Override
        public Optional<Closure> getClosureDataByEmployee(String employeeId, GeneralDate baseDate) {
            Closure closure = ClosureService.getClosureDataByEmployee(
                    ClosureService.createRequireM3(closureRepository, closureEmploymentRepository, shareEmploymentAdapter),
                    new CacheCarrier(), employeeId, baseDate);

            return closure != null ? Optional.of(closure) : Optional.empty();
        }
    }

    @AllArgsConstructor
    private class RequireCreateAnnualWorkLedgerContentService implements CreateAnnualWorkLedgerContentQuery.Require {
        private AffComHistAdapter affComHistAdapter;
        private AttendanceItemServiceAdapter itemServiceAdapter;
        private ActualMultipleMonthAdapter actualMultipleMonthAdapter;

        @Override
        public EmpAffInfoExportDto getAffiliationPeriod(List<String> listSid, YearMonthPeriod YMPeriod, GeneralDate baseDate) {
            return affComHistAdapter.getAffiliationPeriod(listSid, YMPeriod, baseDate);
        }

        @Override
        public List<AttendanceResultDto> getValueOf(List<String> employeeIds, DatePeriod workingDatePeriod, Collection<Integer> itemIds) {
            return itemServiceAdapter.getValueOf(employeeIds, workingDatePeriod, itemIds);
        }


        @Override
        public Map<String, List<MonthlyRecordValueImport>> getActualMultipleMonth(List<String> employeeIds, YearMonthPeriod period, List<Integer> itemIds) {
            return actualMultipleMonthAdapter.getActualMultipleMonth(employeeIds, period, itemIds);
        }

        @Override
        public Map<Integer, Map<String, CodeNameInfoDto>> getAllDataMaster(String companyId) {

            val workTypeData = workTypeRepository.findByCompanyId(companyId);

            Map<String, CodeNameInfoDto> mapWorkTypeData = workTypeData.stream()
                    .collect(Collectors.toMap(e -> e.getWorkTypeCode().v(), j -> new CodeNameInfoDto(
                            j.getWorkTypeCode().v(),
                            j.getAbbreviationName().v(),
                            null
                    )));
            val workHourData = workTimeSettingRepository.findByCompanyId(companyId);
            Map<String, CodeNameInfoDto> mapWorkHourData = workHourData.stream()
                    .collect(Collectors.toMap(e -> e.getWorktimeCode().v(), j -> new CodeNameInfoDto(
                            j.getWorktimeCode().v(),
                            j.getWorkTimeDisplayName().getWorkTimeName().v(),
                            null
                    )));
            Map<Integer, Map<String, CodeNameInfoDto>> rs = new HashMap<>();
            rs.put(WORK_TYPE, mapWorkTypeData);
            rs.put(WORKING_HOURS, mapWorkHourData);
            return rs;
        }
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
