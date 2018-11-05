package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementlayout;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.QpbmtStatementLayout;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.QpbmtStatementLayoutPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStatementLayoutRepository extends JpaRepository implements StatementLayoutRepository{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStatementLayout f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.statementLayoutPk.cid =:cid AND  f.statementLayoutPk.statementCd =:statementCd ";
    private static final String SELECT_BY_KEY_CID_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.statementLayoutPk.cid =:cid ";
    private static final String SELECT_SPEC_NAME = "SELECT e.specCode, e.specName FROM QpbmtStatementLayoutHist f INNER JOIN QpbmtStatementLayout e on e.statementLayoutPk.statementCd = f.statementLayoutPk.statementCd" +
            " Where f.startYearMonth > :startYearMonth AND f.statementLayoutHistPk.cid = :cid";
    @Override
    public List<StatementLayout> getStatementCode(String cid, int startYearMonth) {
        return  this.queryProxy().query(SELECT_SPEC_NAME, QpbmtStatementLayout.class)
                .setParameter("startYearMonth", startYearMonth)
                .setParameter("cid", cid)
                .getList().stream().map(i->i.toDomain()).collect(Collectors.toList());
    }

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
    public List<StatementLayout> getStatementLayoutByCId(String cid) {
        return this.queryProxy().query(SELECT_BY_KEY_CID_STRING, QpbmtStatementLayout.class)
                .setParameter("cid", cid)
                .getList(item -> item.toDomain());
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
