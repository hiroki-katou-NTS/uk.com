package nts.uk.file.at.app.export.workledgeroutputitem;

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
import nts.uk.ctx.at.function.app.query.workledgeroutputitem.GetSettingDetailWorkLedger;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.ActualMultipleMonthAdapter;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.MonthlyRecordValueImport;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AffComHistAdapter;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceItemServiceAdapter;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.CodeNameInfoDto;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.EmpAffInfoExportDto;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.EmployeeInfor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.WorkPlaceInfo;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.*;
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
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.monthlyattditem.aggregate.MonthlyAttItemCanAggregateRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsImport;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class WorkLedgerOutputItemService extends ExportService<WorkLedgerOutputItemFileQuery> {
    @Inject
    private EmpEmployeeAdapter empEmployeeAdapter;

    @Inject
    private CompanyBsAdapter companyBsAdapter;

    @Inject
    private AffWorkplaceAdapter affWorkplaceAdapter;

    @Inject
    private WorkplaceConfigInfoAdapter workplaceConfigInfoAdapter;

    @Inject
    private GetSettingDetailWorkLedger detailWorkLedger;

    @Inject
    private AffComHistAdapter affComHistAdapter;

    @Inject
    private AttendanceItemServiceAdapter itemServiceAdapter;

    @Inject
    private CompanyMonthlyItemService monthlyItemService;

    @Inject
    private ActualMultipleMonthAdapter actualMultipleMonthAdapter;

    @Inject
    private ShareEmploymentAdapter shareEmploymentAdapter;

    @Inject
    private ClosureRepository closureRepository;

    @Inject
    private ClosureEmploymentRepository closureEmploymentRepository;

    @Inject
    private WorkLedgerOutputItemGenerator workLedgerGenerator;

    @Inject
    private MonthlyAttItemCanAggregateRepository monthlyAttItemCanAggregateRepo;

    @Inject
    private MonthlyAttendanceItemRepository monthlyAttendanceItemRepository;

    @Inject
    private GetSpecifyPeriod getSpecifyPeriod;

    @Inject
    private GetMaterData getMaterData;

    private static final int WORKPLACE = 5;

    private static final int EMPLOYMENT = 8;

    private static final int POSITION = 7;

    private static final int CLASSIFICATION = 6;

    @Override
    protected void handle(ExportServiceContext<WorkLedgerOutputItemFileQuery> context) {
        WorkLedgerOutputItemFileQuery query = context.getQuery();

        YearMonth yearMonthStart = new YearMonth(query.getStartMonth());
        YearMonth yearMonthEnd = new YearMonth(query.getEndMonth());
        YearMonthPeriod yearMonthPeriod = new YearMonthPeriod(yearMonthStart, yearMonthEnd);
        val closureId = query.getClosureId() == 0 ? 1 : query.getClosureId();
        String companyId = AppContexts.user().companyId();

        // 1.1
        val periodOptionalStart = this.getSpecifyPeriod.getSpecifyPeriod(yearMonthStart)
                .stream().filter(x -> x.getClosureId().value == closureId).findFirst();
        // 1.2
        val periodOptionalEnd = this.getSpecifyPeriod.getSpecifyPeriod(yearMonthEnd)
                .stream().filter(x -> x.getClosureId().value == closureId).findFirst();
        List<String> lstEmpIds = query.getLstEmpIds();

        if (!periodOptionalStart.isPresent() || !periodOptionalEnd.isPresent()) {
            throw new RuntimeException(" CAN NOT FIND DATE PERIOD WITH CID = "
                    + companyId + "AND CLOSURE_ID = " + closureId);
        }

        // ⑩
        DatePeriod datePeriodStart = periodOptionalStart.get().getPeriod();
        // ⑪
        DatePeriod datePeriodEnd = periodOptionalEnd.get().getPeriod();
        // 2 ⑫
        DatePeriod datePeriod = new DatePeriod(datePeriodStart.start(), datePeriodEnd.end());

        GeneralDate baseDate = datePeriod.end();

        // 3 ① [No.600]社員ID（List）から社員コードと表示名を取得（削除社員考慮）
        List<EmployeeBasicInfoImport> lstEmployeeInfo = empEmployeeAdapter.getEmpInfoLstBySids(lstEmpIds, datePeriod, true, true);
        // 4 ② Call 会社を取得する
        CompanyBsImport companyInfo = companyBsAdapter.getCompanyByCid(companyId);
        // 5 Call 社員ID（List）と基準日から所属職場IDを取得
        List<AffAtWorkplaceImport> lstAffAtWorkplaceImport = affWorkplaceAdapter
                .findBySIdAndBaseDate(lstEmpIds, baseDate);
        List<EmployeeInfor> employeeInfoList = new ArrayList<>();
        lstEmployeeInfo.forEach(e -> {
            val wpl = lstAffAtWorkplaceImport.stream().filter(i -> i.getEmployeeId().equals(e.getSid())).findFirst();
            employeeInfoList.add(new EmployeeInfor(
                    e.getSid(),
                    e.getEmployeeCode(),
                    e.getEmployeeName(),
                    wpl.isPresent() ? wpl.get().getWorkplaceId() : null
            ));
        });
        List<String> listWorkplaceId = lstAffAtWorkplaceImport.stream()
                .map(AffAtWorkplaceImport::getWorkplaceId).collect(Collectors.toList());
        // 5.1 ④ Call [No.560]職場IDから職場の情報をすべて取得する
        List<WorkplaceInfor> lstWorkplaceInfo = workplaceConfigInfoAdapter.getWorkplaceInforByWkpIds(companyId, listWorkplaceId, baseDate);

        List<WorkPlaceInfo> placeInfoList = lstWorkplaceInfo.stream()
                .map(e -> new WorkPlaceInfo(e.getWorkplaceId(), e.getWorkplaceCode(), e.getWorkplaceName(), e.getHierarchyCode()))
                .collect(Collectors.toList());

        // 6 ⑤ 勤務状況表の出力設定の詳細を取得する.

        WorkLedgerOutputItem workLedgerDetail = detailWorkLedger.getDetail(query.getSettingId());


        RequireImpl require = new RequireImpl(monthlyItemService, actualMultipleMonthAdapter, shareEmploymentAdapter, closureRepository,
                closureEmploymentRepository, affComHistAdapter, monthlyAttItemCanAggregateRepo, monthlyAttendanceItemRepository, getMaterData);
        // 7 ⑥
        List<WorkLedgerDisplayContent> listData = CreateWorkLedgerDisplayContentQuery
                .createWorkLedgerDisplayContent(require, datePeriod, employeeInfoList, workLedgerDetail, placeInfoList, yearMonthPeriod);
        Comparator<WorkLedgerDisplayContent> compare = Comparator
                .comparing(WorkLedgerDisplayContent::getWorkplaceCode)
                .thenComparing(WorkLedgerDisplayContent::getEmployeeCode);
        val lsSorted = listData.stream().sorted(compare).collect(Collectors.toList());

        WorkLedgerExportDataSource result = new WorkLedgerExportDataSource(
                companyInfo.getCompanyName(),
                workLedgerDetail.getName().v(),
                yearMonthPeriod,
                query.isZeroDisplay(),
                lsSorted,
                query.isCode()
        );
        workLedgerGenerator.generate(context.getGeneratorContext(), result);

    }

    @AllArgsConstructor
    public class RequireImpl implements CreateWorkLedgerDisplayContentQuery.Require {
        private CompanyMonthlyItemService monthlyItemService;
        private ActualMultipleMonthAdapter actualMultipleMonthAdapter;
        private ShareEmploymentAdapter shareEmploymentAdapter;
        private ClosureRepository closureRepository;
        private ClosureEmploymentRepository closureEmploymentRepository;
        private AffComHistAdapter affComHistAdapter;
        private MonthlyAttItemCanAggregateRepository monthlyAttItemCanAggregateRepo;
        private MonthlyAttendanceItemRepository monthlyAttendanceItemRepository;
        private GetMaterData getMaterData;

        @Override
        public EmpAffInfoExportDto getAffiliationPeriod(List<String> listSid, YearMonthPeriod YMPeriod, GeneralDate baseDate) {
            return affComHistAdapter.getAffiliationPeriod(listSid, YMPeriod, baseDate);
        }

        @Override
        public List<AttItemName> getMonthlyItems(String cid, Optional<String> authorityId, List<Integer> attendanceItemIds,
                                                 List<MonthlyAttendanceItemAtr> itemAtrs) {
            return monthlyItemService.getMonthlyItems(cid, authorityId, attendanceItemIds, itemAtrs);
        }

        @Override
        public Map<String, List<MonthlyRecordValueImport>> getActualMultipleMonth(List<String> employeeIds, YearMonthPeriod period, List<Integer> itemIds) {
            return actualMultipleMonthAdapter.getActualMultipleMonth(employeeIds, period, itemIds);
        }

        @Override
        public List<Integer> getAggregableMonthlyAttId(String cid) {
            return this.monthlyAttItemCanAggregateRepo.getMonthlyAtdItemCanAggregate(cid).stream()
                    .map(t -> t.v().intValue())
                    .collect(Collectors.toList());
        }

        @Override
        public List<MonthlyAttendanceItem> findByAttendanceItemId(String companyId, List<Integer> attendanceItemIds) {
            return monthlyAttendanceItemRepository.findByAttendanceItemId(companyId, attendanceItemIds);
        }

        public Map<String, BsEmploymentHistoryImport> getEmploymentInfor(List<String> listSid, GeneralDate baseDate) {
            return shareEmploymentAdapter.findEmpHistoryVer2(AppContexts.user().companyId(), listSid, baseDate);
        }

        @Override
        public Optional<Closure> getClosureDataByEmployee(String employeeId, GeneralDate baseDate) {
            val cls = ClosureService.getClosureDataByEmployee(
                    ClosureService.createRequireM3(closureRepository, closureEmploymentRepository, shareEmploymentAdapter),
                    new CacheCarrier(), employeeId, baseDate);
            return cls != null ? Optional.of(cls) : Optional.empty();
        }

        @Override
        public Map<Integer, Map<String, CodeNameInfoDto>> getAllDataMaster(String companyId, GeneralDate dateReference, List<Integer> lstDivNO) {

            val data = getMaterData.getAllDataMaster(companyId, dateReference, lstDivNO);
            Map<Integer, Map<String, CodeNameInfoDto>> rs = new HashMap<>();
            Map<Integer, Map<String, CodeNameInfo>> listItem = new HashMap<>();
            listItem.put(WORKPLACE, data.getOrDefault(WORKPLACE, null));
            listItem.put(EMPLOYMENT, data.getOrDefault(EMPLOYMENT, null));
            listItem.put(POSITION, data.getOrDefault(POSITION, null));
            listItem.put(CLASSIFICATION, data.getOrDefault(CLASSIFICATION, null));

            listItem.forEach((k, e) -> {
                Map<String, CodeNameInfoDto> item = new HashMap<>();
                e.forEach((key, value) -> {
                    item.put(key, new CodeNameInfoDto(value.getCode(), value.getName(), value.getId()));
                });
                rs.put(k, item);
            });
            return rs;
        }
    }
}