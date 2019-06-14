package nts.uk.ctx.pr.core.infra.repository.wageprovision.averagewagecalculationset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset.AverageWageCalculationSet;
import nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset.AverageWageCalculationSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset.StatementCustom;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.averagewagecalculationset.QpbmtAverageWage;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.averagewagecalculationset.QpbmtAverageWagePk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaAverageWageCalculationSetRepository extends JpaRepository implements AverageWageCalculationSetRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtAverageWage f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.averageWagePk.cid =:cid ";


    private static final String SELECT_CUSTOM_BY_PAYMENT_ITEM =
            "SELECT a.statementItemPk.categoryAtr, a.statementItemPk.itemNameCd, d.name "
                    + " FROM QpbmtStatementItem a INNER JOIN QpbmtPaymentItemSt b "
                    + " ON a.statementItemPk.cid = b.paymentItemStPk.cid "
                    + " AND a.statementItemPk.categoryAtr = b.paymentItemStPk.categoryAtr "
                    + " AND a.statementItemPk.itemNameCd = b.paymentItemStPk.itemNameCd "
                    + " INNER JOIN QpbmtStatementItemName d"
                    + " ON a.statementItemPk.cid = d.statementItemNamePk.cid "
                    + " AND a.statementItemPk.categoryAtr = d.statementItemNamePk.categoryAtr "
                    + " AND a.statementItemPk.itemNameCd = d.statementItemNamePk.itemNameCd "
                    + " WHERE  a.statementItemPk.cid =:cid "
                    + " AND b.averageWageAtr = 1"
                    + " AND a.statementItemPk.categoryAtr = 0 "
                    + " AND a.deprecatedAtr = 0 ORDER BY a.statementItemPk.itemNameCd";
    private static final String SELECT_CUSTOM_BY_ATTENDANCE_ITEM =
            "SELECT a.statementItemPk.categoryAtr, a.statementItemPk.itemNameCd, d.name "
                    + " FROM QpbmtStatementItem a INNER JOIN QpbmtTimeItemSt c"
                    + " ON a.statementItemPk.cid = c.timeItemStPk.cid "
                    + " AND a.statementItemPk.categoryAtr = c.timeItemStPk.categoryAtr "
                    + " AND a.statementItemPk.itemNameCd = c.timeItemStPk.itemNameCd "
                    + " INNER JOIN QpbmtStatementItemName d"
                    + " ON a.statementItemPk.cid = d.statementItemNamePk.cid "
                    + " AND a.statementItemPk.categoryAtr = d.statementItemNamePk.categoryAtr "
                    + " AND a.statementItemPk.itemNameCd = d.statementItemNamePk.itemNameCd "
                    + " WHERE  a.statementItemPk.cid =:cid "
                    + " AND c.averageWageAtr = 1"
                    + " AND c.timeCountAtr = 1 "
                    + " AND a.statementItemPk.categoryAtr = 2 " +
                    " AND a.deprecatedAtr = 0 ORDER BY a.statementItemPk.itemNameCd";
    private static final String SELECT_ALL_CUSTOM_PAYMENT_ITEM = "SELECT a.statementItemPk.categoryAtr, a.statementItemPk.itemNameCd, d.name "
            + " FROM QpbmtStatementItem a INNER JOIN QpbmtPaymentItemSt b "
            + " ON a.statementItemPk.cid = b.paymentItemStPk.cid "
            + " AND a.statementItemPk.categoryAtr = b.paymentItemStPk.categoryAtr "
            + " AND a.statementItemPk.itemNameCd = b.paymentItemStPk.itemNameCd "
            + " INNER JOIN QpbmtStatementItemName d "
            + " ON a.statementItemPk.cid = d.statementItemNamePk.cid "
            + " AND a.statementItemPk.categoryAtr = d.statementItemNamePk.categoryAtr "
            + " AND a.statementItemPk.itemNameCd = d.statementItemNamePk.itemNameCd "
            + " WHERE  a.statementItemPk.cid =:cid "
            + " AND a.statementItemPk.categoryAtr = 0 AND a.deprecatedAtr = 0"
            + " ORDER BY a.statementItemPk.itemNameCd";
    private static final String SELECT_ALL_CUSTOM_ATTENDANCE_ITEM = "SELECT a.statementItemPk.categoryAtr, a.statementItemPk.itemNameCd, d.name "
            + " FROM QpbmtStatementItem a INNER JOIN QpbmtTimeItemSt c "
            + " ON a.statementItemPk.cid = c.timeItemStPk.cid "
            + " AND a.statementItemPk.categoryAtr = c.timeItemStPk.categoryAtr "
            + " AND a.statementItemPk.itemNameCd = c.timeItemStPk.itemNameCd "
            + " INNER JOIN QpbmtStatementItemName d "
            + " ON a.statementItemPk.cid = d.statementItemNamePk.cid "
            + " AND a.statementItemPk.categoryAtr = d.statementItemNamePk.categoryAtr "
            + " AND a.statementItemPk.itemNameCd = d.statementItemNamePk.itemNameCd "
            + " WHERE  a.statementItemPk.cid =:cid "
            + " AND a.statementItemPk.categoryAtr = 2 "
            + " AND c.timeCountAtr = 1 AND a.deprecatedAtr = 0"
            + " ORDER BY a.statementItemPk.itemNameCd";

    @Override
    public List<AverageWageCalculationSet> getAllAverageWageCalculationSet() {
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtAverageWage.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public List<StatementCustom> getStatemetPaymentItem(String cid) {
        return this.queryProxy().query(SELECT_CUSTOM_BY_PAYMENT_ITEM, Object[].class).setParameter("cid", cid).getList(
                item -> new StatementCustom(item[0] != null ? String.valueOf(item[0]) : "", item[1] != null ? String.valueOf(item[1]) : "",
                        item[2] != null ? String.valueOf(item[2]) : "")
        );
    }

    @Override
    public List<StatementCustom> getStatemetAttendanceItem(String cid) {
        return this.queryProxy().query(SELECT_CUSTOM_BY_ATTENDANCE_ITEM, Object[].class).setParameter("cid", cid).getList(
                item -> new StatementCustom(item[0] != null ? String.valueOf(item[0]) : "", item[1] != null ? String.valueOf(item[1]) : "",
                        item[2] != null ? String.valueOf(item[2]) : "")
        );
    }

    @Override
    public List<StatementCustom> getAllStatemetPaymentItem(String cid) {
        return this.queryProxy().query(SELECT_ALL_CUSTOM_PAYMENT_ITEM, Object[].class).setParameter("cid", cid).getList(
                item -> new StatementCustom(item[0] != null ? String.valueOf(item[0]) : "", item[1] != null ? String.valueOf(item[1]) : "",
                        item[2] != null ? String.valueOf(item[2]) : "")
        );
    }

    @Override
    public List<StatementCustom> getAllStatemetAttendanceItem(String cid) {
        return this.queryProxy().query(SELECT_ALL_CUSTOM_ATTENDANCE_ITEM, Object[].class).setParameter("cid", cid).getList(
                item -> new StatementCustom(item[0] != null ? String.valueOf(item[0]) : "", item[1] != null ? String.valueOf(item[1]) : "",
                        item[2] != null ? String.valueOf(item[2]) : "")
        );
    }

    @Override
    public Optional<AverageWageCalculationSet> getAverageWageCalculationSetById(String cid) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtAverageWage.class)
                .setParameter("cid", cid)
                .getSingle(c -> c.toDomain());
    }

    @Override
    public void add(AverageWageCalculationSet domain) {
        this.commandProxy().insert(QpbmtAverageWage.toEntity(domain));
    }

    @Override
    public void update(AverageWageCalculationSet domain) {
        this.commandProxy().update(QpbmtAverageWage.toEntity(domain));
    }

    @Override
    public void remove(String cid) {
        this.commandProxy().remove(QpbmtAverageWage.class, new QpbmtAverageWagePk(cid));
    }
}
