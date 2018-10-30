package nts.uk.ctx.pr.core.infra.repository.wageprovision.speclayout.itemrangeset;


import javax.ejb.Stateless;

import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.itemrangeset.StatementItemRangeSettingRepository;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.itemrangeset.StatementItemRangeSetting;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.itemrangeset.QpbmtStateItemRangeSet;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.itemrangeset.QpbmtStateItemRangeSetPk;

import java.util.List;
import java.util.Optional;

@Stateless
public class JpaStatementItemRangeSettingRepository extends JpaRepository implements StatementItemRangeSettingRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStateItemRangeSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stateItemRangeSetPk.histId =:histId ";

    @Override
    public List<StatementItemRangeSetting> getAllStatementItemRangeSetting(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtStateItemRangeSet.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<StatementItemRangeSetting> getStatementItemRangeSettingById(String histId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStateItemRangeSet.class)
        .setParameter("histId", histId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(StatementItemRangeSetting domain){
        this.commandProxy().insert(QpbmtStateItemRangeSet.toEntity(domain));
    }

    @Override
    public void update(StatementItemRangeSetting domain){
        this.commandProxy().update(QpbmtStateItemRangeSet.toEntity(domain));
    }

    @Override
    public void remove( String histId){
        this.commandProxy().remove(QpbmtStateItemRangeSet.class, new QpbmtStateItemRangeSetPk(histId));
    }
}
