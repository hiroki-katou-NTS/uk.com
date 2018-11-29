package nts.uk.ctx.pr.core.infra.repository.wageprovision.breakdownitemamount;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount.BreakdownAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount.BreakdownAmountList;
import nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount.BreakdownAmountRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.breakdownitemamount.QpbmtBreakAmount;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.breakdownitemamount.QpbmtBreakAmountPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaBreakAmountRepository extends JpaRepository implements BreakdownAmountRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtBreakAmount f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.breakAmountPk.historyId =:historyId AND  f.breakAmountPk.breakdownItemCode =:breakdownItemCode";
    private static final String SELECT_LIST_BY_HISTORYID = SELECT_ALL_QUERY_STRING + " WHERE f.breakAmountPk.historyId =:historyId";
    private static final String REMOVE_LIST_BY_HISTORYID = "DELETE FROM QpbmtBreakAmount f WHERE f.breakAmountPk.historyId =:historyId";


    private Optional<BreakdownAmount> toDomain(List<QpbmtBreakAmount> entity) {
        if (entity.size() > 0) {
            String historyId = entity.get(0).breakAmountPk.historyId;
            List<BreakdownAmountList> breakdownAmountList = entity.stream()
                    .map(item -> new BreakdownAmountList(item.breakAmountPk.breakdownItemCode, item.amount))
                    .collect(Collectors.toList());
            return Optional.of(new BreakdownAmount(historyId, breakdownAmountList));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<BreakdownAmount> getAllBreakdownAmount() {
        return null;
    }

    @Override
    public Optional<BreakdownAmount> getAllBreakdownAmountCode(String historyId) {
        return toDomain(queryProxy().query(SELECT_LIST_BY_HISTORYID, QpbmtBreakAmount.class).setParameter("historyId", historyId).getList());
    }

    @Override
    public void add(BreakdownAmount domain) {
        this.commandProxy().insertAll(QpbmtBreakAmount.toEntity(domain));
    }

    @Override
    public void update(BreakdownAmount domain) {
        this.commandProxy().updateAll(QpbmtBreakAmount.toEntity(domain));
    }

    @Override
    public void remove(String historyId, String breakdownItemCode) {
        this.commandProxy().remove(QpbmtBreakAmount.class, new QpbmtBreakAmountPk(historyId, breakdownItemCode));
    }

    @Override
    public void removeByHistoryId(String historyId) {
        this.getEntityManager().createQuery(REMOVE_LIST_BY_HISTORYID).setParameter("historyId", historyId).executeUpdate();
    }
}
