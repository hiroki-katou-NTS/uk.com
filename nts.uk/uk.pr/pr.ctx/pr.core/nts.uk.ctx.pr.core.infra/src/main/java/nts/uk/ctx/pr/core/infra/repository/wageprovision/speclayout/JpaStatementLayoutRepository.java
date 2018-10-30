package nts.uk.ctx.pr.core.infra.repository.wageprovision.speclayout;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.QpbmtStatementLayout;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.QpbmtStatementLayoutPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaStatementLayoutRepository extends JpaRepository implements StatementLayoutRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStatementLayout f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.statementLayoutPk.cid =:cid AND  f.statementLayoutPk.statementCd =:statementCd ";

    @Override
    public List<StatementLayout> getAllStatementLayout(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtStatementLayout.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<StatementLayout> getStatementLayoutById(String cid, String statementCd){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStatementLayout.class)
        .setParameter("cid", cid)
        .setParameter("statementCd", statementCd)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(StatementLayout domain){
        this.commandProxy().insert(QpbmtStatementLayout.toEntity(domain));
    }

    @Override
    public void update(StatementLayout domain){
        this.commandProxy().update(QpbmtStatementLayout.toEntity(domain));
    }

    @Override
    public void remove(String cid, String statementCd){
        this.commandProxy().remove(QpbmtStatementLayout.class, new QpbmtStatementLayoutPk(cid, statementCd));
    }
}
