package nts.uk.file.at.app.export.arbitraryperiodsummarytable;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable.CreateDetailOfArbitraryScheduleQuery;
import nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable.DetailOfArbitrarySchedule;
import nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable.GetOutputSettingDetailArbitraryQuery;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitrary;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.EmployeeInfor;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffAtWorkplaceImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceConfigInfoAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsImport;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ArbitraryPeriodSummaryTableService extends ExportService<ArbitraryPeriodSummaryTableFileQuery> {

    @Inject
    private ArbitraryPeriodSummaryTableGenerator periodSummaryTableGenerator;
    @Inject
    private EmpEmployeeAdapter empEmployeeAdapter;
    @Inject
    private AffWorkplaceAdapter affWorkplaceAdapter;
    @Inject
    private WorkplaceConfigInfoAdapter workplaceConfigInfoAdapter;
    @Inject
    private GetOutputSettingDetailArbitraryQuery getOutputSettingDetailArbitraryQuery;
    @Inject
    private CreateDetailOfArbitraryScheduleQuery detailOfArbitraryScheduleQuery;
    private static final String DATE_FORMAT = "yyyy/MM/dd";

    @Inject
    private CompanyBsAdapter companyBsAdapter;

    @Override
    protected void handle(ExportServiceContext<ArbitraryPeriodSummaryTableFileQuery> exportServiceContext) {

        val generatorContext = exportServiceContext.getGeneratorContext();
        val query = exportServiceContext.getQuery();
        val lstEmpIds = query.getLstEmpIds();
        val settingId = query.getSettingId();
        GeneralDate startDate = GeneralDate.fromString(query.getStartDate(), DATE_FORMAT);
        GeneralDate endDate = GeneralDate.fromString(query.getEndDate(), DATE_FORMAT);
        DatePeriod datePeriod = new DatePeriod(startDate, endDate);
        // 社員コードと表示名を取得する
        // ①  Call [No.600]社員ID（List）から社員コードと表示名を取得（削除社員考慮）
        List<EmployeeBasicInfoImport> lstEmployeeInfo = empEmployeeAdapter.getEmpInfoLstBySids(lstEmpIds, datePeriod, true, true);
        // 会社を取得する
        // ② 会社情報略版
        String companyId = AppContexts.user().companyId();
        val sids = lstEmployeeInfo.stream().map(EmployeeBasicInfoImport::getSid).collect(Collectors.toList());
        CompanyBsImport companyInfo = companyBsAdapter.getCompanyByCid(companyId);

        // 社員ID（List）と基準日から所属職場IDを取得
        List<AffAtWorkplaceImport> lstAffAtWorkplaceImport = affWorkplaceAdapter
                .findBySIdAndBaseDate(sids, endDate);

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
        // ④
        List<WorkplaceInfor> lstWorkplaceInfo = workplaceConfigInfoAdapter.getWorkplaceInforByWkpIds(companyId, listWorkplaceId, endDate);

        OutputSettingOfArbitrary ofArbitrary = getOutputSettingDetailArbitraryQuery.getDetail(settingId);

        DetailOfArbitrarySchedule rs = detailOfArbitraryScheduleQuery.getContentOfArbitrarySchedule(datePeriod,
                query.getAggrFrameCode(),
                employeeInfoList,
                lstWorkplaceInfo,
                ofArbitrary,
                query.isDetail(),
                query.isWorkplaceTotal(),
                query.isTotal(),
                query.isCumulativeWorkplace(),
                query.getWorkplacePrintTargetList());

        ArbitraryPeriodSummaryDto data = new ArbitraryPeriodSummaryDto(
                rs,
                ofArbitrary,
                datePeriod,
                companyInfo,
                query
        );
        periodSummaryTableGenerator.generate(generatorContext, data);
    }
}
