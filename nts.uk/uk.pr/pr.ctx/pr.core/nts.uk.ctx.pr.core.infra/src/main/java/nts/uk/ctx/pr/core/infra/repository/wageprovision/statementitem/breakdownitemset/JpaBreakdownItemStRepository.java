package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementitem.breakdownitemset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSetCustom;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSetRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.breakdownitemset.QpbmtBreakdownItemSt;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.breakdownitemset.QpbmtBreakdownItemStPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaBreakdownItemStRepository extends JpaRepository implements BreakdownItemSetRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtBreakdownItemSt f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
            + " WHERE  f.breakdownItemStPk.cid =:cid AND " + " f.breakdownItemStPk.categoryAtr =:categoryAtr AND "
            + " f.breakdownItemStPk.itemNameCd =:itemNameCd AND "
            + " f.breakdownItemStPk.breakdownItemCode =:breakdownItemCode ";
    private static final String SELECT_BY_ID = SELECT_ALL_QUERY_STRING
            + " WHERE  f.breakdownItemStPk.cid =:cid AND " + " f.breakdownItemStPk.categoryAtr =:categoryAtr AND "
            + " f.breakdownItemStPk.itemNameCd =:itemNameCd";
    private static final String SELECT_BY_HIS =
            "SELECT f.breakAmountPk.historyId, a.breakdownItemStPk.breakdownItemCode, a.breakdownItemName " +
                    "FROM QpbmtBreakAmount f INNER JOIN QpbmtBreakdownItemSt a "
                    + " WHERE a.breakdownItemStPk.breakdownItemCode IN :lstBreakdownItemCode";

    @Override
    public List<BreakdownItemSet> getAllBreakdownItemSt() {
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtBreakdownItemSt.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public List<BreakdownItemSet> getBreakdownItemStByStatementItemId(String cid, int categoryAtr, String itemNameCd) {
        return this.queryProxy().query(SELECT_BY_ID, QpbmtBreakdownItemSt.class)
                .setParameter("cid", cid).setParameter("categoryAtr", categoryAtr)
                .setParameter("itemNameCd", itemNameCd).getList(i -> i.toDomain());
    }

    @Override
    public Optional<BreakdownItemSet> getBreakdownItemStById(String cid, int categoryAtr, String itemNameCd, String breakdownItemCode) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtBreakdownItemSt.class)
                .setParameter("cid", cid).setParameter("categoryAtr", categoryAtr)
                .setParameter("itemNameCd", itemNameCd).setParameter("breakdownItemCode", breakdownItemCode)
                .getSingle(c -> c.toDomain());
    }

    @Override
    public List<BreakdownItemSetCustom> getBreakdownItemByHistory(List<String> lstBreakdownItemCode) {
        List<BreakdownItemSetCustom> result = this.queryProxy().query(SELECT_BY_HIS, Object[].class).setParameter("lstBreakdownItemCode", lstBreakdownItemCode)
                .getList(item -> new BreakdownItemSetCustom(item[0] != null ? String.valueOf(item[0]) : "", item[1] != null ? String.valueOf(item[1]) : "",
                        item[2] != null ? String.valueOf(item[2]) : ""));

        return result;
    }

    @Override
    public void add(BreakdownItemSet domain) {
        this.commandProxy().insert(QpbmtBreakdownItemSt.toEntity(domain));
    }

    @Override
    public void update(BreakdownItemSet domain) {
        this.commandProxy().update(QpbmtBreakdownItemSt.toEntity(domain));
    }

    @Override
    public void remove(String cid, int categoryAtr, String itemNameCd, String breakdownItemCode) {
        if (this.getBreakdownItemStById(cid, categoryAtr, itemNameCd, breakdownItemCode).isPresent()) {
            this.commandProxy().remove(QpbmtBreakdownItemSt.class,
                    new QpbmtBreakdownItemStPk(cid, categoryAtr, itemNameCd, breakdownItemCode));
        }
    }

    @Override
    public void removeAll(String cid, int categoryAtr, String itemNameCd) {
        List<BreakdownItemSet> entities = this.getBreakdownItemStByStatementItemId(cid, categoryAtr, itemNameCd);
        entities.forEach(entity -> this.remove(entity.getCid(), entity.getCategoryAtr().value, entity.getItemNameCd().v(), entity.getBreakdownItemCode().v()));

    }

}
