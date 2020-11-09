package nts.uk.file.at.app.export.annualworkledger;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
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
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerContent;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerExportDataSource;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSetting;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.CreateAnnualWorkLedgerContentDomainService;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffAtWorkplaceImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
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
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsImport;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * 勤務状況表の対象ファイルを出力する :: ファイルの出力
     *
     * @param context
     */
    @Override
    protected void handle(ExportServiceContext<AnnualWorkLedgerFileQuery> context) {
        AnnualWorkLedgerFileQuery query = context.getQuery();
        GeneralDate startMonth = query.getStartMonth();
        GeneralDate endMonth = query.getEndMonth();
        YearMonthPeriod yearMonthPeriod = new YearMonthPeriod(startMonth.yearMonth(), endMonth.yearMonth());
        List<String> lstEmpIds = query.getLstEmpIds();
        DatePeriod datePeriod = this.getFromClosureDate(startMonth, endMonth, query.getClosureDate().getClosureDay());
        GeneralDate baseDate = datePeriod.end();

        // 1 Call [No.600]社員ID（List）から社員コードと表示名を取得（削除社員考慮）
        List<EmployeeBasicInfoImport> lstEmployeeInfo = empEmployeeAdapter.getEmpInfoLstBySids(lstEmpIds, datePeriod, true, false);
        Map<String, EmployeeBasicInfoImport> mapEmployeeInfo = lstEmployeeInfo.stream().collect(Collectors.toMap(EmployeeBasicInfoImport::getSid, i -> i));
        // 2 Call 会社を取得する
        String companyId = AppContexts.user().companyId();
        CompanyBsImport companyInfo = companyBsAdapter.getCompanyByCid(companyId);

        // 3 Call 社員ID（List）と基準日から所属職場IDを取得
        List<AffAtWorkplaceImport> lstAffAtWorkplaceImport = affWorkplaceAdapter.findBySIdAndBaseDate(lstEmpIds, baseDate);
        List<String> listWorkplaceId = lstAffAtWorkplaceImport.stream().map(AffAtWorkplaceImport::getWorkplaceId).collect(Collectors.toList());
        // 3.1 Call [No.560]職場IDから職場の情報をすべて取得する
        List<WorkplaceInfor> lstWorkplaceInfo = workplaceConfigInfoAdapter.getWorkplaceInforByWkpIds(companyId, listWorkplaceId, baseDate);

        Map<String, WorkplaceInfor> mapWorkplaceInfo = lstWorkplaceInfo.stream().collect(Collectors.toMap(WorkplaceInfor::getWorkplaceId, i -> i));

        Map<String, WorkplaceInfor> mapEmployeeWorkplace = new HashMap<>();
        lstAffAtWorkplaceImport.forEach(x -> {
            WorkplaceInfor workplaceInfor = mapWorkplaceInfo.get(x.getWorkplaceId());
            mapEmployeeWorkplace.put(x.getEmployeeId(), workplaceInfor);
        });

        // 4 Call 基準日で社員の雇用と締め日を取得する
        RequireClosureDateEmploymentService require1 = new RequireClosureDateEmploymentService(shareEmploymentAdapter, closureRepository, closureEmploymentRepository);
        List<ClosureDateEmployment> lstClosureDateEmployment = GetClosureDateEmploymentDomainService.getByDate(require1, baseDate, lstEmpIds);
        Map<String, ClosureDateEmployment> mapClosureDateEmployment = lstClosureDateEmployment.stream().collect(Collectors.toMap(ClosureDateEmployment::getEmployeeId, i -> i));

        // 5 Call 年間勤務台帳の出力設定の詳細を取得する
        AnnualWorkLedgerOutputSetting outputSetting = annualWorkLedgerOutputSettingFinder.getById(query.getSettingId());

        // 6 Call 年間勤務台帳の表示内容を作成する
        RequireCreateAnnualWorkLedgerContentService require2 = new RequireCreateAnnualWorkLedgerContentService(affComHistAdapter, itemServiceAdapter, actualMultipleMonthAdapter);
        List<AnnualWorkLedgerContent> lstContent = CreateAnnualWorkLedgerContentDomainService.getData(require2, datePeriod, mapEmployeeInfo, outputSetting, mapEmployeeWorkplace, mapClosureDateEmployment);

        AnnualWorkLedgerExportDataSource dataSource = new AnnualWorkLedgerExportDataSource(
                query.getMode(),
                companyInfo.getCompanyName(),
                outputSetting,
                yearMonthPeriod,
                query.isZeroDisplay(),
                lstContent
        );
        displayGenerator.generate(context.getGeneratorContext(), dataSource);
    }

    private DatePeriod getFromClosureDate(GeneralDate startMonth, GeneralDate endMonth, int closureDay) {
        GeneralDate startDate = GeneralDate.ymd(startMonth.year(), startMonth.month(),
                Math.min(closureDay, startMonth.lastDateInMonth())).addDays(1);
        GeneralDate endDate = GeneralDate.ymd(endMonth.year(), endMonth.month(),
                Math.min(closureDay, endMonth.lastDateInMonth()));

        return new DatePeriod(startDate, endDate);
    }

    @AllArgsConstructor
    private class RequireClosureDateEmploymentService implements GetClosureDateEmploymentDomainService.Require {
        private ShareEmploymentAdapter shareEmploymentAdapter;
        private ClosureRepository closureRepository;
        private ClosureEmploymentRepository closureEmploymentRepository;

        @Override
        public Map<String, BsEmploymentHistoryImport> getEmploymentInfor(String companyId, List<String> listSid, GeneralDate baseDate) {
            return shareEmploymentAdapter.findEmpHistoryVer2(companyId, listSid, baseDate);
        }

        @Override
        public Closure getClosureDataByEmployee(String employeeId, GeneralDate baseDate) {
            Closure closure = ClosureService.getClosureDataByEmployee(
                    ClosureService.createRequireM3(closureRepository, closureEmploymentRepository, shareEmploymentAdapter),
                    new CacheCarrier(), employeeId, baseDate);

            return closure;
        }
    }

    @AllArgsConstructor
    private class RequireCreateAnnualWorkLedgerContentService implements CreateAnnualWorkLedgerContentDomainService.Require {
        private AffComHistAdapter affComHistAdapter;
        private AttendanceItemServiceAdapter itemServiceAdapter;
        private ActualMultipleMonthAdapter actualMultipleMonthAdapter;

        @Override
        public List<StatusOfEmployee> getListAffComHistByListSidAndPeriod(List<String> sid, DatePeriod datePeriod) {
            return affComHistAdapter.getListAffComHist(sid, datePeriod);
        }

        @Override
        public AttendanceResultDto getValueOf(String employeeId, GeneralDate workingDate, Collection<Integer> itemIds) {
            return itemServiceAdapter.getValueOf(employeeId, workingDate, itemIds);
        }

        @Override
        public Map<String, List<MonthlyRecordValueImport>> getActualMultipleMonth(List<String> employeeIds, YearMonthPeriod period, List<Integer> itemIds) {
            return actualMultipleMonthAdapter.getActualMultipleMonth(employeeIds, period, itemIds);
        }
    }
}
