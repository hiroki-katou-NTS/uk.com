package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementitem;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemName;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemNameRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.QpbmtSpecItemName;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.QpbmtSpecItemNamePk;

@Stateless
public class JpaStatementItemNameRepository extends JpaRepository implements StatementItemNameRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSpecItemName f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.specItemNamePk.cid =:cid AND  f.specItemNamePk.salaryItemId =:salaryItemId ";

    @Override
    public List<StatementItemName> getAllStatementItemName(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSpecItemName.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<StatementItemName> getStatementItemNameById(String cid, String salaryItemId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSpecItemName.class)
        .setParameter("cid", cid)
        .setParameter("salaryItemId", salaryItemId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(StatementItemName domain){
        this.commandProxy().insert(QpbmtSpecItemName.toEntity(domain));
    }

    @Override
    public void update(StatementItemName domain){
        this.commandProxy().update(QpbmtSpecItemName.toEntity(domain));
    }

    @Override
    public void remove(String cid, String salaryItemId){
        this.commandProxy().remove(QpbmtSpecItemName.class, new QpbmtSpecItemNamePk(cid, salaryItemId)); 
    }
}
