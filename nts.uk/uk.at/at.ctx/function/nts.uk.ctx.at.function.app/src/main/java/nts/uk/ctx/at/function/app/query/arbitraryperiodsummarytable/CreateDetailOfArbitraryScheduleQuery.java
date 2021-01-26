package nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable;


import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AffComHistAdapter;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.ctx.at.shared.dom.monthlyattditem.aggregate.MonthlyAttItemCanAggregateRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 任意期間集計表の表示内容を作成する
 */
@Stateless
public class CreateDetailOfArbitraryScheduleQuery {

    @Inject
    private AffComHistAdapter affComHistAdapter;
    @Inject
    private MonthlyAttItemCanAggregateRepository monthlyAttItemCanAggregateRepo;
    public Object getDetail(DatePeriod period,
                            String aggrFrameCode,
                            List<EmployeeBasicInfoImport> employeeBasicInfoImportList,
                            List<WorkplaceInfor> workplaceInforList) {
        val cid = AppContexts.user().companyId();
        List<String> lisSids = employeeBasicInfoImportList.stream().map(EmployeeBasicInfoImport::getSid).collect(Collectors.toList());
        //⓪: call 社員の指定期間中の所属期間を取得する
        List<StatusOfEmployee> employees = affComHistAdapter.getListAffComHist(lisSids, period);
        //①: call 任意期間別実績を取得する
        val listActualAttendancetime = new ArrayList<>(); // TODO QA: 40272
        if(listActualAttendancetime.isEmpty()){
            throw new BusinessException("Msg_1894");
        }
        List<Integer> getAggregableMonthlyAttId = monthlyAttItemCanAggregateRepo.getMonthlyAtdItemCanAggregate(cid).stream()
                    .map(t -> t.v().intValue())
                    .collect(Collectors.toList());

        return null;

    }
}
