package nts.uk.file.at.app.export.holidayconfirmationtable;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffAtWorkplaceImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceConfigInfoAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureIdPresentClosingPeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureIdPresentClosingPeriods;


import nts.uk.ctx.bs.company.dom.company.*;
import nts.uk.ctx.sys.auth.dom.adapter.workplace.SysAuthWorkplaceAdapter;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class OutputTraceConfirmationTableService extends ExportService<CreateTraceConfirmationTableFileQuery> {
    @Inject
    OutputTraceConfirmTableReportGenerator reportGenerator;

    @Inject
    private GetClosureIdPresentClosingPeriods getClosureIdPresentClosingPeriods;

    @Inject
    private EmpEmployeeAdapter empEmployeeAdapter;

    @Inject
    private CompanyRepository companyRepository;

    @Inject
    private AffWorkplaceAdapter affWorkplaceAdapter;

    @Inject
    private SysAuthWorkplaceAdapter sysAuthWorkplaceAdapter;

    @Inject
    private WorkplaceConfigInfoAdapter workplaceConfigInfoAdapter;

    @Inject
    private CompensLeaveComSetRepository compensLeaveComSetRepo;

    @Override
    protected void handle(ExportServiceContext<CreateTraceConfirmationTableFileQuery> exportServiceContext) {
        val query = exportServiceContext.getQuery();
        val lstEmpIds = query.getListEmployeeId();
        val cid = AppContexts.user().companyId();
        val dataSource = new OutputTraceConfirmTableDataSource();
        // 1-- ①= call 全ての締めの処理年月と締め期間を取得する
        List<ClosureIdPresentClosingPeriod> closingPeriods = getClosureIdPresentClosingPeriods.get(cid);
        // 1.1-②
        Optional<ClosureIdPresentClosingPeriod> employeeBasicInfoImport =
                closingPeriods.stream().filter(x -> x.getClosureId().equals(1)).findFirst();
        DatePeriod datePeriod = null;
        if (employeeBasicInfoImport.isPresent()) {
            YearMonth processingYm = employeeBasicInfoImport.get()
                    .getCurrentClosingPeriod().getProcessingYm();
            val startDate = processingYm.firstGeneralDate();
            val endDate = processingYm.lastGeneralDate();

            datePeriod = new DatePeriod(startDate, endDate);
        }

        // 2--③= Call [No.600]社員ID（List）から社員コードと表示名を取得（削除社員考慮）
        List<EmployeeBasicInfoImport> lstEmployeeInfo = empEmployeeAdapter.getEmpInfoLstBySids(lstEmpIds, datePeriod, true, false);
        GeneralDate referenceDate = datePeriod == null ? null : datePeriod.end();
        val listemployees = lstEmployeeInfo.stream()
                .map(EmployeeBasicInfoImport::getSid).collect(Collectors.toList());
        // 3--④= Call 会社を取得する
        val companyInfo = companyRepository.getComanyByCid(cid);

        // 4--⑤= Call 社員ID（List）と基準日から所属職場IDを取得
        List<AffAtWorkplaceImport> lstAffAtWorkplaceImport = affWorkplaceAdapter
                .findBySIdAndBaseDate(listemployees, referenceDate);
        val listWorkplaceId = lstAffAtWorkplaceImport.stream().map(AffAtWorkplaceImport::getWorkplaceId)
                .collect(Collectors.toList());

        // 4.1-⑥
        List<WorkplaceInfor> lstWorkplaceInfo = workplaceConfigInfoAdapter
                .getWorkplaceInforByWkpIds(cid, listWorkplaceId, referenceDate);

        // 5-- ⑦ get 代休管理設定
        reportGenerator.generate(exportServiceContext.getGeneratorContext(), dataSource);
    }
}
