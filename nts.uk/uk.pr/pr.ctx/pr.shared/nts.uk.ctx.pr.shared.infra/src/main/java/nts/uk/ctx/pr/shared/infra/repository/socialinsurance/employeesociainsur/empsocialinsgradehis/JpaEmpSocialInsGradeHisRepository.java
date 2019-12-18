package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHisRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis.QqsmtEmpSocialInsGradeHis;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis.QqsmtEmpSocialInsGradeHisPk;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmpSocialInsGradeHisRepository extends JpaRepository implements EmpSocialInsGradeHisRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpSocialInsGradeHis f";
    private static final String SELECT_BY_EMPID = SELECT_ALL_QUERY_STRING + " WHERE f.cId = :cId and f.sId = :sId ORDER BY f.startYM";
    private static final String SELECT_BY_HISTID = SELECT_ALL_QUERY_STRING + " WHERE f.qqsmtEmpSocialInsGradeHisPk.historyId = :histId ORDER BY f.startYM";

    /**
     * Convert from domain to entity
     *
     * @param employeeID
     * @param item
     * @return
     */
    private QqsmtEmpSocialInsGradeHis toEntity(String companyId, String employeeID, YearMonthHistoryItem item) {
        return new QqsmtEmpSocialInsGradeHis(new QqsmtEmpSocialInsGradeHisPk(item.identifier()), companyId, employeeID, item.start().v(), item.end().v());
    }

    /**
     * Update entity from domain
     *
     * @param employeeID
     * @param item
     * @return
     */
    private void updateEntity(YearMonthHistoryItem item, QqsmtEmpSocialInsGradeHis entity) {
        entity.startYM = item.start().v();
        entity.endYM = item.end().v();
    }

    @Override
    public void add(String cid, String sid, YearMonthHistoryItem item) {
        this.commandProxy().insert(toEntity(cid, sid, item));
    }

    @Override
    public void update(YearMonthHistoryItem item) {
        Optional<QqsmtEmpSocialInsGradeHis> histItem = this.queryProxy().find(item.identifier(), QqsmtEmpSocialInsGradeHis.class);
        if (!histItem.isPresent()) {
            throw new RuntimeException("invalid QqsmtEmpSocialInsGradeHis");
        }
        updateEntity(item, histItem.get());
        this.commandProxy().update(histItem.get());
    }

    @Override
    public void delete(String histId) {
        Optional<QqsmtEmpSocialInsGradeHis> histItem = this.queryProxy().find(histId, QqsmtEmpSocialInsGradeHis.class);
        if (!histItem.isPresent()) {
            throw new RuntimeException("invalid QqsmtEmpSocialInsGradeHis");
        }
        this.commandProxy().remove(QqsmtEmpSocialInsGradeHis.class, histId);
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
    public Optional<EmpSocialInsGradeHis> getEmpSocialInsGradeHisBySId(String cid, String employeeId) {
        List<QqsmtEmpSocialInsGradeHis> hisList = this.queryProxy()
                .query(SELECT_BY_EMPID, QqsmtEmpSocialInsGradeHis.class)
                .setParameter("cId", cid)
                .setParameter("sId", employeeId).getList();

        if (hisList != null && !hisList.isEmpty()) {
            return Optional.of(toDomainTemp(hisList));
        }
        return Optional.empty();
    }

    /**
     * Convert to domain EmpSocialInsGradeHis
     *
     * @param employeeId
     * @param listHist
     * @return
     */
    private EmpSocialInsGradeHis toDomainTemp(List<QqsmtEmpSocialInsGradeHis> listHist) {
        EmpSocialInsGradeHis domain = new EmpSocialInsGradeHis(listHist.get(0).cId, listHist.get(0).sId, new ArrayList<YearMonthHistoryItem>());
        for (QqsmtEmpSocialInsGradeHis item : listHist) {
            YearMonthHistoryItem dateItem = new YearMonthHistoryItem(item.qqsmtEmpSocialInsGradeHisPk.historyId, new YearMonthPeriod(new YearMonth(item.startYM), new YearMonth( item.endYM)));
            domain.getYearMonthHistoryItems().add(dateItem);
        }
        return domain;
    }

    @Override
    public Optional<EmpSocialInsGradeHis> getEmpSocialInsGradeHisById(String employeeId, String hisId) {
        return Optional.empty();
    }

    @Override
    public Optional<EmpSocialInsGradeHis> getEmpSocialInsGradeHisByHistId(String histId) {
        return this.queryProxy().query(SELECT_BY_HISTID, QqsmtEmpSocialInsGradeHis.class).setParameter("histId", histId)
        .getSingle(x -> x.toDomain());
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
        List<YearMonthHistoryItem> periodList = new ArrayList<>();
        entities.forEach(item -> {
            if (domain.getEmployeeId() == null) {
                domain.setEmployeeId(item.sId);
            }
            periodList.add(new YearMonthHistoryItem(item.qqsmtEmpSocialInsGradeHisPk.historyId, new YearMonthPeriod(new YearMonth(item.startYM), new YearMonth(item.endYM))));
        });

        domain.setYearMonthHistoryItems(periodList);
        return Optional.of(domain);
    }
}
