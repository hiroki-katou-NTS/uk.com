package nts.uk.file.at.app.export.arbitraryperiodsummarytable;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.Period;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;


@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ArbitraryPeriodSummaryTableService extends ExportService<ArbitraryPeriodSummaryTableFileQuery> {

    @Inject
    private ArbitraryPeriodSummaryTableGenerator periodSummaryTableGenerator;

    @Inject
    private EmpEmployeeAdapter empEmployeeAdapter;

    private static final String DATE_FORMAT = "yyyy/MM/dd";

    @Override
    protected void handle(ExportServiceContext<ArbitraryPeriodSummaryTableFileQuery> exportServiceContext) {

        val generatorContext = exportServiceContext.getGeneratorContext();
        val query = exportServiceContext.getQuery();
        val lstEmpIds = query.getLstEmpIds();
        GeneralDate startDate = GeneralDate.fromString(query.getStartDate(), DATE_FORMAT);
        GeneralDate endDate = GeneralDate.fromString(query.getEndDate(), DATE_FORMAT);
        DatePeriod datePeriod = new DatePeriod(startDate, endDate);
        // 社員コードと表示名を取得する
        // ①  Call [No.600]社員ID（List）から社員コードと表示名を取得（削除社員考慮）
        List<EmployeeBasicInfoImport> lstEmployeeInfo = empEmployeeAdapter.getEmpInfoLstBySids(lstEmpIds, datePeriod, true, true);

        // 会社を取得する
        // ② 会社情報略版
        periodSummaryTableGenerator.generate(generatorContext, null);
    }
}
