package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHisRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis.QqsmtEmpSocialInsGradeHis;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis.QqsmtEmpSocialInsGradeHisPk;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis.QqsmtEmpSocialInsGradeInfoPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class JpaEmpSocialInsGradeHisRepository extends JpaRepository implements EmpSocialInsGradeHisRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpSocialInsGradeHis f";
    private static final String SELECT_BY_EMPID = SELECT_ALL_QUERY_STRING + " WHERE f.cId = :cId and f.sId = :sId ORDER BY f.startYM DESC";
    private static final String SELECT_BY_HISTID = SELECT_ALL_QUERY_STRING + " WHERE f.qqsmtEmpSocialInsGradeHisPk.historyId = :histId ORDER BY f.startYM DESC";
    private static final String SELECT_BY_SIDS_AND_BASE_YM = SELECT_ALL_QUERY_STRING + " WHERE f.sid IN :sids AND f.startYM <= :baseYM AND f.endYM >= :baseYM";

    private static final String SELECT_BY_ID_AND_BASE_DATE = SELECT_ALL_QUERY_STRING + " WHERE f.sId =:sid AND f.cId =:cid AND f.startYM <= :baseYM AND f.endYM >= :baseYM ";
    /**
     * Convert from domain to entity
     *
     * @param companyId
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
        Optional<QqsmtEmpSocialInsGradeHis> histItem = this.queryProxy().find(new QqsmtEmpSocialInsGradeHisPk(item.identifier()), QqsmtEmpSocialInsGradeHis.class);
        if (!histItem.isPresent()) {
            throw new RuntimeException("invalid QqsmtEmpSocialInsGradeHis");
        }
        updateEntity(item, histItem.get());
        this.commandProxy().update(histItem.get());
    }

    @Override
    public void delete(String histId) {
        this.commandProxy().remove(QqsmtEmpSocialInsGradeHis.class, new QqsmtEmpSocialInsGradeHisPk(histId));
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
    public Optional<EmpSocialInsGradeHis> getBySidAndBaseDate(String sid, GeneralDate baseDate) {

        if (baseDate == null){
            return Optional.empty();
        }

        List<QqsmtEmpSocialInsGradeHis> result =  this.queryProxy().query(SELECT_BY_ID_AND_BASE_DATE, QqsmtEmpSocialInsGradeHis.class)
                .setParameter("sid", sid)
                .setParameter("cid", AppContexts.user().companyId())
                .setParameter("baseYM",baseDate.yearMonth())
                .getList();
        return result.isEmpty() ? Optional.empty() : Optional.of(toDomainTemp(result));
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

    @Override
    public List<EmpSocialInsGradeHis> getBySidsAndBaseYM(List<String> sids, YearMonth baseYearMonth) {
        List<EmpSocialInsGradeHis> result = new ArrayList<>();
        CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitList -> {
            result.addAll(toDomains(this.queryProxy().query(SELECT_BY_SIDS_AND_BASE_YM, QqsmtEmpSocialInsGradeHis.class)
                    .setParameter("sids", sids)
                    .setParameter("baseYM", baseYearMonth)
                    .getList()));
        });
        return result;
    }

    private List<EmpSocialInsGradeHis> toDomains(List<QqsmtEmpSocialInsGradeHis> entities) {
        List<EmpSocialInsGradeHis> result = new ArrayList<>();
        entities.stream().collect(Collectors.groupingBy(e -> e.sId))
                .forEach((k, v) -> {
                    List<YearMonthHistoryItem> historyItems = v.stream()
                            .sorted((o1, o2) -> o2.startYM.compareTo(o1.startYM))
                            .map(e -> new YearMonthHistoryItem(e.qqsmtEmpSocialInsGradeHisPk.historyId, new YearMonthPeriod(new YearMonth(e.startYM), new YearMonth(e.endYM))))
                            .collect(Collectors.toList());
                    result.add(new EmpSocialInsGradeHis(entities.get(0).cId, k, historyItems));
                });
        return result;
    }

    private Optional<EmpSocialInsGradeHis> toDomain(List<QqsmtEmpSocialInsGradeHis> entities) {
        if (entities.isEmpty()) {
            return Optional.empty();
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
