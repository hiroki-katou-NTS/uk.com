package nts.uk.ctx.pr.core.infra.repository.wageprovision.organizationinformation.salaryclassification.salaryclassificationhistory;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclasshistory.EmploySalaryClassHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclasshistory.EmploySalaryClassHistoryRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.organizationinformation.salaryclassification.salaryclasshistory.QpbmtEmpSalaCategory;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.organizationinformation.salaryclassification.salaryclasshistory.QpbmtEmpSalaCategoryPk;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.organizationinformation.salaryclassification.salaryclasshistory.QpbmtEmpSalaClassHis;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmploySalaryClassHistoryRepository extends JpaRepository implements EmploySalaryClassHistoryRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmpSalaCategory f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empSalaCategoryPk.sid =:sid AND f.empSalaCategoryPk.hisId =:hisId ";
    private static final String SELECT_BY_EMPLOYEEID = SELECT_ALL_QUERY_STRING + " WHERE  f.empSalaCategoryPk.hisId =:hisId AND f.empSalaCategoryPk.hisId ";

    @Override
    public List<EmploySalaryClassHistory> getAllEmploySalaryClassHistory() {
        return null;
    }


    @Override
    public void add(EmploySalaryClassHistory domain) {

    }

    @Override
    public void update(EmploySalaryClassHistory domain) {

    }


    @Override
    public Optional<EmploySalaryClassHistory> getEmploySalaryClassHistoryById(String employeeId, String hisId) {
        List<QpbmtEmpSalaClassHis> entities = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtEmpSalaClassHis.class)
                .setParameter("sid", employeeId)
                .setParameter("hisId", hisId)
                .getList();
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new EmploySalaryClassHistory(employeeId, toDomain(entities)));
    }


    @Override
    public void remove(String hisId) {
        this.commandProxy().remove(QpbmtEmpSalaCategory.class, new QpbmtEmpSalaCategoryPk(hisId));
    }

    private List<YearMonthHistoryItem> toDomain(List<QpbmtEmpSalaClassHis> entities) {
        List<YearMonthHistoryItem> yearMonthHistoryItemList = new ArrayList<YearMonthHistoryItem>();
        if (entities.isEmpty()) {
            return yearMonthHistoryItemList;
        }

        entities.forEach(entity -> {
            yearMonthHistoryItemList.add(new YearMonthHistoryItem(entity.empSalaClassHisPk.hisId,
                    new YearMonthPeriod(new YearMonth(entity.startYearMonth),
                            new YearMonth(entity.endYearMonth))));
        });
        return yearMonthHistoryItemList;
    }
}
