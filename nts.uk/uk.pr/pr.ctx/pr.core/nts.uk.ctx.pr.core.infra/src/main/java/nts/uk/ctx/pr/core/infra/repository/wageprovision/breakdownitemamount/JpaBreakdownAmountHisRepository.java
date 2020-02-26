package nts.uk.ctx.pr.core.infra.repository.wageprovision.breakdownitemamount;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount.BreakdownAmountHis;
import nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount.BreakdownAmountHisRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.breakdownitemamount.QpbmtBreakAmountHis;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaBreakdownAmountHisRepository extends JpaRepository implements BreakdownAmountHisRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtBreakAmountHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.breakAmountHisPk.cid =:cid AND  f.breakAmountHisPk.categoryAtr =:categoryAtr AND  f.breakAmountHisPk.itemNameCd =:itemNameCd AND  f.breakAmountHisPk.employeeId =:employeeId ";
    private static final String SELECT_BY_KEY_STRING_SALARY_BONUS_ATR = SELECT_ALL_QUERY_STRING + "" +
            " WHERE  f.breakAmountHisPk.cid =:cid " +
            " AND  f.breakAmountHisPk.categoryAtr =:categoryAtr " +
            " AND  f.breakAmountHisPk.itemNameCd =:itemNameCd " +
            " AND  f.breakAmountHisPk.employeeId =:employeeId" +
            " AND  f.breakAmountHisPk.salaryBonusAtr =:salaryBonusAtr ";
    private static final String DELETE_BY_HISTORYID = "DELETE FROM QpbmtBreakAmountHis f" +
            " WHERE  f.breakAmountHisPk.cid =:cid " +
            " AND  f.breakAmountHisPk.categoryAtr =:categoryAtr " +
            " AND  f.breakAmountHisPk.itemNameCd =:itemNameCd " +
            " AND  f.breakAmountHisPk.employeeId =:employeeId" +
            " AND  f.breakAmountHisPk.salaryBonusAtr =:salaryBonusAtr " +
            " AND  f.breakAmountHisPk.historyId =:historyId ";

    private static final String UPDATE_BY_HISTORYID = "UPDATE QpbmtBreakAmountHis f" +
            " SET  f.endYearMonth = 999912" +
            " WHERE  f.breakAmountHisPk.cid =:cid " +
            " AND  f.breakAmountHisPk.categoryAtr =:categoryAtr " +
            " AND  f.breakAmountHisPk.itemNameCd =:itemNameCd " +
            " AND  f.breakAmountHisPk.employeeId =:employeeId" +
            " AND  f.breakAmountHisPk.salaryBonusAtr =:salaryBonusAtr " +
            " AND  f.breakAmountHisPk.historyId =:lastHistoryId ";

    private static final String UPDATE_BY_LASTHISTORYID = "UPDATE QpbmtBreakAmountHis f" +
            " SET  f.endYearMonth =:startYearMonth" +
            " WHERE  f.breakAmountHisPk.cid =:cid " +
            " AND  f.breakAmountHisPk.categoryAtr =:categoryAtr " +
            " AND  f.breakAmountHisPk.itemNameCd =:itemNameCd " +
            " AND  f.breakAmountHisPk.employeeId =:employeeId" +
            " AND  f.breakAmountHisPk.salaryBonusAtr =:salaryBonusAtr " +
            " AND  f.breakAmountHisPk.historyId =:lastHistoryId ";

    private Optional<BreakdownAmountHis> toDomain(List<QpbmtBreakAmountHis> entity) {
        if (entity.size() > 0) {
            String cid = entity.get(0).breakAmountHisPk.cid;
            int categoryAtr = entity.get(0).breakAmountHisPk.categoryAtr;
            String itemNameCd = entity.get(0).breakAmountHisPk.itemNameCd;
            String employeeId = entity.get(0).breakAmountHisPk.employeeId;
            List<YearMonthHistoryItem> period = entity.stream()
                    .map(item -> new YearMonthHistoryItem(item.breakAmountHisPk.historyId,
                            new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth))))
                    .collect(Collectors.toList());
            int salaryBonusAtr = entity.get(0).breakAmountHisPk.categoryAtr;
            return Optional.of(new BreakdownAmountHis(cid, categoryAtr, itemNameCd, employeeId, period, salaryBonusAtr));
        } else {
            return Optional.empty();
        }
    }


    @Override
    public List<BreakdownAmountHis> getAllBreakdownAmountHis() {
        return null;
    }

    @Override
    public Optional<BreakdownAmountHis> getBreakdownAmountHisBySalaryBonusAtr(String cid,int categoryAtr, String itemNameCd, String employeeId, int salaryBonusAtr) {
        if (salaryBonusAtr == 0) {
            return this.toDomain(this.queryProxy().query(SELECT_BY_KEY_STRING_SALARY_BONUS_ATR, QpbmtBreakAmountHis.class)
                    .setParameter("cid", cid)
                    .setParameter("categoryAtr", categoryAtr)
                    .setParameter("itemNameCd", itemNameCd)
                    .setParameter("employeeId", employeeId)
                    .setParameter("salaryBonusAtr", 0).getList());
        } else {
            return this.toDomain(this.queryProxy().query(SELECT_BY_KEY_STRING_SALARY_BONUS_ATR, QpbmtBreakAmountHis.class)
                    .setParameter("cid", cid)
                    .setParameter("categoryAtr", categoryAtr)
                    .setParameter("itemNameCd", itemNameCd)
                    .setParameter("employeeId", employeeId)
                    .setParameter("salaryBonusAtr", 1).getList());
        }
    }

    @Override
    public Optional<BreakdownAmountHis> getBreakdownAmountHisById() {
        return Optional.empty();
    }

    @Override
    public void add(BreakdownAmountHis domain) {
        this.commandProxy().insertAll(QpbmtBreakAmountHis.toEntity(domain));
    }

    @Override
    public void update(BreakdownAmountHis domain) {
        this.commandProxy().updateAll(QpbmtBreakAmountHis.toEntity(domain));
    }

    @Override
    public void updateByHistoryId(String cid, int categoryAtr, String itemNameCd, String employeeId, int salaryBonusAtr, String lastHistoryId) {
        this.getEntityManager().createQuery(UPDATE_BY_HISTORYID)
                .setParameter("cid", cid)
                .setParameter("categoryAtr", categoryAtr)
                .setParameter("itemNameCd", itemNameCd)
                .setParameter("employeeId", employeeId)
                .setParameter("salaryBonusAtr", salaryBonusAtr)
                .setParameter("lastHistoryId", lastHistoryId).executeUpdate();
    }

    @Override
    public void updateByLastHistoryId(String cid, int categoryAtr, String itemNameCd, String employeeId, int salaryBonusAtr, String lastHistoryId, int startYearMonth) {
        this.getEntityManager().createQuery(UPDATE_BY_LASTHISTORYID)
                .setParameter("cid", cid)
                .setParameter("categoryAtr", categoryAtr)
                .setParameter("itemNameCd", itemNameCd)
                .setParameter("employeeId", employeeId)
                .setParameter("salaryBonusAtr", salaryBonusAtr)
                .setParameter("lastHistoryId", lastHistoryId)
                .setParameter("startYearMonth", startYearMonth).executeUpdate();
    }


    @Override
    public void remove(String cid,int categoryAtr, String itemNameCd, String employeeId, int salaryBonusAtr, String historyId) {
        this.getEntityManager().createQuery(DELETE_BY_HISTORYID)
                .setParameter("cid", cid)
                .setParameter("categoryAtr", categoryAtr)
                .setParameter("itemNameCd", itemNameCd)
                .setParameter("employeeId", employeeId)
                .setParameter("salaryBonusAtr", salaryBonusAtr)
                .setParameter("historyId", historyId).executeUpdate();
    }
}
