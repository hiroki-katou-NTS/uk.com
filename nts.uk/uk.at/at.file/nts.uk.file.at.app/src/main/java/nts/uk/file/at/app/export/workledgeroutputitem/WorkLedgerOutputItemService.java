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
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.EmployeeInfor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.WorkPlaceInfo;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.*;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffAtWorkplaceImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceConfigInfoAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsImport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

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


    @Override
    protected void handle(ExportServiceContext<WorkLedgerOutputItemFileQuery> context) {
        WorkLedgerOutputItemFileQuery query = context.getQuery();

        YearMonth yearMonthStart = new YearMonth(query.getStartMonth());
        YearMonth yearMonthEnd = new YearMonth(query.getEndMonth());
        YearMonthPeriod yearMonthPeriod = new YearMonthPeriod(yearMonthStart, yearMonthEnd);

        List<String> lstEmpIds = query.getLstEmpIds();
        val cl = closureRepository.findById(AppContexts.user().companyId(), query.getClosureId());
        val basedateNow = GeneralDate.today();
        if (!cl.isPresent() || cl.get().getHistoryByBaseDate(basedateNow) == null) {
            throw new BusinessException("Còn QA");
        }
        val closureDate = cl.get().getHistoryByBaseDate(basedateNow).getClosureDate();
        DatePeriod datePeriod = this.getFromClosureDate(yearMonthStart, yearMonthEnd, closureDate.getClosureDay().v());
        // [No.600]社員ID（List）から社員コードと表示名を取得（削除社員考慮）
        List<EmployeeBasicInfoImport> lstEmployeeInfo = empEmployeeAdapter.getEmpInfoLstBySids(lstEmpIds, datePeriod, true, true);
        // 2 Call 会社を取得する
        String companyId = AppContexts.user().companyId();
        CompanyBsImport companyInfo = companyBsAdapter.getCompanyByCid(companyId);
        // 3 Call 社員ID（List）と基準日から所属職場IDを取得
        GeneralDate baseDate = datePeriod.end();
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
        // 3.1 Call [No.560]職場IDから職場の情報をすべて取得する
        List<WorkplaceInfor> lstWorkplaceInfo = workplaceConfigInfoAdapter.getWorkplaceInforByWkpIds(companyId, listWorkplaceId, baseDate);

        List<WorkPlaceInfo> placeInfoList = lstWorkplaceInfo.stream().map(e -> new WorkPlaceInfo(e.getWorkplaceId(), e.getWorkplaceCode(), e.getWorkplaceName())).collect(Collectors.toList());

        // 4. 勤務状況表の出力設定の詳細を取得する.

        WorkLedgerOutputItem workLedgerDetail = detailWorkLedger.getDetail(query.getSettingId());


        RequireImpl require = new RequireImpl(monthlyItemService, actualMultipleMonthAdapter, shareEmploymentAdapter, closureRepository, closureEmploymentRepository, affComHistAdapter);
        List<WorkLedgerDisplayContent> listData = CreateWorkLedgerDisplayContentDomainService.createWorkLedgerDisplayContent(require, datePeriod, employeeInfoList, workLedgerDetail, placeInfoList);
        Comparator<WorkLedgerDisplayContent> compare = Comparator
                .comparing(WorkLedgerDisplayContent::getWorkplaceCode)
                .thenComparing(WorkLedgerDisplayContent::getEmployeeCode);
        val lsSorted = listData.stream().sorted(compare).collect(Collectors.toList());

        WorkLedgerExportDataSource result = new WorkLedgerExportDataSource(
                companyInfo.getCompanyName(),
                workLedgerDetail.getName().v(),
                yearMonthPeriod,
                closureDate,
                query.isZeroDisplay(),
                lsSorted
        );
        workLedgerGenerator.generate(context.getGeneratorContext(), result);

    }

    @AllArgsConstructor
    public class RequireImpl implements CreateWorkLedgerDisplayContentDomainService.Require {
        private CompanyMonthlyItemService monthlyItemService;
        private ActualMultipleMonthAdapter actualMultipleMonthAdapter;
        private ShareEmploymentAdapter shareEmploymentAdapter;
        private ClosureRepository closureRepository;
        private ClosureEmploymentRepository closureEmploymentRepository;
        private AffComHistAdapter affComHistAdapter;

        @Override
        public List<StatusOfEmployee> getAffiliateEmpListDuringPeriod(DatePeriod datePeriod, List<String> empIdList) {
            return affComHistAdapter.getListAffComHist(empIdList, datePeriod);
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
            return null;
        }

        @Override
        public Map<String, BsEmploymentHistoryImport> getEmploymentInfor(String companyId, List<String> listSid, GeneralDate baseDate) {
            return shareEmploymentAdapter.findEmpHistoryVer2(companyId, listSid, baseDate);
        }

        @Override
        public Closure getClosureDataByEmployee(String employeeId, GeneralDate baseDate) {
            return ClosureService.getClosureDataByEmployee(
                    ClosureService.createRequireM3(closureRepository, closureEmploymentRepository, shareEmploymentAdapter),
                    new CacheCarrier(), employeeId, baseDate);
        }
    }

    private DatePeriod getFromClosureDate(YearMonth startMonth, YearMonth endMonth, int closureDay) {
        GeneralDate startDate = GeneralDate.ymd(startMonth.year(), startMonth.month(),
                Math.min(closureDay, startMonth.lastDateInMonth())).addDays(1);
        GeneralDate endDate = GeneralDate.ymd(endMonth.year(), endMonth.month(),
                Math.min(closureDay, endMonth.lastDateInMonth()));

        return new DatePeriod(startDate, endDate);
    }
}