package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHisRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.GenericHistYMPeriod;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis.QqsmtEmpSocialInsGradeHis;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmpSocialInsGradeHisRepository extends JpaRepository implements EmpSocialInsGradeHisRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpSocialInsGradeHis f";
    private static final String SELECT_BY_EMPID = SELECT_ALL_QUERY_STRING + " WHERE f.sId = :sId";
    private static final String SELECT_BY_HISTID = SELECT_ALL_QUERY_STRING + " WHERE f.qqsmtEmpSocialInsGradeHisPk.historyId = :histId";

    @Override
    public void add(EmpSocialInsGradeHis domain) {

    }

    @Override
    public void update(EmpSocialInsGradeHis domain) {

    }

    @Override
    public void remove(EmpSocialInsGradeHis domain) {

    }

    @Override
    public void remove(String sId) {

    }

    @Override
    public void remove(String sId, String histId) {

    }

    @Override
    public List<EmpSocialInsGradeHis> getAllEmpSocialInsGradeHis() {
        return null;
    }

    @Override
    public Optional<EmpSocialInsGradeHis> getEmpSocialInsGradeHisBySId(String employeeId) {
        List<QqsmtEmpSocialInsGradeHis> hisList = this.queryProxy()
                .query(SELECT_BY_EMPID, QqsmtEmpSocialInsGradeHis.class)
                .setParameter("sId", employeeId).getList();
        return toDomain(hisList);
    }

    @Override
    public Optional<EmpSocialInsGradeHis> getEmpSocialInsGradeHisById(String employeeId, String hisId) {
        return Optional.empty();
    }

    @Override
    public Optional<EmpSocialInsGradeHis> getEmpSocialInsGradeHisByHistId(String histId) {
        return Optional.of(this.queryProxy().query(SELECT_BY_HISTID, QqsmtEmpSocialInsGradeHis.class).setParameter("histId", histId)
        .getSingle(x -> x.toDomain()).orElse(null));
    }

    @Override
    public Optional<EmpSocialInsGradeHis> getEmpSocialInsGradeHisById(List<String> employeeIds, GeneralDate startDate) {
        return Optional.empty();
    }

    private Optional<EmpSocialInsGradeHis> toDomain(List<QqsmtEmpSocialInsGradeHis> entities) {
        if (entities.isEmpty()) {
            return null;
        }
        EmpSocialInsGradeHis domain = new EmpSocialInsGradeHis();
        List<GenericHistYMPeriod> periodList = new ArrayList<>();
        entities.forEach(item -> {
            if (domain.getEmployeeId() == null) {
                domain.setEmployeeId(item.sId);
            }
            periodList.add(new GenericHistYMPeriod(item.qqsmtEmpSocialInsGradeHisPk.historyId, new YearMonthPeriod(new YearMonth(item.startYM), new YearMonth(item.endYM))));
        });

        domain.setPeriod(periodList);
        return Optional.of(domain);
    }
}
