package nts.uk.file.at.app.export.workledgeroutputitem;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.app.query.workledgeroutputitem.GetSettingDetailWorkLedger;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AffComHistAdapter;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceItemServiceAdapter;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.EmployeeInfor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.WorkPlaceInfo;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItem;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffAtWorkplaceImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceConfigInfoAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsImport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
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
    private  WorkLedgerOutputItemGenerator  workLedgerGenerator;

    @Override
    protected void handle(ExportServiceContext<WorkLedgerOutputItemFileQuery> context) {
        WorkLedgerOutputItemFileQuery query = context.getQuery();
        GeneralDate targetDate = query.getTargetDate();
        List<String> lstEmpIds = query.getLstEmpIds();
        ClosureDate closureDate = new ClosureDate(query.getClosureDate().getClosureDay(), query.getClosureDate().getLastDayOfMonth());
        DatePeriod datePeriod = this.getFromClosureDate(targetDate, closureDate);
        // [No.600]社員ID（List）から社員コードと表示名を取得（削除社員考慮）
        List<EmployeeBasicInfoImport> lstEmployeeInfo = empEmployeeAdapter.getEmpInfoLstBySids(lstEmpIds, datePeriod, true, true);
        // 2 Call 会社を取得する
        String companyId = AppContexts.user().companyId();
        CompanyBsImport companyInfo = companyBsAdapter.getCompanyByCid(companyId);
        // 3 Call 社員ID（List）と基準日から所属職場IDを取得
        GeneralDate lastDate = GeneralDate.ymd(targetDate.year(), targetDate.month(), targetDate.lastDateInMonth());
        GeneralDate baseDate = lastDate;
        List<AffAtWorkplaceImport> lstAffAtWorkplaceImport = affWorkplaceAdapter
                .findBySIdAndBaseDate(lstEmpIds, baseDate);
        List<EmployeeInfor> employeeInfoList = new ArrayList<EmployeeInfor>();
        lstEmployeeInfo.forEach(e->{
            val wpl = lstAffAtWorkplaceImport.stream().filter(i->i.getEmployeeId().equals(e.getSid())).findFirst();
            employeeInfoList.add(new EmployeeInfor(
                    e.getSid(),
                    e.getEmployeeCode(),
                    e.getEmployeeName(),
                    wpl.isPresent()?wpl.get().getWorkplaceId():null
            ));
        });
        List<String> listWorkplaceId = lstAffAtWorkplaceImport.stream()
                .map(AffAtWorkplaceImport::getWorkplaceId).collect(Collectors.toList());
        // 3.1 Call [No.560]職場IDから職場の情報をすべて取得する
        List<WorkplaceInfor> lstWorkplaceInfo = workplaceConfigInfoAdapter.getWorkplaceInforByWkpIds(companyId, listWorkplaceId, baseDate);

        List<WorkPlaceInfo> placeInfoList =lstWorkplaceInfo.stream().map(e->new WorkPlaceInfo(e.getWorkplaceId(),e.getWorkplaceCode(),e.getWorkplaceName())).collect(Collectors.toList());

        // 4. 勤務状況表の出力設定の詳細を取得する.
        WorkLedgerOutputItem workLedgerDetail = detailWorkLedger.getDetail(query.getSettingId());
    }
    private DatePeriod getFromClosureDate(GeneralDate targetDate, ClosureDate closureDate) {
        Integer closureDay = closureDate.getClosureDay().v();
        return new DatePeriod(GeneralDate.ymd(targetDate.year(), targetDate.month() - 1, closureDay + 1),
                GeneralDate.ymd(targetDate.year(), targetDate.month(), closureDay));
    }
}
