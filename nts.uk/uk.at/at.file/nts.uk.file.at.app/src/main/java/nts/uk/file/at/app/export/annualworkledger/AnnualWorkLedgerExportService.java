package nts.uk.file.at.app.export.annualworkledger;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.app.find.annualworkledger.AnnualWorkLedgerOutputSettingFinder;
import nts.uk.ctx.at.function.dom.commonform.ClosureDateEmployment;
import nts.uk.ctx.at.function.dom.commonform.GetClosureDateEmploymentDomainService;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSetting;
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
import nts.uk.shr.com.time.calendar.date.ClosureDate;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<String> lstEmpIds = query.getLstEmpIds();

        // 1 Call [No.600]社員ID（List）から社員コードと表示名を取得（削除社員考慮）
        ClosureDate closureDate = new ClosureDate(query.getClosureDate().getClosureDay(), query.getClosureDate().getLastDayOfMonth());
        DatePeriod datePeriod = this.getFromClosureDate(startMonth, endMonth, closureDate);
        List<EmployeeBasicInfoImport> lstEmployeeInfo = empEmployeeAdapter.getEmpInfoLstBySids(lstEmpIds, datePeriod, true, false);

        // 2 Call 会社を取得する
        String companyId = AppContexts.user().companyId();
        CompanyBsImport companyInfo = companyBsAdapter.getCompanyByCid(companyId);

        // 3 Call 社員ID（List）と基準日から所属職場IDを取得
        GeneralDate baseDate = GeneralDate.ymd(endMonth.year(), endMonth.month(), endMonth.lastDateInMonth());
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
        RequireClosureDateEmploymentService require = new RequireClosureDateEmploymentService(shareEmploymentAdapter, closureRepository, closureEmploymentRepository);
        List<ClosureDateEmployment> lstClosureDateEmployment = GetClosureDateEmploymentDomainService.getByDate(require, baseDate, lstEmpIds);

        // 5 Call 年間勤務台帳の出力設定の詳細を取得する
        AnnualWorkLedgerOutputSetting outputSetting = annualWorkLedgerOutputSettingFinder.getById(query.getSettingId());

        // 6 Call 年間勤務台帳の表示内容を作成する

    }

    private DatePeriod getFromClosureDate(GeneralDate startMonth, GeneralDate endMonth, ClosureDate closureDate) {
        // TODO Confirming QA
        Integer closureDay = closureDate.getClosureDay().v();
        return new DatePeriod(GeneralDate.ymd(startMonth.year(), startMonth.month(), closureDay + 1),
                GeneralDate.ymd(endMonth.year(), endMonth.month(), closureDay));
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
}
