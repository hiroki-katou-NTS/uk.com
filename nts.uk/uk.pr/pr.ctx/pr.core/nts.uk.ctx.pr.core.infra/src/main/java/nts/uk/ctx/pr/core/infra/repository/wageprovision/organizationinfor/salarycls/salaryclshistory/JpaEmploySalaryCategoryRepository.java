package nts.uk.ctx.pr.core.infra.repository.wageprovision.organizationinfor.salarycls.salaryclshistory;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory.EmploySalaryCategory;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory.EmploySalaryCategoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory.EmploySalaryClassHistory;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.organizationinfor.salarycls.salaryclshistory.QpbmtEmpSalaCategory;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmploySalaryCategoryRepository extends JpaRepository implements EmploySalaryCategoryRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmpSalaCategory f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empSalaCategoryPk.sid =:sid AND f.empSalaCategoryPk.hisId =:hisId ";
    private static final String SELECT_BY_EMPLOYEE_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.empSalaCategoryPk.hisId =:hisId AND f.empSalaCategoryPk.hisId ";


    @Override
    public List<EmploySalaryCategory> getAllEmploySalaryClassHistory() {
        return null;
    }

    @Override
    public Optional<EmploySalaryCategory> getEmploySalaryClassHistoryById(String hisId) {
        return Optional.empty();
    }

    @Override
    public void add(EmploySalaryCategory domain) {

    }

    @Override
    public void update(EmploySalaryCategory domain) {

    }

    @Override
    public void remove(String hisId) {

    }

    @Override
    public Optional<EmploySalaryClassHistory> getEmploySalaryClassHistoryById(String employeeId, String hisId) {
        List<QpbmtEmpSalaCategory> entities = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtEmpSalaCategory.class)
                .setParameter("sid", employeeId)
                .setParameter("hisId", hisId)
                .getList();
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new EmploySalaryClassHistory(employeeId, toDomain(entities)));
    }

    private List<YearMonthHistoryItem> toDomain(List<QpbmtEmpSalaCategory> entities) {
        List<YearMonthHistoryItem> yearMonthHistoryItemList = new ArrayList<YearMonthHistoryItem>();
        if (entities.isEmpty()) {
            return yearMonthHistoryItemList;
        }

        entities.forEach(entity -> {
            yearMonthHistoryItemList.add(new YearMonthHistoryItem(entity.empSalaCategoryPk.hisId,
                    new YearMonthPeriod(new YearMonth(entity.startYearMonth),
                            new YearMonth(entity.endYearMonth))));
        });
        return yearMonthHistoryItemList;
    }
}
